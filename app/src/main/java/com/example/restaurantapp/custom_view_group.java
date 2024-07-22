package com.example.restaurantapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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

public class custom_view_group extends BaseAdapter {
    String[] group_id, group_name, user_name, user_contact,image,permission;
    private Context context;

    public custom_view_group(Context applicationContext, String[] group_id, String[] group_name, String[] user_name, String[] user_contact, String[] image, String[] permission) {
        this.context = applicationContext;
        this.group_id = group_id;
        this.group_name = group_name;
        this.user_name = user_name;
        this.user_contact = user_contact;
        this.image = image;
        this.permission = permission;
    }

    @Override
    public int getCount() {
        return group_name.length;
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
            gridView = inflator.inflate(R.layout.activity_custom_view_group, null);
        } else {
            gridView = (View) view;
        }

        TextView tv1 = (TextView) gridView.findViewById(R.id.textView57);
        TextView tv2 = (TextView) gridView.findViewById(R.id.textView54);
        TextView tv3 = (TextView) gridView.findViewById(R.id.textView56);
        ImageView img = (ImageView) gridView.findViewById(R.id.imageView7);
        Button b1 = (Button) gridView.findViewById(R.id.button17);
        Button b2 = (Button) gridView.findViewById(R.id.button18);
        Button b3 = (Button) gridView.findViewById(R.id.button20);
        Button b4 = (Button) gridView.findViewById(R.id.button22);

        if(permission[i].equalsIgnoreCase("member")){
            b4.setVisibility(View.INVISIBLE);
            b1.setVisibility(View.INVISIBLE);

        }




        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
                SharedPreferences.Editor ed = sh.edit();
                ed.putString("group_id",group_id[i]);
                ed.commit();
                Intent i = new Intent(context.getApplicationContext(),edit_group.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
                SharedPreferences.Editor ed = sh.edit();
                String lid = sh.getString("lid","");
                ed.putString("group_id",group_id[i]);
                ed.putString("lid",lid);
                ed.putString("permission",permission[i]);
                ed.commit();
                Intent i = new Intent(context.getApplicationContext(),view_member.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
                SharedPreferences.Editor ed = sh.edit();
                ed.putString("group_id",group_id[i]);
                ed.commit();
                Intent i = new Intent(context.getApplicationContext(),view_message.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);

            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
                String url = sh.getString("url", "") + "and_remove_grp";

                RequestQueue requestQueue = Volley.newRequestQueue(context);
                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObj = new JSONObject(response);
                                    if (jsonObj.getString("status").equalsIgnoreCase("ok")) {
                                        Toast.makeText(context, "Group removed", Toast.LENGTH_SHORT).show();
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

                        params.put("group_id", group_id[i]);

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
        tv3.setTextColor(Color.BLACK);

        tv1.setText(group_name[i]);
        tv2.setText(user_name[i]);
        tv3.setText(user_contact[i]);

        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context);
        String ip=sh.getString("ipaddress","");

        String url="http://" + ip + ":5000"+image[i];


        Picasso.with(context).load(url).transform(new CircleTransform()). into(img);

        return gridView;


    }
}
