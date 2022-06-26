package com.example.applikacja.ui.dashboard;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.applikacja.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.isNull;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    public LinkedList<QueryDocumentSnapshot> scores;
    public LinkedList<TextView> board;

    public boolean compare(QueryDocumentSnapshot a, QueryDocumentSnapshot b){
        return Integer.parseInt(String.valueOf(a.getData().get("score"))) < Integer.parseInt(String.valueOf(b.getData().get("score")));
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        board = new LinkedList<TextView>();
        scores = new LinkedList<QueryDocumentSnapshot>();

        TextView t1 = root.findViewById(R.id.textView3);
        TextView t2 = root.findViewById(R.id.textView4);
        TextView t3 = root.findViewById(R.id.textView5);
        TextView t4 = root.findViewById(R.id.textView6);
        TextView t5 = root.findViewById(R.id.textView7);
        TextView t6 = root.findViewById(R.id.textView8);
        TextView t7 = root.findViewById(R.id.textView9);
        TextView t8 = root.findViewById(R.id.textView10);
        TextView t9 = root.findViewById(R.id.textView11);
        TextView t10 = root.findViewById(R.id.textView12);

        board.add(t1);
        board.add(t2);
        board.add(t3);
        board.add(t4);
        board.add(t5);
        board.add(t6);
        board.add(t7);
        board.add(t8);
        board.add(t9);
        board.add(t10);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("scores")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (!isNull(document.get("score")) && Integer.valueOf(String.valueOf(document.get("score"))) != 0)
                                {
                                    scores.add(document);
                                    Log.d("Success", document.getId() + " => " + document.getData());
                                }


                            }

                            Collections.sort(scores, new Comparator<QueryDocumentSnapshot>() {
                                @Override
                                public int compare(QueryDocumentSnapshot t0, QueryDocumentSnapshot t1) {
                                    return Integer.valueOf(String.valueOf(t0.getData().get("score"))) - Integer.valueOf(String.valueOf(t1.getData().get("score")));
                                }

                            });
                            for(int i=0; i<scores.size(); i++){
                                if(scores.get(i) != null)
                                    board.get(i).setText(String.valueOf(scores.get(i).getData().get("userName") + ": " + String.valueOf(scores.get(i).getData().get("score")) + " ms"));
                            }
                        } else {
                            Log.w("Failure", "Error getting documents.", task.getException());
                        }
                    }
                });


        return root;
    }
}