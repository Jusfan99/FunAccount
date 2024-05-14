package com.example.funaccount.bmobModels;

import com.example.funaccount.util.Date;

import cn.bmob.v3.BmobObject;

public class AccountRecord extends BmobObject {
  private float mMoney;        //金额
  private String mRemark;      //备注
  private boolean mIsIncome;   //收入or支出
  private String mType;        //分类
  private Date mDate;
  private long mId;
  private String mUserId;
  private float mBudget;      //预算
  private boolean mIsNecessary; //是否必要花费

  public boolean isIsNecessary() {
    return mIsNecessary;
  }

  public void setIsNecessary(boolean mIsNecessary) {
    this.mIsNecessary = mIsNecessary;
  }

  public void setBudget(float budget) {
    this.mBudget = budget;
  }

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

  public void setId(long count) {
    this.mId = count;
  }

  public long getId() {
    return mId;
  }

  public String getUserId() {
    return mUserId;
  }

  public void setUserId(String userId) {
    mUserId = userId;
  }

  public float getBudget() {
    return mBudget;
  }
}