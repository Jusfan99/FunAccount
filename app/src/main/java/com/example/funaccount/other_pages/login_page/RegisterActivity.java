package com.example.funaccount.other_pages.login_page;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.funaccount.R;
import com.example.funaccount.bmobModels.UserData;

import androidx.appcompat.app.AppCompatActivity;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CountListener;
import cn.bmob.v3.listener.SaveListener;

public class RegisterActivity extends AppCompatActivity {
  private EditText mAccount;                        //用户名编辑
  private EditText mPwd;                            //密码编辑
  private EditText mPwdCheck;                       //密码编辑
  private Button mSureButton;                       //确定按钮
  private Button mCancelButton;                     //取消按钮
  private Context mContext = this;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.register);
    mAccount = (EditText) findViewById(R.id.resetpwd_edit_name);
    mPwd = (EditText) findViewById(R.id.resetpwd_edit_pwd_old);
    mPwdCheck = (EditText) findViewById(R.id.resetpwd_edit_pwd_new);

    mSureButton = (Button) findViewById(R.id.register_btn_sure);
    mCancelButton = (Button) findViewById(R.id.register_btn_cancel);

    mSureButton.setOnClickListener(m_register_Listener);      //注册界面两个按钮的监听事件
    mCancelButton.setOnClickListener(m_register_Listener);
  }

  View.OnClickListener m_register_Listener = new View.OnClickListener() {    //不同按钮按下的监听事件选择
    public void onClick(View v) {
      switch (v.getId()) {
        case R.id.register_btn_sure:                       //确认按钮的监听事件
          registerCheck();
          break;
        case R.id.register_btn_cancel:                     //取消按钮的监听事件,由注册界面返回登录界面
          Intent intent_Register_to_Login = new Intent(RegisterActivity.this,
              LoginActivity.class);    //切换User Activity至Login Activity
          startActivity(intent_Register_to_Login);
          finish();
          break;
      }
    }
  };

  public void registerCheck() {                                //确认按钮的监听事件
    if (isUserNameAndPwdValid()) {
      String userName = mAccount.getText().toString().trim();
      String userPwd = mPwd.getText().toString().trim();
      String userPwdCheck = mPwdCheck.getText().toString().trim();
      if (userPwd.equals(userPwdCheck) == false) {     //两次密码输入不一样
        Toast.makeText(this, getString(R.string.password_different), Toast.LENGTH_SHORT).show();
        return;
      }
      BmobQuery<UserData> bmobQuery = new BmobQuery<UserData>();
      bmobQuery.addWhereEqualTo("userName", userName);
      bmobQuery.count(UserData.class, new CountListener() {
        @Override
        public void done(Integer integer, BmobException e) {
          if (e == null) {
            if (integer > 0) {
              Toast.makeText(mContext, getString(R.string.username_repeat), Toast.LENGTH_SHORT).show();
            } else {
              UserData userData = new UserData(userName, userPwd);
              userData.setUserId(createId());
              userData.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                  if (e == null) {
                    Toast.makeText(mContext, getString(R.string.logup_success), Toast.LENGTH_SHORT).show();
                    Intent intent_Register_to_Login = new Intent(RegisterActivity.this,
                        LoginActivity.class);
                    startActivity(intent_Register_to_Login);
                    finish();
                  } else {
                    Toast.makeText(mContext, getString(R.string.logup_fail), Toast.LENGTH_SHORT).show();
                  }
                }
              });
            }
          } else {
            Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
          }
        }
      });
    }
  }

  public boolean isUserNameAndPwdValid() {
    if (mAccount.getText().toString().trim().equals("")) {
      Toast.makeText(this, getString(R.string.user_name_empty),
          Toast.LENGTH_SHORT).show();
      return false;
    } else if (mPwd.getText().toString().trim().equals("")) {
      Toast.makeText(this, getString(R.string.password_empty),
          Toast.LENGTH_SHORT).show();
      return false;
    } else if (mPwdCheck.getText().toString().trim().equals("")) {
      Toast.makeText(this, getString(R.string.password_check_empty),
          Toast.LENGTH_SHORT).show();
      return false;
    }
    return true;
  }

  public String createId() {
    String id = String.valueOf((long) (Math.random() * 9 * Math.pow(10, 7)) + (long) Math.pow(10, 7));
    final int[] count = {0};
    BmobQuery<UserData> bmobQuery = new BmobQuery<UserData>();
    bmobQuery.addWhereEqualTo("userId", id);
    bmobQuery.count(UserData.class, new CountListener() {
      @Override
      public void done(Integer integer, BmobException e) {
        if (e == null) {
          count[0] = integer;
        } else {
          Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
        }
      }
    });
    if (count[0] != 0) {
      return createId();
    } else {
      return id;
    }
  }
}