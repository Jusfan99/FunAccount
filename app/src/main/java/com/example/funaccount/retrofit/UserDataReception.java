package com.example.funaccount.retrofit;

import com.example.funaccount.util.UserData;

//接收返回数据的类
public class UserDataReception {
    private int status;
    private UserData userData;

    public UserData getUserData(){
        return userData;
    }
}
