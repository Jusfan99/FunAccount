package com.example.funaccount.other_pages.login_page;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.funaccount.R;
import com.example.funaccount.util.UserData;

import java.util.List;

import androidx.annotation.Nullable;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CountListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class LoginActivity extends Activity {
    int PWD_TRUE = 1;
    int PWD_FALSE = 0;
    int pwdResetFlag = 0;
    private EditText mAccount;              //用户名编辑
    private EditText mPwd;                  //密码编辑
    private Button mRegisterButton;         //注册按钮
    private Button mLoginButton;            //登录按钮
    private Button mCancelButton;           //注销按钮
    private CheckBox mRememberCheck;
    private Context mContext = this;

    private String mUserId;
    private SharedPreferences mLoginSp;
    private String mUserNameValue, mPasswordValue;
    private View mLoginView;
    private View mLoginSuccessView;
    private TextView mLoginSuccessShow;
    private TextView mChangePwdText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        mAccount = (EditText) findViewById(R.id.login_edit_account);
        mPwd = (EditText) findViewById(R.id.login_edit_pwd);
        mRegisterButton = (Button) findViewById(R.id.login_btn_register);
        mLoginButton = (Button) findViewById(R.id.login_btn_login);
        mCancelButton = (Button) findViewById(R.id.login_btn_cancle);
        mLoginView = findViewById(R.id.login_view);
        mLoginSuccessView = findViewById(R.id.login_success_view);
        mLoginSuccessShow = (TextView) findViewById(R.id.login_success_show);
        mChangePwdText = (TextView) findViewById(R.id.login_text_change_pwd);
        mRememberCheck = (CheckBox) findViewById(R.id.Login_Remember);

        mLoginSp = getSharedPreferences("userInfo", 0);
        String name = mLoginSp.getString("USER_NAME", "");
        String pwd = mLoginSp.getString("PASSWORD", "");
        //如果上次选了记住密码，那进入登录页面也自动勾选记住密码，并填上用户名和密码
        if (mLoginSp.getBoolean("mRememberCheck", false)) {
            mAccount.setText(name);
            mPwd.setText(pwd);
            mRememberCheck.setChecked(true);
        }

        mRegisterButton.setOnClickListener(mListener);
        mLoginButton.setOnClickListener(mListener);
        mCancelButton.setOnClickListener(mListener);
        mChangePwdText.setOnClickListener(mListener);
    }

    View.OnClickListener mListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.login_btn_register:
                    Intent intent_Login_to_Register = new Intent(LoginActivity.this,
                            RegisterActivity.class);
                    startActivity(intent_Login_to_Register);
                    finish();
                    break;
                case R.id.login_btn_login:                              //登录界面的登录按钮
                    login();
                    break;
                case R.id.login_btn_cancle:                             //登录界面的注销按钮
                    cancel();
                    break;
                case R.id.login_text_change_pwd:                             //登录界面的修改密码按钮
                    Intent intent_Login_to_reset = new Intent(LoginActivity.this,
                            ResetPwdActivity.class);
                    startActivity(intent_Login_to_reset);
                    finish();
                    break;
            }
        }
    };

    //登录按钮监听事件
    public void login() {
        if (isUserNameAndPwdValid()) {
            //获取当前输入的用户名和密码信息
            String userName = mAccount.getText().toString().trim();
            String userPwd = mPwd.getText().toString().trim();
            SharedPreferences.Editor editor = mLoginSp.edit();
            BmobQuery<UserData> bmobQuery = new BmobQuery<UserData>();
            bmobQuery.addWhereEqualTo("userName", userName);
            bmobQuery.addWhereEqualTo("userPwd", userPwd);
            bmobQuery.count(UserData.class, new CountListener() {
                @Override
                public void done(Integer integer, BmobException e) {
                    if (e == null) {
                        if (integer == PWD_TRUE) {
                            //保存用户名和密码
                            editor.putString("USER_NAME", userName);
                            editor.putString("PASSWORD", userPwd);
                            //是否记住密码
                            if (mRememberCheck.isChecked()) {
                                editor.putBoolean("mRememberCheck", true);
                            } else {
                                editor.putBoolean("mRememberCheck", false);
                            }
                            editor.apply();
                            //切换Login Activity至User Activity
                            Intent intent = new Intent(LoginActivity.this, UserActivity.class);
                            intent.putExtra("userName", userName);
                            startActivity(intent);
                            finish();
                            //登录成功提示
                            Toast.makeText(mContext, getString(R.string.login_success), Toast.LENGTH_SHORT).show();
                        } else if (integer == PWD_FALSE) {
                            //登录失败提示
                            Toast.makeText(mContext, getString(R.string.login_fail), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
    }

    public void cancel() {           //注销
        if (isUserNameAndPwdValid()) {
            //获取当前输入的用户名和密码信息
            String userName = mAccount.getText().toString().trim();
            String userPwd = mPwd.getText().toString().trim();
            BmobQuery<UserData> bmobQuery = new BmobQuery<UserData>();
            bmobQuery.addWhereEqualTo("userName", userName);
            bmobQuery.addWhereEqualTo("userPwd", userPwd);
            bmobQuery.findObjects(new FindListener<UserData>() {
                @Override
                public void done(List<UserData> list, BmobException e) {
                    if (e == null) {
                        if (list.size() == PWD_TRUE) {
                            mPwd.setText("");
                            mAccount.setText("");
                            for (UserData userData : list) {
                                userData.delete(new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if (e == null) {
                                            Toast.makeText(mContext, getString(R.string.account_remove_success), Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(mContext, getString(R.string.account_remove_fail), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        } else if (list.size() == PWD_FALSE) {
                            Toast.makeText(mContext, getString(R.string.account_remove_fail), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
    }

    public boolean isUserNameAndPwdValid() {
        if (mAccount.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.user_name_empty), Toast.LENGTH_SHORT).show();
            return false;
        } else if (mPwd.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.password_empty),
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
