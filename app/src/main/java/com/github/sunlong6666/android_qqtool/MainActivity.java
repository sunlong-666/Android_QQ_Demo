package com.github.sunlong6666.android_qqtool;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.Tencent;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{

    private QQShare qqShare;
    private QQLogin qqLogin;
    private QQGetUser qqGetUser;

    private MyHandler myHandler;

    String shareType = "QQ好友";

    private Dialog dialog;

    private Button m_bt_qqLogin;
    private ImageView m_iv_image;
    private TextView m_tv_userShow;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.m_bt_qqLogin:
                if (AppData.tencent.isSessionValid()) qqCancel();
                else qqLogin();
                break;
            case R.id.m_bt_qqShare:
                createQqShareDialog();
                break;
        }
    }

    //登录QQ
    private void qqLogin()
    {
        AppData.tencent.login(this,AppData.SCOPE,qqLogin);
    }

    //注销QQ
    public void qqCancel()
    {
        AppData.tencent.logout(this);
        myHandler.sendEmptyMessage(MyHandler.QQ_LOGIN_CANCEL);
        m_iv_image.setImageResource(R.drawable.qq);
        m_tv_userShow.setText("未登录");
        Toast.makeText(MainActivity.this,"已注销",Toast.LENGTH_SHORT).show();
    }

    private void createQqShareDialog()
    {
        shareType = "QQ好友";
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.qq_share_dialog,null);
        builder.setTitle("分享到QQ");
        builder.setView(dialogView);
        dialog = builder.show();
        RadioGroup shareDialog_rg_share = dialogView.findViewById(R.id.shareDialog_rg_share);
        shareDialog_rg_share.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i)
            {
                RadioButton shareTo = radioGroup.findViewById(i);
                switch (shareTo.getText().toString())
                {
                    case "QQ好友":
                        shareType = "QQ好友";
                        break;
                    case "QQ空间":
                        shareType = "QQ空间";
                        break;
                }
            }
        });
        Button shareDialog_bt_cancel = dialogView.findViewById(R.id.shareDialog_bt_cancel);
        shareDialog_bt_cancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
            }
        });
        Button shareDialog_bt_share = dialogView.findViewById(R.id.shareDialog_bt_share);
        shareDialog_bt_share.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                EditText shareDialog_et_title = dialogView.findViewById(R.id.shareDialog_et_title);
                EditText shareDialog_et_content = dialogView.findViewById(R.id.shareDialog_et_content);
                if (shareType.equals("QQ好友")) qqShare.shareToQQ(shareDialog_et_title.getText().toString().trim(),shareDialog_et_content.getText().toString().trim());
                else qqShare.shareToQZ(shareDialog_et_title.getText().toString().trim(),shareDialog_et_content.getText().toString().trim());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (AppData.tencent != null)
        {
            if (requestCode == Constants.REQUEST_LOGIN)
            {
                if (resultCode == -1)
                {
                    myHandler.sendEmptyMessage(MyHandler.QQ_LOGIN_SUCCESS);
                    Tencent.onActivityResultData(requestCode, resultCode, data, qqLogin);
                    Tencent.handleResultData(data, qqLogin);
                    UserInfo info = new UserInfo(this, AppData.tencent.getQQToken());
                    info.getUserInfo(qqGetUser);
                }
            }
            if (requestCode == Constants.REQUEST_QQ_SHARE || requestCode == Constants.REQUEST_QZONE_SHARE)
            {
                dialog.dismiss();
                if (resultCode == -1)
                {
                    Tencent.onActivityResultData(requestCode,resultCode, data, qqShare);
                    Tencent.handleResultData(data, qqShare);
                }
            }
        }
    }

    private void initData()
    {
        AppData.tencent = Tencent.createInstance(AppData.APP_ID, this);
        myHandler = new MyHandler(m_bt_qqLogin);
        qqLogin = new QQLogin(this);
        qqGetUser = new QQGetUser(this,m_iv_image,m_tv_userShow);
        qqShare = new QQShare(this,this);
    }

    private void initView()
    {
        m_iv_image = (ImageView)findViewById(R.id.m_iv_image);
        m_iv_image.setImageResource(R.drawable.qq);
        m_tv_userShow = (TextView)findViewById(R.id.m_tv_userShow);
        m_bt_qqLogin = (Button)findViewById(R.id.m_bt_qqLogin);
        m_bt_qqLogin.setOnClickListener(this);
        ((Button)findViewById(R.id.m_bt_qqShare)).setOnClickListener(this);
    }

}