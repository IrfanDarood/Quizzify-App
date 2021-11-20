package com.example.quizzifyapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity implements View.OnClickListener {
    private TextView register;
    private EditText email,pass;
    private Button loginbtn;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth=FirebaseAuth.getInstance();

        email=(EditText)findViewById(R.id.emailid);
        pass=(EditText)findViewById(R.id.password);

        register=findViewById(R.id.register);
        register.setOnClickListener(this);
        loginbtn=(Button) findViewById(R.id.registeruser1);
        loginbtn.setOnClickListener(this);
    }
    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.registeruser1:
                loginUser();
                break;
            case R.id.register:
                startActivity(new Intent(this, UserRegistration.class));
                break;
        }
    }
    private void loginUser() {
        String mail= email.getText().toString().trim();
        String pword=pass.getText().toString().trim();
        if(mail.isEmpty()){
            email.setError("Email can't be empty");
            email.requestFocus();
            return;
        }
        if(pword.isEmpty()){
            pass.setError("Please Enter the Password");
            pass.requestFocus();
            return;
        }
        mAuth.signInWithEmailAndPassword(mail,pword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    FirebaseUser user = mAuth.getCurrentUser();
                    DatabaseReference db=FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid());
                    db.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            User u=snapshot.getValue(User.class);
                            if(u.role.equals("Student")){
                                finish();
                                Intent intent=new Intent(Login.this,StudentWelcome.class).putExtra("userid",u.idno);
                                startActivity(intent);
                            }
                            else{
                                finish();
                                startActivity(new Intent(Login.this,Welcome.class));
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                else{
                    Toast.makeText(Login.this, "Invalid Email and password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}