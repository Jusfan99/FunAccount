package com.example.funaccount.other_pages.login_page;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.funaccount.R;
import com.example.funaccount.util.UserData;
import com.example.funaccount.util.UserDataManager;

import androidx.appcompat.app.AppCompatActivity;

public class ResetpwdActivity extends AppCompatActivity {
    int TRUE = 1;
    int FALSE = 0;

    private EditText mAccount;                        //用户名编辑
    private EditText mPwdOld;                        //密码编辑
    private EditText mPwdNew;                        //密码编辑
    private EditText mPwdCheck;                       //密码编辑
    private Button mSureButton;                       //确定按钮
    private Button mCancelButton;                     //取消按钮
    private UserDataManager mUserDataManager;         //用户数据管理类

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

        mSureButton.setOnClickListener(mResetpwdListener);      //注册界面两个按钮的监听事件
        mCancelButton.setOnClickListener(mResetpwdListener);

        mUserDataManager = new UserDataManager(this);
        mUserDataManager.openDataBase();                              //建立本地数据库
    }

    View.OnClickListener mResetpwdListener = new View.OnClickListener() {    //不同按钮按下的监听事件选择
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.resetpwd_btn_sure:                       //确认按钮的监听事件
                    resetpwdCheck();
                    break;
                case R.id.resetpwd_btn_cancel:                     //取消按钮的监听事件,由注册界面返回登录界面
                    Intent intent_Resetpwd_to_Login = new Intent(ResetpwdActivity.this, LoginActivity.class);    //切换Resetpwd Activity至Login Activity
                    startActivity(intent_Resetpwd_to_Login);
                    finish();
                    break;
            }
        }
    };

    public void resetpwdCheck() {                                //确认按钮的监听事件
        if (isUserNameAndPwdValid()) {
            String userName = mAccount.getText().toString().trim();
            String userPwd_old = mPwdOld.getText().toString().trim();
            String userPwd_new = mPwdNew.getText().toString().trim();
            String userPwdCheck = mPwdCheck.getText().toString().trim();
            int result = mUserDataManager.findUserByNameAndPwd(userName, userPwd_old);
            if (result == TRUE) {                                             //返回1说明用户名和密码均正确,继续后续操作
                if (userPwd_new.equals(userPwdCheck) == false) {           //两次密码输入不一样
                    Toast.makeText(this, "两次输入不一致", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    UserData mUser = new UserData(userName, userPwd_new);
                    mUserDataManager.openDataBase();
                    boolean flag = mUserDataManager.updateUserData(mUser);
                    if (flag == false) {
                        Toast.makeText(this, "修改失败", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "您已修改密码", Toast.LENGTH_SHORT).show();
                        mUser.pwdresetFlag = 1;
                        Intent intent_Register_to_Login = new Intent(ResetpwdActivity.this, LoginActivity.class);    //切换User Activity至Login Activity
                        startActivity(intent_Register_to_Login);
                        finish();
                    }
                }
            } else if (result == FALSE) {                                       //返回0说明用户名和密码不匹配，重新输入
                Toast.makeText(this, "用户名或密码有误，请重新输入", Toast.LENGTH_SHORT).show();
                return;
            }
        }
    }

    public boolean isUserNameAndPwdValid() {
        String userName = mAccount.getText().toString().trim();
        //检查用户是否存在
        int count = mUserDataManager.findUserByName(userName);
        //用户不存在时返回，给出提示文字
        if (count <= 0) {
            Toast.makeText(this, "用户名不存在", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (mAccount.getText().toString().trim().equals("")) {
            Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return false;
        } else if (mPwdOld.getText().toString().trim().equals("")) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        } else if (mPwdNew.getText().toString().trim().equals("")) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        } else if (mPwdCheck.getText().toString().trim().equals("")) {
            Toast.makeText(this, "请再次输入密码", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}