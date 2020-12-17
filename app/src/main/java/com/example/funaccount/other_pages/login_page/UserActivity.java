package com.example.funaccount.other_pages.login_page;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.funaccount.MainActivity;
import com.example.funaccount.R;
import com.example.funaccount.setting_page.SettingFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class UserActivity extends AppCompatActivity {
    private Button mReturnButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user);
        mReturnButton = (Button)findViewById(R.id.returnback);
    }
    public void backToLogin(View view) {
        Intent intent3 = new Intent(UserActivity.this,LoginActivity.class) ;
        startActivity(intent3);
        finish();
    }
    public void backToSetting(View view){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        SettingFragment settingFragment = new SettingFragment();
        fragmentTransaction.replace(R.id.setting_fragment,settingFragment);
        fragmentTransaction.commit();
    }
}