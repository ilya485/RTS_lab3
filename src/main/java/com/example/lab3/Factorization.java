package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Factorization extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factorization);

        Button calc = (Button) findViewById(R.id.Calc);

        calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView number = (TextView) findViewById(R.id.number);
                TextView res = (TextView) findViewById(R.id.res);
                res.setText(fact(Integer.parseInt(number.getText().toString())));
            }
        });
    }

    public String fact(int n) {
        int s;
        double y = 0;
        String res = "";
        s = (int) Math.ceil(Math.sqrt(n));
        while (true) {
            y = Math.sqrt(Math.pow((s), 2) - n);
            if (y % 1 == 0) {
                res = "x=" + (int) (s - y) + "\ny=" + (int) (s + y);
                return res;
            }
            s++;
        }
    }

    @Override
    public void onBackPressed() {
        try {
            Intent intent = new Intent(Factorization.this, MainActivity.class);
            startActivity(intent);
            finish();
        } catch (Exception e) {
        }

    }
}
