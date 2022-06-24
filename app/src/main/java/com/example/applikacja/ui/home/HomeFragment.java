package com.example.applikacja.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.applikacja.MainActivity;
import com.example.applikacja.R;
import com.example.applikacja.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

public class HomeFragment extends Fragment {

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

    Handler handler = new Handler();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
        new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final Button startButton = root.findViewById(R.id.startButton);
        final Button startButton2 = root.findViewById(R.id.startButton2);
        final Button gameButtonGreen = root.findViewById(R.id.gameButtonGreen);
        final Button gameButtonRed = root.findViewById(R.id.gameButtonRed);
        final Button gameButtonBlue = root.findViewById(R.id.gameButtonBlue);


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



        return root;
    }
}


