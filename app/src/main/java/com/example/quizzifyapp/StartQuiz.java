package com.example.quizzifyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
//import com.sasank.roundedhorizontalprogress.RoundedHorizontalProgressBar;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

public class StartQuiz extends AppCompatActivity {
    private ArrayList<question> questionList;
    private CountDownTimer quiztimer;
    private int timelimit=40, index=0, correctcount=0,wrongcount=0;
    private question que;
    private TextView card_question, optiona,optionb,optionc,optiond;
    private CardView cardOA,cardOB,cardOC,cardOD;
    private Button next;
    private long pressedTime;
    String userid,RID;


   private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_quiz);
        progressBar=findViewById(R.id.quiz_timer);



        card_question=findViewById(R.id.queDisplay);
        optiona=findViewById(R.id.op1);
        optionb=findViewById(R.id.op2);
        optionc=findViewById(R.id.op3);
        optiond=findViewById(R.id.op4);
        cardOA=findViewById(R.id.cardoa);
        cardOB=findViewById(R.id.cardob);
        cardOC=findViewById(R.id.cardoc);
        cardOD=findViewById(R.id.cardod);
        next=findViewById(R.id.nextBtn);
        next.setEnabled(false);

        quiztimer=new CountDownTimer(40000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timelimit=timelimit-1;
                progressBar.setProgress(timelimit);
            }

            @Override
            public void onFinish() {
                if(index<questionList.size()-1){
                    index++;
                    que=questionList.get(index);
                    setAllData();
                    resetColor();
                    enableButton();
                    timelimit=40;
                    if(quiztimer!=null){
                        quiztimer.cancel();
                    }
                    next.setEnabled(false);
                    start();
                }
                else{
                    if(quiztimer!=null){
                        quiztimer.cancel();
                    }
                    result();
                }
            }
        };
        quiztimer.start();

        Intent intent=getIntent();
        String RoomID=intent.getStringExtra("roomid");
        userid=intent.getStringExtra("userid");
        Query query= FirebaseDatabase.getInstance().getReference("Quiz").orderByChild("RoomID").equalTo(RoomID);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String QID="";
                    for(DataSnapshot s: snapshot.getChildren()){
                        Quiz q=s.getValue(Quiz.class);
                        QID=q.QuizID;
                        RID=q.RoomID;
                    }
                    Query query1=FirebaseDatabase.getInstance().getReference("QuizQuestions").orderByChild("quizid").equalTo(QID);
                    query1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                            if(datasnapshot.exists()){
                                questionList=new ArrayList<>();
                                for(DataSnapshot q: datasnapshot.getChildren()){
                                    question que=q.getValue(question.class);
                                    questionList.add(que);
                                }
                                startquiz();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
//
    public void startquiz(){
        Collections.shuffle(questionList);
        que=questionList.get(index);
        setAllData();
    }

    private void setAllData() {
        card_question.setText(que.question);
        Random r=new Random();
        switch (r.nextInt(4)){
            case 0:
                optiona.setText(que.option_crt);
                optionb.setText(que.option_incrt1);
                optionc.setText(que.option_incrt2);
                optiond.setText(que.option_incrt3);
                break;

            case 1:
                optionb.setText(que.option_crt);
                optionc.setText(que.option_incrt1);
                optiond.setText(que.option_incrt2);
                optiona.setText(que.option_incrt3);
                break;
            case 2:
                optionc.setText(que.option_crt);
                optiond.setText(que.option_incrt1);
                optiona.setText(que.option_incrt2);
                optionb.setText(que.option_incrt3);
                break;
            case 3:
                optiond.setText(que.option_crt);
                optiona.setText(que.option_incrt1);
                optionb.setText(que.option_incrt2);
                optionc.setText(que.option_incrt3);
                break;

        }

    }
    public void Correct(CardView cardview){
        cardview.setCardBackgroundColor(getResources().getColor(R.color.green));
        correctcount++;

        disableButton();
        next.setEnabled(true);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quiztimer.onFinish();
            }
        });

    }
    public void Wrong(CardView cardview){
        cardview.setCardBackgroundColor(getResources().getColor(R.color.red));
        disableButton();
        next.setEnabled(true);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wrongcount++;
                quiztimer.onFinish();
            }
        });

    }
    public void result(){
        finish();
        String totalques= String.valueOf(questionList.size());
        String correct=String.valueOf(correctcount);
        startActivity(new Intent(StartQuiz.this, ShowResult.class).putExtra("totalques",totalques)
                .putExtra("correct",correct).putExtra("subject", que.subject).putExtra("topic", que.Topic)
                .putExtra("quizid",que.quizid).putExtra("userid",userid).putExtra("roomid",RID));
    }
    public void enableButton(){
        cardOA.setEnabled(true);
        cardOB.setEnabled(true);
        cardOC.setEnabled(true);
        cardOD.setEnabled(true);
    }
    public void disableButton(){
        cardOA.setEnabled(false);
        cardOB.setEnabled(false);
        cardOC.setEnabled(false);
        cardOD.setEnabled(false);
    }
    public void resetColor(){
        cardOA.setCardBackgroundColor(getResources().getColor(R.color.white));
        cardOB.setCardBackgroundColor(getResources().getColor(R.color.white));
        cardOC.setCardBackgroundColor(getResources().getColor(R.color.white));
        cardOD.setCardBackgroundColor(getResources().getColor(R.color.white));
    }


    public void optionClickA(View view) {
        if(optiona.getText().equals(que.option_crt)){
            cardOA.setCardBackgroundColor(getResources().getColor(R.color.green));
            if(index<questionList.size()){
                Correct(cardOA);
            }
//            else{
//                result();
//            }

        }
        else{
            Wrong(cardOA);
        }
    }
    public void optionClickB(View view) {
        if(optionb.getText().equals(que.option_crt)){
            cardOB.setCardBackgroundColor(getResources().getColor(R.color.green));
            if(index<questionList.size()){
                Correct(cardOB);
            }
//            else{
//                result();
//            }

        }
        else{
            Wrong(cardOB);
        }
    }
    public void optionClickC(View view) {
        if(optionc.getText().equals(que.option_crt)){
            cardOC.setCardBackgroundColor(getResources().getColor(R.color.green));
            if(index<questionList.size()){
                Correct(cardOC);
            }
//            else{
//                result();
//            }

        }
        else{
            Wrong(cardOC);
        }
    }
    public void optionClickD(View view) {
        if(optiond.getText().equals(que.option_crt)){
            cardOD.setCardBackgroundColor(getResources().getColor(R.color.green));
            if(index<questionList.size()){
                Correct(cardOD);
            }
//            else{
//                result();
//            }

        }
        else{
            Wrong(cardOD);
        }
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