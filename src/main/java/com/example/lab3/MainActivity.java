package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button factorization = (Button) findViewById(R.id.factorization);
        Button parceptron = (Button) findViewById(R.id.parceptron);
        Button gen_alg = (Button) findViewById(R.id.gen_alg);

        factorization.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(MainActivity.this, Factorization.class);
                    startActivity(intent);
                    finish();
                } catch (Exception e){} ;
            }
        });

        parceptron.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(MainActivity.this, Parceptron.class);
                    startActivity(intent);
                    finish();
                } catch (Exception e) {};
            }
        });

        gen_alg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(MainActivity.this, Genetic_alg.class);
                    startActivity(intent);
                    finish();
                } catch (Exception e) {};
            }
        });

    }
}
