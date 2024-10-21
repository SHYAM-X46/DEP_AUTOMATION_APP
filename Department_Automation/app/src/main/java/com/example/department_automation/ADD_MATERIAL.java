package com.example.department_automation;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ADD_MATERIAL extends AppCompatActivity {
    ImageView img;
    EditText material, t1;
    Button b2;
    SharedPreferences sh;
    String res,url;
    String fileName = "", path = "";
    String PathHolder = "";
    byte[] filedt = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_material);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        img = findViewById(R.id.imageView6);
        material = findViewById(R.id.editTextTextPersonName29);
        t1 = findViewById(R.id.editTextTextPersonName49);
        b2 = findViewById(R.id.button12);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        sh.getString("ip","");
        url=sh.getString("url","")+"/andaddmaterial";


        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("application/pdf");
                startActivityForResult(intent, 1);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mat=material.getText().toString();
                String t=t1.getText().toString();
                uploadBitmap(t);


            }
        });
    }

    ProgressDialog pd;
    private void uploadBitmap(String t) {
        pd=new ProgressDialog(ADD_MATERIAL.this);
        pd.setMessage("Uploading....");
        pd.show();
        if (PathHolder.isEmpty() || filedt == null) {
            Toast.makeText(ADD_MATERIAL.this, "Please select a PDF file", Toast.LENGTH_SHORT).show();
            pd.dismiss();
            return;
        }
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, url,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response1) {
                        pd.dismiss();
                        String x=new String(response1.data);
                        try {
                            JSONObject obj = new JSONObject(new String(response1.data));
                            if (obj.getString("status").equalsIgnoreCase("ok")) {
                                Toast.makeText(ADD_MATERIAL.this, "Successfully uploaded", Toast.LENGTH_LONG).show();
                                Intent i=new Intent(getApplicationContext(),ADD_MATERIAL.class);
                                startActivity(i);
                            } else {
                                Toast.makeText(getApplicationContext(), "Failed to upload", Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("t",t);
                params.put("sem",sh.getString("sem",""));
                params.put("sub",sh.getString("sub",""));
                params.put("course",sh.getString("course",""));
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                params.put("file", new DataPart(PathHolder , filedt ));
                return params;
            }
        };

        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedPdfUri = data.getData();
            if (selectedPdfUri != null) {
                try {
                    // Read the content of the selected PDF file
                    byte[] pdfData = readPdfContent(selectedPdfUri);
                    // Handle the PDF data as needed
                    // For example, you can upload the PDF using Volley
                    filedt = pdfData;
                    PathHolder = getFilename(selectedPdfUri);
                    Toast.makeText(this, "Selected", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Failed to read PDF content", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private String getFilename(Uri selectedPdfUri) {
        String fileName = null;
        Cursor cursor = getContentResolver().query(selectedPdfUri, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            if (nameIndex != -1) {
                fileName = cursor.getString(nameIndex);
            }
            cursor.close();
        }
        return fileName;
    }

    private byte[] readPdfContent(Uri uri) throws IOException {
        ContentResolver contentResolver = getContentResolver();
        InputStream inputStream = contentResolver.openInputStream(uri);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        return outputStream.toByteArray();
    }

}