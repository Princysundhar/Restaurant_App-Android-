package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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
import android.widget.TextView;

public class custom_view_upcoming_booking extends BaseAdapter {
    String[]order_id,amount,date,status,delivery_status,payment_status,payment_date,restaurant_info;
    private Context context;


    public custom_view_upcoming_booking(Context applicationContext, String[] order_id, String[] amount, String[] date, String[] status, String[] delivery_status, String[] payment_status, String[] payment_date, String[] restaurant_info) {
        this.context = applicationContext;
        this.order_id = order_id;
        this.amount = amount;
        this.date = date;
        this.status = status;
        this.delivery_status = delivery_status;
        this.payment_date = payment_date;
        this.payment_status = payment_status;
        this.restaurant_info = restaurant_info;

    }

    @Override
    public int getCount() {
        return amount.length;
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
            gridView = inflator.inflate(R.layout.activity_custom_view_upcoming_booking, null);

        } else {
            gridView = (View) view;

        }
        TextView tv1 = (TextView) gridView.findViewById(R.id.textView38);
        TextView tv2 = (TextView) gridView.findViewById(R.id.textView40);
        TextView tv3 = (TextView) gridView.findViewById(R.id.textView42);
        TextView tv4 = (TextView) gridView.findViewById(R.id.textView44);
        TextView tv5 = (TextView) gridView.findViewById(R.id.textView46);
        TextView tv6 = (TextView) gridView.findViewById(R.id.textView48);
        TextView tv7 = (TextView) gridView.findViewById(R.id.textView50);
        Button b1 = (Button) gridView.findViewById(R.id.button14);



        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
                SharedPreferences.Editor ed = sh.edit();
                ed.putString("order_id",order_id[i]);
                ed.putString("oamount",amount[i]);
                ed.commit();
                Intent i = new Intent(context.getApplicationContext(),choose_date.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });



        tv1.setTextColor(Color.BLACK);
        tv2.setTextColor(Color.BLACK);
        tv3.setTextColor(Color.BLACK);
        tv4.setTextColor(Color.BLACK);
        tv5.setTextColor(Color.BLACK);
        tv6.setTextColor(Color.BLACK);
        tv7.setTextColor(Color.BLACK);

        tv1.setText(amount[i]);
        tv2.setText(date[i]);
        tv3.setText(status[i]);
        tv4.setText(delivery_status[i]);
        tv5.setText(payment_status[i]);
        tv6.setText(payment_date[i]);
        tv7.setText(restaurant_info[i]);

        return gridView;
    }
}