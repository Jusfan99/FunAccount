package com.example.funaccount.util;

public class AccountRecord {
    private float mMoney;        //金额
    private String mRemark;      //备注
    private boolean mIsIncome;   //收入or支出
    private String mType;        //分类
    private Date mDate;

    public void setMoney(float mMoney) {
        this.mMoney = mMoney;
    }

    public void setRemark(String mRemark) {
        this.mRemark = mRemark;
    }

    public void setIsIncome(boolean isIncome) {
        this.mIsIncome = isIncome;
    }

    public void setType(String type) {
        this.mType = type;
    }

    public float getMoney() {
        return this.mMoney;
    }

    public void setDate(Date date) {
        this.mDate = date;
    }

    public Date getDate() {
        return mDate;
    }

    public String getRemark() {
        return this.mRemark;
    }

    public String getType() {
        return this.mType;
    }

    public boolean isIncome() {
        return this.mIsIncome;
    }
}