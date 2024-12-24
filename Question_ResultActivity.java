package com.example.sppbtest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Question_ResultActivity extends AppCompatActivity {
    private TextView textScore1, textScore2, textScore3, textScore4, textScore5, textTotalScore;
    private TextView textTotalScoreResult, textTotalScoreResult2;
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
        setContentView(R.layout.activity_question_result);

        textScore1 = findViewById(R.id.textScore1);
        textScore2 = findViewById(R.id.textScore2);
        textScore3 = findViewById(R.id.textScore3);
        textScore4 = findViewById(R.id.textScore4);
        textScore5 = findViewById(R.id.textScore5);
        textTotalScore = findViewById(R.id.textTotalScore);
        textTotalScoreResult = findViewById(R.id.textTotalScoreResult);
        textTotalScoreResult2 = findViewById(R.id.textTotalScoreResult2);

        finishButton = findViewById(R.id.finishButton);

        total_score = getIntent().getIntExtra("total_score", 0);
        score1 = getIntent().getIntExtra("score1", 0);
        score2 = getIntent().getIntExtra("score2", 0);
        score3 = getIntent().getIntExtra("score3", 0);
        score4 = getIntent().getIntExtra("score4", 0);
        score5 = getIntent().getIntExtra("score5", 0);

        textScore1.setText(score1 + "점");
        textScore2.setText(score2 + "점");
        textScore3.setText(score3 + "점");
        textScore4.setText(score4 + "점");
        textScore5.setText(score5 + "점");
        textTotalScore.setText(total_score + "점");

        if(total_score==0){
            textTotalScoreResult.setText("근감소증이 의심되지 않습니다.");
            textTotalScoreResult2.setText("");
        }
        else if(total_score>=1 && total_score<=3){
            textTotalScoreResult.setText("근감소증이 약간 의심됩니다.");
            textTotalScoreResult2.setText("검사를 권장합니다.");
        }
        else if(total_score>=4){
            textTotalScoreResult.setText("근감소증이 매우 의심됩니다.");
            textTotalScoreResult2.setText("검사를 매우 권장합니다.");
        }

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("QuestionResult", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("QuestionResult", total_score);
                editor.apply();
                Intent intent = new Intent(Question_ResultActivity.this, Test_ListActivity.class);
                startActivity(intent);
            }
        });
    }
}
