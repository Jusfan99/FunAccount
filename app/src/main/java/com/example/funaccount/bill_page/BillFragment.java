package com.example.funaccount.bill_page;


import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.funaccount.R;
import com.example.funaccount.util.AccountRecordManager;
import com.example.funaccount.util.BillItem;
import com.example.funaccount.util.BillShowHelper;

import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class BillFragment extends BillShowHelper {
    private static BillShowAdapter mBillShowAdapter;
    private static ArrayList<BillItem> list;
    private static Activity mActivity;
    Button mAddOneBtn;
    static TextView mNoBillToday;
    private static RecyclerView mRecycleView;
    AccountRecordManager mRecordManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bill_frag, null);
        mRecordManager = new AccountRecordManager(getContext());
        mRecordManager.openDataBase();
        mActivity = getActivity();
        mAddOneBtn = view.findViewById(R.id.addone_btn);
        mNoBillToday = view.findViewById(R.id.no_bill_today);
        list = todayBillList(mActivity, mRecordManager);
        mNoBillToday.setVisibility(View.GONE);
        mAddOneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addoneFragmentShow(new AddOneFragment());
            }
        });
        mBillShowAdapter = new BillShowAdapter(mActivity, list);
        mRecycleView = view.findViewById(R.id.bill_show_recycler);
        initRecyclerView(mRecycleView, mBillShowAdapter);

        if (mBillShowAdapter.getItemCount() == 0) {
            mNoBillToday.setVisibility(View.VISIBLE);
        }
        return view;
    }

    //Fragment切换
    private void addoneFragmentShow(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.nav_default_enter_anim, R.anim.nav_default_enter_anim);
        transaction.replace(R.id.add_one_frag, fragment, "addOne");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public static void updateView(AccountRecordManager recordManager) {
        mNoBillToday.setVisibility(View.GONE);
        list = todayBillList(mActivity, recordManager);
        mBillShowAdapter.setBillItems(list);
        mRecycleView.setAdapter(mBillShowAdapter);
        mRecycleView.setLayoutManager(new LinearLayoutManager(mActivity));
    }
}