package com.example.funaccount.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class AccountRecordManager {

    private static final String DB_NAME = "account_record";
    private static final String TABLE_NAME = "record_table";
    public static final String RECORD_ID = "_id";
    public static final String MONEY = "money";
    public static final String REMARK = "remark";
    public static final String IS_INCOME = "is_income";
    public static final String TYPE = "type";
    public static final String YEAR = "year";
    public static final String MONTH = "month";
    public static final String DAY = "day";


    private static final int DB_VERSION = 2;
    private Context mContext = null;

    private static final String DB_CREATE = "CREATE TABLE " + TABLE_NAME + " ("
            + RECORD_ID + " verchar primary key,"
            + REMARK + " varchar,"
            + TYPE + " varchar,"
            + MONEY + " real,"
            + YEAR + " integer,"
            + MONTH + " integer,"
            + DAY + " integer,"
            + IS_INCOME + " integer" + ");";

    private SQLiteDatabase mSQLiteDatabase = null;
    private AccountRecordManager.RecordManagementHelper mRecordHelper = null;

    private static class RecordManagementHelper extends SQLiteOpenHelper {

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
        int isIncome = accountRecord.isIncome() ? 1 : 0;
        ContentValues values = new ContentValues();
        values.put(MONEY, money);
        values.put(REMARK, remark);
        values.put(TYPE, type);
        values.put(IS_INCOME, isIncome);
        values.put(YEAR, accountRecord.getDate().getYear());
        values.put(MONTH, accountRecord.getDate().getMonth());
        values.put(DAY, accountRecord.getDate().getDay());
        return mSQLiteDatabase.insert(TABLE_NAME, RECORD_ID, values);
    }

    public void getAllRecord(ArrayList<BillItem> billItems) {
        Cursor cursor = mSQLiteDatabase.query(TABLE_NAME, null, null,
                null, null, null, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    String type = cursor.getString(cursor.getColumnIndex(TYPE));
                    String remark = cursor.getString(cursor.getColumnIndex(REMARK));
                    int id = cursor.getInt(cursor.getColumnIndex(RECORD_ID));
                    float money = cursor.getFloat(cursor.getColumnIndex(MONEY));
                    boolean isIncome = cursor.getInt(cursor.getColumnIndex(IS_INCOME)) == 1;
                    Date date = new Date(cursor.getInt(cursor.getColumnIndex(YEAR)),
                            cursor.getInt(cursor.getColumnIndex(MONTH)), cursor.getInt(cursor.getColumnIndex(DAY)));
                    BillItem billItem = new BillItem(money, type, isIncome);
                    billItem.mRemark = remark;
                    billItem.mId = id;
                    billItem.setmDate(date);
                    billItems.add(0, billItem);
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
        }
    }

    public void getMonthRecord(ArrayList<BillItem> billItems, int year, int month) {
        Cursor cursor = mSQLiteDatabase.query(TABLE_NAME, null, MONTH + "='" + month
                        + "' and " + YEAR + "='" + year + "'",
                null, null, null, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    String type = cursor.getString(cursor.getColumnIndex(TYPE));
                    String remark = cursor.getString(cursor.getColumnIndex(REMARK));
                    int id = cursor.getInt(cursor.getColumnIndex(RECORD_ID));
                    float money = cursor.getFloat(cursor.getColumnIndex(MONEY));
                    boolean isIncome = cursor.getInt(cursor.getColumnIndex(IS_INCOME)) == 1;
                    Date date = new Date(cursor.getInt(cursor.getColumnIndex(YEAR)),
                            cursor.getInt(cursor.getColumnIndex(MONTH)), cursor.getInt(cursor.getColumnIndex(DAY)));
                    BillItem billItem = new BillItem(money, type, isIncome);
                    billItem.mRemark = remark;
                    billItem.mId = id;
                    billItem.setmDate(date);
                    billItems.add(0, billItem);
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
        }
    }

    public void getTodayRecord(ArrayList<BillItem> billItems, int year, int month, int day) {
        Cursor cursor = mSQLiteDatabase.query(TABLE_NAME, null, MONTH + "='" + month
                        + "' and " + YEAR + "='" + year + "' and " + DAY + "='" + day + "'",
                null, null, null, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    String type = cursor.getString(cursor.getColumnIndex(TYPE));
                    String remark = cursor.getString(cursor.getColumnIndex(REMARK));
                    int id = cursor.getInt(cursor.getColumnIndex(RECORD_ID));
                    float money = cursor.getFloat(cursor.getColumnIndex(MONEY));
                    boolean isIncome = cursor.getInt(cursor.getColumnIndex(IS_INCOME)) == 1;
                    Date date = new Date(cursor.getInt(cursor.getColumnIndex(YEAR)),
                            cursor.getInt(cursor.getColumnIndex(MONTH)), cursor.getInt(cursor.getColumnIndex(DAY)));
                    BillItem billItem = new BillItem(money, type, isIncome);
                    billItem.mRemark = remark;
                    billItem.mId = id;
                    billItem.setmDate(date);
                    billItems.add(0, billItem);
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
        }
    }
}