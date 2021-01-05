package com.example.funaccount.bill_page;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.funaccount.R;
import com.example.funaccount.util.AccountRecord;
import com.example.funaccount.util.AccountRecordManager;
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
    private EditText mTypeEdit;
    private EditText mRemarkEdit;
    private AccountRecordManager mRecordManager;
    private AccountRecord mAccountRecord;
    private Date mDate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.addone, null);
        mFinish = view.findViewById(R.id.finish_add);
        mIncome = view.findViewById(R.id.income_checked);
        mExpend = view.findViewById(R.id.expend_checked);
        mMoneyEdit = view.findViewById(R.id.eidt_money);
        mTypeEdit = view.findViewById(R.id.eidt_type);
        mRemarkEdit = view.findViewById(R.id.eidt_remark);

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
        if(contentIsOk()) {
            long flag = mRecordManager.insertAccountRecord(mAccountRecord);
            if(flag == -1)
                Toast.makeText(this.getContext(), "记账失败", Toast.LENGTH_SHORT).show();
            else {
                Toast.makeText(this.getContext(), "已记录", Toast.LENGTH_SHORT).show();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.remove(fragmentManager.findFragmentByTag("addOne"));
                transaction.commit();
            }
        }else {
            Toast.makeText(this.getContext(), "请检查填写情况", Toast.LENGTH_SHORT).show();
        }
    }
    //定义CheckBox监听器
    CompoundButton.OnCheckedChangeListener mOnCheckedChangeListener
            = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked) {
                if(buttonView.getText().toString().equals(mIncome.getText().toString())){
                    cancleAnother(mExpend);
                }else {
                    cancleAnother(mIncome);
                }
            }
        }
        //实现单选
        public void cancleAnother(CheckBox checkBox){
            if(checkBox.isChecked()) {
                checkBox.setChecked(false);
            }
        }
    };
//    合法性判断和数据存储
    public boolean contentIsOk() {
        if(mMoneyEdit.getText().toString().trim().equals("")) {
            return false;
        }
        float money = Float.parseFloat(mMoneyEdit.getText().toString().trim());
        String type = mTypeEdit.getText().toString().trim();
        String remark = mRemarkEdit.getText().toString().trim();
        if(type.equals("") || (!mIncome.isChecked() && !mExpend.isChecked())) {
            return false;
        }else if(money <= 0) {
            Toast.makeText(this.getContext(), "您填写的金额不合理", Toast.LENGTH_SHORT).show();
            return false;
        }else {
            mAccountRecord.setMoney(money);
            mAccountRecord.setRemark(remark);
            mAccountRecord.setType(type);
            mAccountRecord.setIsIncome(mIncome.isChecked());
            return true;
        }
    }
}
