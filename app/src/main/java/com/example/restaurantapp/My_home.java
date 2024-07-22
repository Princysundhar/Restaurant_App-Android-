package com.example.restaurantapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class My_home extends AppCompatActivity {
    ImageView logout;
    CardView c_restaurant,c_changepassword,c_send_feedback,c_view_cart,c_order,c_group,c_request;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_my_home);
        c_restaurant =(CardView)findViewById(R.id.c2);
        c_changepassword =(CardView)findViewById(R.id.c4);
        c_view_cart =(CardView)findViewById(R.id.c5);
        c_group =(CardView)findViewById(R.id.c7);
        c_order =(CardView)findViewById(R.id.c8);
        c_send_feedback =(CardView)findViewById(R.id.c9);
        logout = findViewById(R.id.imageView3);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor ed = sh.edit();
                ed.commit();
                ed.clear();
                Intent i = new Intent(getApplicationContext(),login.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });

        c_restaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),view_nearest_restaurant.class);
                startActivity(i);
            }
        });
        c_changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),change_password.class);
                startActivity(i);
            }
        });
        c_send_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),send_feedback.class);
                startActivity(i);
            }
        });
        c_view_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),view_cart.class);
                startActivity(i);
            }
        });
        c_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),view_order.class);
                startActivity(i);
            }
        });
        c_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),view_group.class);
                startActivity(i);
            }
        });
    }
}