package com.example.funaccount.util;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

import androidx.annotation.NonNull;

public class SettingItem implements Serializable {
    public int mimgId; //icon
    public String mitemName;

    public SettingItem(){
    }

    public SettingItem(int imgPath,String itemName){
        this.mimgId = imgPath;
        this.mitemName = itemName;
    }
    public Object getImgPath(){
        return mimgId;
    }
    public String getItemName(){
        return mitemName;
    }
    public void setImgPath(int imgPath){
        this.mimgId = imgPath;
    }
    public void setItemName(String itemName){
        this.mitemName = itemName;
    }

    @NonNull
    @Override
    public String toString() {
        return "SettingItem{" + "imgPath='" + mimgId + '\'' + "，itemName+'" + mitemName + '\'' + '}';
    }
}
