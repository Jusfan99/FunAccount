package com.example.funaccount.util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.funaccount.retrofit.GetUserData;
import com.example.funaccount.retrofit.UserDataReception;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

public class UserDataViewModel extends ViewModel {
    private String mUserId = "00000000";
    private UserData mUserData;
    private MutableLiveData<UserData> mUser;

    public LiveData<UserData> getUser(Context context) {
        if (mUser == null) {
            mUser = new MutableLiveData<UserData>();
            loadUser(context);
        }
        return mUser;
    }

    private void loadUser(Context context) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.baidu.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //支持rxjava
                .build();
        GetUserData request = retrofit.create(GetUserData.class);

        Observable<UserDataReception> observable = request.getCall(mUserId);

        observable.subscribeOn(Schedulers.io())     //在io线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread())      //在主线程处理结果
                .subscribe(new Observer<UserDataReception>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d(TAG, "开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(@NonNull UserDataReception userDataReception) {
                        mUserData = userDataReception.getUserData();
                        mUser.setValue(mUserData);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Toast.makeText(context, "发送请求失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "成功");
                    }
                });
    }
}
