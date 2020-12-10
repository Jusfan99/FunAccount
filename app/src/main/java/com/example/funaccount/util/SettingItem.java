package com.example.funaccount.util;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

import androidx.annotation.NonNull;

public class SettingItem implements Serializable {
    public int imgId; //icon
    public String itemName;

    public SettingItem(){
    }

    public SettingItem(int imgPath,String itemName){
        this.imgId = imgPath;
        this.itemName = itemName;
    }
    public Object getImgPath(){
        return imgId;
    }
    public String getItemName(){
        return itemName;
    }
    public void setImgPath(int imgPath){
        this.imgId = imgPath;
    }
    public void setItemName(String itemName){
        this.itemName = itemName;
    }

    @NonNull
    @Override
    public String toString() {
        return "SettingItem{" + "imgPath='" + imgId + '\'' + "，itemName+'" + itemName + '\'' + '}';
    }
}
