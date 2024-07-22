package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    EditText e1, e2, e3, e4, e5, e6, e7, e8, e9, e10;
    Button b1;
    String MobilePattern = "[6-9][0-9]{9}";
    String PinPattern = "[6-9][0-9]{5}";
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String password_pattern = "[A-Za-z0-9]{3,8}";
    ImageView img;
    ProgressDialog pd;
    Bitmap bitmap = null;
    boolean imageSelected = false; // Track if an image is selected

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        e1 = findViewById(R.id.editTextTextPersonName);
        e2 = findViewById(R.id.editTextTextEmailAddress2);
        e3 = findViewById(R.id.editTextPhone);
        e4 = findViewById(R.id.editTextTextPersonName2);
        e5 = findViewById(R.id.editTextTextPersonName3);
        e6 = findViewById(R.id.editTextTextPersonName4);
        e7 = findViewById(R.id.editTextTextPersonName5);
        e8 = findViewById(R.id.editTextTextPersonName6);
        e9 = findViewById(R.id.editTextTextPassword2);
        e10 = findViewById(R.id.editTextTextPassword3);
        img = findViewById(R.id.imageView9);

        e7.setText(gpstracker.lati);
        e8.setText(gpstracker.longi);
        e7.setEnabled(false);
        e8.setEnabled(false);
        b1 = findViewById(R.id.button3);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 100);
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = e1.getText().toString();
                String email = e2.getText().toString();
                String contact = e3.getText().toString();
                String place = e4.getText().toString();
                String post = e5.getText().toString();
                String pin = e6.getText().toString();
                String latitude = e7.getText().toString();
                String longitude = e8.getText().toString();
                String password = e9.getText().toString();
                String confirm_password = e10.getText().toString();
                int flag=0;
                if(name.equalsIgnoreCase("")){
                    e1.setError("Required");
                    flag++;
                }
                if(!email.matches(emailPattern)){
                    e2.setError("Enter email with valid pattern");
                    flag++;
                }
                if(!contact.matches(MobilePattern)){
                    e3.setError("Enter contact with 10 digits");
                    flag++;
                }
                if(place.equalsIgnoreCase("")){
                    e4.setError("Required");
                    flag++;
                }
                if(post.equalsIgnoreCase("")){
                    e5.setError("Required");
                    flag++;
                }
                if(!pin.matches(PinPattern)){
                    e6.setError("Enter pin number with 6 digits");
                    flag++;
                }
                if(!password.matches(password_pattern)){
                    e9.setError("Enter password with valid pattern");
                    flag++;
                }
                if(!password.matches(confirm_password)){
                    e10.setError("Doesnt match");
                    flag++;
                }
                if(bitmap == null){
                    Toast.makeText(Register.this, "Choose profile picture", Toast.LENGTH_SHORT).show();
                    flag++;
                }
                if(flag == 0) {
                    uploadBitmap(name, email, contact, place, post, pin, latitude, longitude, password, confirm_password);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                img.setImageBitmap(bitmap);
                imageSelected = true; // Image is selected
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Converting bitmap to byte array
    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private void uploadBitmap(final String name, final String email, final String contact, final String place, final String post, final String pin,final String latitude,final String longitude, final String password, final String confirm_password) {
        pd = new ProgressDialog(Register.this);
        pd.setMessage("Uploading....");
        pd.show();
        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String url = sh.getString("url", "") + "and_user_register";
        Log.d(url, "");

        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, url,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            pd.dismiss();
                            JSONObject obj = new JSONObject(new String(response.data));

                            if (obj.getString("status").equals("ok")) {
                                Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(), view_group.class);
                                startActivity(i);
                            } else {
                                Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                SharedPreferences o = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                params.put("name", name);
                params.put("email", email);
                params.put("contact", contact);
                params.put("place", place);
                params.put("post", post);
                params.put("pin", pin);
                params.put("latitude", gpstracker.lati);
                params.put("longitude", gpstracker.longi);
                params.put("password", password);
                params.put("confirm_password", confirm_password);
                params.put("lid", sh.getString("lid", ""));

                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                if (imageSelected) { // Only upload if an image is selected
                    long imagename = System.currentTimeMillis();
                    params.put("pic", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                }
                return params;
            }
        };

        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }
}
