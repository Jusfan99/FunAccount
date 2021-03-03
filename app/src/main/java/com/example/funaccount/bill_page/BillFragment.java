package com.example.funaccount.bill_page;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class BillFragment extends BillShowHelper {
    private static BillShowAdapter mBillShowAdapter;
    private static ArrayList<BillItem> list;
    private static Activity mActivity;
    static Button mAddOneBtn;
    static TextView mNoBillToday;
    private static RecyclerView mRecycleView;
    AccountRecordManager mRecordManager;
    //本地广播管理者   可以用来注册广播
    LocalBroadcastManager mLocalBroadcastManager;
    //本地广播接收者
    LocalReceiver mLocalReceiver = new LocalReceiver();
    IntentFilter mIntentFilter = new IntentFilter();

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
                addOneFragmentShow(new AddOneFragment());
            }
        });
        mBillShowAdapter = new BillShowAdapter(mActivity, list);
        mRecycleView = view.findViewById(R.id.bill_show_recycler);
        initRecyclerView(mRecycleView, mBillShowAdapter);

        if (mBillShowAdapter.getItemCount() == 0) {
            mNoBillToday.setVisibility(View.VISIBLE);
        }
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(getContext());
        mIntentFilter.addAction(LOCAL_BROADCAST);   //添加action
        mLocalBroadcastManager.registerReceiver(mLocalReceiver, mIntentFilter);
        return view;
    }

    //Fragment切换
    private void addOneFragmentShow(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.nav_default_enter_anim, R.anim.nav_default_enter_anim);
        transaction.replace(R.id.add_one_frag, fragment, "addOne");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public static void updateView(AccountRecordManager recordManager) {
        list = todayBillList(mActivity, recordManager);
        BillShowHelper.addingOne = false;
        if (list.size() == 0) {
            mNoBillToday.setVisibility(View.VISIBLE);
        } else {
            mNoBillToday.setVisibility(View.GONE);
            mBillShowAdapter.setBillItems(list);
            mRecycleView.setAdapter(mBillShowAdapter);
            mRecycleView.setLayoutManager(new LinearLayoutManager(mActivity));
        }
    }

    public class LocalReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();
            if (!action.equals(LOCAL_BROADCAST)) {
                return;
            }
            if (intent.getBooleanExtra("delete", false)) {
                updateView(mRecordManager);
            }
        }
    }

    @Override
    public void onDestroy() {
        if (mLocalBroadcastManager != null) {
            mLocalBroadcastManager.unregisterReceiver(mLocalReceiver);
        }
        super.onDestroy();
    }
}