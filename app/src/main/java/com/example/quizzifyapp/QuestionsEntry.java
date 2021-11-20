package com.example.quizzifyapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;
import java.util.UUID;

public class QuestionsEntry extends AppCompatActivity {
    private EditText qtn,op_crt,op_incrt1,op_incrt2,op_incrt3,sub,topic;
    private TextView qNo;
    private Button submit,next,finish;
    public Integer questionNo=1;
    private long pressedTime;
    private String quizID = UUID.randomUUID().toString();
    FirebaseDatabase db =FirebaseDatabase.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions_entry);
        Intent intent=getIntent();
        String subjectselectd= intent.getStringExtra("subject");
        String topicselected=intent.getStringExtra("topic");
        DatabaseReference root = db.getReference().child("QuizQuestions");
        qNo=(TextView)findViewById(R.id.QuestionNo);
        qNo.setText("Q"+questionNo.toString());
        qtn= (EditText) findViewById(R.id.question);
        op_crt=(EditText)findViewById(R.id.crt_option);
        op_incrt3=(EditText)findViewById(R.id.incrt_option1);
        op_incrt1=(EditText)findViewById(R.id.incrt_option2);
        op_incrt2=(EditText)findViewById(R.id.incrt_option3);
        next=(Button) findViewById(R.id.enterNextQues);
        next.setEnabled(false);
        finish=findViewById(R.id.finish);
        finish.setVisibility(View.GONE);
        finish.setEnabled(false);
        submit=(Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String question=qtn.getText().toString().trim();
                String crt_op=op_crt.getText().toString().trim();
                String incrt_op1=op_incrt1.getText().toString().trim();
                String incrt_op2=op_incrt2.getText().toString().trim();
                String incrt_op3=op_incrt3.getText().toString().trim();
                if(question.isEmpty()||crt_op.isEmpty()||incrt_op1.isEmpty()||incrt_op2.isEmpty()||incrt_op3.isEmpty()){
                    Toast.makeText(QuestionsEntry.this, "Please enter all the fields.", Toast.LENGTH_SHORT).show();
                }
                else{
                    root.push().setValue(new question(questionNo.toString(),question,crt_op,incrt_op1,incrt_op2,incrt_op3,subjectselectd,topicselected,quizID));
                    submit.setEnabled(false);
                    next.setEnabled(true);
                    finish.setEnabled(true);
                    next.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(questionNo>4){
                                finish.setVisibility(View.VISIBLE);
                            }
                            if(questionNo<9){
                                submit.setEnabled(true);
                                next.setEnabled(false);
                                finish.setEnabled(false);

                            }

                            else{
                                next.setVisibility(View.GONE);
                                submit.setEnabled(true);

                            }
                            qtn.setText(null);
                            op_crt.setText(null);
                            op_incrt1.setText(null);
                            op_incrt2.setText(null);
                            op_incrt3.setText(null);
                            questionNo=questionNo+1;
                            qNo.setText("Q"+questionNo.toString());
                        }
                    });
                    finish.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            startActivity(new Intent(QuestionsEntry.this, DisplayQuizID.class).putExtra("quizid",quizID));
                        }
                    });
                }
            }
        });
    }
    @Override
    public void onBackPressed() {

        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finish();
        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
    }
}