package com.example.funaccount.util;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.funaccount.R;
import com.example.funaccount.setting_page.SettingFragment;

import java.util.Set;

public class BudgetHomeFragment extends Fragment {
  private ImageView mCloseBtn;
  private Button mAddBudget;
  private Button mChangeBudget;
  private TextView mBudgetMark;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.budget_setting_item, container, false);
    bindView(view);
    bindListener();
    return view;
  }

  public void bindView(View view) {
    mCloseBtn = view.findViewById(R.id.budget_close);
    mAddBudget = view.findViewById(R.id.add_budget);
    mChangeBudget = view.findViewById(R.id.change_budget);
    mBudgetMark = view.findViewById(R.id.budget_mark);
  }

  public void bindListener() {
    EditText et = new EditText(getContext());
    AlertDialog.Builder builder = new AlertDialog.Builder(getContext()).setTitle("设置每周预算")
        .setIcon(R.drawable.setting4)
        .setView(et)
        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            String input = et.getText().toString();
            if (input.equals("")) {
              Toast.makeText(getContext(), "填写内容不能为空！" + input, Toast.LENGTH_LONG).show();
            } else {
              Toast.makeText(getContext(), "预算设置成功 " + input, Toast.LENGTH_SHORT).show();
            }
          }
        })
        .setNegativeButton("取消", null);
    mCloseBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.remove(fragmentManager.findFragmentByTag("budgetHome"));
        transaction.commit();
      }
    });

    mAddBudget.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        builder.create().show();
      }
    });
  }
}
