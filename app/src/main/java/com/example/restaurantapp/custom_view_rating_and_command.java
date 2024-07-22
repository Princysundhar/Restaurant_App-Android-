package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

public class custom_view_rating_and_command extends BaseAdapter {
    String[] rating_id, rating, comment, date, userinfo;
    private Context context;

    public custom_view_rating_and_command(Context applicationContext, String[] rating_id, String[] rating, String[] comment, String[] date, String[] userinfo) {
        this.context = applicationContext;
        this.rating_id = rating_id;
        this.rating = rating;
        this.comment = comment;
        this.date = date;
        this.userinfo = userinfo;

    }


    @Override
    public int getCount() {
        return userinfo.length;
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
            //gridView=inflator.inflate(R.layout.customview, null);
            gridView = inflator.inflate(R.layout.activity_custom_view_rating_and_command, null);

        } else {
            gridView = (View) view;

        }
        TextView tv1 = (TextView) gridView.findViewById(R.id.textView14);
        TextView tv2 = (TextView) gridView.findViewById(R.id.textView16);
        TextView tv3 = (TextView) gridView.findViewById(R.id.textView18);
        RatingBar r1 = (RatingBar) gridView.findViewById(R.id.ratingBar2);


        tv1.setTextColor(Color.BLACK);
        tv2.setTextColor(Color.BLACK);
        tv3.setTextColor(Color.BLACK);
        r1.setRating(Float.parseFloat(rating[i]));
        r1.setEnabled(false);

        tv1.setText(comment[i]);
        tv2.setText(date[i]);
        tv3.setText(userinfo[i]);



        return gridView;
    }
}