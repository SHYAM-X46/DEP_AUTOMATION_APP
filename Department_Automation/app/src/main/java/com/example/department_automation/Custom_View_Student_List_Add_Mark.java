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
import android.widget.ListView;
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

public class Custom_View_Student_List_Add_Mark extends BaseAdapter {
    ListView abc;
    SharedPreferences sh;
    String[] name,reg_no,id,img, attendance;
    private Context context;
    String sem, sub;

    public Custom_View_Student_List_Add_Mark(Context context, String[] name, String[] reg_no, String[] id, String[] img, String sem, String sub, String[] attendance) {
        this.context = context;
        this.name = name;
        this.reg_no = reg_no;
        this.id = id;
        this.img = img;
        this.sem = sem;
        this.sub = sub;
        this.attendance = attendance;
    }

    @Override
    public int getCount() {
        return name.length;
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
            gridView=inflator.inflate(R.layout.activity_custom_view_student_addmark,null);//same class name

        }
        else
        {
            gridView=(View)view;

        }
        ImageView im=gridView.findViewById(R.id.imageView5);
        TextView t1=(TextView)gridView.findViewById(R.id.textView45);
        TextView t2=(TextView)gridView.findViewById(R.id.textView47);
        TextView t3=(TextView)gridView.findViewById(R.id.textView76);
        Button B1=gridView.findViewById(R.id.button7);
        Button B2=gridView.findViewById(R.id.button6);


        t1.setText(name[i]);
        t2.setText(reg_no[i]);
        t3.setText(attendance[i]+"%");


        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context);
        String url = sh.getString("url", "")+img[i];
        Picasso.with(context.getApplicationContext()).load(url).transform(new CircleTransform()).into(im);


        B1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent j = new Intent(context, ADD_MARK.class);
                j.putExtra("sem", sem);
                j.putExtra("sub", sub);
                j.putExtra("sid", id[i]);
                j.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(j);

            }
        });
        B2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent j = new Intent(context, ATTENDANCE.class);
                j.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                j.putExtra("name", name[i]);
                j.putExtra("studid", id[i]);
                j.putExtra("sem", sem);
                context.startActivity(j);

            }
        });

        return gridView;
    }
}