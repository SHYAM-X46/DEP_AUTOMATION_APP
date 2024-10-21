package com.example.department_automation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.department_automation.databinding.ActivityHomeBinding;

public class home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityHomeBinding binding;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarHome.toolbar);
//        binding.appBarHome.fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setBackgroundTintList(null);
        navigationView.setItemIconTintList(null);
//        navigationView.setNavigationItemSelectedListener(null);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.nav_home)
        {
            Intent i =new Intent(getApplicationContext(),home.class);
            startActivity(i);
        }
        else if (id==R.id.nav_profile)
        {
            Intent i = new Intent(getApplicationContext(), PROFILE.class);
            startActivity(i);
        }
        else if (id==R.id.nav_viewstudent)
        {
            Intent i = new Intent(getApplicationContext(), view_student.class);
            startActivity(i);
        }
        else if (id==R.id.nav_addstudent)
        {
            Intent i = new Intent(getApplicationContext(), ADD_STUDENT.class);
            startActivity(i);
        }
        else if (id==R.id.nav_subject)
        {
            Intent i = new Intent(getApplicationContext(), StudentListAddMark1.class);
            startActivity(i);
        }
        else if (id==R.id.nav_LOGOUT)
        {
            SharedPreferences.Editor editor = sh.edit();
            editor.putString("logid", "");
            editor.commit();
            finish();
            Intent i=new Intent(getApplicationContext(),login.class);
            startActivity(i);
        }

//        else if (id==R.id.nav_custom_view_student_addmark)
//        {
//            Intent i = new Intent(getApplicationContext(), StudentListAddMark1.class);
//            startActivity(i);
//        }
//        else if (id==R.id.nav_material)
//        {
//            Intent i = new Intent(getApplicationContext(), ADD_MATERIAL.class);
//            startActivity(i);
//        }

        return false;

}}