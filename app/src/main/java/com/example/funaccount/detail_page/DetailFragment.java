package com.example.funaccount.detail_page;

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
import java.util.LinkedList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class DetailFragment extends BillShowHelper {
    BillShowAdapter mBillShowAdapter;
    Button mNextMonth;
    Button mLastMonth;
    TextView mCheckedMonth;
    RecyclerView mRecyclerView;

    private ArrayList<String> mMonths;
    private int mShowYear;
    private int mShowMonth;
    private int mLastClickCount = 0;
    private int mNextClickCount = 0;

    private final int mThisYear = Calendar.getInstance().get(Calendar.YEAR);
    private final int mThisMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_frag, null);
        mMonths = new ArrayList<String>();
        initMonthList(mMonths);
        initUi(view);
        return view;
    }

    private void initUi(View view) {
        mShowMonth = mThisMonth;
        mShowYear = mThisYear;
        mBillShowAdapter = new BillShowAdapter(getActivity(), monthBillList(mShowYear, mShowMonth));
        mRecyclerView = view.findViewById(R.id.detail_show_bill);
        initRecyclerView(mRecyclerView, mBillShowAdapter);
        mCheckedMonth = view.findViewById(R.id.checked_month);
        mCheckedMonth.setText(mMonths.get(0));
        mLastMonth = view.findViewById(R.id.turn_left_item);
        mNextMonth = view.findViewById(R.id.turn_right_item);
        mLastMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMonths.size() > mLastClickCount + 1) {
                    mLastClickCount++;
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
                if (mNextClickCount < mLastClickCount) {
                    mNextClickCount++;
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
        AccountRecordManager mRecordManager = new AccountRecordManager(this.getContext());
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
        mBillShowAdapter.setBillItems(monthBillList(year, month));
        initRecyclerView(mRecyclerView, mBillShowAdapter);
        mCheckedMonth.setText(monthToString(year, month));
    }

    private String monthToString(int year, int month) {
        return year + "年" + month + "月";
    }
}

