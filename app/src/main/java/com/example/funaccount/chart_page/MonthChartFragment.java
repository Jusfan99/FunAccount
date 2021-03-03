package com.example.funaccount.chart_page;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.funaccount.R;
import com.example.funaccount.util.PieChartManager;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MonthChartFragment extends Fragment {
    private PieChart mPieChart1, mPieChart2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.monthchart_frag, null);
//        mPieChart1 = view.findViewById(R.id.pie_chat1);
//        mPieChart2 = view.findViewById(R.id.pie_chat2);
//        showIncomePieChart();
//        showExpendPieChart();
        return view;
    }

    private void showIncomePieChart() {
        //设置每份所占数量
        List<PieEntry> types = new ArrayList<>();
        types.add(new PieEntry(2.0f, "理财"));
        types.add(new PieEntry(3.0f, "薪资"));
        types.add(new PieEntry(4.0f, ""));
        types.add(new PieEntry(5.0f, ""));
        types.add(new PieEntry(1.0f, ""));
        // 设置每份的颜色
        List<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#6785f2"));
        colors.add(Color.parseColor("#675cf2"));
        colors.add(Color.parseColor("#496cef"));
        colors.add(Color.parseColor("#aa63fa"));
        colors.add(Color.parseColor("#f5a658"));
        PieChartManager pieChartManager = new PieChartManager(mPieChart2);
        pieChartManager.showPieChart(types, colors);
    }

    private void showExpendPieChart() {
        // 设置每份所占数量
        List<PieEntry> types = new ArrayList<>();
        types.add(new PieEntry(2.0f, "饮食"));
        types.add(new PieEntry(3.0f, "出行"));
        types.add(new PieEntry(4.0f, "生活"));
        types.add(new PieEntry(5.0f, "服饰"));
        types.add(new PieEntry(6.0f, "其他"));
        //设置每份的颜色
        List<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#6785f2"));
        colors.add(Color.parseColor("#675cf2"));
        colors.add(Color.parseColor("#496cef"));
        colors.add(Color.parseColor("#aa63fa"));
        colors.add(Color.parseColor("#58a9f5"));

        PieChartManager pieChartManager = new PieChartManager(mPieChart1);
        pieChartManager.showPieChart(types, colors);
    }
}
