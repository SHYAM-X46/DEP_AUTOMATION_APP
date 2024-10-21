package com.example.department_automation;

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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ADD_STUDENT extends AppCompatActivity {
    EditText a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14,a15,a16,a17,a18,a19;
    RadioGroup rg1;
    RadioButton m1,f2;
    Button B1;
    Bitmap bitmap = null;
    SharedPreferences sh;

    String pinpattern = "[0-9]{6}";
    String phonepattern = "[6789][0-9]{10}";
    String adharpattern = "[23456789][0-9]{12}";
    String emailpattern = "\"[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$\"";


    ProgressDialog pd;
    ImageView img;
    String url;
    String gender;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        a1 = findViewById(R.id.editTextTextPersonName5);
        a2 = findViewById(R.id.editTextTextPersonName6);
        a3 = findViewById(R.id.editTextTextPersonName9);
        a4 = findViewById(R.id.editTextTextPersonName13);
        a5 = findViewById(R.id.editTextTextPersonName17);
        a6 = findViewById(R.id.editTextTextPersonName18);
        a7 = findViewById(R.id.editTextTextPersonName19);
        a8 = findViewById(R.id.editTextTextPersonName20);
        a9 = findViewById(R.id.editTextTextPersonName21);
        a10 = findViewById(R.id.editTextTextPersonName22);
        a11 = findViewById(R.id.editTextTextPersonName23);
        a12 = findViewById(R.id.editTextTextPersonName24);
        a13 = findViewById(R.id.editTextTextPersonName25);
        a14 = findViewById(R.id.editTextTextPersonName26);
        a15 = findViewById(R.id.editTextTextMultiLine);
        a16 = findViewById(R.id.editTextTextMultiLine2);
        a17 = findViewById(R.id.editTextTextPersonName27);
        a18 = findViewById(R.id.editTextTextPersonName28);
        a19 = findViewById(R.id.editTextTextMultiLine3);
        rg1 = findViewById(R.id.radioGroup2);
        m1 = findViewById(R.id.radioButton5);
        f2 = findViewById(R.id.radioButton4);
        img = findViewById(R.id.imageView3);
        B1 = findViewById(R.id.button4);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 100);
            }
        });
        B1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Fnm=a1.getText().toString();
                String Snm=a2.getText().toString();
                String phn=a3.getText().toString();
                String email=a4.getText().toString();
                String reg=a5.getText().toString();
                String dad=a6.getText().toString();
                String mom=a7.getText().toString();
                String house=a8.getText().toString();
                String plc=a9.getText().toString();
                String pin=a10.getText().toString();
                String post=a11.getText().toString();
                String dis=a12.getText().toString();
                String state=a13.getText().toString();
                String adhar=a14.getText().toString();
                String identifi=a15.getText().toString();
                String club=a16.getText().toString();
                String blood=a17.getText().toString();
                String lic=a18.getText().toString();
                String about=a19.getText().toString();
                if (m1.isChecked()){
                    gender = "male";
                }
                if (f2.isChecked()){
                    gender = "female";
                }
                int flag=0;
                if(Fnm.equalsIgnoreCase("")){
                    a1.setError("Enter the Firstname");
                    flag++;
                }
                if(Snm.equalsIgnoreCase("")){
                    a2.setError("Enter the Secondname");
                    flag++;
                }if(reg.equalsIgnoreCase("")){
                    a5.setError("Enter the Register Number");
                    flag++;
                }if(dad.equalsIgnoreCase("")){
                    a6.setError("Enter the Father Name");
                    flag++;
                }if(mom.equalsIgnoreCase("")){
                    a7.setError("Enter the Mother Name");
                    flag++;
                }if(house.equalsIgnoreCase("")){
                    a8.setError("Enter the House Name");
                    flag++;
                }if(plc.equalsIgnoreCase("")){
                    a9.setError("Enter the Place");
                    flag++;
                }if(post.equalsIgnoreCase("")){
                    a11.setError("Enter the Post");
                    flag++;
                }if(dis.equalsIgnoreCase("")){
                    a12.setError("Enter the District");
                    flag++;
                }if(state.equalsIgnoreCase("")){
                    a13.setError("Enter the State");
                    flag++;
                }if(identifi.equalsIgnoreCase("")){
                    a15.setError("Enter the Identification");
                    flag++;
                }if(blood.equalsIgnoreCase("")){
                    a17.setError("Enter the Blood group");
                    flag++;
                }
                if(!pin.matches(pinpattern)){
                    a10.setError("Enter a valid pincode");
                    flag++;
                }if(!phn.matches(phonepattern)){
                    a3.setError("Enter a valid phone number");
                    flag++;
                }if(!adhar.matches(adharpattern)){
                    a14.setError("Enter a valid aadhar number");
                    flag++;
                }if(!email.matches(emailpattern)){
                    a4.setError("Enter a valid email");
                    flag++;
                }
                if(bitmap==null){
                    Toast.makeText(ADD_STUDENT.this, "choose image", Toast.LENGTH_SHORT).show();
                    flag++;
                }


                uploadBitmap(Fnm,Snm,phn,email,reg,dad,mom,house,plc,pin,post,dis,state,adhar,identifi,club,blood,lic,about);

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
    private void uploadBitmap(final String Fnm, final String Snm, final String phn, final String email, final String reg, final String dad,final String mom,final String house,final String plc,final String pin,final String post,final String dis,final String state, final String adhar,final String identifi ,final String club, final String blood ,final String lic ,final String about) {


        pd = new ProgressDialog(ADD_STUDENT.this);
        pd.setMessage("Uploading....");
        pd.show();
        url = sh.getString("url", "")+"/and_add_student";
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, url,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            pd.dismiss();


                            JSONObject obj = new JSONObject(new String(response.data));

                            if(obj.getString("status").equals("ok")){
                                Toast.makeText(getApplicationContext(), "Student Added", Toast.LENGTH_SHORT).show();
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
                params.put("Fnm", Fnm);//passing to python
                params.put("Snm", Snm);//passing to python
                params.put("phn", phn);
                params.put("email", email);
                params.put("reg", reg);
                params.put("dad", dad);
                params.put("mom", mom);
                params.put("house", house);
                params.put("plc", plc);
                params.put("pin", pin);
                params.put("post", post);
                params.put("dis", dis);
                params.put("state", state);
                params.put("adhar", adhar);
                params.put("identifi", identifi);
                params.put("club", club);
                params.put("blood", blood);
                params.put("lic", lic);
                params.put("about", about);
                params.put("gender", gender);
                params.put("lid", sh.getString("lid", ""));
                return params;
            }


            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("pic", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                return params;
            }
        };

        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }

}

