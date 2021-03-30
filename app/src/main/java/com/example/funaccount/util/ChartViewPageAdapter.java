package com.example.funaccount.util;

import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class ChartViewPageAdapter extends PagerAdapter {
  private List<View> mViewList;

  public void AdapterViewpager(List<View> mViewList) {
    this.mViewList = mViewList;
  }

  @Override
  public int getCount() {
    return mViewList.size();
  }

  @NonNull
  @Override
  public Object instantiateItem(@NonNull ViewGroup container, int position) {
    container.addView(mViewList.get(position));
    return mViewList.get(position);
  }

  @Override
  public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
    return view == mViewList.get((int) Integer.parseInt(object.toString()));
  }

  @Override
  public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
    container.removeView(mViewList.get(position));
  }
}
