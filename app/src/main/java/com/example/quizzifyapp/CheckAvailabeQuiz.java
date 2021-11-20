package com.example.quizzifyapp;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class CheckAvailabeQuiz extends AppCompatActivity {
    private TextView msg,quiz;
    private ListView listView;
    String  topicselected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_availabe_quiz);
        Intent intent=getIntent();
        String subjectselectd= intent.getStringExtra("subject");
        topicselected=intent.getStringExtra("topic");

        FirebaseDatabase db =FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = db.getReference().child("QuizQuestions");
        msg=findViewById(R.id.messageincheckavail);
        quiz=findViewById(R.id.quiz);
        msg.setText(subjectselectd);
        quiz.setText(topicselected);
        listView=findViewById(R.id.quizzes);
        List<String> subs=new ArrayList<>();
        List<String> tops=new ArrayList<>();
        Query query=FirebaseDatabase.getInstance().getReference("QuizQuestions").orderByChild("subject").equalTo(subjectselectd);

        query.addListenerForSingleValueEvent(valueEventListener);

    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

            if (dataSnapshot.exists()) {

                ArrayList<String> quizzs=new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    question q = snapshot.getValue(question.class);
                    if(!quizzs.contains(q.quizid) && q.Topic.equals(topicselected)){

                            quizzs.add(q.quizid);
                    }

                }
                ArrayAdapter quizadapter=new ArrayAdapter(CheckAvailabeQuiz.this, android.R.layout.simple_list_item_1,quizzs);
                listView.setAdapter(quizadapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(CheckAvailabeQuiz.this, quizzs.get(position), Toast.LENGTH_SHORT).show();
                        String qid=quizzs.get(position);
                        startActivity(new Intent(CheckAvailabeQuiz.this, FacultyQuizDisplay.class).putExtra("quiz_id",qid));
                    }
                });
            }
            else{
                Toast.makeText(CheckAvailabeQuiz.this, "NO QUIZZES AVAILABLE", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
}