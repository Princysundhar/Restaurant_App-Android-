package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class change_password extends AppCompatActivity {
    EditText e1,e2,e3;
    Button b1;
    String password_pattern="[A-Za-z0-9]{3,8}";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        e1 = findViewById(R.id.editTextTextPassword4);
        e2 = findViewById(R.id.editTextTextPassword5);
        e3 = findViewById(R.id.editTextTextPassword6);
        b1 = findViewById(R.id.button5);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String c_password = e1.getText().toString();
                String new_password = e2.getText().toString();
                String confirm_password = e3.getText().toString();
                int flag=0;
                if(c_password.equalsIgnoreCase("")){
                    e1.setError("Required");
                    flag++;
                }
                if(!new_password.matches(password_pattern)){
                    e2.setError("Enter password with valid pattern");
                    flag++;
                }
                if(!confirm_password.matches(new_password)){
                    e2.setError("Doesnt Match");
                    flag++;
                }
                if(flag==0){
                    SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    sh.getString("ipaddress","");
                    String url = sh.getString("url","") +"and_change_password";

                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    //  Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                                    // response
                                    try {
                                        JSONObject jsonObj = new JSONObject(response);
                                        if (jsonObj.getString("status").equalsIgnoreCase("ok")) {
                                            Toast.makeText(change_password.this, "Updated", Toast.LENGTH_SHORT).show();
                                            Intent i = new Intent(getApplicationContext(),My_home.class);
                                            startActivity(i);

                                        }else if (jsonObj.getString("status").equalsIgnoreCase("no")) {
                                            Toast.makeText(change_password.this, "Not updated", Toast.LENGTH_SHORT).show();
                                            Intent i = new Intent(getApplicationContext(),My_home.class);
                                            startActivity(i);

                                        }


                                        // }
                                        else {
                                            Toast.makeText(getApplicationContext(), "Not found", Toast.LENGTH_LONG).show();
                                        }

                                    }    catch (Exception e) {
                                        Toast.makeText(getApplicationContext(), "Error" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // error
                                    Toast.makeText(getApplicationContext(), "eeeee" + error.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                    ) {
                        @Override
                        protected Map<String, String> getParams() {
                            SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            Map<String, String> params = new HashMap<String, String>();

                            params.put("old",c_password);
                            params.put("new",new_password);
                            params.put("confirm",confirm_password);
                            params.put("lid",sh.getString("lid",""));

                            return params;
                        }
                    };

                    int MY_SOCKET_TIMEOUT_MS=100000;

                    postRequest.setRetryPolicy(new DefaultRetryPolicy(
                            MY_SOCKET_TIMEOUT_MS,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    requestQueue.add(postRequest);
                }
            }
        });
    }
}