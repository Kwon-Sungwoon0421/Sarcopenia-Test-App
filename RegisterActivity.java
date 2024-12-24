package com.example.sppbtest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextUserName, editTextBirthday, editTextPhone, editTextId, editTextPw, editTextPwCheck;
    private Button buttonSignup;
    private CheckBox checkBox1, checkBox2, checkBox3, checkBox4;
    private TextView learn_more1, learn_more2, learn_more3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextUserName = findViewById(R.id.name);
        editTextBirthday = findViewById(R.id.birthdate);
        editTextPhone = findViewById(R.id.phone);
        editTextId = findViewById(R.id.id);
        editTextPw = findViewById(R.id.password);
        editTextPwCheck = findViewById(R.id.passwordCheck);

        buttonSignup = findViewById(R.id.signup);

        checkBox1 = findViewById(R.id.checkBox1); // 전체 선택 체크박스
        checkBox2 = findViewById(R.id.checkBox2);
        checkBox3 = findViewById(R.id.checkBox3);
        checkBox4 = findViewById(R.id.checkBox4);

        learn_more1 = findViewById(R.id.learn_more1);
        learn_more2 = findViewById(R.id.learn_more2);
        learn_more3 = findViewById(R.id.learn_more3);

        checkBox1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                checkBox2.setChecked(true);
                checkBox3.setChecked(true);
                checkBox4.setChecked(true);
            }
            else {
                checkBox2.setChecked(false);
                checkBox3.setChecked(false);
                checkBox4.setChecked(false);
            }
        });

        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkBox2.isChecked() || !checkBox3.isChecked() || !checkBox4.isChecked()) {
                    Toast.makeText(RegisterActivity.this, "모든 약관에 동의해야 회원가입이 가능합니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                registerUser();
            }
        });

        learn_more1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        learn_more2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        learn_more3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    private void registerUser() {
        String username = editTextUserName.getText().toString().trim();
        String birthday = editTextBirthday.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();
        String id = editTextId.getText().toString().trim();
        String pw = editTextPw.getText().toString().trim();
        String pwCheck = editTextPwCheck.getText().toString().trim();

        if (!pw.equals(pwCheck)) {
            Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (username.isEmpty() || birthday.isEmpty() || phone.isEmpty() ||  id.isEmpty() || pw.isEmpty()) {
            Toast.makeText(this, "모든 필드를 입력하세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        Log.d("RegisterActivity", "Name: " + username);
        Log.d("RegisterActivity", "Birthdate: " + birthday);
        Log.d("RegisterActivity", "Phone: " + phone);
        Log.d("RegisterActivity", "ID: " + id);
        Log.d("RegisterActivity", "Password: " + pw);

        String url = "http://www.all-tafp.org/SPPB_register.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("RegisterActivity", "Response: " + response);

                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            String message = jsonResponse.getString("message");
                            Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
                            if (success) {
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegisterActivity.this, "서버 오류", Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("birthday", birthday);
                params.put("phone", phone);
                params.put("id", id);
                params.put("pw", pw);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
