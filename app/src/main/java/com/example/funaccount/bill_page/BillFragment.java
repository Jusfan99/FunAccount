package com.example.funaccount.bill_page;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.funaccount.R;
import com.example.funaccount.util.BillShowHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class BillFragment extends BillShowHelper {
    Button mAddOneBtn;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bill_frag,container,false);
        mAddOneBtn = view.findViewById(R.id.addone_btn);
        mAddOneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addoneFragmentShow(new AddOneFragment());
            }
        });
        return view;
    }
    //Fragment切换
    private void addoneFragmentShow(Fragment fragment){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.add_one_frag,fragment,"addone");
        transaction.addToBackStack(null);
        transaction.commit();
    }
}