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
    private Button mCancleButton;           //注销按钮
    private CheckBox mRememberCheck;

    private String mUserId;
    private SharedPreferences mLoginSp;
    private String mUserNameValue, mPasswordValue;
    private View mLoginView;
    private View mLoginSuccessView;
    private TextView mLoginSuccessShow;
    private TextView mChangepwdText;
    private UserDataManager mUserDataManager;   //用户数据管理


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        mAccount = (EditText) findViewById(R.id.login_edit_account);
        mPwd = (EditText) findViewById(R.id.login_edit_pwd);
        mRegisterButton = (Button) findViewById(R.id.login_btn_register);
        mLoginButton = (Button) findViewById(R.id.login_btn_login);
        mCancleButton = (Button) findViewById(R.id.login_btn_cancle);
        mLoginView = findViewById(R.id.login_view);
        mLoginSuccessView = findViewById(R.id.login_success_view);
        mLoginSuccessShow = (TextView) findViewById(R.id.login_success_show);
        mChangepwdText = (TextView) findViewById(R.id.login_text_change_pwd);
        mRememberCheck = (CheckBox) findViewById(R.id.Login_Remember);

        mLoginSp = getSharedPreferences("userInfo", 0);
        String name = mLoginSp.getString("USER_NAME", "");
        String pwd = mLoginSp.getString("PASSWORD", "");
        //boolean choseAutoLogin =login_sp.getBoolean("mAutologinCheck", false);
        //如果上次选了记住密码，那进入登录页面也自动勾选记住密码，并填上用户名和密码
        if (mLoginSp.getBoolean("mRememberCheck", false)) {
            mAccount.setText(name);
            mPwd.setText(pwd);
            mRememberCheck.setChecked(true);
        }

        mRegisterButton.setOnClickListener(mListener);                      //采用OnClickListener方法设置不同按钮按下之后的监听事件
        mLoginButton.setOnClickListener(mListener);
        mCancleButton.setOnClickListener(mListener);
        mChangepwdText.setOnClickListener(mListener);

        mUserDataManager = new UserDataManager(this);
        mUserDataManager.openDataBase();                              //建立本地数据库
    }

    View.OnClickListener mListener = new View.OnClickListener() {                  //不同按钮按下的监听事件选择
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.login_btn_register:                            //登录界面的注册按钮
                    Intent intent_Login_to_Register = new Intent(LoginActivity.this, RegisterActivity.class);
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
                    Intent intent_Login_to_reset = new Intent(LoginActivity.this, ResetpwdActivity.class);
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

    public void login() {                                              //登录按钮监听事件
        if (isUserNameAndPwdValid()) {
            String userName = mAccount.getText().toString().trim();    //获取当前输入的用户名和密码信息
            String userPwd = mPwd.getText().toString().trim();
            SharedPreferences.Editor editor = mLoginSp.edit();
            //mUserDataManager.deleteAllUserDatas();
            int result = mUserDataManager.findUserByNameAndPwd(userName, userPwd);
            if (result == PWD_TRUE) {                                                 //返回1说明用户名和密码均正确
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

                Intent intent = new Intent(LoginActivity.this, UserActivity.class);    //切换Login Activity至User Activity
                startActivity(intent);
                finish();
                Toast.makeText(this, "登陆成功", Toast.LENGTH_SHORT).show();//登录成功提示
            } else if (result == PWD_FALSE) {
                Toast.makeText(this, "抱歉，登录失败", Toast.LENGTH_SHORT).show();  //登录失败提示
            }
        }
    }

    public void cancel() {           //注销
        if (isUserNameAndPwdValid()) {
            String userName = mAccount.getText().toString().trim();    //获取当前输入的用户名和密码信息
            String userPwd = mPwd.getText().toString().trim();
            int result = mUserDataManager.findUserByNameAndPwd(userName, userPwd);
            if (result == PWD_TRUE) {                                             //返回1说明用户名和密码均正确
                Toast.makeText(this, "您的账号已成功注销", Toast.LENGTH_SHORT).show();
//                <span style="font-family: Arial;">//注销成功提示</span>
                mPwd.setText("");
                mAccount.setText("");
                mUserDataManager.deleteUserDatabyname(userName);
            } else if (result == PWD_FALSE) {
                Toast.makeText(this, "注销失败，请重试", Toast.LENGTH_SHORT).show();  //注销失败提示
            }
        }

    }

    public boolean isUserNameAndPwdValid() {
        if (mAccount.getText().toString().trim().equals("")) {
            Toast.makeText(this, "用户名不能为空",
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (mPwd.getText().toString().trim().equals("")) {
            Toast.makeText(this, "密码不能为空",
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
