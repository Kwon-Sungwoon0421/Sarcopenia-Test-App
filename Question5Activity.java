package com.example.sppbtest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Question5Activity extends AppCompatActivity {

    private RadioGroup radioGroup;

    private Button finishButton;

    int total_score = 0;
    int score1 = 0;
    int score2 = 0;
    int score3 = 0;
    int score4 = 0;
    int score5 = 0;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question5);

        total_score = getIntent().getIntExtra("total_score", 0);
        score1 = getIntent().getIntExtra("score1", 0);
        score2 = getIntent().getIntExtra("score2", 0);
        score3 = getIntent().getIntExtra("score3", 0);
        score4 = getIntent().getIntExtra("score4", 0);

        radioGroup = findViewById(R.id.radioGroup);
        finishButton = findViewById(R.id.finishButton);

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedID = radioGroup.getCheckedRadioButtonId();

                if (selectedID == R.id.radioButton1) {
                    score5 = 0;
                } else if (selectedID == R.id.radioButton2) {
                    score5 = 1;
                } else if (selectedID == R.id.radioButton3) {
                    score5 = 2;
                } else {
                    Toast.makeText(Question5Activity.this, "항목을 선택해주세요!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(Question5Activity.this, Question_ResultActivity.class);
                total_score+=score5;
                intent.putExtra("total_score", total_score);
                intent.putExtra("score1", score1);
                intent.putExtra("score2", score2);
                intent.putExtra("score3", score3);
                intent.putExtra("score4", score4);
                intent.putExtra("score5", score5);
                startActivity(intent);
            }
        });

    }
}
