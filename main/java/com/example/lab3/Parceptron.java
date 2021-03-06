package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class Parceptron extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Dialog pop_up;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parceptron);
        final int P = 4;
        final int[][] pointsArr = {{0, 6}, {1, 5}, {3, 3}, {2, 4}};

        TextView points = (TextView) findViewById(R.id.points);
        String arr = "P=" + P + "\n";
        for (int i = 0; i < pointsArr.length; i++) {
            arr += "(";
            arr += pointsArr[i][0] + "; ";
            arr += pointsArr[i][1] + "), ";
        }
        points.setText(arr);

        pop_up=new Dialog(this);


        Button calc = (Button) findViewById(R.id.button);
        calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView edTime = (TextView) findViewById(R.id.edTime);
                int t = Integer.parseInt(edTime.getText().toString());
                int iter = Integer.parseInt(((TextView) findViewById(R.id.edIter)).getText().toString());
                double sigma = Double.parseDouble(((TextView) findViewById(R.id.edSigma)).getText().toString());
                double[] result = parceptron(P, pointsArr, sigma, t, iter);
                String res;

                pop_up.requestWindowFeature(Window.FEATURE_NO_TITLE);
                pop_up.setContentView(R.layout.pop_up);
                pop_up.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                Button btnOk = (Button)pop_up.findViewById(R.id.ok);
                TextView pop_up_time = (TextView)pop_up.findViewById(R.id.pop_up_time);
                pop_up_time.setBackground(new ColorDrawable(Color.WHITE));

                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pop_up.dismiss();
                    }
                });
                pop_up_time.setText("Час виконання: " + (int)result[3] + "наносекунд");
                pop_up.show();


                res = "w1=" + result[0] + "\n";
                res += "w2=" + result[1] + "\n";
                res += "Кількість ітерацій: " + result[2]+"\n";
                res += "Час виканання: " + (int)result[3]+"\n";

                ((TextView) findViewById(R.id.res)).setText(res);
            }
        });



    }

    public static double[] parceptron(int P, int[][] points, double sigma, long time, int iter) {
        double delta;
        double y;
        double w1 = 0, w2 = 0;
        time*=1000000;

        int j = 0;
        long timeBefore = System.nanoTime();
        long timeAfter = 0;
        for (int k = 0; k < iter; k++) {
            {
                for (int i = 0; i < points.length; i++) {
                    j++;
                    y = points[i][0] * w1 + points[i][1] * w2;
                    if (y == Double.NEGATIVE_INFINITY || y == Double.POSITIVE_INFINITY) {
                        System.out.println();
                    }
                    delta = P - y;
                    w1 = w1 + delta * sigma * points[i][0];
                    w2 = w2 + delta * sigma * points[i][1];
                    timeAfter = System.nanoTime();
                    if (w1 == Double.NEGATIVE_INFINITY) {
                        System.out.println();
                    }
                    if (w1 != w1) {
                        double g = w1;
                    }
                    if ((w1 * points[0][0] + w2 * points[0][1] > P &&
                            w1 * points[1][0] + w2 * points[1][1] > P &&
                            w1 * points[2][0] + w2 * points[2][1] < P &&
                            w1 * points[3][0] + w2 * points[3][1] < P) ||
                            timeAfter - timeBefore > time) {
                        double[] arr = {w1, w2, j, (timeAfter - timeBefore)};
                        return arr;

                    }
                }
            }
        }
        double[] arr = {w1, w2, j, (timeAfter - timeBefore)};
        return arr;
    }

    @Override
    public void onBackPressed() {
        try {
            Intent intent = new Intent(Parceptron.this, MainActivity.class);
            startActivity(intent);
            finish();
        } catch (Exception e) {
        }
    }
}
