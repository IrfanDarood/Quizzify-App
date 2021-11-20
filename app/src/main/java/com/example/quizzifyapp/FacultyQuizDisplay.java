package com.example.quizzifyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class FacultyQuizDisplay extends AppCompatActivity {
    private TextView message;
    private Button copyid;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        String qid = i.getStringExtra("quiz_id");
        setContentView(R.layout.activity_faculty_quiz_display);
        message = findViewById(R.id.messageQuizId);
        listView=findViewById(R.id.quizquestions);
        copyid=findViewById(R.id.CopyQuizId);
        message.setText(qid);
        copyid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("QuizId", qid);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(FacultyQuizDisplay.this, "Copied to ClipBoard", Toast.LENGTH_SHORT).show();
            }
        });
        FirebaseDatabase fdb = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = fdb.getReference().child("QuizQuestions");
        Query query = FirebaseDatabase.getInstance().getReference("QuizQuestions").orderByChild("quizid").equalTo(qid);

        query.addListenerForSingleValueEvent(valueEventListener);
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot1) {
            if (snapshot1.exists()) {
                ArrayList<String> questions = new ArrayList<>();
                ArrayList<String> crtop = new ArrayList<>();
                ArrayList<String> incrtop1 = new ArrayList<>();
                ArrayList<String> incrtop2 = new ArrayList<>();
                ArrayList<String> incrtop3 = new ArrayList<>();

                for (DataSnapshot snapshot : snapshot1.getChildren()) {
                    question q = snapshot.getValue(question.class);
                        questions.add(q.question);
                        crtop.add(q.option_crt);
                        incrtop1.add(q.option_incrt1);
                        incrtop2.add(q.option_incrt2);
                        incrtop3.add(q.option_incrt3);
                }
                String[] ques=new String[questions.size()];
                questions.toArray(ques);
                String[] opcrt=new String[questions.size()];
                crtop.toArray(opcrt);
                String[] opincrt1=new String[questions.size()];
                incrtop1.toArray(opincrt1);
                String[] opincrt2=new String[questions.size()];
                incrtop2.toArray(opincrt2);
                String[] opincrt3=new String[questions.size()];
                incrtop3.toArray(opincrt3);
                CustomBaseAdapter customBaseAdapter=new CustomBaseAdapter(getApplicationContext(),ques,opcrt,opincrt1,opincrt2,opincrt3);
                listView.setAdapter(customBaseAdapter);

            }


        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };
}