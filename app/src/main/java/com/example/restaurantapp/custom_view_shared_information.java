package com.example.restaurantapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class custom_view_shared_information extends BaseAdapter {
    private Context context;
    private List<Boolean> checkedList;
    private String[] sid, user_name, group_icon, group_name;
    private Button commonShareButton;

    public custom_view_shared_information(Context applicationContext, String[] sid, String[] group_name, String[] user_name, String[] group_icon, Button commonShareButton) {
        this.context = applicationContext;
        this.sid = sid;
        this.user_name = user_name;
        this.group_icon = group_icon;
        this.group_name = group_name;
        this.checkedList = new ArrayList<>(sid.length);
        for (int i = 0; i < sid.length; i++) {
            checkedList.add(false);
        }
        this.commonShareButton = commonShareButton;
        updateShareButtonVisibility();
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            view = inflater.inflate(R.layout.activity_custom_view_shared_information, null);
        }

        TextView tv1 = view.findViewById(R.id.groupName);
        TextView tv2 = view.findViewById(R.id.memberList);
        ImageView img1 = view.findViewById(R.id.groupIcon);
        CheckBox groupCheckbox = view.findViewById(R.id.groupCheckbox);

        groupCheckbox.setTag(i);

        groupCheckbox.setChecked(checkedList.get(i));

        groupCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            int position = (int) buttonView.getTag();
            checkedList.set(position, isChecked);

            SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
            SharedPreferences.Editor ed = sh.edit();
            Set<String> selectedIds = sh.getStringSet("selected_ids", new HashSet<>());
            String selectedId = sid[position];

            if (isChecked) {
                selectedIds.add(selectedId);
            } else {
                selectedIds.remove(selectedId);
            }

            ed.putStringSet("selected_ids", selectedIds);
            ed.apply();

            Toast.makeText(context, "Selected IDs: " + selectedIds, Toast.LENGTH_SHORT).show();
            updateShareButtonVisibility();
        });

        tv1.setTextColor(Color.BLACK);
        tv2.setTextColor(Color.BLACK);

        tv1.setText(group_name[i]); // Set group name

        // Concatenate user names for this group
        StringBuilder members = new StringBuilder();
        for (int j = 0; j < user_name.length; j++) {
            if (group_name[i].equals(group_name[j])) {
                members.append(user_name[j]);
                members.append(", ");
            }
        }
        // Remove the trailing comma and space if there are any members
        if (members.length() > 2) {
            members.setLength(members.length() - 2);
        }

        tv2.setText(members.toString());

        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context);
        String ip = sh.getString("ipaddress", "");
        String imageUrl = "http://" + ip + ":5000" + group_icon[i];
        Picasso.with(context).load(imageUrl).transform(new CircleTransform()).into(img1);

        return view;
    }

    // Method to update share button visibility based on checkedList
    private void updateShareButtonVisibility() {
        boolean anyChecked = false;
        for (boolean isChecked : checkedList) {
            if (isChecked) {
                anyChecked = true;
                break;
            }
        }
        if (anyChecked) {
            commonShareButton.setVisibility(View.VISIBLE);
        } else {
            commonShareButton.setVisibility(View.GONE);
        }
    }
}
