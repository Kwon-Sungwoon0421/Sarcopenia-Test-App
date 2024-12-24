package com.example.sppbtest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ID_SearchActivity extends AppCompatActivity {
    private EditText ETname, ETphone;
    private Button search, back;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id_search);

        ETname = findViewById(R.id.name);
        ETphone = findViewById(R.id.phone);

        search = findViewById(R.id.search);
        back = findViewById(R.id.back);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = ETname.getText().toString().trim();
                String phone = ETphone.getText().toString().trim();

                if (username.isEmpty() || phone.isEmpty()) {
                    showAlert("모든 필드를 입력해주세요.");
                    return;
                }

                searchID(username, phone);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ID_SearchActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    private void searchID(String username, String phone) {
        String url = "http://www.all-tafp.org/SPPB_find_id.php";

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                String id = jsonResponse.getString("id");
                                Intent intent = new Intent(ID_SearchActivity.this, ID_Search_ResultActivity.class);
                                intent.putExtra("found_id", id);
                                startActivity(intent);
                            } else {
                                String message = jsonResponse.getString("message");
                                showAlert(message);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            showAlert("응답 처리 중 오류가 발생했습니다.");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        showAlert("서버 요청 중 오류가 발생했습니다.");
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("phone", phone);
                return params;
            }
        };

        queue.add(request);
    }

    private void showAlert(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setPositiveButton("확인", (dialog, which) -> dialog.dismiss())
                .show();
    }
}
