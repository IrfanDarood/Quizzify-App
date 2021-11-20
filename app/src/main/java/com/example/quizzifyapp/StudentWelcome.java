package com.example.quizzifyapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.FirebaseDatabase;

public class StudentWelcome extends AppCompatActivity {
    private TextView message;
    private EditText ClassId;
    private Button join;

    private long pressedTime;

    private String RoomID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_welcome);
        Intent i=getIntent();
        String userid=i.getStringExtra("userid");

        message = findViewById(R.id.classroomidmessage);
        ClassId = findViewById(R.id.ClassroomId);
        join = findViewById(R.id.EnterClassroom);
        FirebaseDatabase db=FirebaseDatabase.getInstance();
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RoomID = ClassId.getText().toString().trim();
                ClassId.setText(null);
                if(RoomID.isEmpty()){
                    ClassId.setError("Room ID Required");
                    ClassId.requestFocus();

                }
                else{

                    startActivity(new Intent(StudentWelcome.this, StartQuiz.class).putExtra("roomid",RoomID).putExtra("userid",userid));
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


//        if (!RoomID.isEmpty()) {
//
//            Query query = FirebaseDatabase.getInstance().getReference("Quiz").orderByChild("RoomID").equalTo(RoomID);
////            query.addListenerForSingleValueEvent(valueEventListeners);
//            Toast.makeText(StudentWelcome.this, q.QuizID, Toast.LENGTH_SHORT).show();
//        }



//    ValueEventListener valueEventListeners=new ValueEventListener() {
//        @Override
//        public void onDataChange(@NonNull DataSnapshot snapshot) {
//            if (snapshot.exists()) {
//                for (DataSnapshot s : snapshot.getChildren()) {
//                    Quiz a = snapshot.getValue(Quiz.class);
//                    Toast.makeText(StudentWelcome.this, a.QuizID, Toast.LENGTH_SHORT).show();
//                    String qid = a.QuizID;
//                }


//                startActivity(new Intent(StudentWelcome.this, StartQuiz.class).putExtra("quizid",qid));
//                if(qid.isEmpty()){
//                    Toast.makeText(StudentWelcome.this, "Qid is empty", Toast.LENGTH_SHORT).show();
//                }
//                else{
//                    Toast.makeText(StudentWelcome.this, "done", Toast.LENGTH_SHORT).show();
//                }

//                Query query2 = FirebaseDatabase.getInstance().getReference("QuizQuestions").orderByChild("quizid").equalTo(qid);
//
//                query2.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot datasnapshot) {
//                        if(datasnapshot.exists()){
//                            Toast.makeText(StudentWelcome.this, ((char ) datasnapshot.getChildrenCount()), Toast.LENGTH_SHORT).show();
//                            question[] ques=new question[((int) datasnapshot.getChildrenCount())];
//                            int i=0;
//                            for(DataSnapshot dsnapshot: datasnapshot.getChildren()){
//                                ques[i]=dsnapshot.getValue(question.class);
//                                i++;
//                                Toast.makeText(StudentWelcome.this, ques[i].question, Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//            }
//        }

//        @Override
//        public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        }


//    };

