package com.example.funaccount.other_pages.login_page;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.funaccount.R;
import com.example.funaccount.retrofit.GetUserData;
import com.example.funaccount.retrofit.UserDataReception;
import com.example.funaccount.setting_page.SettingFragment;
import com.example.funaccount.util.UserData;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

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
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //支持rxjava
                .build();
        GetUserData request = retrofit.create(GetUserData.class);

        Observable<UserDataReception> observable = request.getCall(mUserId);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserDataReception>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d(TAG, "开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(@NonNull UserDataReception userDataReception) {
                        mUserData = userDataReception.getUserData();
                        mResponseId = findViewById(R.id.response_id);
                        String text = "id:"+mUserData.getUserId()+" name:"+mUserData.getUserName();
                        mResponseId.setText(text);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Toast.makeText(getBaseContext(),"发送请求失败",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG,"成功");
                    }
                });
    }
}