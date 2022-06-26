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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import static android.content.ContentValues.TAG;
import static java.sql.Types.NULL;

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
    String userEmail;
    String userName;
    Vector<Integer> scores = new Vector<Integer>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game1);

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
                    randNum = Min + (int) (Math.random() * ((Max - Min) + 1));
                    color = 0 + (int) (Math.random() * ((100 - 0) + 1));
                    Log.d("randnum", String.valueOf(randNum));
                    Log.d("color", String.valueOf(color));
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            if (!blueClicked){
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
                        }

                            startButton2.setVisibility(View.VISIBLE);
                        }
                    }, randNum);
                }
            });

            gameButtonBlue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    blueClicked = true;
                    gameButtonBlue.setVisibility(View.INVISIBLE);
                    startButton2.setText("Too fast!");
                    startButton2.setVisibility(View.VISIBLE);
                    Map<String, Object> score = new HashMap<>();
                    score.put("user", userEmail);
                    score.put("userName", userName);
                    score.put("score", NULL);
                    score.put("buttonClicked", "blue");
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
                            });
                }
            });

            gameButtonGreen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    gameButtonGreen.setVisibility(View.INVISIBLE);
                    difference = System.currentTimeMillis() - startTime;
                    startButton2.setText(String.valueOf(difference) + " ms");
                    //scores.add((int)difference);

                    Map<String, Object> score = new HashMap<>();
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
                            });
                }
            });

            gameButtonRed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    gameButtonRed.setVisibility(View.INVISIBLE);
                    startButton2.setText("Don't click on red!");
                    startButton2.setVisibility(View.VISIBLE);


                    Map<String, Object> score = new HashMap<>();
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
                            });
                }
            });


        }
}