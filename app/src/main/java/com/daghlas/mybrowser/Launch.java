package com.daghlas.mybrowser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class Launch extends AppCompatActivity {

    RelativeLayout searchB;
    EditText search;
    LinearLayout linkedInBtn, gmailBtn, youtubeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.theme));

        search = findViewById(R.id.url);
        searchB = findViewById(R.id.search_bar);
        linkedInBtn = findViewById(R.id.linkedin);
        gmailBtn = findViewById(R.id.gmail);
        youtubeBtn = findViewById(R.id.youtube);

        searchB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Launch.this, MainActivity.class);
                startActivity(intent);
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Launch.this, MainActivity.class);
                startActivity(intent);
            }
        });
        linkedInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Launch.this, LinkedIn.class);
                startActivity(intent);
            }
        });
        gmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Launch.this, Gmail.class);
                startActivity(intent);
            }
        });
        youtubeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Launch.this, YouTube.class);
                startActivity(intent);
            }
        });
    }
}