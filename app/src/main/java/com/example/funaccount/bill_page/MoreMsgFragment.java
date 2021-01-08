package com.example.funaccount.bill_page;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.funaccount.MainActivity;
import com.example.funaccount.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;

public class MoreMsgFragment extends Fragment {
    private Button mCloseButton;
    private TextView mBillMoney;
    private TextView mBillRemark;
    private TextView mBillDate;
    private TextView mBillIncome;
    private TextView mBillType;

    private float mMoney;
    private String mRemark;
    private String mDate;
    private String mType;
    private String mIncome;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.more_msg, null);
        mCloseButton = view.findViewById(R.id.more_msg_close);
        mCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.remove(fragmentManager.findFragmentByTag("moreMsg"));
                transaction.commit();
            }
        });
        mBillDate = view.findViewById(R.id.more_msg_time);
        mBillMoney = view.findViewById(R.id.more_msg_money);
        mBillType = view.findViewById(R.id.more_msg_type);
        mBillRemark = view.findViewById(R.id.more_msg_remark);
        mBillIncome = view.findViewById(R.id.more_msg_income);

        getParentFragmentManager().setFragmentResultListener("messageKey",
                this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                mMoney = result.getFloat("money");
                mDate = result.getString("date");
                mIncome = result.getString("income");
                mRemark = result.getString("remark");
                mType = result.getString("type");

                bindContent();
            }
        });
        return view;
    }

    private void bindContent() {
        mBillMoney.setText(String.valueOf(mMoney));
        mBillRemark.setText(mRemark);
        mBillType.setText(mType);
        mBillIncome.setText(mIncome);
        mBillDate.setText(mDate);
    }
}
