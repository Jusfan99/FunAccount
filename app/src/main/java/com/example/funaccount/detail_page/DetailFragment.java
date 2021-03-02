package com.example.funaccount.detail_page;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.funaccount.R;
import com.example.funaccount.util.AccountRecordManager;
import com.example.funaccount.util.BillShowHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

public class DetailFragment extends BillShowHelper {
    private static BillShowAdapter mBillShowAdapter;
    Button mNextMonth;
    Button mLastMonth;
    TextView mCheckedMonth;
    RecyclerView mRecyclerView;
    TextView mSumIncomeText;
    TextView mSumExpendText;
    AccountRecordManager mRecordManager;
    TextView mMonthEmpty;

    private ArrayList<String> mMonths;
    private int mShowYear;
    private int mShowMonth;
    private int mLastClickCount = 1;
    private int mNextClickCount;
    private float mSumIncome;
    private float mSunExpend;
    private IntentFilter mIntentFilter;
    private LocalReceiver mLocalReceiver;    //本地广播接收者
    private LocalBroadcastManager mLocalBroadcastManager;   //本地广播管理者   可以用来注册广播

    private final int mThisYear = Calendar.getInstance().get(Calendar.YEAR);
    private final int mThisMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_frag, null);
        mMonths = new ArrayList<String>();
        mRecordManager = initDataBase();
        initMonthList(mMonths);
        initUi(view);
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(getContext());
        mLocalReceiver = new LocalReceiver();
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(LOCAL_BROADCAST);   //添加action
        mLocalBroadcastManager.registerReceiver(mLocalReceiver, mIntentFilter);
        return view;
    }

    private void initUi(View view) {
        mShowMonth = mThisMonth;
        mShowYear = mThisYear;
        mMonthEmpty = view.findViewById(R.id.month_detail_empty);

        mRecyclerView = view.findViewById(R.id.detail_show_bill);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mBillShowAdapter = new BillShowAdapter(getActivity(),
                monthBillList(mShowYear, mShowMonth, mRecordManager));
        initRecyclerView(mRecyclerView, mBillShowAdapter);
        mCheckedMonth = view.findViewById(R.id.checked_month);
        mCheckedMonth.setText(mMonths.get(0));
        mLastMonth = view.findViewById(R.id.turn_left_item);
        mNextMonth = view.findViewById(R.id.turn_right_item);
        mSumExpendText = view.findViewById(R.id.month_expend_sum);
        mSumIncomeText = view.findViewById(R.id.month_income_sum);
        updateUi(mShowYear, mShowMonth);
        mNextClickCount = mMonths.size();

        mLastMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mNextClickCount > 1) {
                    mLastClickCount++;
                    mNextClickCount--;
                    if (mShowMonth == 1) {
                        mShowMonth = 12;
                        mShowYear--;
                    } else {
                        mShowMonth--;
                    }
                    updateUi(mShowYear, mShowMonth);
                } else {
                    Toast.makeText(v.getContext(), "您没有更多账单", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mNextMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLastClickCount > 1) {
                    mNextClickCount++;
                    mLastClickCount--;
                    if (mShowMonth == 12) {
                        mShowMonth = 1;
                        mShowYear++;
                    } else {
                        mShowMonth++;
                    }
                    updateUi(mShowYear, mShowMonth);
                }
            }
        });
    }

    private void initMonthList(List<String> months) {
        int year = mThisYear;
        int month = mThisMonth;
        for (int i = 0; i < mRecordManager.getEarliestMonthIndex(); i++) {
            mMonths.add(monthToString(year, month));
            if (month == 1) {
                month = 12;
                year--;
            } else {
                month--;
            }
        }
    }

    private void updateUi(int year, int month) {
        if (monthBillList(year, month, mRecordManager) != null &&
                monthBillList(year, month, mRecordManager).size() != 0) {
            mRecyclerView.setVisibility(View.VISIBLE);
            mMonthEmpty.setVisibility(View.GONE);
            mBillShowAdapter = new BillShowAdapter(mMonthEmpty.getContext(),
                    monthBillList(year, month, mRecordManager));
        } else {
            mMonthEmpty.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        }
        initRecyclerView(mRecyclerView, mBillShowAdapter);
        mCheckedMonth.setText(monthToString(year, month));
        mSumIncomeText.setText(String.valueOf(sumIncome(monthBillList(year, month, mRecordManager))));
        mSumExpendText.setText(String.valueOf(sumExpend(monthBillList(year, month, mRecordManager))));
    }

    public static void updateView() {
        mBillShowAdapter.notifyDataSetChanged();
    }

    private String monthToString(int year, int month) {
        return year + "年" + month + "月";
    }

    public class LocalReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();
            if (!action.equals(LOCAL_BROADCAST)) {
                return;
            }
            if (intent.getBooleanExtra("delete", false)) {
                updateUi(mShowYear, mShowMonth);
            }
        }
    }

    @Override
    public void onDestroy() {
        mLocalBroadcastManager.unregisterReceiver(mLocalReceiver);
        super.onDestroy();
    }
}

