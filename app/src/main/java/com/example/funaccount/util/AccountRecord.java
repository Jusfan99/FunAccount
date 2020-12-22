package com.example.funaccount.util;

public class AccountRecord {
    private float money;        //金额
    private String remark;      //备注
    private boolean isIncome;   //收入or支出
    private String type;        //分类

    public void setMoney(float money) {
        this.money = money;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public void setIsIncome(boolean isIncome) {
        this.isIncome = isIncome;
    }
    public void setType(String type) {
        this.type = type;
    }
    public float getMoney() {
        return this.money;
    }
    public String getRemark() {
        return this.remark;
    }
    public String getType() {
        return this.type;
    }
    public boolean isIncome() {
        return this.isIncome;
    }
}