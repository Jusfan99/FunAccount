package com.example.funaccount.chart_page;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.funaccount.R;
import com.example.funaccount.util.BillItem;
import com.example.funaccount.util.FragAdapter;
import com.example.funaccount.util.PieChartManager;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class ChartFragment extends Fragment {
    List<Fragment> mFragments = new ArrayList<Fragment>();
    View mView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.chart_frag, container, false);
            dataBind(mView);
        } else {
            ViewGroup parent = (ViewGroup) mView.getParent();
            if (parent != null) {
                parent.removeView(mView);
            }
        }
        return mView;
    }

    public void dataBind(View view) {
        FragAdapter adapter = new FragAdapter(getChildFragmentManager(), mFragments);
        ViewPager viewPager = view.findViewById(R.id.viewpaper_chart);
        viewPager.setAdapter(adapter);
        TabLayout tableLayout = view.findViewById(R.id.chart_tab);
        tableLayout.setupWithViewPager(viewPager);
        tableLayout.setTabMode(TabLayout.MODE_FIXED);
        TabLayout.Tab tab1 = tableLayout.getTabAt(0);
        tab1.setText(getString(R.string.this_week));
        TabLayout.Tab tab2 = tableLayout.getTabAt(1);
        tab2.setText(getString(R.string.this_month));
        TabLayout.Tab tab3 = tableLayout.getTabAt(2);
        tab3.setText(getString(R.string.this_year));
    }

    public void initData() {
        mFragments.add(new WeekChartFragment());
        mFragments.add(new MonthChartFragment());
        mFragments.add(new YearChartFragment());
    }

    //获取每种收入次数
    public void getTypeProportion(ArrayList<BillItem> billItems, float[] mIncomeMoney, float[] mExpendMoney) {
        for(int i = 0; i < billItems.size(); i++) {
            String type = billItems.get(i).getType();
            switch (type) {
                case ("理财") :
                    mIncomeMoney[0] += billItems.get(i).getMoney() ;
                    break;
                case ("工资"):
                    mIncomeMoney[1] += billItems.get(i).getMoney() ;
                    break;
                case ("兼职"):
                    mIncomeMoney[2] += billItems.get(i).getMoney() ;
                    break;
                case ("奖金"):
                    mIncomeMoney[3] += billItems.get(i).getMoney() ;
                    break;
                case ("饮食"):
                    mExpendMoney[0] += billItems.get(i).getMoney() ;
                    break;
                case ("出行"):
                    mExpendMoney[1] += billItems.get(i).getMoney() ;
                    break;
                case ("生活"):
                    mExpendMoney[2] += billItems.get(i).getMoney() ;
                    break;
                case ("服饰"):
                    mExpendMoney[3] += billItems.get(i).getMoney() ;
                    break;
                case ("其他"):{
                    if(billItems.get(i).isIncome()) {
                        mIncomeMoney[4] += billItems.get(i).getMoney() ;
                    } else {
                        mExpendMoney[4] += billItems.get(i).getMoney() ;
                    }
                    break;
                }
            }
        }
    }
    public void showIncomePieChart(float[] mIncomeMoney, PieChart mPieChart2) {
        //设置每份所占数量
        List<PieEntry> types = new ArrayList<>();
        types.add(new PieEntry(mIncomeMoney[0], "理财"));
        types.add(new PieEntry(mIncomeMoney[1], "工资"));
        types.add(new PieEntry(mIncomeMoney[2], "兼职"));
        types.add(new PieEntry(mIncomeMoney[3], "奖金"));
        types.add(new PieEntry(mIncomeMoney[4], "其他"));
        // 设置每份的颜色
        List<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#EEB4B4"));
        colors.add(Color.parseColor("#EE9572"));
        colors.add(Color.parseColor("#EEAEEE"));
        colors.add(Color.parseColor("#F0E68C"));
        colors.add(Color.parseColor("#C1FFC1"));
        PieChartManager pieChartManager = new PieChartManager(mPieChart2);
        pieChartManager.showPieChart(types, colors);
    }

    public void showExpendPieChart(float[] mExpendMoney, PieChart mPieChart1) {
        // 设置每份所占数量
        List<PieEntry> types = new ArrayList<>();
        types.add(new PieEntry(mExpendMoney[0], "饮食"));
        types.add(new PieEntry(mExpendMoney[1], "出行"));
        types.add(new PieEntry(mExpendMoney[2], "生活"));
        types.add(new PieEntry(mExpendMoney[3], "服饰"));
        types.add(new PieEntry(mExpendMoney[4], "其他"));
        //设置每份的颜色
        List<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#EEB4B4"));
        colors.add(Color.parseColor("#EE9572"));
        colors.add(Color.parseColor("#EEAEEE"));
        colors.add(Color.parseColor("#F0E68C"));
        colors.add(Color.parseColor("#C1FFC1"));

        PieChartManager pieChartManager = new PieChartManager(mPieChart1);
        pieChartManager.showPieChart(types, colors);
    }

}
