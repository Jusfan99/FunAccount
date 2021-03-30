package com.example.funaccount.chart_page;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.funaccount.R;
import com.example.funaccount.util.AccountRecordManager;
import com.github.mikephil.charting.charts.PieChart;

import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class YearChartFragment extends Fragment {
  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.yearchart_frag, null);
    PieChart mPieChart1 = view.findViewById(R.id.pie_chat1);
    PieChart mPieChart2 = view.findViewById(R.id.pie_chat2);
    TextView Income = view.findViewById(R.id.year_income);
    TextView Expend = view.findViewById(R.id.year_expend);
    TextView yearDay = view.findViewById(R.id.this_year_date);
    Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    yearDay.setText(year + "年");
    AccountRecordManager recordManager = new AccountRecordManager(getContext());
    recordManager.openDataBase();
    ChartFragment chartFragment = new ChartFragment();
    float[] mIncomeMoney = {0, 0, 0, 0, 0};
    float[] mExpendMoney = {0, 0, 0, 0, 0};
    chartFragment.getTypeProportion(recordManager.getYearRecord(Calendar.getInstance().get(Calendar.YEAR)),
        mIncomeMoney, mExpendMoney);
    float sumIncome = chartFragment.showIncomePieChart(mIncomeMoney, mPieChart2);
    float sumExpend = chartFragment.showExpendPieChart(mExpendMoney, mPieChart1);
    Income.setText(String.valueOf(sumIncome));
    Expend.setText(String.valueOf(sumExpend));
    return view;
  }
}
