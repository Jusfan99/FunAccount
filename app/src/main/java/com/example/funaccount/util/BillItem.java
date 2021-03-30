package com.example.funaccount.util;

import java.io.Serializable;

public class BillItem implements Serializable {
  private boolean mIsIncome;
  private String mType;
  private float mMoney;
  private String mRemark;
  private long mId;
  private Date mDate;

  public void setDate(Date mDate) {
    this.mDate = mDate;
  }

  public void setRemake(String remake) {
    this.mRemark = remake;
  }

  public void setId(long id) {
    this.mId = id;
  }

  public Date getDate() {
    return mDate;
  }

  public BillItem() {
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

  public long getId() {
    return mId;
  }
}
