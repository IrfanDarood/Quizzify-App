package com.example.quizzifyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class UserRegistration extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private TextView register_user;
    private EditText name,email,id_no, password,cpassword;
    private RadioGroup role;
    private RadioButton userrole;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);

        mAuth = FirebaseAuth.getInstance();
        register_user=findViewById(R.id.registeruser1);
        register_user.setOnClickListener(this);
        name= findViewById(R.id.username);
        email =  findViewById(R.id.emailid);
        id_no= findViewById(R.id.idno);
        password= findViewById(R.id.password);
        cpassword=findViewById(R.id.confirmpassword);
        role=findViewById(R.id.userRole);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.registeruser1:
                register_user();
        }
    }

    private void register_user() {

        String username= name.getText().toString().trim();
        String mail= email.getText().toString().trim();
        String idno=id_no.getText().toString().trim();
        String pass= password.getText().toString().trim();
        String cpass= cpassword.getText().toString().trim();
        int selectedrole=role.getCheckedRadioButtonId();
        userrole=findViewById(selectedrole);
        String userRole=userrole.getText().toString();

        if(username.isEmpty()){
            name.setError("Full Name is required");
            name.requestFocus();
            return;
        }
        if(mail.isEmpty()){
            email.setError("Email is required");
            email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
            email.setError("Enter a Valid Email Address");
            email.requestFocus();
            return;
        }
            if(idno.isEmpty()){
            id_no.setError("Student Roll Number is required");
            id_no.requestFocus();
            return;
        }
        if(pass.isEmpty()){
            password.setError("Enter a Password");
            password.requestFocus();
            return;
        }
        if(pass.length()<=6){
            password.setError("Must be longer than 6 Characters.");
            password.requestFocus();
            return;
        }
        if(cpass.isEmpty()){
            cpassword.setError("Re-enter the Password");
            cpassword.requestFocus();
            return;
        }
        if(!cpass.equals(pass)){
            cpassword.setError("Passwords Do Not Match");
            cpassword.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(mail,pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>(){
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task){
                        if(task.isSuccessful()){
                            User user= new User(username,mail,idno,pass,userRole);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(UserRegistration.this, "successfully registered!", Toast.LENGTH_SHORT).show();
                                        try{
                                            finishActivity(0);
                                            finish();
                                            startActivity(new Intent(UserRegistration.this, Login.class));
                                        }
                                        catch(Exception e){
                                            Toast.makeText(UserRegistration.this, "can't terminate activity", Toast.LENGTH_SHORT).show();

                                        }

                                    }
                                    else{
                                        Toast.makeText(UserRegistration.this,"Registration Failed!",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(UserRegistration.this, "Failed to Register", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }
}