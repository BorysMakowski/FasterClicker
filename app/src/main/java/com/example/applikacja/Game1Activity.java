package com.example.applikacja;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.applikacja.ui.home.HomeViewModel;

import java.util.Vector;

public class Game1Activity extends AppCompatActivity {

    private HomeViewModel homeViewModel;
    long startTime;
    long difference;
    int Min = 1000;
    int Max = 7000;
    int randNum;
    int color;
    boolean blueClicked = false;
    boolean greenClicked = false;
    Vector<Integer> scores = new Vector<Integer>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game1);

       Handler handler = new Handler();

        final Button startButton = findViewById(R.id.startButton);
        final Button startButton2 = findViewById(R.id.startButton2);
        final Button gameButtonGreen = findViewById(R.id.gameButtonGreen);
        final Button gameButtonRed = findViewById(R.id.gameButtonRed);
        final Button gameButtonBlue = findViewById(R.id.gameButtonBlue);


            startButton2.setVisibility(View.INVISIBLE);
            startButton2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startButton.setVisibility(View.VISIBLE);
                    startButton2.setVisibility(View.INVISIBLE);
                    gameButtonGreen.setText("Click me!");
                }
            });


            startButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startButton.setVisibility(View.INVISIBLE);


                    gameButtonBlue.setVisibility(View.VISIBLE);
                    randNum = Min + (int) (Math.random() * ((Max - Min) + 1));
                    color = 0 + (int) (Math.random() * ((100 - 0) + 1));
                    Log.d("randnum", String.valueOf(randNum));
                    Log.d("color", String.valueOf(color));
                    handler.postDelayed(new Runnable() {
                        public void run(){
                            randNum = Min + (int) (Math.random() * ((Max - Min) + 1));

                            if (color > 30) {
                                Log.d("green", String.valueOf(randNum));
                                gameButtonBlue.setVisibility(View.INVISIBLE);
                                gameButtonGreen.setVisibility(View.VISIBLE);
                                startTime = System.currentTimeMillis();
                                handler.postDelayed(() -> {
                                    gameButtonGreen.setVisibility(View.INVISIBLE);
                                }, 2000);

                            } else {
                                Log.d("red", String.valueOf(randNum));
                                gameButtonBlue.setVisibility(View.INVISIBLE);
                                gameButtonRed.setVisibility(View.VISIBLE);

                                handler.postDelayed(() -> {
                                    gameButtonRed.setVisibility(View.INVISIBLE);
                                }, 1500);


                            }

                            startButton2.setVisibility(View.VISIBLE);
                        }
                    }, randNum);
                }
            });

            gameButtonBlue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    gameButtonBlue.setVisibility(View.INVISIBLE);
                    startButton2.setText("Too fast!");
                    startButton2.setVisibility(View.VISIBLE);
                }
            });

            gameButtonGreen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    gameButtonGreen.setVisibility(View.INVISIBLE);
                    difference = System.currentTimeMillis() - startTime;
                    startButton2.setText(String.valueOf(difference) + " ms");
                    scores.add((int)difference);
                }
            });

            gameButtonRed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    gameButtonRed.setVisibility(View.INVISIBLE);
                    startButton2.setText("Don't click on red!");
                    startButton2.setVisibility(View.VISIBLE);
                }
            });


        }
}