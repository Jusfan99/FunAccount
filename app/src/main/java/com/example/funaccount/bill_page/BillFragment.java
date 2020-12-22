package com.example.funaccount.bill_page;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.funaccount.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import static java.util.Locale.*;

public class BillFragment extends Fragment {
    Button mAddoneBtn;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bill_frag,container,false);
        mAddoneBtn = view.findViewById(R.id.addone_btn);
        mAddoneBtn.setOnClickListener(new View.OnClickListener() {
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