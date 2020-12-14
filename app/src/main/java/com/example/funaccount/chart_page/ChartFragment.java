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
    List<Fragment> mfragments = new ArrayList<Fragment>();
    View mView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(mView == null){
            mView = inflater.inflate(R.layout.chart_frag,container,false);
            initPager(mView);
        } else {
            ViewGroup parent = (ViewGroup) mView.getParent();
            if (parent != null) {
                parent.removeView(mView);
            }
        }
        return mView;
    }

    public void initPager(View view){
        mfragments.add(new WeekChartFragment());
        mfragments.add(new MonthChartFragment());
        mfragments.add(new YearChartFragment());
        FragAdapter adapter = new FragAdapter(getChildFragmentManager(),mfragments);
        ViewPager viewPager = view.findViewById(R.id.viewpaper_chart);
        viewPager.setAdapter(adapter);
        TabLayout tableLayout = view.findViewById(R.id.chart_tab);
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
