package com.example.funaccount.bill_page;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.funaccount.R;
import com.example.funaccount.util.BillShowHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class BillFragment extends BillShowHelper {
    Button mAddOneBtn;
    BillShowAdapter mBillShowAdapter;
    TextView mNoBillToday;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bill_frag, null);
        mAddOneBtn = view.findViewById(R.id.addone_btn);
        mNoBillToday = view.findViewById(R.id.no_bill_today);
        mNoBillToday.setVisibility(View.GONE);
        mAddOneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addoneFragmentShow(new AddOneFragment());
            }
        });
        mBillShowAdapter = new BillShowAdapter(getActivity(), initData());
//        initListener(billShowAdapter);
        initRecyclerView(view, mBillShowAdapter);

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

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        updateUi();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void updateUi() {
        mBillShowAdapter = new BillShowAdapter(getActivity(), initData());
        initRecyclerView(getView(), mBillShowAdapter);
    }
}