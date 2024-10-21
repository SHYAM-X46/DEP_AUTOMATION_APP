package com.example.department_automation;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class custom_view_student extends BaseAdapter {
    String[] name,email,reg_no,father_name,mother_name,id,img;
    private Context context;
    String url;
    SharedPreferences sh;
    
    public  custom_view_student(Context context, String[] name, String[] email, String[] reg_no, String[] father_name, String[] mother_name, String[] id,String[] img){
        this.context = context;
        this.id = id;
        this.name = name;
        this.email = email;
        this.reg_no = reg_no;
        this.father_name = father_name;
        this.mother_name = mother_name;
        this.img = img;

    }


//    public custom_view_student(Context applicationContext, String[] strings, String[] name, String[] email, String[] reg_no, String[] father_name, String[] mother_name, String[] id) {
//    }

    @Override
    public int getCount() {
        return email.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflator=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        if(view==null)
        {
            gridView=new View(context);
            //gridView=inflator.inflate(R.layout.customview, null);
            gridView=inflator.inflate(R.layout.activity_custom_view_student,null);//same class name

        }
        else
        {
            gridView=(View)view;

        }
        ImageView im=(ImageView) gridView.findViewById(R.id.imageView2);
        TextView t1=(TextView)gridView.findViewById(R.id.textView35);
        TextView t2=(TextView)gridView.findViewById(R.id.textView36);
        TextView t3=(TextView)gridView.findViewById(R.id.textView37);
        TextView t4=(TextView)gridView.findViewById(R.id.textView38);
        TextView t5=(TextView)gridView.findViewById(R.id.textView39);
        Button button=(Button)gridView.findViewById(R.id.button8);
        button.setTag(i);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int ik = (int)view.getTag();
                sh = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
                url = sh.getString("url","")+"/and_delete_student";

                RequestQueue requestQueue = Volley.newRequestQueue(context);
                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //  .makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                                try {
                                    JSONObject jsonObj = new JSONObject(response);
                                    if (jsonObj.getString("status").equalsIgnoreCase("ok")) {
                                        Toast.makeText(context, "student deleted", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(context,home.class);
                                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        context.startActivity(i);
//
                                    } else {
                                        Toast.makeText(context, "Not found", Toast.LENGTH_LONG).show();
                                    }

                                } catch (Exception e) {
                                    Toast.makeText(context, "Error" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // error
                                Toast.makeText(context, "eeeee" + error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                ) {

                    //                value Passing android to python
                    @Override
                    protected Map<String, String> getParams() {
                        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context);
                        Map<String, String> params = new HashMap<String, String>();

                       params.put("id",id[ik]);


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
        Button button2=(Button)gridView.findViewById(R.id.button10);
        button2.setTag(i);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int ik=(int)view.getTag();
                SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor ed = sh.edit();
                ed.putString("sid", id[ik]);
                ed.commit();
                Intent im = new Intent(context, student_view2.class);
                im.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(im);
            }
        });
//        t1.setTextColor(Color.BLACK);//color setting
//        t2.setTextColor(Color.BLACK);
//        t3.setTextColor(Color.BLACK);
//        t4.setTextColor(Color.BLACK);
//        t5.setTextColor(Color.BLACK);


        t1.setText(name[i]);
        t2.setText(email[i]);
        t3.setText(reg_no[i]);
        t4.setText(father_name[i]);
        t5.setText(mother_name[i]);

        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context);
        String url = sh.getString("url", "")+img[i];
        Picasso.with(context.getApplicationContext()).load(url).transform(new CircleTransform()).into(im);

        return gridView;

    }
}