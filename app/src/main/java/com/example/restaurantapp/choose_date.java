package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class choose_date extends AppCompatActivity {
    TextView e1;
    SharedPreferences sh;
    Button b1;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_date);
        e1 = findViewById(R.id.textView42);
        b1 = findViewById(R.id.button8);

        e1.setOnClickListener(new View.OnClickListener() {

            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR);
            int mMonth = c.get(Calendar.MONTH);
            int mDay = c.get(Calendar.DAY_OF_MONTH);

            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(choose_date.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                int a = monthOfYear + 1;

                                String mm = String.valueOf(a);
                                String mn = "0" + mm;

                                if (mm.length() == 1) {
                                    e1.setText(year + "-" + (mn) + "-" + dayOfMonth);
                                } else {
                                    e1.setText(year + "-" + (mm) + "-" + dayOfMonth);
                                }
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = e1.getText().toString();
                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor ed = sh.edit();
                ed.putString("method","online");
                ed.commit();

                int flag = 0;
                if (date.equalsIgnoreCase("")) {
                    e1.setError("Enter the date");
                    flag++;
                }
                if (flag == 0) {
                    Intent i = new Intent(getApplicationContext(), PaymentActivity.class);
                    startActivity(i);

                }
            }
        });
    }
}
