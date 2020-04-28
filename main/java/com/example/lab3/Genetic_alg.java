package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class Genetic_alg extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genetic_alg);
        Button calc = (Button) (findViewById(R.id.calc));
        calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = ((TextView) findViewById(R.id.coef)).getText().toString();
                int y = Integer.parseInt(((TextView) findViewById(R.id.y)).getText().toString());
                int n = Integer.parseInt(((TextView) findViewById(R.id.n)).getText().toString());
                str = str.replace(" ", "");
                String[] strCoef = str.split(",");
                int[] x = new int[strCoef.length];
                for (int i = 0; i < strCoef.length; i++) {
                    x[i] = Integer.parseInt(strCoef[i]);
                }
                int[] roots = genetic(x, y, n);
                String res = "Цілі корені:";
                for (int i = 0; i < roots.length-1; i++) {
                    res += roots[i] + ", ";
                }
                res += "\nПеревірка:\n";
                y = roots[0]*x[0];
                res+=x[0] + "*" + roots[0];
                for (int i = 1; i < roots.length-1; i++) {
                    y += roots[i] * x[i];
                    res += "+" + x[i] + "*" + roots[i];
                }
                res+="="+y;
                res+="\nОптимальний відсток мутації:" + roots[roots.length-1];
                TextView resView=(TextView)findViewById(R.id.res);
                resView.setText(res);
            }
        });
    }

    @Override
    public void onBackPressed() {
        try {
            Intent intent = new Intent(Genetic_alg.this, MainActivity.class);
            startActivity(intent);
            finish();
        } catch (Exception e) {
        }
        ;
    }

    public static int[] genetic(int[] x, int y, int n) {
        Random r = new Random();
        int[][] population = new int[n][x.length];
        int[] roots = new int[x.length];
        int[] res=new int[x.length+1];
        int itter;
        int minItter = Integer.MAX_VALUE;
        int procent=0;
        boolean f;
        for (int k = 4; k <= x.length * n; k += 4) { //Кількість мутованих особин
            itter = 0;
            /* Generate start population*/
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < population[i].length; j++) {
                    population[i][j] = r.nextInt(y / 2);
                    //System.out.print(population[i][j] + " ");
                }
                //System.out.println();
            }
            f = true;
            //System.out.println();
            while (f) {
                itter++;
                /*Calculation delta*/
                int[] deltas = new int[population.length];
                for (int i = 0; i < deltas.length; i++) {
                    deltas[i] = delta(x, y, population[i]);
                    if (deltas[i] == 0) {
                        roots = population[i];
                        f = false;
                    }
                }

                if (f) {
                    /*Search parents*/
                    int[] min = {deltas[0], deltas[1]};
                    int[] par1 = population[0];
                    int[] par2 = population[1];
                    for (int i = 2; i < deltas.length; i++) {
                        if (deltas[i] < min[0]) {
                            min[0] = deltas[i];
                            par1 = population[i];
                        }
                        if (deltas[i] != min[0] && deltas[i] < min[1]) {
                            min[1] = deltas[i];
                            par2 = population[i];
                        }
                    }

                    /*Сrossover */
                    int[][] child = new int[2][x.length];

                    for (int j = 0; j < x.length; j++) {
                        child[0] = par1;
                        child[1] = par2;
                        for (int i = j; i < x.length - 1; i++) {
                            int a = child[0][i];
                            child[0][i] = child[1][i];
                            child[1][i] = a;
                            if (delta(x, y, child[0]) == 0) {
                                roots = child[0];
                                f = false;
                            }
                            if (delta(x, y, child[1]) == 0) {
                                roots = child[1];
                                f = false;
                            }
                        }
                    }

                    /*Mutation*/
                    for (int j = 0; j < k; j++) {
                        population[r.nextInt(population.length - 1)][x.length - 1] = r.nextInt(y / 2); //random change k individual of population
                    }
                }
            }
            if (itter < minItter) {
                minItter = itter;
                procent = 100 * k / (n * x.length);
            }
        }
        for (int i = 0; i < roots.length; i++) {
            res[i]=roots[i];
        }
        res[res.length-1] = procent;
        return res;
    }

    public static int delta(int[] x, int y, int[] coef) {
        int delta = 0;
        for (int i = 0; i < x.length; i++) {
            delta += x[i] * coef[i];
        }
        delta = Math.abs(y - delta);
        return delta;
    }

}
