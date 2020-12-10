package com.example.funaccount.chart_page;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.VerifiedInputEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;


import com.example.funaccount.R;
import com.example.funaccount.util.ChartViewPageAdapter;
import com.example.funaccount.util.FragAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

public class ChartFragment extends Fragment {
    List<Fragment> fragments = new ArrayList<Fragment>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.chart_frag,container,false);
    }

    @Override
    public void onStart() {
        super.onStart();
        fragments.add(new WeekChartFragment());
        fragments.add(new MonthChartFragment());
        fragments.add(new YearChartFragment());
//        views.add(getView().findViewById(R.id.week_frag));
//        views.add(getView().findViewById(R.id.month_frag));
//        views.add(getView().findViewById(R.id.year_frag));
        FragAdapter adapter = new FragAdapter(getChildFragmentManager(),fragments);
        ViewPager viewPager = getView().findViewById(R.id.viewpaper_chart);
//        ChartViewPageAdapter chartViewPageAdapter = new ChartViewPageAdapter();
//        chartViewPageAdapter.AdapterViewpager(views);
        viewPager.setAdapter(adapter);
        //    List<View> views = new ArrayList<View>();
        TabLayout tableLayout = (TabLayout) getView().findViewById(R.id.chart_tab);
        tableLayout.setupWithViewPager(viewPager);
        tableLayout.setTabMode(TabLayout.MODE_FIXED);
        TabLayout.Tab tab1 = tableLayout.getTabAt(0);
        tab1.setText("本周");
        TabLayout.Tab tab2 = tableLayout.getTabAt(1);
        tab2.setText("本月");
        TabLayout.Tab tab3 = tableLayout.getTabAt(2);
        tab3.setText("今年");
    }
}
