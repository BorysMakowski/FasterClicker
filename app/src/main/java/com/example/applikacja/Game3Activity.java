package com.example.applikacja;

import static java.sql.Types.NULL;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.applikacja.ui.home.HomeViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

public class Game3Activity extends AppCompatActivity {

    private HomeViewModel homeViewModel;
    long startTime;
    long difference;
    int Min = 1000;
    int Max = 7000;
    boolean end = false;
    int randNum;
    int color;
    float dx, dy;
    boolean blueClicked = false;
    boolean greenClicked = false;
    String userEmail;
    String userName;
    int finscore = 0;
    int speed = 300;
    int streak = 0;
    Vector<Integer> scores = new Vector<Integer>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game3);
        final DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        userName = auth.getCurrentUser().getDisplayName();
        userEmail = auth.getCurrentUser().getEmail();
        Log.d("Email", userEmail);


        Handler handler = new Handler();

        final Button startButton = findViewById(R.id.startButton);
        final Button startButton2 = findViewById(R.id.startButton2);
        final Button gameButtonGreen = findViewById(R.id.gameButtonGreen);
        final Button gameButtonRed = findViewById(R.id.gameButtonRed);
        final Button gameButtonBlue = findViewById(R.id.gameButtonBlue);
        final TextView scoreTextView = findViewById(R.id.textView16);

        startButton2.setVisibility(View.INVISIBLE);
        startButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blueClicked = false;
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
                for (int i = 1; i < 100; i++) {
                    int finalI = i;
                    handler.postDelayed(new Runnable() {
                        public void run() {


                            gameButtonBlue.setVisibility(View.VISIBLE);
                            randNum = Min + (int) (Math.random() * ((Max - Min) + 1));
                            color = (int) (Math.random() * ((100) + 1));

                            Random R = new Random();
                            dx = R.nextFloat() * displaymetrics.widthPixels;
                            dy = R.nextFloat() * displaymetrics.heightPixels;

                            if (dx > displaymetrics.widthPixels * 0.8) {
                                dx -= 200;
                            }
                            if (dx < displaymetrics.widthPixels * 0.2) {
                                dx += 200;
                            }
                            if (dy > displaymetrics.heightPixels * 0.8) {
                                dy -= 200;
                            }
                            if (dy < displaymetrics.heightPixels * 0.2) {
                                dy += 200;
                            }
                            if (!blueClicked) {
                                randNum = Min + (int) (Math.random() * ((Max - Min) + 1));


                                gameButtonGreen.animate()
                                        .x(dx)
                                        .y(dy)
                                        .setDuration(speed)
                                        .start();

                                gameButtonBlue.setVisibility(View.INVISIBLE);
                                gameButtonGreen.setVisibility(View.VISIBLE);
                                startTime = System.currentTimeMillis();


                            }
                        }
                    }, randNum + i * 500);
                }


            }

        });

        gameButtonBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                blueClicked = true;

      //          startButton2.setVisibility(View.VISIBLE);
                streak = 0;



           /*     db.collection("scores")
                        .add(score)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error adding document", e);
                            }
                        });*/
            }
        });

        gameButtonGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameButtonGreen.setVisibility(View.INVISIBLE);
                difference = System.currentTimeMillis() - startTime;
                streak++;
                if (streak > 5 && streak < 10)
                    speed = 500;
                else if (streak > 10)
                    speed = 700;

                Log.d("STREAK AAAAAAAAAAAAAAAAAAA", String.valueOf(streak));
                //    startButton2.setText(String.valueOf(difference) + " ms");
                //scores.add((int)difference);
                finscore += difference;
                scoreTextView.setText(String.valueOf(finscore));
              /*  Map<String, Object> score = new HashMap<>();
                score.put("user", userEmail);
                score.put("userName", userName);
                score.put("score", difference);
                score.put("buttonClicked", "green");
                score.put("date", Calendar.getInstance().getTime());



                db.collection("scores")
                        .add(score)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error adding document", e);
                            }
                        });*/
            }
        });

        gameButtonRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameButtonRed.setVisibility(View.INVISIBLE);
                //  startButton2.setText("Don't click on red!");
                //    startButton2.setVisibility(View.VISIBLE);
                finscore -= 500;
                scoreTextView.setText(String.valueOf(finscore));
             /*   Map<String, Object> score = new HashMap<>();
                score.put("user", userEmail);
                score.put("userName", userName);
                score.put("score", NULL);
                score.put("buttonClicked", "red");
                score.put("date", Calendar.getInstance().getTime());



                db.collection("scores")
                        .add(score)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error adding document", e);
                            }
                        });*/
            }
        });
    }

    }