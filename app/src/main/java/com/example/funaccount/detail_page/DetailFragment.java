package com.example.funaccount.detail_page;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.funaccount.R;
import com.example.funaccount.util.BillShowHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DetailFragment extends BillShowHelper {
    BillShowAdapter mBillShowAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_frag, null);
        mBillShowAdapter = new BillShowAdapter(getActivity(), initAllData());
        initRecyclerView(view.findViewById(R.id.detail_show_bill), mBillShowAdapter);
        return view;
    }
}

