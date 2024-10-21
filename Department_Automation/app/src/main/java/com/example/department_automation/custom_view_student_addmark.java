package com.example.department_automation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class custom_view_student_addmark extends BaseAdapter {
    String[] name,reg_no,id,img;
    private Context context;
    String url;
    SharedPreferences sh;

    public custom_view_student_addmark(Context applicationContext, String[] name, String[] reg_no, String[] id, String[] img) {
    }


    @Override
    public int getCount() {
        return 0;
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
        LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        if (view == null) {
            gridView = new View(context);
            //gridView=inflator.inflate(R.layout.customview, null);
            gridView = inflator.inflate(R.layout.activity_custom_view_student_addmark, null);//same class name

        } else {
            gridView = (View) view;

        }
        ImageView im = (ImageView) gridView.findViewById(R.id.imageView5);
        TextView t1 = (TextView) gridView.findViewById(R.id.textView45);
        TextView t3 = (TextView) gridView.findViewById(R.id.textView47);
        Button button = (Button) gridView.findViewById(R.id.button7);
        button.setTag(i);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int ik = (int) view.getTag();
                sh=PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor ed=sh.edit();
                ed.putString("id",id[ik]);
                ed.commit();
                Intent i=new Intent(context,ADD_MARK.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });

//        t1.setTextColor(Color.BLACK);//color setting
//        t2.setTextColor(Color.BLACK);
//        t3.setTextColor(Color.BLACK);
//        t4.setTextColor(Color.BLACK);
//        t5.setTextColor(Color.BLACK);


        t1.setText(name[i]);
        t3.setText(reg_no[i]);

        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context);
        String url = sh.getString("url", "") + img[i];
        Picasso.with(context.getApplicationContext()).load(url).transform(new CircleTransform()).into(im);

        return gridView;
    }
}