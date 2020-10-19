package com.github.sunlong6666.android_qqtool;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import org.json.JSONObject;

public class QQGetUser implements IUiListener
{

    private ImageView m_iv_image;
    private TextView m_tv_userShow;
    private Context context;

    QQGetUser(Context context,ImageView m_iv_image, TextView m_tv_userShow)
    {
        this.context = context;
        this.m_iv_image = m_iv_image;
        this.m_tv_userShow = m_tv_userShow;
    }

    @Override
    public void onComplete(Object object)
    {
        try
        {
            JSONObject userData = (JSONObject)object;
            String nickName = userData.getString("nickname");
            String gender = userData.getString("gender");
            String province = userData.getString("province");
            String year = userData.getString("year");
            String city = userData.getString("city");
            String image_url = userData.getString("figureurl_qq");
            m_tv_userShow.setText("昵称：" + nickName + "\n"
                    + "性别：" + gender +"\n"
                    +"地址： "+ province+"省 "+ city +"市"+"\n"
                    +"生日： "+year+"年"+"\n");
            Glide.with(context).load(image_url).error(R.mipmap.ic_launcher).into(m_iv_image);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(UiError uiError)
    {

    }

    @Override
    public void onCancel()
    {

    }

}
