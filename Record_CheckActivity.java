package com.example.sppbtest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Record_CheckActivity extends AppCompatActivity {
    private TextView QuestionScore, AVG_GripStrengthScore, WalkingScore, StandUpScore, BalanceScore, TotalScore, username;
    private ImageButton BackButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_check);

        QuestionScore = findViewById(R.id.QuestionScore);
        AVG_GripStrengthScore = findViewById(R.id.AVG_GripStrengthScore);
        WalkingScore = findViewById(R.id.WalkingScore);
        StandUpScore = findViewById(R.id.StandUpScore);
        BalanceScore = findViewById(R.id.BalanceScore);
        TotalScore = findViewById(R.id.TotalScore);
        BackButton = findViewById(R.id.BackButton);
        username = findViewById(R.id.username);

        int Total_Score = getIntent().getIntExtra("Total_Score", 0);
        int QuestionResult = getIntent().getIntExtra("QuestionResult", 0);
        int AVG_GripStrength = getIntent().getIntExtra("AVG_GripStrength", 0);
        int Walking_Score = getIntent().getIntExtra("Walking_Score", 0);
        int StandUp_Score = getIntent().getIntExtra("StandUp_Score", 0);
        int Balance_Score = getIntent().getIntExtra("Balance_Score", 0);
        String usernameText = getIntent().getStringExtra("username");

        QuestionScore.setText(QuestionResult + "점");
        AVG_GripStrengthScore.setText(AVG_GripStrength + "점");
        WalkingScore.setText(Walking_Score + "점");
        StandUpScore.setText(StandUp_Score + "점");
        BalanceScore.setText(Balance_Score + "점");
        TotalScore.setText(Total_Score + "점");
        username.setText(usernameText + "님");


        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Record_CheckActivity.this, Test_RecordActivity.class);
                startActivity(intent);
            }
        });
    }
}
