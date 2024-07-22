package com.example.restaurantapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class custom_view_member extends BaseAdapter {
    String[] member_id, name, contact,status,user_image;
    private Context context;

    public custom_view_member(Context applicationContext, String[] member_id, String[] name, String[] contact,String[] status,String[] user_image) {
        this.context = applicationContext;
        this.member_id = member_id;
        this.name = name;
        this.contact = contact;
        this.status = status;
        this.user_image = user_image;
    }

    @Override
    public int getCount() {
        return name.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        if (view == null) {
            gridView = new View(context);
            gridView = inflator.inflate(R.layout.activity_custom_view_member, null);
        } else {
            gridView = view;
        }

        TextView tv1 = (TextView) gridView.findViewById(R.id.textView57);
        TextView tv2 = (TextView) gridView.findViewById(R.id.textView54);
        TextView tv3 = (TextView) gridView.findViewById(R.id.textView58);
        TextView tv4 = (TextView) gridView.findViewById(R.id.textView59);
        ImageView img = (ImageView) gridView.findViewById(R.id.imageView10);



        ImageView b1 = (ImageView) gridView.findViewById(R.id.imageView8);
        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        if (sh.getString("permission","").equalsIgnoreCase("member")) {
            b1.setVisibility(View.GONE); // Hide the button if the member is an admin
            tv4.setVisibility(View.GONE);
        }


       else {
            if (status[i].equalsIgnoreCase("admin")) {
                b1.setVisibility(View.GONE); // Hide the button if the member is an admin
                tv4.setVisibility(View.GONE); // Hide the button if the member is an admin
            } else {
                b1.setVisibility(View.VISIBLE); // Show the button if the member is not an admin
            }
        }

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
                String url = sh.getString("url", "") + "and_remove_member";

                RequestQueue requestQueue = Volley.newRequestQueue(context);
                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObj = new JSONObject(response);
                                    if (jsonObj.getString("status").equalsIgnoreCase("ok")) {
                                        Toast.makeText(context, "Member removed", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(context, view_group.class);
                                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        context.startActivity(i);
                                    } else {
                                        Toast.makeText(context, "Not an admin", Toast.LENGTH_LONG).show();
                                    }
                                } catch (Exception e) {
                                    Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(context, "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() {
                        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context);
                        Map<String, String> params = new HashMap<>();

                        params.put("member_id", member_id[i]);

                        return params;
                    }
                };

                int MY_SOCKET_TIMEOUT_MS = 100000;
                postRequest.setRetryPolicy(new DefaultRetryPolicy(
                        MY_SOCKET_TIMEOUT_MS,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                requestQueue.add(postRequest);
            }
        });





        tv1.setTextColor(Color.BLACK);
        tv2.setTextColor(Color.BLACK);
        tv3.setTextColor(Color.RED);

        tv1.setText(name[i]);
        tv2.setText(contact[i]);
        tv3.setText(status[i]);

        String ip=sh.getString("ipaddress","");

        String url="http://" + ip + ":5000"+user_image[i];


        Picasso.with(context).load(url).transform(new CircleTransform()). into(img);

        return gridView;
    }
}
