package com.example.funaccount.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Calendar;

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
    public static final String ONLY_SIGNAL = "only";

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
            + ONLY_SIGNAL + " real,"
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
        ContentValues values = new ContentValues();
        values.put(MONEY, accountRecord.getMoney());
        values.put(REMARK, accountRecord.getRemark());
        values.put(TYPE, accountRecord.getType());
        values.put(IS_INCOME, accountRecord.isIncome() ? 1 : 0);
        values.put(YEAR, accountRecord.getDate().getYear());
        values.put(MONTH, accountRecord.getDate().getMonth());
        values.put(DAY, accountRecord.getDate().getDay());
        values.put(ONLY_SIGNAL, accountRecord.getId());
        return mSQLiteDatabase.insert(TABLE_NAME, RECORD_ID, values);
    }

    public long deleteOneRecord(long id) {
        return mSQLiteDatabase.delete(TABLE_NAME, ONLY_SIGNAL + " = ?", new String[]{String.valueOf(id)});
    }

    public ArrayList<BillItem> getAllRecord() {
        ArrayList<BillItem> billItems = new ArrayList<BillItem>();
        Cursor cursor = mSQLiteDatabase.query(TABLE_NAME, null, null,
                null, null, null, null);
        addBillItem(billItems, cursor);
        return billItems;
    }

    public ArrayList<BillItem> getYearRecord(int year) {
        ArrayList<BillItem> billItems = new ArrayList<BillItem>();
        Cursor cursor = mSQLiteDatabase.query(TABLE_NAME, null, YEAR + "='" + year + "'",
                null, null, null, null);
        addBillItem(billItems, cursor);
        return billItems;
    }


    public ArrayList<BillItem> getMonthRecord(int year, int month) {
        ArrayList<BillItem> billItems = new ArrayList<BillItem>();
        Cursor cursor = mSQLiteDatabase.query(TABLE_NAME, null, MONTH + "='" + month
                        + "' and " + YEAR + "='" + year + "'",
                null, null, null, null);
        addBillItem(billItems, cursor);
        return billItems;
    }

    public ArrayList<BillItem> getTodayRecord(int year, int month, int day) {
        ArrayList<BillItem> billItems = new ArrayList<BillItem>();
        Cursor cursor = mSQLiteDatabase.query(TABLE_NAME, null, MONTH + "='" + month
                        + "' and " + YEAR + "='" + year + "' and " + DAY + "='" + day + "'",
                null, null, null, null);
        addBillItem(billItems, cursor);
        return billItems;
    }

    //暂时没考虑本周刚好跨年
    public ArrayList<BillItem> getThisWeekRecord() {
        Calendar calendar = Calendar.getInstance();
        ArrayList<BillItem> billItems = new ArrayList<BillItem>();
        int firstDayOfWeek = calendar.getFirstDayOfWeek();
        int today = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1;
        int lastMonth = month - 1;
        int year = calendar.get(Calendar.YEAR);
        Cursor cursor;
        if(today >= firstDayOfWeek) {
            cursor = mSQLiteDatabase.query(TABLE_NAME, null, MONTH + "='" + month
                            + "' and " + YEAR + "='" + year + "' and " + DAY + ">='" + firstDayOfWeek + "'",
                    null, null, null, null);
        } else {
            cursor = mSQLiteDatabase.query(TABLE_NAME, null, MONTH + "='" + month
                    + "' and " + YEAR + "='" + year + "' and " + DAY + "<=" + today +
                    "' or " + MONTH + "='" + lastMonth + "' and " + YEAR + "='" + year + "' and " +
                    DAY + ">=" + firstDayOfWeek + "'", null, null, null, null);
        }
        addBillItem(billItems, cursor);
        return billItems;
    }

    private void addBillItem(ArrayList<BillItem> billItems, Cursor cursor) {
        try {
            if (cursor.moveToFirst()) {
                do {
                    String type = cursor.getString(cursor.getColumnIndex(TYPE));
                    float money = cursor.getFloat(cursor.getColumnIndex(MONEY));
                    boolean isIncome = cursor.getInt(cursor.getColumnIndex(IS_INCOME)) == 1;
                    BillItem billItem = new BillItem(money, type, isIncome);
                    billItem.setRemake(cursor.getString(cursor.getColumnIndex(REMARK)));
                    if (cursor.getColumnIndex(ONLY_SIGNAL) != -1) {
                        billItem.setId(cursor.getLong(cursor.getColumnIndex(ONLY_SIGNAL)));
                    }
                    billItem.setDate(new Date(cursor.getInt(cursor.getColumnIndex(YEAR)),
                            cursor.getInt(cursor.getColumnIndex(MONTH)), cursor.getInt(cursor.getColumnIndex(DAY))));
                    billItems.add(0, billItem);
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
        }
    }

    public int getEarliestMonthIndex() {
        ArrayList<BillItem> billItems = new ArrayList<>();
        openDataBase();
        billItems = getAllRecord();
        if (billItems.size() == 0) {
            return 1;
        }
        BillItem earliestItem = billItems.get(billItems.size() - 1);
        int earliestYear = earliestItem.getDate().getYear();
        int earliestMonth = earliestItem.getDate().getMonth();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        int thisMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
        return ((thisYear - earliestYear) * 12 + (thisMonth - earliestMonth)) + 1;
    }

    public long getDataCount() {
        String sql = "select count(*) from record_table";
        Cursor cursor = mSQLiteDatabase.rawQuery(sql, null);
        cursor.moveToFirst();
        long count = cursor.getLong(0);
        cursor.close();
        return count;
    }
}