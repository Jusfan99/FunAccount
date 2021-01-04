package com.example.funaccount.util;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.funaccount.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class BillShowHelper extends Fragment {
    public static class BillShowAdapter extends RecyclerView.Adapter<BillShowAdapter.MyViewHolder> {
        private final Context mContext;
        private ArrayList<BillItem> mBillItems;

        public ArrayList<BillItem> getBillItems() {
            return mBillItems;
        }

        public void setBillItems(ArrayList<BillItem> mBillItems) {
            this.mBillItems = mBillItems;
        }

        public BillShowAdapter(Context context, ArrayList<BillItem> billItems) {
            this.mBillItems = billItems;
            this.mContext = context;
        }

        public static class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView mMoney;
            public TextView mType;
            public TextView mRemark;
            public TextView mIsIncome;
            public MyViewHolder(View itemView) {
                super(itemView);
                mMoney = itemView.findViewById(R.id.bill_money);
                mType = itemView.findViewById(R.id.bill_type);
                mRemark = itemView.findViewById(R.id.bill_remark);
                mIsIncome = itemView.findViewById(R.id.bill_is_income);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        }

        public interface OnItemClickListener {
            //点击每一项的实现方法
            public void OnItemClick(View view, BillItem billItem);
        }

        private BillShowHelper.BillShowAdapter.OnItemClickListener onItemClickListener;

        public void setOnItemClickListener(BillShowHelper.BillShowAdapter.OnItemClickListener onItemClickListener){
            this.onItemClickListener = onItemClickListener;
        }

        @NonNull
        @Override
        public BillShowHelper.BillShowAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = View.inflate(mContext,R.layout.bill_item_show,null);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            BillItem data = mBillItems.get(position);
            holder.mRemark.setText(data.mRemark);
            holder.mType.setText(data.mType);
            holder.mMoney.setText(String.valueOf(data.mMoney));
            holder.mIsIncome.setText("收入");
            if(!data.mIsIncome) {
                holder.mIsIncome.setText("支出");
            }
        }

        @Override
        public int getItemCount() {
            return mBillItems.size();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public ArrayList<BillItem> initData(){
        AccountRecordManager mRecordManager = new AccountRecordManager(this.getContext());
        mRecordManager.openDataBase();
        ArrayList<BillItem> billItems = new ArrayList<BillItem>();
        mRecordManager.getAllRecord(billItems);
        return billItems;
    }

    public void initRecyclerView(View view, BillShowAdapter billShowAdapter){
        RecyclerView recyclerView = view.findViewById(R.id.bill_show_recycler);
        recyclerView.setAdapter(billShowAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public void initListener(BillShowAdapter billShowAdapter){
        billShowAdapter.setOnItemClickListener(new BillShowAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, BillItem billItem) {

            }
        });
    }
}
