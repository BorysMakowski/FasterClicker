package com.example.applikacja.ui.notifications;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.applikacja.MainActivity;
import com.example.applikacja.R;
import com.example.applikacja.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;

import static java.util.Objects.isNull;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        final TextView textView = root.findViewById(R.id.textViewEmail);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        textView.setText("Hello, " + auth.getCurrentUser().getDisplayName());

        final TextView textViewBestScore = root.findViewById(R.id.textViewBestScore);
        final TextView textViewRed = root.findViewById(R.id.textViewRed);
        final TextView textViewGreen = root.findViewById(R.id.textViewGreen);
        final TextView textViewBlue = root.findViewById(R.id.textViewBlue);
        final TextView textViewMean = root.findViewById(R.id.textViewMean);




        LinkedList<QueryDocumentSnapshot> scores = new LinkedList<QueryDocumentSnapshot>();
        db.collection("scores")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (!isNull(document.get("score")) && Integer.valueOf(String.valueOf(document.get("score"))) != 0)
                                    if(String.valueOf(document.get("user")).equals(String.valueOf(auth.getCurrentUser().getEmail())))
                                        scores.add(document);
                                Log.d("Success", document.getId() + " => " + document.getData());

                            }

                            Collections.sort(scores, new Comparator<QueryDocumentSnapshot>() {
                                @Override
                                public int compare(QueryDocumentSnapshot t0, QueryDocumentSnapshot t1) {
                                    return Integer.valueOf(String.valueOf(t1.getData().get("score"))) - Integer.valueOf(String.valueOf(t0.getData().get("score")));
                                }

                            });
                            for(int i=0; i<scores.size(); i++){
                                Log.d("Success", String.valueOf(scores.get(i).get("user")) + " => " + String.valueOf(auth.getCurrentUser().getEmail()));
                                if(String.valueOf(scores.get(i).get("user")).equals(String.valueOf(auth.getCurrentUser().getEmail())))
                                    textViewBestScore.setText("Your fastest click: " + scores.get(i).get("score") + " ms");
                                else
                                    textViewBestScore.setText("Click faster!");

                            }


                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if(String.valueOf(document.get("user")).equals(String.valueOf(auth.getCurrentUser().getEmail())))
                                    if (Integer.valueOf(String.valueOf(document.get("score"))) == 0)
                                        scores.add(document);
                                Log.d("Success", document.getId() + " => " + document.getData());

                            }
                            int timesBlue = 0;
                            int timesGreen = 0;
                            int timesRed = 0;
                            int sum = 0;
                            int divider = 0;
                            double mean = 0.0;
                            for (int i=0; i<scores.size(); i++)
                            {
                                if (Integer.parseInt(String.valueOf(scores.get(i).get("score"))) != 0)
                                {
                                    sum+=Integer.parseInt(String.valueOf(scores.get(i).get("score")));
                                    divider++;
                                }
                                if (String.valueOf(scores.get(i).get("buttonClicked")).equals("blue"))
                                    timesBlue++;
                                if (String.valueOf(scores.get(i).get("buttonClicked")).equals("green"))
                                    timesGreen++;
                                if (String.valueOf(scores.get(i).get("buttonClicked")).equals("red"))
                                    timesRed++;
                            }
                            if (divider !=0)
                            mean = sum/divider;

                            textViewRed.setText("Red clicked " + timesRed + " times");
                            textViewGreen.setText("Green clicked " + timesGreen + " times");
                            textViewBlue.setText("Blue clicked " + timesBlue + " times");
                            textViewMean.setText("Mean click time: " + mean + " ms");
                        } else {
                            Log.w("Failure", "Error getting documents.", task.getException());
                        }
                    }
                });


        Button logout = root.findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);

            }
        });
        return root;
    }
}