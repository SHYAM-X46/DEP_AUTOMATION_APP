package com.example.department_automation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class StudentListAddMark1 extends AppCompatActivity {

    String[] batch;
    String[] batchid;
    String[] course;
    String[] courseid;
    String[] semester;
    String[] semesterid;
    String[] subject;
    String[] subjectid;
    String[] name;
    String[] reg_no;
    String[] id;
    String[] img;
    String[] attendance;
    String bc,cr,sems,subj;
    Spinner s1,s2,s3,s4;
    Button b1;
    Button b2;
    ListView v1;
    SharedPreferences sh;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view_allocated_subjects);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        s1 = findViewById(R.id.spinner3);
        s2 = findViewById(R.id.spinner4);
        s3 = findViewById(R.id.spinner5);
        s4 = findViewById(R.id.spinner6);
        b1 = findViewById(R.id.button5);
        b2 = findViewById(R.id.button16);
        v1 = findViewById(R.id.Listview1);

        url = sh.getString("url","")+"/and_view_allocated_subjects";

        Toast.makeText(this, "TriTrack", Toast.LENGTH_SHORT).show();

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            if (jsonObj.getString("status").equalsIgnoreCase("ok")) {

                                JSONArray js = jsonObj.getJSONArray("batches");//from python
                                JSONArray as = jsonObj.getJSONArray("course");//from python
                                JSONArray vs = jsonObj.getJSONArray("sem");//from python
                                JSONArray cs = jsonObj.getJSONArray("subject");//from python
                                batch = new String[js.length()];
                                batchid = new String[js.length()];
                                course = new String[as.length()];
                                courseid = new String[as.length()];
                                semester = new String[vs.length()];
                                semesterid = new String[vs.length()];
                                subject = new String[cs.length()];
                                subjectid = new String[cs.length()];




                                for (int i = 0; i < js.length(); i++) {
                                    JSONObject u = js.getJSONObject(i);
                                    batch[i] = u.getString("batch");//dbcolumn name in double quotes
                                    batchid[i] = u.getString("id");
                                }
                                for (int i =0; i < as.length(); i++){
                                    JSONObject u = as.getJSONObject(i);
                                    course[i] = u.getString("course");//dbcolumn name in double quotes
                                    courseid[i] = u.getString("id");
                                }
                                for (int i =0; i < vs.length(); i++){
                                    JSONObject u = vs.getJSONObject(i);
                                    semester[i] = u.getString("sem");//dbcolumn name in double quotes
                                    semesterid[i] = u.getString("id");
                                }
                                for (int i =0; i < cs.length(); i++){
                                    JSONObject u = cs.getJSONObject(i);
                                    subject[i] = u.getString("subject");//dbcolumn name in double quotes
                                    subjectid[i] = u.getString("id");
                                }
                                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getApplicationContext(),
                                        android.R.layout.simple_spinner_item, batch);
                                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                s1.setAdapter(adapter1);
                                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(),
                                        android.R.layout.simple_spinner_item, course);
                                adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                s2.setAdapter(adapter2);
                                ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(getApplicationContext(),
                                        android.R.layout.simple_spinner_item, semester);
                                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                s3.setAdapter(adapter3);
                                ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(getApplicationContext(),
                                        android.R.layout.simple_spinner_item, subject);
                                adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                s4.setAdapter(adapter4);

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
                params.put("lid", sh.getString("lid", ""));//passing to python
                return params;
            }
        };


        int MY_SOCKET_TIMEOUT_MS = 100000;

        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(postRequest);

        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                bc=batchid[pos];

            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        s2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                cr=courseid[pos];

            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        s3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                sems=semesterid[pos];

            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        s4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                subj=subjectid[pos];

            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String batch = batchid[s1.getSelectedItemPosition()];
                String course = courseid[s2.getSelectedItemPosition()];
                String sem = semester[s3.getSelectedItemPosition()];
                String sub = subjectid[s4.getSelectedItemPosition()];
                Toast.makeText(StudentListAddMark1.this, "searching", Toast.LENGTH_SHORT).show();

                url = sh.getString("url","")+"/list_students";


                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
//                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                                try {
                                    JSONObject jsonObj = new JSONObject(response);
                                    if (jsonObj.getString("status").equalsIgnoreCase("ok")) {
                                        JSONArray js = jsonObj.getJSONArray("data");

                                        name = new String[js.length()];
                                        reg_no = new String[js.length()];
                                        id = new String[js.length()];
                                        img = new String[js.length()];
                                        attendance = new String[js.length()];

                                        for (int i = 0; i < js.length(); i++) {
                                            JSONObject u = js.getJSONObject(i);
                                            name[i] = u.getString("name");//dbcolumn name in double quotes
                                            reg_no[i] = u.getString("regno");
                                            id[i] = u.getString("id");
                                            img[i] = u.getString("img");
                                            attendance[i] = u.getString("att");
                                        }
                                        v1.setAdapter(new Custom_View_Student_List_Add_Mark(getApplicationContext(),name,reg_no,id,img, sem, sub, attendance));

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
                        params.put("batch", bc);
                        params.put("course", cr);
                        params.put("sem", sems);
                        params.put("sub", subj);
                        params.put("lid", sh.getString("lid", ""));
                        return params;

                    }
                };


                int MY_SOCKET_TIMEOUT_MS = 100000;

                postRequest.setRetryPolicy(new DefaultRetryPolicy(
                        MY_SOCKET_TIMEOUT_MS,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                requestQueue.add(postRequest);


            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String course = courseid[s2.getSelectedItemPosition()];
                String sem = semester[s3.getSelectedItemPosition()];
                String sub = subjectid[s4.getSelectedItemPosition()];
                SharedPreferences.Editor ed= sh.edit();
                ed.putString("course", course);
                ed.putString("sem",sem);
                ed.putString("sub",sub);
                ed.commit();
                Intent i = new Intent(getApplicationContext(),ADD_MATERIAL.class);
                startActivity(i);


            }
        });


    }
}