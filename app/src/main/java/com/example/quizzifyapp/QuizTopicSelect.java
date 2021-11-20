package com.example.quizzifyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class QuizTopicSelect extends AppCompatActivity {
    private EditText subj,top,no_of_ques;
    private Button checkavail,createnew;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_topic_select);
        subj=findViewById(R.id.SubjectEntry);
        top=findViewById(R.id.TopicEntry);
        checkavail=findViewById(R.id.CheckAvailableQuiz1);
        createnew=findViewById(R.id.CreateQuiz);

        checkavail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sub=subj.getText().toString().toUpperCase().trim();
                String tp=top.getText().toString().toUpperCase().trim();


                if (sub.isEmpty() || tp.isEmpty()) {
                    Toast.makeText(QuizTopicSelect.this, "Enter the subject and topic", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(QuizTopicSelect.this, "ok", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(QuizTopicSelect.this, CheckAvailabeQuiz.class).putExtra("subject",sub).putExtra("topic",tp));
                }
            }
        });
        createnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sub=subj.getText().toString().toUpperCase().trim();
                String tp=top.getText().toString().toUpperCase().trim();

                if (sub.isEmpty() || tp.isEmpty()) {
                    Toast.makeText(QuizTopicSelect.this, "Enter the subject and topic", Toast.LENGTH_SHORT).show();
                }
                else{
                    startActivity(new Intent(QuizTopicSelect.this, QuestionsEntry.class).putExtra("subject",sub).putExtra("topic",tp));
                }
            }
        });

    }
}