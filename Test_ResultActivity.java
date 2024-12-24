package com.example.sppbtest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;

public class Test_ResultActivity extends AppCompatActivity {

    private Button FinishButton;
    private TextView textSet1, textSet2;
    private TextView textLeftGrip_StrengthScore, textRightGrip_StrengthScore, textWalkingScore, textStand_UPScore, textBalanceScore;

    int LeftGripStrength, RightGripStrength, WalkingScore, BalanceScore, Stand_UpScore;
    int TotalScore = 0;
    int AVG_GripStrength = 0;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_result);

        LeftGripStrength = getIntent().getIntExtra("LeftGripStrength", 0);
        RightGripStrength = getIntent().getIntExtra("RightGripStrength", 0);
        WalkingScore = getIntent().getIntExtra("WalkingScore", 0);
        BalanceScore = getIntent().getIntExtra("BalanceScore", 0);
        Stand_UpScore = getIntent().getIntExtra("Stand_UpScore", 0);

        AVG_GripStrength = (LeftGripStrength+RightGripStrength)/2;

        FinishButton = findViewById(R.id.FinishButton);
        textSet1 = findViewById(R.id.textSet1);
        textSet2 = findViewById(R.id.textSet2);
        textLeftGrip_StrengthScore = findViewById(R.id.LeftGrip_StrengthScore);
        textRightGrip_StrengthScore = findViewById(R.id.RightGrip_StrengthScore);
        textWalkingScore = findViewById(R.id.WalkingScore);
        textStand_UPScore = findViewById(R.id.Stand_UpScore);
        textBalanceScore = findViewById(R.id.BalanceScore);

        textLeftGrip_StrengthScore.setText("좌: " + LeftGripStrength + "kg");
        textRightGrip_StrengthScore.setText("우: " + RightGripStrength + "kg");
        textWalkingScore.setText(WalkingScore + " 점");
        textStand_UPScore.setText(Stand_UpScore + " 점");
        textBalanceScore.setText(BalanceScore + " 점");

        TotalScore = WalkingScore + Stand_UpScore + BalanceScore;

        textSet1.setText("검사 결과 총점 " + TotalScore + "점으로 " + "근감소증이");

        if (TotalScore <= 9 && TotalScore > 3){
            textSet2.setText("의심됩니다.");
        } else if (TotalScore <= 3) {
            textSet2.setText("매우 의심됩니다.");
        } else if (TotalScore > 10) {
            textSet2.setText("의심되지 않습니다.");
        }

        FinishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertTotalScoreToDatabase(TotalScore, AVG_GripStrength, WalkingScore, Stand_UpScore, BalanceScore);
            }
        });
    }

    private void insertTotalScoreToDatabase(int TotalScore, int AVG_GripStrength, int WalkingScore, int Stand_UpScore, int BalanceScore) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
        String id = sharedPreferences.getString("id", "defaultId");

        SharedPreferences sharedPreferences1 = getSharedPreferences("QuestionResult", MODE_PRIVATE);
        int get = 0;
        int QuestionResult = sharedPreferences1.getInt("QuestionResult", get);

        if (id.equals("defaultId") || !id.matches("\\d+")) {
            Toast.makeText(this, "유효한 식별번호가 없습니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            String url = "http://www.all-tafp.org/score_save.php?id=" + URLEncoder.encode(id, "UTF-8") +
                    "&Total_Score=" + TotalScore + "&QuestionResult=" + QuestionResult + "&AVG_GripStrength=" + AVG_GripStrength +
                    "&Walking_Score=" + WalkingScore + "&StandUp_Score=" + Stand_UpScore + "&Balance_Score=" + BalanceScore;

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                String status = jsonResponse.getString("status");
                                String message = jsonResponse.getString("message");

                                if (status.equals("success")) {
                                    Toast.makeText(Test_ResultActivity.this, "총점이 성공적으로 저장되었습니다.", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Test_ResultActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(Test_ResultActivity.this, message, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                Toast.makeText(Test_ResultActivity.this, "응답 처리 오류", Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Test_ResultActivity.this, "서버 오류", Toast.LENGTH_SHORT).show();
                        }
                    });

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "잘못된 요청입니다.", Toast.LENGTH_SHORT).show();
        }
    }

}
