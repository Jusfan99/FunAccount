package com.example.funaccount.util;

import java.io.Serializable;

public class BillItem implements Serializable {
    public boolean mIsIncome;
    public String mType;
    public float mMoney;
    public String mRemark;
    public int mId;

    public BillItem(){
    }

    public BillItem(float money, String type, boolean isIncome) {
        this.mMoney = money;
        this.mType = type;
        this.mIsIncome = isIncome;
    }

    public boolean isIncome() {
        return mIsIncome;
    }

    public String getType() {
        return mType;
    }

    public float getMoney() {
        return mMoney;
    }

    public String getRemark() {
        return mRemark;
    }

    public int getId() {
        return mId;
    }
}
