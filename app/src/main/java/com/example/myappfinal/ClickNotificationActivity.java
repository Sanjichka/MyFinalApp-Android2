package com.example.myappfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ClickNotificationActivity extends AppCompatActivity {

    TextView txt;
    TextView txt1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click_notification);

        Intent intent = getIntent();
        String start = intent.getStringExtra("start");
        String finish = intent.getStringExtra("finish");
        String weather = intent.getStringExtra("weather");

        txt = findViewById(R.id.tv_click);
        txt1 = findViewById(R.id.tv1_click);

        if(weather.equals("no")) {
            txt.setText(start);
            txt1.setText(finish);
        }
        else {
            txt.setText(R.string.txt_TakeBreak);
            txt1.setText(R.string.txt_PerfectWeather);
        }


    }
}