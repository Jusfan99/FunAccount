package com.example.funaccount.chart_page;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.funaccount.R;
import com.example.funaccount.util.FragAdapter;
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
}
