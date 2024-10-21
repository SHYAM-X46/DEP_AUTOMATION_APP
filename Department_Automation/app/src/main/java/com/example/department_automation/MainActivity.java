package com.example.department_automation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText e1;
    SharedPreferences sh;
    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        e1 = findViewById(R.id.editTextTextPersonName);
        e1.setText(sh.getString("ip",""));
        b1 = findViewById(R.id.button);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ip = e1.getText().toString();
                String url = "http://"+ip+":8000";
                SharedPreferences.Editor ed= sh.edit();
                ed.putString("url",url);
                ed.putString("image",url);
                ed.putString("ip",ip);
                ed.commit();
                Intent i = new Intent(getApplicationContext(),login.class);
                startActivity(i);

            }
        });
    }
}