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

public class Question3Activity extends AppCompatActivity {

    private RadioGroup radioGroup;

    private Button nextButton;

    int total_score = 0;
    int score1 = 0;
    int score2 = 0;
    int score3 = 0;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question3);

        total_score = getIntent().getIntExtra("total_score", 0);
        score1 = getIntent().getIntExtra("score1", 0);
        score2 = getIntent().getIntExtra("score2", 0);

        radioGroup = findViewById(R.id.radioGroup);
        nextButton = findViewById(R.id.nextbutton);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedID = radioGroup.getCheckedRadioButtonId();

                if (selectedID == R.id.radioButton1) {
                    score3 = 0;
                } else if (selectedID == R.id.radioButton2) {
                    score3 = 1;
                } else if (selectedID == R.id.radioButton3) {
                    score3 = 2;
                } else {
                    Toast.makeText(Question3Activity.this, "항목을 선택해주세요!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(Question3Activity.this, Question4Activity.class);
                total_score+=score3;
                intent.putExtra("total_score", total_score);
                intent.putExtra("score1", score1);
                intent.putExtra("score2", score2);
                intent.putExtra("score3", score3);
                startActivity(intent);
            }
        });

    }
}
