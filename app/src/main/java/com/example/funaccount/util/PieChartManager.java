package com.example.funaccount.util;

import android.graphics.Color;
import android.graphics.Typeface;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.List;

public class PieChartManager {
  public PieChart mPieChart;

  public PieChartManager(PieChart pieChart) {
    this.mPieChart = pieChart;
    initPieChart();
  }

  private void initPieChart() {
    //是否显示中间的洞
    mPieChart.setDrawHoleEnabled(false);
    mPieChart.setHoleRadius(40f);
    //半透明
    mPieChart.setTransparentCircleRadius(30f);
    mPieChart.setTransparentCircleColor(Color.WHITE);
    mPieChart.setTransparentCircleAlpha(125); //透明度

    //中心文字
    mPieChart.setDrawCenterText(false);
    mPieChart.setCenterText("分类占比");
    mPieChart.setCenterTextColor(Color.parseColor("#a1a1a1"));
    mPieChart.setCenterTextSizePixels(36);
    mPieChart.setCenterTextRadiusPercent(1f);
    mPieChart.setCenterTextTypeface(Typeface.DEFAULT);
    mPieChart.setCenterTextOffset(0, 0);

    mPieChart.setRotationAngle(0); //旋转角度
    mPieChart.setRotationEnabled(true);
    mPieChart.setUsePercentValues(true);
    mPieChart.getDescription().setEnabled(false);//取消右下角描述

    //是否显示每个部分的文字描述
    mPieChart.setDrawEntryLabels(false);
    mPieChart.setEntryLabelColor(Color.RED);
    mPieChart.setEntryLabelTextSize(14);
    mPieChart.setEntryLabelTypeface(Typeface.DEFAULT_BOLD);

    //偏移
    mPieChart.setExtraOffsets(20, 8, 75, 8);

    mPieChart.setBackgroundColor(Color.TRANSPARENT);

    //转动摩擦阻力
    mPieChart.setDragDecelerationFrictionCoef(0.75f);

    //获取图例
    Legend legend = mPieChart.getLegend();
    legend.setOrientation(Legend.LegendOrientation.VERTICAL);
    legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
    legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);

    legend.setXEntrySpace(7f);
    legend.setYEntrySpace(10f);
    legend.setYOffset(10f);
    legend.setXOffset(10f);
    legend.setTextColor(Color.parseColor("#a1a1a1"));
    legend.setTextSize(13);
  }

  public void showPieChart(List<PieEntry> items, List<Integer> colors) {
    //数据集合
    PieDataSet dataSet = new PieDataSet(items, "");

    dataSet.setColors(colors);
    dataSet.setDrawValues(true);
    dataSet.setValueTextSize(14);
    dataSet.setValueTextColor(Color.parseColor("#ADD8E6"));
    dataSet.setValueTypeface(Typeface.DEFAULT_BOLD);

    //当值位置为外边线时，表示线的前半段长度
    dataSet.setValueLinePart1Length(0.4f);
    //当值位置为外边线时，表示线的后半段长度
    dataSet.setValueLinePart2Length(0.4f);
    //当valuePosits为OutSiDice时，指示偏移为切片大小的百分比
    dataSet.setValueLinePart1OffsetPercentage(80f);
    //当值位置为外边线时，表示线的颜色
    dataSet.setValueLineColor(Color.parseColor("#a1a1a1"));
    //设置Y值的位置在圆内还是圆外
    dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
    //设置每条之前的间隙
    dataSet.setSliceSpace(0);

    //设置饼状Item被选中时变化的距离
    dataSet.setSelectionShift(5f);
    //填充数据
    PieData pieData = new PieData(dataSet);
    //格式化显示的数据为%百分比
    pieData.setValueFormatter(new PercentFormatter());
    //显示试图
    mPieChart.setData(pieData);
  }
}
