package com.example.sppbtest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Test_ListActivity extends AppCompatActivity {

    private TextView username, id;
    private ImageButton GoQuestion, GoTest, BackButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_list);

        username = findViewById(R.id.username);
        GoQuestion = findViewById(R.id.imageButton1);
        GoTest = findViewById(R.id.imageButton2);
        BackButton = findViewById(R.id.BackButton);
        id = findViewById(R.id.id);

        SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
        String usernameValue = sharedPreferences.getString("name", "defaultName");
        String userId = sharedPreferences.getString("id", "defaultId");

        username.setText(usernameValue + "님");
        id.setText("(식별 번호: " + userId +")");

        GoQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Test_ListActivity.this, Question_MainActivity.class);
                startActivity(intent);
            }
        });

        GoTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Test_ListActivity.this, Evaluation_ItemsActivity.class);
                startActivity(intent);
            }
        });

        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Test_ListActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}

