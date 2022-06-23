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
    int Min = 500;
    int Max = 4000;
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
        final Button gameButtonGreen = root.findViewById(R.id.gameButtonGreen);
        final Button gameButtonRed = root.findViewById(R.id.gameButtonRed);
        final Button gameButtonBlue = root.findViewById(R.id.gameButtonBlue);
        final TextView result = root.findViewById(R.id.result);




        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startButton.setVisibility(View.INVISIBLE);


  //////////////////////
                for (int i = 0; i < 5; i++) {
                    gameButtonBlue.setVisibility(View.VISIBLE);
                    blueClicked = false;
                    randNum = Min + (int) (Math.random() * ((Max - Min) + 1));
                    color = 0 + (int) (Math.random() * ((100 - 0) + 1));
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            if (!blueClicked)
                            {
                                gameButtonBlue.setVisibility(View.INVISIBLE);
                                if (color > 30) {
                                    gameButtonGreen.setVisibility(View.VISIBLE);
                                    startTime = System.currentTimeMillis();


                                    new Timer().schedule(new TimerTask() {
                                        @Override
                                        public void run() {
                                            if (greenClicked)
                                            {
                                                greenClicked = false;
                                            }
                                            else
                                            {
                                                result.setText("too slow");
                                            }
                                            gameButtonGreen.setVisibility(View.INVISIBLE);
                                        }
                                    }, 1000);


                                    //wait for click
                                } else
                                {
                                    gameButtonRed.setVisibility(View.VISIBLE);
                                    new Timer().schedule(new TimerTask() {
                                        @Override
                                        public void run() {
                                            gameButtonRed.setVisibility(View.INVISIBLE);
                                        }
                                    }, 1000);
                                }
                            }
                        }

                    }, randNum);
                }
///////////średni wynik





            }
        });

        gameButtonBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result.setText("too fast");
                blueClicked = true;
            }
        });

        gameButtonGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                difference = System.currentTimeMillis() - startTime;
                result.setText(String.valueOf(difference) + " ms");
                scores.add((int)difference);
            }
        });

        gameButtonRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result.setText("u not supposed to click that");
            }
        });



        return root;
    }
}


