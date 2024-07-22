package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
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

import java.util.HashMap;
import java.util.Map;

public class view_cart extends AppCompatActivity {
    ListView li;
    TextView tv1;
    Button b1;
    String[] cart_id, quantity, userinfo, menu_name, menu_amount, menu_image, restaurant_info;
//    String type = "pre-booking";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cart);
        li = findViewById(R.id.listview);
        tv1 = findViewById(R.id.textView25);
        b1 = findViewById(R.id.button12);

        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        sh.getString("ipaddress", "");
        String url = sh.getString("url", "") + "and_view_cart";

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            if (jsonObj.getString("status").equalsIgnoreCase("ok")) {
                                String amount = jsonObj.getString("t");
                                SharedPreferences.Editor ed = sh.edit();
                                ed.putString("amount", amount);
                                ed.commit();
                                tv1.setText(amount);
                                tv1.setTextColor(Color.RED);

                                JSONArray js = jsonObj.getJSONArray("data");
                                cart_id = new String[js.length()];
                                quantity = new String[js.length()];
                                userinfo = new String[js.length()];
                                menu_name = new String[js.length()];
                                menu_amount = new String[js.length()];
                                menu_image = new String[js.length()];
                                restaurant_info = new String[js.length()];

                                for (int i = 0; i < js.length(); i++) {
                                    JSONObject u = js.getJSONObject(i);
                                    cart_id[i] = u.getString("cart_id");
                                    quantity[i] = u.getString("quantity");
                                    userinfo[i] = u.getString("username") + "\n" + u.getString("contact");
                                    menu_name[i] = u.getString("menu_name");
                                    menu_amount[i] = u.getString("menu_amount");
                                    menu_image[i] = u.getString("menu_image");
                                    restaurant_info[i] = u.getString("res_name") + "\n" + u.getString("res_contact");
                                }

                                li.setAdapter(new custom_view_cart(getApplicationContext(), cart_id, quantity, userinfo, menu_name, menu_amount, menu_image, restaurant_info));
                            } else {
                                Toast.makeText(getApplicationContext(), "Not found", Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Toast.makeText(getApplicationContext(), "Error" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                Map<String, String> params = new HashMap<>();
                params.put("lid", sh.getString("lid", ""));
                return params;
            }
        };

        int MY_SOCKET_TIMEOUT_MS = 100000;

        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(postRequest);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//                String order_id = sh.getString("order_id", "");

                new AlertDialog.Builder(view_cart.this)
                        .setTitle("Choose Your Booking Style")
                        .setMessage("Do you need pre-booking?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                SharedPreferences.Editor ed = sh.edit();
                                ed.putString("type","Pre-booking");
                                ed.putString("method","online");
                                ed.commit();
                                Intent intent = new Intent(getApplicationContext(), choose_date.class);
                                startActivity(intent);

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                SharedPreferences.Editor ed = sh.edit();
                                ed.putString("type","current booking");
                                ed.commit();
                                Intent intent = new Intent(getApplicationContext(), payment_mode.class);
                                startActivity(intent);
                            }
                        })
                        .show();
            }

        });

    }


}
