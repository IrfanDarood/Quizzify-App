package com.example.quizzifyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayQuizID extends AppCompatActivity {
    private TextView msg;
    private EditText qid;
    private Button done,copyid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_quiz_id);
        msg=findViewById(R.id.message);
        qid=findViewById(R.id.QuizID);
        Intent intent=getIntent();
        msg.setText("QUIZ ID");
        String quizid=intent.getStringExtra("quizid");
        qid.setText(quizid);
        qid.setFocusable(false);
        copyid=findViewById(R.id.copyID);
        copyid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("QuizId", quizid);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(DisplayQuizID.this, "Copied to ClipBoard", Toast.LENGTH_SHORT).show();
            }
        });



        done=findViewById(R.id.copyID);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DisplayQuizID.this, Welcome.class));
            }
        });

    }
}