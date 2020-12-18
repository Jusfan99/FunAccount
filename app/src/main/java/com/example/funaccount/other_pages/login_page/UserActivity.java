package com.example.funaccount.other_pages.login_page;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.funaccount.MainActivity;
import com.example.funaccount.R;
import com.example.funaccount.retrofit.GetUserData;
import com.example.funaccount.retrofit.UserDataReception;
import com.example.funaccount.setting_page.SettingFragment;
import com.example.funaccount.util.UserData;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserActivity extends AppCompatActivity {
    private Button mReturnButton;
    private String mUserId = "00000000";
    private UserData mUserData;
    private TextView mResponseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user);
        mReturnButton = (Button)findViewById(R.id.returnback);
        new RequestThread().start();
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

    public class RequestThread extends Thread{
        @Override
        public void run() {
            request();
        }
    }

    public void request(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.baidu.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GetUserData request = retrofit.create(GetUserData.class);
        Call<UserDataReception> call = request.getCall(mUserId);
        call.enqueue(new Callback<UserDataReception>() {
            @Override
            public void onResponse(Call<UserDataReception> call, Response<UserDataReception> response) {
                mUserData = response.body().getUserData();
                mResponseId = findViewById(R.id.response_id);
                mResponseId.setText("id:"+mUserData.getUserId()+" name:"+mUserData.getUserName());
            }

            @Override
            public void onFailure(Call<UserDataReception> call, Throwable t) {
                Toast.makeText(getBaseContext(),"发送请求失败",Toast.LENGTH_SHORT).show();
            }
        });
    }
}