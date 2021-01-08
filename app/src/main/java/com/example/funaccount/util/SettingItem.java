package com.example.funaccount.util;

import java.io.Serializable;

import androidx.annotation.NonNull;

public class SettingItem implements Serializable {
    public int mImagId; //icon
    public String mItemName;

    public SettingItem() {
    }

    public SettingItem(int imgPath, String itemName) {
        this.mImagId = imgPath;
        this.mItemName = itemName;
    }

    public Object getImgPath() {
        return mImagId;
    }

    public String getItemName() {
        return mItemName;
    }

    public void setImgPath(int imgPath) {
        this.mImagId = imgPath;
    }

    public void setItemName(String itemName) {
        this.mItemName = itemName;
    }

    @NonNull
    @Override
    public String toString() {
        return "SettingItem{" + "imgPath='" + mImagId + '\'' + "，itemName+'" + mItemName + '\'' + '}';
    }
}
