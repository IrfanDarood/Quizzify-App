package com.example.quizzifyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class WaitAndResults extends AppCompatActivity {
    private TextView time,roomID;
    private Button GetResults;
    private long pressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_and_results);


        Intent intent=getIntent();
        String RID=intent.getStringExtra("roomid");
        String QID=intent.getStringExtra("quizid");
        time=findViewById(R.id.time);
        GetResults=findViewById(R.id.getResults);
        roomID=findViewById(R.id.roomId);
        roomID.setText(RID);
        roomID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("RoomId", RID);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(WaitAndResults.this, "Copied to ClipBoard", Toast.LENGTH_SHORT).show();
            }
        });
        GetResults.setEnabled(false);
        new CountDownTimer(120000,1000){

            @Override
            public void onTick(long l) {

                String t=String.format(Locale.ENGLISH,"%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(l),
                        TimeUnit.MILLISECONDS.toSeconds(l)-TimeUnit.MINUTES
                        .toSeconds(TimeUnit.MILLISECONDS.toMinutes(l)));
                time.setText(t);
            }

            @Override
            public void onFinish() {
                GetResults.setEnabled(true);
            }
        }.start();
        GetResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(WaitAndResults.this, ResultsToTeacher.class).putExtra("quizid",QID));
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