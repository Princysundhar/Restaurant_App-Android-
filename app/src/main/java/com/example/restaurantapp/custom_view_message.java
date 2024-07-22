package com.example.restaurantapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.example.restaurantapp.CircleTransform;
import com.example.restaurantapp.R;
import com.squareup.picasso.Picasso;

public class custom_view_message extends BaseAdapter {
    String[] message_id, username, restaurant_name, message, type, image;
    private Context context;

    public custom_view_message(Context applicationContext, String[] message_id, String[] username, String[] restaurant_name, String[] message, String[] type, String[] image) {
        this.context = applicationContext;
        this.message_id = message_id;
        this.username = username;
        this.restaurant_name = restaurant_name;
        this.message = message;
        this.type = type;
        this.image = image;
    }

    @Override
    public int getCount() {
        return restaurant_name.length;
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
            gridView = inflator.inflate(R.layout.activity_custom_view_message, null);
        } else {
            gridView = view;
        }

        TextView tv1 = gridView.findViewById(R.id.text2);
        TextView tv2 = gridView.findViewById(R.id.text3);
        TextView tv3 = gridView.findViewById(R.id.text2_2);
        TextView tv4 = gridView.findViewById(R.id.text3_2);
        ImageView im = gridView.findViewById(R.id.imageView13);
        ImageView im2 = gridView.findViewById(R.id.imageView11);

        tv1.setTextColor(Color.BLACK);
        tv2.setTextColor(Color.BLACK);
        tv3.setTextColor(Color.BLACK);
        tv4.setTextColor(Color.BLACK);

        CardView cardView1 = gridView.findViewById(R.id.c2);
        CardView cardView2 = gridView.findViewById(R.id.c3);

        if (type[i].equals("receive")) {
            cardView1.setVisibility(View.VISIBLE);
            cardView2.setVisibility(View.GONE);
            tv1.setText(username[i]);
            tv2.setText(message[i]);
            if(image[i].equalsIgnoreCase("no"))
            {

            }
            else {
                ViewGroup.LayoutParams layoutParams = im.getLayoutParams();
                int imageSizeInDp = (int) context.getResources().getDisplayMetrics().density * 100; // Convert dp to pixels
                layoutParams.width = imageSizeInDp;
                layoutParams.height = imageSizeInDp;
                im.setLayoutParams(layoutParams);

                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context);
                String ip = sh.getString("ipaddress", "");
                String url = "http://" + ip + ":5000" + image[i];

                Picasso.with(context).load(url).transform(new CircleTransform()).into(im);
            }

            // Set image view height and width programmatically

        } else {
            cardView1.setVisibility(View.GONE);
            cardView2.setVisibility(View.VISIBLE);
            tv3.setText(username[i]);
            tv4.setText(message[i]);

            // Set image view height and width programmatically
            if(image[i].equalsIgnoreCase("no"))
            {

            }
            else {
                ViewGroup.LayoutParams layoutParams = im2.getLayoutParams();
                int imageSizeInDp = (int) context.getResources().getDisplayMetrics().density * 100; // Convert dp to pixels
                layoutParams.width = imageSizeInDp;
                layoutParams.height = imageSizeInDp;
                im.setLayoutParams(layoutParams);

                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context);
                String ip = sh.getString("ipaddress", "");
                String url = "http://" + ip + ":5000" + image[i];

                Picasso.with(context).load(url).transform(new CircleTransform()).into(im2);
            }
        }

        return gridView;
    }
}
