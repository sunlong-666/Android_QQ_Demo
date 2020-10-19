package com.github.sunlong6666.android_qqtool;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.widget.Button;
import androidx.annotation.NonNull;

public class MyHandler extends Handler
{

    public static boolean QQ_LOGIN_STATE = false;
    public static final int QQ_LOGIN_SUCCESS = 1001;
    public static final int QQ_LOGIN_CANCEL = 3003;

    private Button m_bt_qqLogin;
    MyHandler(Button m_bt_qqLogin)
    {
        this.m_bt_qqLogin = m_bt_qqLogin;
    }

    @Override
    public void handleMessage(@NonNull Message msg)
    {
        switch (msg.what)
        {
            case QQ_LOGIN_SUCCESS:
                QQ_LOGIN_STATE = true;
                m_bt_qqLogin.setTextColor(Color.RED);
                m_bt_qqLogin.setText("注销");
                break;
            case QQ_LOGIN_CANCEL:
                QQ_LOGIN_STATE = false;
                m_bt_qqLogin.setTextColor(Color.WHITE);
                m_bt_qqLogin.setText("登录");
                break;
        }
    }

}
