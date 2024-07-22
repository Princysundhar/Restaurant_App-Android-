package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class custom_view_nearest_restaurant extends BaseAdapter {
    String[]rid,name,email,contact,latitude,longitude;
    private Context context;



    public custom_view_nearest_restaurant(Context applicationContext, String[] rid, String[] name, String[] email, String[] contact, String[] latitude, String[] longitude) {
        this.context = applicationContext;
        this.rid = rid;
        this.name = name;
        this.email = email;
        this.contact = contact;
        this.latitude = latitude;
        this.longitude = longitude;
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
        LayoutInflater inflator=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        if(view==null)
        {
            gridView=new View(context);
            //gridView=inflator.inflate(R.layout.customview, null);
            gridView=inflator.inflate(R.layout.activity_custom_view_nearest_restaurant,null);

        }
        else
        {
            gridView=(View)view;

        }
        TextView tv1=(TextView)gridView.findViewById(R.id.textView5);
        TextView tv2=(TextView)gridView.findViewById(R.id.textView7);
        TextView tv3=(TextView)gridView.findViewById(R.id.textView9);
        Button b1 = (Button)gridView.findViewById(R.id.button4);
        Button b2 = (Button)gridView.findViewById(R.id.button8);
        Button b3 = (Button)gridView.findViewById(R.id.button9);
        Button b4 = (Button)gridView.findViewById(R.id.button21);
        b1.setTag(i);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int ik = (int)view.getTag();
                String url = "http://maps.google.com/?q=" + latitude[ik] + "," + longitude[ik];
                Intent i = new Intent(android.content.Intent.ACTION_VIEW,  Uri.parse(url));
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);

            }
        });
//        b2.setTag(i);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                int pos = (int)view.getTag();
                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
                SharedPreferences.Editor ed = sh.edit();
                ed.putString("rid",rid[i]);
                ed.commit();
                Intent i = new Intent(context.getApplicationContext(),view_rating_and_command.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);

            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                int pos = (int)view.getTag();
                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
                SharedPreferences.Editor ed = sh.edit();
                ed.putString("rid",rid[i]);
                ed.commit();
                Intent i = new Intent(context.getApplicationContext(),view_menu.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);

            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                int pos = (int)view.getTag();
                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
                String lid = sh.getString("lid","");
                String sid = sh.getString("sid","");
                SharedPreferences.Editor ed = sh.edit();
                ed.putString("rid",rid[i]);
                ed.putString("lid",lid);
                ed.putString("sid",sid);
                ed.commit();
                Intent i = new Intent(context.getApplicationContext(),view_shared_information.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);

            }
        });



        tv1.setTextColor(Color.BLACK);
        tv2.setTextColor(Color.BLACK);
        tv3.setTextColor(Color.BLACK);


        tv1.setText(name[i]);
        tv2.setText(email[i]);
        tv3.setText(contact[i]);




        return gridView;

    }
}