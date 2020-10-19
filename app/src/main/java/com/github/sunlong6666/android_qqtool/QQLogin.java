package com.github.sunlong6666.android_qqtool;

import android.content.Context;
import android.widget.Toast;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import org.json.JSONException;
import org.json.JSONObject;

public class QQLogin implements IUiListener
{

    private Context context;
    QQLogin(Context context)
    {
        this.context = context;
    }

    @Override
    public void onComplete(Object object)
    {
        try
        {
            JSONObject data = (JSONObject) object;
            Toast.makeText(context,"登录成功",Toast.LENGTH_SHORT).show();
            String openID = data.getString("openid");
            String accessToken = data.getString("access_token");
            String expires = data.getString("expires_in");
            AppData.tencent.setOpenId(openID);
            AppData.tencent.setAccessToken(accessToken, expires);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(UiError uiError)
    {
        Toast.makeText(context,"登录失败",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCancel()
    {
        Toast.makeText(context,"登录取消",Toast.LENGTH_SHORT).show();
    }

}
