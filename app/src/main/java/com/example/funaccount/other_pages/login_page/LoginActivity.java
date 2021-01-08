package com.example.funaccount.other_pages.login_page;

import android.app.Activity;
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
import com.example.funaccount.util.UserDataManager;

import androidx.annotation.Nullable;

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

    private String mUserId;
    private SharedPreferences mLoginSp;
    private String mUserNameValue, mPasswordValue;
    private View mLoginView;
    private View mLoginSuccessView;
    private TextView mLoginSuccessShow;
    private TextView mChangePwdText;
    private UserDataManager mUserDataManager;   //用户数据管理


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

        mUserDataManager = new UserDataManager(this);
        mUserDataManager.openDataBase();
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
                            ResetpwdActivity.class);
                    startActivity(intent_Login_to_reset);
                    finish();
                    break;
            }
            if (mUserDataManager != null) {
                mUserDataManager.closeDataBase();
                mUserDataManager = null;
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
            //mUserDataManager.deleteAllUserDatas();
            int result = mUserDataManager.findUserByNameAndPwd(userName, userPwd);
            //返回1说明用户名和密码均正确
            if (result == PWD_TRUE) {
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
                startActivity(intent);
                finish();
                //登录成功提示
                Toast.makeText(this, getString(R.string.login_success), Toast.LENGTH_SHORT).show();
            } else if (result == PWD_FALSE) {
                //登录失败提示
                Toast.makeText(this, getString(R.string.login_fail), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void cancel() {           //注销
        if (isUserNameAndPwdValid()) {
            //获取当前输入的用户名和密码信息
            String userName = mAccount.getText().toString().trim();
            String userPwd = mPwd.getText().toString().trim();
            int result = mUserDataManager.findUserByNameAndPwd(userName, userPwd);
            if (result == PWD_TRUE) {                                             
                //返回1说明用户名和密码均正确
                Toast.makeText(this, getString(R.string.account_remove_success), Toast.LENGTH_SHORT).show();
//                <span style="font-family: Arial;">//注销成功提示</span>
                mPwd.setText("");
                mAccount.setText("");
                mUserDataManager.deleteUserDatabyname(userName);
            } else if (result == PWD_FALSE) {
                //注销失败提示
                Toast.makeText(this, getString(R.string.account_remove_fail), Toast.LENGTH_SHORT).show();
            }
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
