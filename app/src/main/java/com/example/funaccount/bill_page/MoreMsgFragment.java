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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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

        bindContent();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMoney = ((MainActivity) getActivity()).getBillMoney();
        mDate = ((MainActivity) getActivity()).getBillDate();
        mIncome = ((MainActivity) getActivity()).getBillIsIncome();
        mRemark = ((MainActivity) getActivity()).getBillRemark();
        mType = ((MainActivity) getActivity()).getBillType();
    }

//    @Override
//    public void onCreate(@NonNull Context context) {
//        super.onAttach(context);
//        mMoney = ((MainActivity) context).getBillMoney();
//        mDate = ((MainActivity) context).getBillDate();
//        mIncome = ((MainActivity) context).getBillIsIncome();
//        mRemark = ((MainActivity) context).getBillRemark();
//        mType = ((MainActivity) context).getBillType();
//    }

    private void bindContent() {
        mBillMoney.setText(String.valueOf(mMoney));
        mBillRemark.setText(mRemark);
        mBillType.setText(mType);
        mBillIncome.setText(mIncome);
        mBillDate.setText(mDate);
    }
}
