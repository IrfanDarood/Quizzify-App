package com.example.quizzifyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ShowResult extends AppCompatActivity {
    private TextView marks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_result);
        Intent intent=getIntent();
        String total=intent.getStringExtra("totalques");
        String correct=intent.getStringExtra("correct");
        String sub=intent.getStringExtra("subject");
        String top=intent.getStringExtra("topic");
        String Quizid=intent.getStringExtra("quizid");
        String userid=intent.getStringExtra("userid");
        String roomid=intent.getStringExtra("roomid");
        marks=findViewById(R.id.marks);


        DatabaseReference root = FirebaseDatabase.getInstance().getReference().child("Score");
        root.push().setValue(new Score(userid,Quizid,sub,top,total,correct,roomid));
        marks.setText(correct+" / "+total);

    }
}