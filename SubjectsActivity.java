package com.example.sppbtest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
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

public class SubjectsActivity extends AppCompatActivity {
    private RadioGroup radioGroup;
    private EditText EditName, EditBirthdate, EditHeight, EditWeight, EditId;
    private Button FinishButton;
    private String gender = "";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects);

        radioGroup = findViewById(R.id.radioGroup);
        EditName = findViewById(R.id.name);
        EditBirthdate = findViewById(R.id.birthdate);
        EditHeight = findViewById(R.id.height);
        EditWeight = findViewById(R.id.weight);
        FinishButton = findViewById(R.id.finishButton);
        EditId = findViewById(R.id.id);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.manButton) {
                    gender = "남성";
                } else if (checkedId == R.id.womanButton) {
                    gender = "여성";
                }
                Log.d("SubjectsActivity", "Selected gender: " + gender);
            }
        });

        FinishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerSubjects();
            }
        });
    }

    private void registerSubjects() {
        String name = EditName.getText().toString().trim();
        String birthdate = EditBirthdate.getText().toString().trim();
        String weight = EditWeight.getText().toString().trim();
        String height = EditHeight.getText().toString().trim();
        String id = EditId.getText().toString().trim();

        if (name.isEmpty() || birthdate.isEmpty() || weight.isEmpty() || height.isEmpty() || gender.isEmpty() || id.isEmpty()) {
            Toast.makeText(this, "모든 필드를 입력하세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        double heightValue, weightValue;
        try {
            heightValue = Double.parseDouble(height);
            weightValue = Double.parseDouble(weight);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "키와 체중은 숫자로 입력해야 합니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (id.length() > 10 || !id.matches("\\d+")) {
            Toast.makeText(this, "식별번호는 최대 10자리 숫자여야 합니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", name);
        editor.putString("id", id);
        editor.apply();

        try {
            String encodedName = URLEncoder.encode(name, "UTF-8");
            String encodedBirthdate = URLEncoder.encode(birthdate, "UTF-8");
            String encodedGender = URLEncoder.encode(gender, "UTF-8");
            String url = "http://www.all-tafp.org/SPPB_subjects.php?name=" + encodedName +
                    "&birthdate=" + encodedBirthdate +
                    "&weight=" + weightValue +
                    "&height=" + heightValue +
                    "&gender=" + encodedGender +
                    "&id=" + id;

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                String status = jsonResponse.getString("status");
                                String message = jsonResponse.getString("message");

                                if (status.equals("success")) {
                                    Toast.makeText(SubjectsActivity.this, message, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(SubjectsActivity.this, Test_ListActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(SubjectsActivity.this, message, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                Toast.makeText(SubjectsActivity.this, "응답 처리 오류", Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(SubjectsActivity.this, "서버 오류", Toast.LENGTH_SHORT).show();
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
