package com.example.funaccount.other_pages.login_page;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.funaccount.R;
import com.example.funaccount.util.UserData;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CountListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class ResetPwdActivity extends AppCompatActivity {
  int CHECK_TRUE = 1;
  int CHECK_FALSE = 0;

  private EditText mAccount;                        //用户名编辑
  private EditText mPwdOld;                        //密码编辑
  private EditText mPwdNew;                        //密码编辑
  private EditText mPwdCheck;                       //密码编辑
  private Button mSureButton;                       //确定按钮
  private Button mCancelButton;                     //取消按钮
  private Context mContext = this;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.resetpwd);
    mAccount = (EditText) findViewById(R.id.resetpwd_edit_name);
    mPwdOld = (EditText) findViewById(R.id.resetpwd_edit_pwd_old);
    mPwdNew = (EditText) findViewById(R.id.resetpwd_edit_pwd_new);
    mPwdCheck = (EditText) findViewById(R.id.resetpwd_edit_pwd_check);

    mSureButton = (Button) findViewById(R.id.resetpwd_btn_sure);
    mCancelButton = (Button) findViewById(R.id.resetpwd_btn_cancel);

    mSureButton.setOnClickListener(mResetPwdListener);      //注册界面两个按钮的监听事件
    mCancelButton.setOnClickListener(mResetPwdListener);
  }

  View.OnClickListener mResetPwdListener = new View.OnClickListener() {    //不同按钮按下的监听事件选择
    public void onClick(View v) {
      switch (v.getId()) {
        case R.id.resetpwd_btn_sure:                       //确认按钮的监听事件
          resetPwdCheck();
          break;
        case R.id.resetpwd_btn_cancel:                     //取消按钮的监听事件,由注册界面返回登录界面
          Intent intent_Resetpwd_to_Login = new Intent(ResetPwdActivity.this,
              LoginActivity.class);    //切换Resetpwd Activity至Login Activity
          startActivity(intent_Resetpwd_to_Login);
          finish();
          break;
      }
    }
  };

  public void resetPwdCheck() {                                //确认按钮的监听事件
    if (isUserNameAndPwdValid()) {
      String userName = mAccount.getText().toString().trim();
      String userPwdOld = mPwdOld.getText().toString().trim();
      String userPwdNew = mPwdNew.getText().toString().trim();
      String userPwdCheck = mPwdCheck.getText().toString().trim();
      BmobQuery<UserData> bmobQuery = new BmobQuery<UserData>();
      bmobQuery.addWhereEqualTo("userName", userName);
      bmobQuery.addWhereEqualTo("userPwd", userPwdOld);
      bmobQuery.findObjects(new FindListener<UserData>() {
        @Override
        public void done(List<UserData> list, BmobException e) {
          if (e == null) {
            if (list.size() == CHECK_TRUE) {
              if (userPwdNew.equals(userPwdCheck) == false) {           //两次密码输入不一样
                Toast.makeText(mContext, getString(R.string.password_different), Toast.LENGTH_SHORT).show();
              } else {
                for (UserData userData : list) {
                  userData.setUserPwd(userPwdNew);
                  userData.update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                      if (e == null) {
                        Toast.makeText(mContext, getString(R.string.reset_success), Toast.LENGTH_SHORT).show();
                        userData.pwdresetFlag = 1;
                        Intent intent_Register_to_Login = new Intent(ResetPwdActivity.this,
                            LoginActivity.class);    //切换User Activity至Login Activity
                        startActivity(intent_Register_to_Login);
                        finish();
                      } else {
                        Toast.makeText(mContext, getString(R.string.reset_fail), Toast.LENGTH_SHORT).show();
                      }
                    }
                  });
                }
              }
            } else if (list.size() == CHECK_FALSE) {
              Toast.makeText(mContext, getString(R.string.user_check_false), Toast.LENGTH_SHORT).show();
            }
          }
        }
      });
    }
  }

  public boolean isUserNameAndPwdValid() {
    final boolean[] result = {true};
    String userName = mAccount.getText().toString().trim();
    BmobQuery<UserData> bmobQuery = new BmobQuery<UserData>();
    bmobQuery.addWhereEqualTo("userName", userName);
    bmobQuery.count(UserData.class, new CountListener() {
      @Override
      public void done(Integer integer, BmobException e) {
        if (integer <= 0) {
          Toast.makeText(mContext, getString(R.string.username_inexistence), Toast.LENGTH_SHORT).show();
          result[0] = false;
        }
      }
    });
    if (mAccount.getText().toString().trim().equals("")) {
      Toast.makeText(this, getString(R.string.user_name_empty), Toast.LENGTH_SHORT).show();
      return false;
    } else if (mPwdOld.getText().toString().trim().equals("")) {
      Toast.makeText(this, getString(R.string.password_empty), Toast.LENGTH_SHORT).show();
      return false;
    } else if (mPwdNew.getText().toString().trim().equals("")) {
      Toast.makeText(this, getString(R.string.password_empty), Toast.LENGTH_SHORT).show();
      return false;
    } else if (mPwdCheck.getText().toString().trim().equals("")) {
      Toast.makeText(this, getString(R.string.password_check_empty), Toast.LENGTH_SHORT).show();
      return false;
    }
    return result[0];
  }
}