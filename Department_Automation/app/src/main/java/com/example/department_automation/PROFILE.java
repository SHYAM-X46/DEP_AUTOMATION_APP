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

public class PROFILE extends AppCompatActivity {
    EditText p1,p2,p3,p4,p5,p6,p7,p8,p9;
    RadioGroup rg;
    RadioButton m,f,o;
    String gender = "male";
    Button up;
    SharedPreferences sh;
    String url;
    ImageView img1;
    Bitmap bitmap = null;
    ProgressDialog pd;

    String pinpattern = "[0-9]{6}";
    String phonepattern = "[6789][0-9]{9}";
    String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        p1 = findViewById(R.id.editTextTextPersonName3);
        p2 = findViewById(R.id.editTextTextPersonName4);
        p3 = findViewById(R.id.editTextTextPersonName8);
        p4 = findViewById(R.id.editTextTextPersonName10);
        p5 = findViewById(R.id.editTextTextPersonName11);
        p6 = findViewById(R.id.editTextTextPersonName12);
        p7 = findViewById(R.id.editTextTextPersonName14);
        p8 = findViewById(R.id.editTextTextPersonName15);
        p9 = findViewById(R.id.editTextTextPersonName16);
        img1 = findViewById(R.id.imageView);
        rg = findViewById(R.id.radioGroup);
        m = findViewById(R.id.radioButton3);
        f = findViewById(R.id.radioButton2);
        o = findViewById(R.id.radioButton);
        up = findViewById(R.id.button3);

        url = sh.getString("url","")+"/andview_profile";

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //  Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            if (jsonObj.getString("status").equalsIgnoreCase("ok")) {
                                p1.setText(jsonObj.getString("name"));
                                p2.setText(jsonObj.getString("Phone_no"));
                                p3.setText(jsonObj.getString("Email"));
                                p4.setText(jsonObj.getString("House_name"));
                                p5.setText(jsonObj.getString("Place"));
                                p6.setText(jsonObj.getString("Pin"));
                                p7.setText(jsonObj.getString("Post"));
                                p8.setText(jsonObj.getString("District"));
                                p9.setText(jsonObj.getString("State"));

                                String g = jsonObj.getString("gender");

                                if (g.equalsIgnoreCase("Male")){
                                    m.setChecked(true);
                                }
                                else if(g.equalsIgnoreCase("Female")){
                                    f.setChecked(true);

                                }
                                else {
                                    o.setChecked(true);
                                }

                                String image = jsonObj.getString("image");
                                sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                sh.getString("url","");
                                String url = sh.getString("url", "")+image;
                                Picasso.with(getApplicationContext()).load(url).transform(new CircleTransform()).into(img1);
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
                        Toast.makeText(getApplicationContext(), "eeeee" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        ) {

            //                value Passing android to python
            @Override
            protected Map<String, String> getParams() {
                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                Map<String, String> params = new HashMap<String, String>();

                params.put("lid",sh.getString("lid",""));

                return params;
            }
        };


        int MY_SOCKET_TIMEOUT_MS = 100000;

        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(postRequest);

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 100);
            }
        });


        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(m.isChecked()){
                    String gender="male";
                }
                if(o.isChecked()){
                    String gender="others";
                }
                String upd1=p1.getText().toString();
                String upd2=p2.getText().toString();
                String upd3=p3.getText().toString();
                String upd4=p4.getText().toString();
                String upd5=p5.getText().toString();
                String upd6=p6.getText().toString();
                String upd7=p7.getText().toString();
                String upd8=p8.getText().toString();
                String upd9=p9.getText().toString();

                int flag=0;
                if(upd1.equalsIgnoreCase("")){
                    p1.setError("Enter the Name");
                    flag++;
                }if(upd4.equalsIgnoreCase("")){
                    p4.setError("Enter the house name");
                    flag++;
                }if(upd5.equalsIgnoreCase("")){
                    p5.setError("Enter the place");
                    flag++;
                }if(upd7.equalsIgnoreCase("")){
                    p7.setError("Enter the post");
                    flag++;
                }if(upd8.equalsIgnoreCase("")){
                    p8.setError("Enter the district");
                    flag++;
                }if(upd9.equalsIgnoreCase("")){
                    p9.setError("Enter the state");
                    flag++;
                }
                if(!upd6.matches(pinpattern)){
                    p6.setError("Enter a valid pincode");
                    flag++;
                }
                if(!upd2.matches(phonepattern)){
                    p2.setError("Enter a valid number");
                    flag++;
                }
                if(!upd3.matches(emailpattern)){
                    p3.setError("Enter the email correctly");
                    flag++;
                }


                if(flag==0) {
                    if(bitmap == null){
                        uploadBitmap1(upd1, upd2, upd3, upd4, upd5, upd6, upd7, upd8, upd9);
                    }
                    else {
                        uploadBitmap(upd1, upd2, upd3, upd4, upd5, upd6, upd7, upd8, upd9);
                    }

                }
            }

            private void uploadBitmap1(String upd1, String upd2, String upd3, String upd4, String upd5, String upd6, String upd7, String upd8, String upd9) {
                pd = new ProgressDialog(PROFILE.this);
                pd.setMessage("Uploading....");
                pd.show();
                url = sh.getString("url", "")+"/andupdate_profile";

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
                        params.put("gender",gender );

                        params.put("lid", sh.getString("lid", ""));
                        return params;
                    }


//                    @Override
//                    protected Map<String, DataPart> getByteData() {
//                        Map<String, DataPart> params = new HashMap<>();
//                        long imagename = System.currentTimeMillis();
//                        params.put("pic", new DataPart(imagename + ".jpg", getFileDataFromDrawable(bitmap)));
//                        return params;
//                    }
                };

                Volley.newRequestQueue(getApplicationContext()).add(volleyMultipartRequest);

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

                img1.setImageBitmap(bitmap);


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
    private void uploadBitmap(final String upd1, final String upd2, final String upd3, final String upd4, final String upd5, final String upd6,final String upd7,final String upd8,final String upd9) {


        pd = new ProgressDialog(PROFILE.this);
        pd.setMessage("Uploading....");
        pd.show();
        url = sh.getString("url", "")+"/andupdate_profile";

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
                params.put("gender",gender );

                params.put("lid", sh.getString("lid", ""));
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


