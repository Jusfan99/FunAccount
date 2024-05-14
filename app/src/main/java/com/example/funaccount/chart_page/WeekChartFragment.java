package com.example.funaccount.chart_page;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.funaccount.R;
import com.example.funaccount.util.AccountRecordManager;
import com.example.funaccount.util.BillItem;
import com.github.mikephil.charting.charts.PieChart;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class WeekChartFragment extends Fragment {
  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.weekchart_frag, null);
    PieChart mPieChart1 = view.findViewById(R.id.pie_chat1);
    PieChart mPieChart2 = view.findViewById(R.id.pie_chat2);
    TextView Income = view.findViewById(R.id.week_income_sum);
    TextView NeceExpend = view.findViewById(R.id.week_expend_nece);
    TextView UnneceExpend = view.findViewById(R.id.week_expend_unnece);
    TextView weekDays = view.findViewById(R.id.this_week_date);
    Calendar calendar = Calendar.getInstance();
    int firstDayOfWeek = calendar.getFirstDayOfWeek();
    int today = calendar.get(Calendar.DAY_OF_MONTH);
    int month = calendar.get(Calendar.MONTH) + 1;
    int year = calendar.get(Calendar.YEAR);
    if (firstDayOfWeek < today && today - firstDayOfWeek < 7) {
      weekDays.setText(year + "." + month + "." + firstDayOfWeek + "——" + year + "." + month + "." + today);
    } else if (firstDayOfWeek > today) {
      int lastMonth = month - 1;
      weekDays.setText(year + "." + lastMonth + "." + firstDayOfWeek + "——" + year + "." + month + "." + today);
    } else {
      weekDays.setText(month + "." + today);
    }

    AccountRecordManager recordManager = new AccountRecordManager(getContext());
    recordManager.openDataBase();
    ChartFragment chartFragment = new ChartFragment();
    float[] mIncomeMoney = {0, 0, 0, 0, 0};
    float[] mExpendMoney = {0, 0, 0, 0, 0};
    ArrayList<BillItem> items = recordManager.getThisWeekRecord();
    chartFragment.getTypeProportion(items, mIncomeMoney, mExpendMoney);
    float necessaryExpend = chartFragment.getNecessaryExpend(items);
    float sumIncome = chartFragment.showIncomePieChart(mIncomeMoney, mPieChart2);
    float sumExpend = chartFragment.showExpendPieChart(mExpendMoney, mPieChart1);
    Income.setText(String.valueOf(sumIncome));
    NeceExpend.setText(String.valueOf(necessaryExpend));
    UnneceExpend.setText(String.valueOf(sumExpend - necessaryExpend));
    return view;
  }
}
