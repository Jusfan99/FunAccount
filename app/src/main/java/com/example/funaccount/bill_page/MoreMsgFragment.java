package com.example.funaccount.bill_page;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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
  private boolean mIsMoreMsgShow;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.more_msg, null);
    mCloseButton = view.findViewById(R.id.more_msg_close);
    mBillDate = view.findViewById(R.id.more_msg_time);
    mBillMoney = view.findViewById(R.id.more_msg_money);
    mBillType = view.findViewById(R.id.more_msg_type);
    mBillRemark = view.findViewById(R.id.more_msg_remark);
    mBillIncome = view.findViewById(R.id.more_msg_income);
    BillFragment.mAddOneBtn.setClickable(false);

    getParentFragmentManager().setFragmentResultListener("messageKey",
        this, new FragmentResultListener() {
          @Override
          public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
            mMoney = result.getFloat("money");
            mDate = result.getString("date");
            mIncome = result.getString("income");
            mRemark = result.getString("remark");
            mType = result.getString("type");
            mIsMoreMsgShow = result.getBoolean("isOn");

            bindContent();
          }
        });

    mCloseButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Bundle message = new Bundle();
        message.putBoolean("isOn", false);
        BillFragment.mAddOneBtn.setClickable(true);

        FragmentManager fragmentManager = getParentFragmentManager();
        fragmentManager.setFragmentResult("backMessage", message);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.remove(fragmentManager.findFragmentByTag("moreMsg"));
        transaction.commit();
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
