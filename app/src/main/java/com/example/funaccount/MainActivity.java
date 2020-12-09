package com.example.funaccount;


import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.os.Bundle;

import com.example.funaccount.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //隐藏标题
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        NavController navController = Navigation.findNavController(this,R.id.main_nav_host);
        BottomNavigationView navigationView = findViewById(R.id.main_nav_view);
        NavigationUI.setupWithNavController(navigationView,navController);

    }
//    public void changeTextColor(){
//        NavigationView navigationView = (NavigationView)findViewById(R.id.main_nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
//        Resources resource = (Resources)getBaseContext().getResources();
//        @SuppressLint("UseCompatLoadingForColorStateLists") ColorStateList colorStateList = (ColorStateList)resource.getColorStateList(R.color.nav_menu_item_color);
//        navigationView.setItemTextColor(colorStateList);
//    }
}