package com.example.quizzifyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ResultsToTeacher extends AppCompatActivity {
    private ListView listView;
    private TextView msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_to_teacher);

        listView = findViewById(R.id.StudentScores);
        msg = findViewById(R.id.msg);
        Intent i = getIntent();
        String QID = i.getStringExtra("quizid");
        String RID= i.getStringExtra("roomid");
        DatabaseReference db=FirebaseDatabase.getInstance().getReference();
        Query query = FirebaseDatabase.getInstance().getReference("Score").orderByChild("roomid").equalTo(RID);
        query.addListenerForSingleValueEvent(valueEventListener);
    }


//    @Override
//    public void onDataChange(@NonNull DataSnapshot snapshot) {
//        if(snapshot.exists()){
//            ArrayList<String> scores=new ArrayList<>();
//            for(DataSnapshot snapshot1 : snapshot.getChildren()){
//                Score score = snapshot1.getValue(Score.class);
//                Toast.makeText(ResultsToTeacher.this, score.userid, Toast.LENGTH_SHORT).show();
//                if(!scores.contains(score.userid)){
//                    scores.add(score.userid);
////                            Toast.makeText(ResultsToTeacher.this, x.userid, Toast.LENGTH_SHORT).show();
//                }
//            }
//            ArrayAdapter scoreadapter=new ArrayAdapter(ResultsToTeacher.this, android.R.layout.simple_list_item_1,scores);
//            listView.setAdapter(scoreadapter);
//        }
//        else{
//            Toast.makeText(ResultsToTeacher.this, "Not Available", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    @Override
//    public void onCancelled(@NonNull DatabaseError error) {
//
//    }
//});
    ValueEventListener valueEventListener=new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
        if(snapshot.exists()){
        ArrayList<String> scores=new ArrayList<>();
        for(DataSnapshot snapshot1 : snapshot.getChildren()){
            Score score = snapshot1.getValue(Score.class);
            String Entry=score.userid+"   ("+score.marksobtained+"/"+score.totalmarks+")";
            if(!scores.contains(Entry)){
                scores.add(Entry);
            }
        }
            Collections.sort(scores);
        ArrayAdapter<String> scoreadapter=new ArrayAdapter<>(ResultsToTeacher.this, android.R.layout.simple_list_item_1,scores);
        listView.setAdapter(scoreadapter);
        }
        else{
        Toast.makeText(ResultsToTeacher.this, "Not Available", Toast.LENGTH_SHORT).show();
        }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };
}