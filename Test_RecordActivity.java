package com.example.sppbtest;

import static android.app.ProgressDialog.show;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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

public class Test_RecordActivity extends AppCompatActivity {

    private ImageButton BackButton;
    private Button SearchButton;

    private EditText EditNumber;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_record);

        BackButton = findViewById(R.id.BackButton);
        SearchButton = findViewById(R.id.SearchButton);
        EditNumber = findViewById(R.id.EditNumber);

        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Test_RecordActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        SearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Number = EditNumber.getText().toString().trim();
                if (Number.isEmpty()) {
                    Toast.makeText(Test_RecordActivity.this, "식별번호를 입력하세요.", Toast.LENGTH_SHORT).show();
                } else {
                    searchRecord(Number);
                }
            }
        });

    }

    private void searchRecord(String Number) {
            try {
                String url = "http://www.all-tafp.org/score_save_get.php?Number=" + URLEncoder.encode(Number, "UTF-8");

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    Log.d("Server Response", response);

                                    JSONObject jsonResponse = new JSONObject(response);
                                    String status = jsonResponse.getString("status");
                                    if (status.equals("success")) {
                                        JSONObject data = jsonResponse.getJSONObject("data");

                                        int totalScore = data.getInt("Total_Score");
                                        int questionResult = data.getInt("QuestionResult");
                                        int avgGripStrength = data.getInt("AVG_GripStrength");
                                        int walkingScore = data.getInt("Walking_Score");
                                        int standUpScore = data.getInt("StandUp_Score");
                                        int balanceScore = data.getInt("Balance_Score");
                                        String username = data.getString("username");

                                        Intent intent = new Intent(Test_RecordActivity.this, Record_CheckActivity.class);
                                        intent.putExtra("Total_Score", totalScore);
                                        intent.putExtra("QuestionResult", questionResult);
                                        intent.putExtra("AVG_GripStrength", avgGripStrength);
                                        intent.putExtra("Walking_Score", walkingScore);
                                        intent.putExtra("StandUp_Score", standUpScore);
                                        intent.putExtra("Balance_Score", balanceScore);
                                        intent.putExtra("username", username);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        String message = jsonResponse.getString("message");
                                        Toast.makeText(Test_RecordActivity.this, message, Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(Test_RecordActivity.this, "응답 처리 오류: " + response, Toast.LENGTH_SHORT).show();
                                }
                            }

                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(Test_RecordActivity.this, "서버 오류", Toast.LENGTH_SHORT).show();
                            }
                        });

                RequestQueue requestQueue = Volley.newRequestQueue(this);
                requestQueue.add(stringRequest);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(Test_RecordActivity.this, "잘못된 요청입니다.", Toast.LENGTH_SHORT).show();
            }

    }
}
