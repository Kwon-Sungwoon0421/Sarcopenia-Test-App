package com.example.sppbtest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class LoginActivity extends AppCompatActivity {

    private EditText editTextId, editTextPassword;
    private Button buttonLogin;
    private TextView sign, id_search, pw_search;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextId = findViewById(R.id.id);
        editTextPassword = findViewById(R.id.password);
        buttonLogin = findViewById(R.id.login);
        sign = findViewById(R.id.sign);
        id_search = findViewById(R.id.id_search);
        pw_search = findViewById(R.id.pw_search);

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        id_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ID_SearchActivity.class);
                startActivity(intent);
            }
        });

        pw_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, PW_SearchActivity.class);
                startActivity(intent);
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
    }

    private void loginUser() {
        String id = editTextId.getText().toString().trim();
        String pw = editTextPassword.getText().toString().trim();

        if (id.isEmpty() || pw.isEmpty()) {
            Toast.makeText(this, "ID와 비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = "http://www.all-tafp.org/SPPB_login.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            String message = jsonResponse.getString("message");
                            Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                            if (success) {
                                // saveUserName 메소드에 이름 넘겨줌
                                String getname = jsonResponse.getString("username");  // 서버에서 반환된 사용자 이름
                                saveUserName(getname);

                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, "서버 오류", Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", id);
                params.put("pw", pw);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    // 받은 이름을 통해 SharedPreferences에 사용자 이름 저장
    private void saveUserName(String getname) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserName", MODE_PRIVATE); // sharedPreferences 객체 생성
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", getname);  // "username"에 사용자 이름을 저장
        editor.apply();
    }
}
