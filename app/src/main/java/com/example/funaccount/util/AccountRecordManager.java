package com.example.funaccount.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AccountRecordManager {

    private static final String DB_NAME = "account_record";
    private static final String TABLE_NAME = "record_table";
    public static final String RECORDID = "_id";
    public static final String MONEY = "money";
    public static final String REMARK = "remark";
    public static final String ISINCOME = "is_income";
    public static final String TYPE = "type";


    private static final int DB_VERSION = 2;
    private Context mContext = null;

    private static final String DB_CREATE = "CREATE TABLE " + TABLE_NAME + " ("
            + RECORDID + " verchar primary key,"
            + REMARK + " varchar,"
            + TYPE + " varchar,"
            + MONEY + " real,"
            + ISINCOME + " integer" + ");";

    private SQLiteDatabase mSQLiteDatabase = null;
    private AccountRecordManager.RecordManagementHelper mRecordHelper = null;

    private static class RecordManagementHelper extends SQLiteOpenHelper{

        RecordManagementHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + ";");
            db.execSQL(DB_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onCreate(db);
        }
    }

    public AccountRecordManager(Context context) {
        mContext = context;
    }
    public void openDataBase() throws SQLException {
        mRecordHelper = new AccountRecordManager.RecordManagementHelper(mContext);
        mSQLiteDatabase = mRecordHelper.getWritableDatabase();
    }
    //关闭数据库
    public void closeDataBase() throws SQLException {
        mRecordHelper.close();
    }
    //添加新纪录 （记账）
    public long insertAccountRecord(AccountRecord accountRecord) {
        float money = accountRecord.getMoney();
        String remark = accountRecord.getRemark();
        String type = accountRecord.getType();
        int isIncome = accountRecord.isIncome()? 1:0;
        ContentValues values = new ContentValues();
        values.put(MONEY, money);
        values.put(REMARK, remark);
        values.put(TYPE, type);
        values.put(ISINCOME, isIncome);
        return mSQLiteDatabase.insert(TABLE_NAME, RECORDID, values);
    }
}