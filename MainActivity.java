package com.example.sppbtest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView username;

    private ImageButton GoTestList, GoTestRecord;

    private Button XYZ_Button;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.username);

        GoTestList = findViewById(R.id.imageButton1);
        GoTestRecord = findViewById(R.id.imageButton2);
        XYZ_Button = findViewById(R.id.XYZ_Button);

        SharedPreferences sharedPreferences = getSharedPreferences("UserName", MODE_PRIVATE);
        String getname = sharedPreferences.getString("username", "defaultName");
        username.setText(getname);

        GoTestList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SubjectsActivity.class);
                startActivity(intent);
            }
        });

        GoTestRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Test_RecordActivity.class);
                startActivity(intent);
            }
        });

        XYZ_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, XYZActivity.class);
                startActivity(intent);
            }
        });

    }
}