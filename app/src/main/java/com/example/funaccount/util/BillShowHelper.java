package com.example.funaccount.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.funaccount.R;
import com.example.funaccount.bill_page.MoreMsgFragment;
import com.example.funaccount.detail_page.DetailFragment;

import java.util.ArrayList;
import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class BillShowHelper extends Fragment {
  public boolean mIsMoreMsgShow = false;
  public static final String LOCAL_BROADCAST = "com.example.funaccount.detail_page.LOCAL_BROADCAST";
  public static boolean addingOne = false;

  public class BillShowAdapter extends RecyclerView.Adapter<BillShowAdapter.MyViewHolder> {
    public final Context mContext;
    private ArrayList<BillItem> mBillItems;
    private LinearLayout mItemContent;
    private TextView mDelete;
    private int mDeleteWidth;
    public boolean mIsDeleteShow = false;
    public int mScreenWidth;
    private int mDownX;
    private int mDownY;
    private int mScrollY;
    private LinearLayout.LayoutParams mLayoutParams;

    public ArrayList<BillItem> getBillItems() {
      return mBillItems;
    }

    public void setBillItems(ArrayList<BillItem> mBillItems) {
      this.mBillItems = mBillItems;
    }

    public BillShowAdapter(Context context, ArrayList<BillItem> billItems) {
      this.mBillItems = billItems;
      this.mContext = context;
      WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
      DisplayMetrics dm = new DisplayMetrics();
      manager.getDefaultDisplay().getMetrics(dm);
      mScreenWidth = dm.widthPixels;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
      public TextView mMoney;
      public TextView mType;
      public TextView mDate;
      public TextView mIsIncome;
      public TextView mDelete;

      public MyViewHolder(View itemView) {
        super(itemView);
        mMoney = itemView.findViewById(R.id.bill_money);
        mType = itemView.findViewById(R.id.bill_type);
        mDate = itemView.findViewById(R.id.bill_date);
        mIsIncome = itemView.findViewById(R.id.bill_is_income);
        mDelete = itemView.findViewById(R.id.tv_delete);
      }

      public void setTouchListener(View.OnTouchListener onTouchListener) {
        itemView.setOnTouchListener(onTouchListener);
      }

    }

    @NonNull
    @Override
    public BillShowHelper.BillShowAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                                          int viewType) {
      View itemView = View.inflate(mContext, R.layout.bill_item_show, null);
      return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
      BillItem data = mBillItems.get(position);
      holder.mDate.setText(data.getDate().toString());
      holder.mType.setText(data.getType());
      holder.mMoney.setText(String.valueOf(data.getMoney()));
      holder.mIsIncome.setText(R.string.income);

      holder.setTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
          if (!addingOne) {
            updatePageStatus();
            if (mIsMoreMsgShow) {
              return false;
            } else {
              int action = event.getAction();
              switch (action) {
                case MotionEvent.ACTION_DOWN:
                  doActionDown(event, holder);
                  break;
                case MotionEvent.ACTION_MOVE:
                  return doActionMove(event);
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                  doActionUp(data, v, event);
                  break;
              }
              return true;
            }
          } else {
            return false;
          }
        }
      });

      holder.mDelete.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          showDeleteDialog(position);
        }
      });

      if (!data.isIncome()) {
        holder.mIsIncome.setText(R.string.expend);
      }
    }

    private void showDeleteDialog(int position) {
      final AlertDialog.Builder deleteDialog = new AlertDialog.Builder(getActivity());
      deleteDialog.setTitle("删除账单");
      deleteDialog.setMessage("是否确认删除这笔账单");
      deleteDialog.setPositiveButton("删除", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
          removeDataFromDataBase(mBillItems.get(position).getId());
          removeDataFromView(position);
          backToNormal();

          LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getContext());
          final Intent intent = new Intent(DetailFragment.LOCAL_BROADCAST);
          intent.putExtra("delete", true);
          localBroadcastManager.sendBroadcast(intent);
        }
      });
      deleteDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
          backToNormal();
        }
      });
      deleteDialog.show();
    }

    private void removeDataFromView(int position) {
      mBillItems.remove(position);
      //删除动画
      notifyItemRemoved(position);
      notifyDataSetChanged();
    }

    private void doActionDown(MotionEvent e, BillShowAdapter.MyViewHolder viewHolder) {
      if (mIsDeleteShow) {
        backToNormal();
      }
      //获取按下时的坐标x、y
      mDownX = (int) e.getX();
      mDownY = (int) e.getY();

      View itemView = viewHolder.itemView;
      mDelete = itemView.findViewById(R.id.tv_delete);
      mItemContent = itemView.findViewById(R.id.bill_item_content);
      //2、获取删除按钮的宽度
      mDeleteWidth = mDelete.getLayoutParams().width;
      mLayoutParams = (LinearLayout.LayoutParams) mItemContent.getLayoutParams();
      mLayoutParams.width = mScreenWidth;
      mItemContent.setLayoutParams(mLayoutParams);
    }

    private boolean doActionMove(MotionEvent e) {
      //计算偏移量
      int scrollX = (int) (e.getX() - mDownX);
      //左右滑动
      if (e.getX() < mDownX) {
        //向左滑动
        if (-(scrollX / 2) >= mDeleteWidth) {
          scrollX = -mDeleteWidth;
        }
        //重新设置第一个childView的左边距
        mLayoutParams.leftMargin = scrollX;
        mItemContent.setLayoutParams(mLayoutParams);
      }
      return true;
    }

    private void doActionUp(BillItem data, View v, MotionEvent e) {
      int y = (int) e.getY();
      mScrollY = (int) (y - mDownY);
      // 偏移量大于按钮尺寸的一半，则显示
      if (-mLayoutParams.leftMargin >= mDeleteWidth / 2) {
        mLayoutParams.leftMargin = -mDeleteWidth;
        //给显示按钮的标识赋值
        mIsDeleteShow = true;
        mDelete.setVisibility(VISIBLE);
      } else if (-mLayoutParams.leftMargin < mDeleteWidth / 2 && -mLayoutParams.leftMargin >= 10) {
        backToNormal();
      } else if (mScrollY > -1 && mScrollY < 10) {
        if (mIsDeleteShow) {
          backToNormal();
          mIsDeleteShow = false;
        } else {
          mIsMoreMsgShow = true;
          Bundle message = new Bundle();
          message.putBoolean("isOn", mIsMoreMsgShow);
          message.putString("date", data.getDate().toString());
          message.putString("type", data.getType());
          message.putFloat("money", data.getMoney());
          message.putString("income", data.isIncome() ?
              mContext.getString(R.string.income) : mContext.getString(R.string.expend));
          message.putString("remark", data.getRemark());
          message.putString("necessary", data.isNecessary() ? "是" : "否");

          FragmentManager fragmentManager = FragmentManager.findFragment(v).getChildFragmentManager();
          fragmentManager.setFragmentResult("messageKey", message);
          FragmentTransaction transaction = fragmentManager.beginTransaction();
          transaction.replace(R.id.more_msg_frag, new MoreMsgFragment(), "moreMsg");
          transaction.addToBackStack(null);
          transaction.commit();
        }
      }
      mItemContent.setLayoutParams(mLayoutParams);
    }

    public void backToNormal() {
      //重新设置第一个childView的左边距
      mLayoutParams.leftMargin = 0;
      mItemContent.setLayoutParams(mLayoutParams);
      mDelete.setVisibility(GONE);
    }

    @Override
    public int getItemCount() {
      return mBillItems.size();
    }
  }

  //用mIsMoreMsgShow判断是否已展示更多信息 决定是否开启点击事件
  public void updatePageStatus() {
    getChildFragmentManager().setFragmentResultListener("backMessage", this,
        new FragmentResultListener() {
          @Override
          public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
            mIsMoreMsgShow = result.getBoolean("isOn");
          }
        });
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container
      , @Nullable Bundle savedInstanceState) {
    AccountRecordManager mRecordManager = initDataBase();
    ArrayList<BillItem> mBillItems = new ArrayList<BillItem>();
    return super.onCreateView(inflater, container, savedInstanceState);
  }

  public AccountRecordManager initDataBase() {
    AccountRecordManager recordManager = new AccountRecordManager(getContext());
    recordManager.openDataBase();
    return recordManager;
  }

  public static ArrayList<BillItem> todayBillList(Context context, AccountRecordManager recordManager) {
    recordManager = new AccountRecordManager(context);
    ArrayList<BillItem> billItems = new ArrayList<BillItem>();
    recordManager.openDataBase();
    billItems = recordManager.getTodayRecord(Calendar.getInstance().get(Calendar.YEAR),
        Calendar.getInstance().get(Calendar.MONTH) + 1, Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
    return billItems;
  }

  public ArrayList<BillItem> allBillList(AccountRecordManager recordManager) {
    return recordManager.getAllRecord();
  }

  public ArrayList<BillItem> monthBillList(int year, int month, AccountRecordManager recordManager) {
    return recordManager.getMonthRecord(year, month);
  }

  public void initRecyclerView(RecyclerView recyclerView, BillShowAdapter billShowAdapter) {
    recyclerView.setAdapter(billShowAdapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
  }

  public float sumIncome(ArrayList<BillItem> billItems) {
    float sum = 0;
    for (BillItem billItem : billItems) {
      if (billItem.isIncome()) {
        sum += billItem.getMoney();
      }
    }
    return sum;
  }

  public float sumExpend(ArrayList<BillItem> billItems) {
    float sum = 0;
    for (BillItem billItem : billItems) {
      if (!billItem.isIncome()) {
        sum += billItem.getMoney();
      }
    }
    return sum;
  }

  private void removeDataFromDataBase(long id) {
    long flag = initDataBase().deleteOneRecord(id);
    if (flag == -1) {
      Toast.makeText(getContext(), getString(R.string.delete_fail), Toast.LENGTH_SHORT).show();
    } else {
      Toast.makeText(this.getContext(), getString(R.string.delete_bill_success), Toast.LENGTH_SHORT).show();
    }
  }
}
