package com.example.quizzifyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class scraping extends AppCompatActivity {
   TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scraping);
        String words="l";
        textView=findViewById(R.id.editTextTextPersonName2);
        try {
            Document document= Jsoup.connect("https://nutribaba.herokuapp.com/").get();
            String title = document.title();
            System.out.println("title is: " + title);
            Toast.makeText(scraping.this, title, Toast.LENGTH_SHORT).show();
            Elements links = document.select("a");
            for (Element row : links ) {
                System.out.println(row);
            }

                words = document.text();
            System.out.println("Hello");

        } catch (IOException e) {
            e.printStackTrace();
        }
       textView.setText(words);
    }
}