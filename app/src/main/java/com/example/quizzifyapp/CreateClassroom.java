package com.example.quizzifyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class CreateClassroom extends AppCompatActivity {
    private TextView message,RoomId;
    private EditText QuizId;
    private Button Start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_classroom);
        char[] _base62chars =
                "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
        Random _random = new Random();
        StringBuilder rid = new StringBuilder(7);
        for (int i=0; i<7; i++)
            rid.append(_base62chars[_random.nextInt(62)]);
        RoomId=findViewById(R.id.RoomId);
        RoomId.setText(rid);
        QuizId=findViewById(R.id.QuizIdEntry);
        Start=findViewById(R.id.QuizStart);

        DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Quiz");



        Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Start.setEnabled(false);
                String quizid=QuizId.getText().toString().trim();
                if(quizid.isEmpty()){
                    QuizId.setError("Enter a Quiz ID");
                    QuizId.requestFocus();
                    return;
                }
                else{
                    ref.push().setValue(new Quiz(quizid,rid.toString()));
                    Toast.makeText(CreateClassroom.this, "Quiz is Active", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(CreateClassroom.this, WaitAndResults.class).putExtra("quizid",quizid).putExtra("roomid",rid.toString()));
                }

            }
        });
    }
}