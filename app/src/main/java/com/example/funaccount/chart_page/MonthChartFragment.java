package com.example.funaccount.chart_page;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.funaccount.R;
import com.example.funaccount.util.AccountRecordManager;
import com.example.funaccount.util.PieChartManager;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MonthChartFragment extends Fragment {
    private final float[] mIncomeMoney = {0, 0, 0, 0, 0};
    private final float[] mExpendMoney = {0, 0, 0, 0, 0};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.monthchart_frag, null);
        PieChart mPieChart1 = view.findViewById(R.id.pie_chat1);
        PieChart mPieChart2 = view.findViewById(R.id.pie_chat2);
        AccountRecordManager recordManager = new AccountRecordManager(getContext());
        recordManager.openDataBase();
        ChartFragment chartFragment = new ChartFragment();
        chartFragment.getTypeProportion(recordManager.getMonthRecord(Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH)+1), mIncomeMoney, mExpendMoney);
        chartFragment.showIncomePieChart(mIncomeMoney, mPieChart2);
        chartFragment.showExpendPieChart(mExpendMoney, mPieChart1);
        return view;
    }
}
