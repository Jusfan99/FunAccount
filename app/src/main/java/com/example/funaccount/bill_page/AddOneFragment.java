package com.example.funaccount.bill_page;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.funaccount.MainActivity;
import com.example.funaccount.R;
import com.example.funaccount.bmobModels.AccountRecord;
import com.example.funaccount.util.AccountRecordManager;
import com.example.funaccount.util.BillShowHelper;
import com.example.funaccount.util.Date;

import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class AddOneFragment extends Fragment {

  private Button mFinish;
  private CheckBox mIncome;
  private CheckBox mExpend;
  private EditText mMoneyEdit;
  private Spinner mTypeSpinner;
  private ArrayAdapter<String> mAdapter;
  private EditText mRemarkEdit;
  private AccountRecordManager mRecordManager;
  private AccountRecord mAccountRecord;
  private Date mDate;
  private String mTypeResult;
  private CheckBox mNecessary;

  private static final String[] mIncomeType = {"理财", "工资", "兼职", "奖金", "其他"};
  private static final String[] mExpendType = {"饮食", "出行", "生活", "服饰", "其他"};
  private static final String[] mEmptyType = {"请先选择收入or支出"};

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container
      , @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.addone, null);
    mFinish = view.findViewById(R.id.finish_add);
    mIncome = view.findViewById(R.id.income_checked);
    mExpend = view.findViewById(R.id.expend_checked);
    mMoneyEdit = view.findViewById(R.id.eidt_money);
    mTypeSpinner = view.findViewById(R.id.spinner_type);
    mNecessary = view.findViewById(R.id.necessary_spending);
    mAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, mEmptyType);
    mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    mTypeSpinner.setAdapter(mAdapter);
    mTypeSpinner.setVisibility(View.VISIBLE);
    mTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mTypeResult = ((TextView) view).getText().toString();
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });
    mRemarkEdit = view.findViewById(R.id.eidt_remark);
    BillShowHelper.addingOne = true;

    mFinish.setOnClickListener(onClickListener);
    mIncome.setOnCheckedChangeListener(mOnCheckedChangeListener);
    mExpend.setOnCheckedChangeListener(mOnCheckedChangeListener);

    mAccountRecord = new AccountRecord();
    mRecordManager = new AccountRecordManager(this.getContext());
    mRecordManager.openDataBase();
    return view;
  }

  //Button监听器
  View.OnClickListener onClickListener = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      finishClick();
    }
  };

  private void finishClick() {
    Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH) + 1;
    int day = calendar.get(Calendar.DAY_OF_MONTH);
    mDate = new Date(year, month, day);
    mAccountRecord.setDate(mDate);
    if (contentIsOk()) {
      long flag = mRecordManager.insertAccountRecord(mAccountRecord);
      if (flag == -1) {
        Toast.makeText(this.getContext(), getString(R.string.add_fail), Toast.LENGTH_SHORT).show();
      } else {
        Toast.makeText(this.getContext(), getString(R.string.add_success), Toast.LENGTH_SHORT).show();
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        BillFragment.updateView(mRecordManager);
        transaction.remove(fragmentManager.findFragmentByTag("addOne"));
        transaction.commit();
        if (!TextUtils.isEmpty(((MainActivity) getActivity()).getUserId())) {
          mAccountRecord.setUserId(((MainActivity) getActivity()).getUserId());
          mRecordManager.addToBomb(mAccountRecord);
        }
      }
    } else {
      Toast.makeText(this.getContext(), getString(R.string.check_input), Toast.LENGTH_SHORT).show();
    }
  }

  //定义CheckBox监听器
  CompoundButton.OnCheckedChangeListener mOnCheckedChangeListener
      = new CompoundButton.OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
      if (isChecked) {
        if (buttonView.getText().toString().equals(mIncome.getText().toString())) {
          cancleAnother(mExpend);
          changeSpinnerAdapter(mIncomeType);
        } else {
          cancleAnother(mIncome);
          changeSpinnerAdapter(mExpendType);
        }
      }
    }

    //实现单选
    public void cancleAnother(CheckBox checkBox) {
      if (checkBox.isChecked()) {
        checkBox.setChecked(false);
      }
    }
  };

  //    合法性判断和数据存储
  public boolean contentIsOk() {
    if (mMoneyEdit.getText().toString().trim().equals("")) {
      return false;
    }
    float money = Float.parseFloat(mMoneyEdit.getText().toString().trim());
    String type = mTypeResult.trim();
    String remark = mRemarkEdit.getText().toString().trim();
    if (type.equals("") || (!mIncome.isChecked() && !mExpend.isChecked())) {
      return false;
    } else if (money <= 0) {
      Toast.makeText(this.getContext(), getString(R.string.money_input_check), Toast.LENGTH_SHORT).show();
      return false;
    } else {
      mAccountRecord.setMoney(money);
      mAccountRecord.setRemark(remark);
      mAccountRecord.setType(type);
      mAccountRecord.setIsIncome(mIncome.isChecked());
      mAccountRecord.setId(mRecordManager.getDataCount() + 1);
      mAccountRecord.setIsNecessary(mNecessary.isChecked());
      return true;
    }
  }

  public void changeSpinnerAdapter(String[] items) {
    mAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, items);
    mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    mTypeSpinner.setAdapter(mAdapter);
    mTypeSpinner.setVisibility(View.GONE);
    mTypeSpinner.setVisibility(View.VISIBLE);
  }
}
