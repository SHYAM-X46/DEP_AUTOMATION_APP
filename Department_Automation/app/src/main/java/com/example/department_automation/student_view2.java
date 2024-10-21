package com.example.department_automation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class student_view2 extends AppCompatActivity {
    EditText a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14,a15,a16,a17,a18,a19;
    ImageView img;
    Button b1;
    String url;
    Bitmap bitmap = null;
    SharedPreferences sh;
    ProgressDialog pd;
    RadioGroup r1;
    RadioButton s1,s2,s3;
    String gender="";

    String pinpattern = "[0-9]{6}";
    String phonepattern = "[6789][0-9]{10}";
    String emailpattern = "\"[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$\"";
    String adharpattern = "[23456789][0-9]{12}";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_view2);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        a1 = findViewById(R.id.editTextTextPersonName30);
        a2 = findViewById(R.id.editTextTextPersonName31);
        a3 = findViewById(R.id.editTextTextPersonName32);
        a4 = findViewById(R.id.editTextTextPersonName33);
        a5 = findViewById(R.id.editTextTextPersonName34);
        a6 = findViewById(R.id.editTextTextPersonName35);
        a7 = findViewById(R.id.editTextTextPersonName36);
        a8 = findViewById(R.id.editTextTextPersonName37);
        a9 = findViewById(R.id.editTextTextPersonName38);
        a10 = findViewById(R.id.editTextTextPersonName39);
        a11 = findViewById(R.id.editTextTextPersonName40);
        a12 = findViewById(R.id.editTextTextPersonName41);
        a13 = findViewById(R.id.editTextTextPersonName42);
        a14 = findViewById(R.id.editTextTextPersonName43);
        a15 = findViewById(R.id.editTextTextPersonName44);
        a16 = findViewById(R.id.editTextTextPersonName45);
        a17 = findViewById(R.id.editTextTextPersonName46);
        a18 = findViewById(R.id.editTextTextPersonName47);
        a19 = findViewById(R.id.editTextTextPersonName48);
        r1 = findViewById(R.id.radioGroup3);
        s1 = findViewById(R.id.radioButton10);
        s2 = findViewById(R.id.radioButton8);
        s3 = findViewById(R.id.radioButton7);
        img = findViewById(R.id.imageView4);
        b1 = findViewById(R.id.button13);

        url = sh.getString("url","")+"/andview_studentprofile";
        Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show();

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //  Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            if (jsonObj.getString("status").equalsIgnoreCase("ok")) {
                                a1.setText(jsonObj.getString("firstname"));
                                a2.setText(jsonObj.getString("secondname"));
                                a3.setText(jsonObj.getString("phnno"));
                                a4.setText(jsonObj.getString("registerno"));
                                a5.setText(jsonObj.getString("email"));
                                a6.setText(jsonObj.getString("fathername"));
                                a7.setText(jsonObj.getString("mothername"));
                                a8.setText(jsonObj.getString("housename"));
                                a9.setText(jsonObj.getString("place"));
                                a10.setText(jsonObj.getString("pin"));
                                a11.setText(jsonObj.getString("post"));
                                a12.setText(jsonObj.getString("district"));
                                a13.setText(jsonObj.getString("state"));
                                a14.setText(jsonObj.getString("aadhaarno"));
                                a15.setText(jsonObj.getString("identimark"));
                                a16.setText(jsonObj.getString("joinedclub"));
                                a17.setText(jsonObj.getString("bloodgrp"));
                                a18.setText(jsonObj.getString("licno"));
                                a19.setText(jsonObj.getString("abt"));

                                String g = jsonObj.getString("gender");

                                if (g.equalsIgnoreCase("Male")){
                                    s1.setChecked(true);
                                }
                                else if(g.equalsIgnoreCase("Female")){
                                    s2.setChecked(true);

                                }
                                else {
                                    s3.setChecked(true);
                                }


                                String image = jsonObj.getString("image");
                                sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                sh.getString("url","");
                                String url = sh.getString("url", "")+image;
                                Picasso.with(getApplicationContext()).load(url).transform(new CircleTransform()).into(img);
//
                            } else {
                                Toast.makeText(getApplicationContext(), "Not found", Toast.LENGTH_LONG).show();
                            }

                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Error" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Toast.makeText(getApplicationContext(), "error" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        ) {

            //                value Passing android to python
            @Override
            protected Map<String, String> getParams() {
                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                Map<String, String> params = new HashMap<String, String>();
                params.put("sid", sh.getString("sid", ""));//passing to python
                return params;
            }
        };


        int MY_SOCKET_TIMEOUT_MS = 100000;

        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(postRequest);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 100);
            }
        });


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String upd1=a1.getText().toString();
                String upd2=a2.getText().toString();
                String upd3=a3.getText().toString();
                String upd4=a4.getText().toString();
                String upd5=a5.getText().toString();
                String upd6=a6.getText().toString();
                String upd7=a7.getText().toString();
                String upd8=a8.getText().toString();
                String upd9=a9.getText().toString();
                String upd10=a10.getText().toString();
                String upd11=a11.getText().toString();
                String upd12=a12.getText().toString();
                String upd13=a13.getText().toString();
                String upd14=a14.getText().toString();
                String upd15=a15.getText().toString();
                String upd16=a16.getText().toString();
                String upd17=a17.getText().toString();
                String upd18=a18.getText().toString();
                String upd19=a19.getText().toString();

                if (s1.isChecked()){
                    gender = "male";
                }
                if (s2.isChecked()){
                    gender = "female";
                }if (s3.isChecked()){
                    gender = "others";
                }
                int flag=0;
                if(upd1.equalsIgnoreCase("")){
                    a1.setError("Enter the Firstname");
                    flag++;
                }
                if(upd2.equalsIgnoreCase("")){
                    a2.setError("Enter the Secondname");
                    flag++;
                }if(upd4.equalsIgnoreCase("")){
                    a4.setError("Enter the Register Number");
                    flag++;
                }if(upd6.equalsIgnoreCase("")){
                    a6.setError("Enter the Father Name");
                    flag++;
                }if(upd7.equalsIgnoreCase("")){
                    a7.setError("Enter the Mother Name");
                    flag++;
                }if(upd8.equalsIgnoreCase("")){
                    a8.setError("Enter the House Name");
                    flag++;
                }if(upd9.equalsIgnoreCase("")){
                    a9.setError("Enter the Place");
                    flag++;
                }if(upd11.equalsIgnoreCase("")){
                    a11.setError("Enter the Post");
                    flag++;
                }if(upd12.equalsIgnoreCase("")){
                    a12.setError("Enter the District");
                    flag++;
                }if(upd13.equalsIgnoreCase("")){
                    a13.setError("Enter the State");
                    flag++;
                }if(upd15.equalsIgnoreCase("")){
                    a15.setError("Enter the Identification");
                    flag++;
                }if(upd17.equalsIgnoreCase("")){
                    a17.setError("Enter the Blood group");
                    flag++;
                }
                if(!upd10.matches(pinpattern)){
                    a10.setError("Enter a valid pincode");
                    flag++;
                }if(!upd3.matches(phonepattern)){
                    a3.setError("Enter a valid phone number");
                    flag++;
                }if(!upd14.matches(adharpattern)){
                    a14.setError("Enter a valid aadhar number");
                    flag++;
                }if(!upd5.matches(emailpattern)){
                    a5.setError("Enter a valid email");
                    flag++;
                }

                uploadBitmap(upd1,upd2,upd3,upd4,upd5,upd6,upd7,upd8,upd9,upd10,upd11,upd12,upd13,upd14,upd15,upd16,upd17,upd18,upd19);
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


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    //converting to bitarray
    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
    private void uploadBitmap(final String upd1, final String upd2, final String upd3, final String upd4, final String upd5, final String upd6,final String upd7,final String upd8,final String upd9,final String upd10,final String upd11,final String upd12,final String upd13,final String upd14,final String upd15,final String upd16,final String upd17,final String upd18,final String upd19) {


        pd = new ProgressDialog(student_view2.this);
        pd.setMessage("Uploading....");
        pd.show();
        url = sh.getString("url", "")+"/andupdate_studentprofile";

        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, url,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            pd.dismiss();


                            JSONObject obj = new JSONObject(new String(response.data));

                            if(obj.getString("status").equals("ok")){
                                Toast.makeText(getApplicationContext(), "profile updated", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(), home.class);
                                startActivity(i);
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Registration failed" ,Toast.LENGTH_SHORT).show();
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
                params.put("upd1",upd1 );//passing to python
                params.put("upd2",upd2 );//passing to python
                params.put("upd3",upd3 );
                params.put("upd4",upd4 );
                params.put("upd5",upd5 );
                params.put("upd6",upd6 );
                params.put("upd7",upd7 );
                params.put("upd8",upd8 );
                params.put("upd9",upd9 );
                params.put("upd10",upd10 );
                params.put("upd11",upd11 );
                params.put("upd12",upd12 );
                params.put("upd13",upd13 );
                params.put("upd14",upd14 );
                params.put("upd15",upd15 );
                params.put("upd16",upd16 );
                params.put("upd17",upd17 );
                params.put("upd18",upd18 );
                params.put("upd19",upd19 );
                params.put("gender",gender );

                params.put("lid", sh.getString("lid", ""));
                params.put("sid", sh.getString("sid", ""));

                return params;
            }


            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("pic", new DataPart(imagename + ".jpg", getFileDataFromDrawable(bitmap)));
                return params;
            }
        };

        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }

}


