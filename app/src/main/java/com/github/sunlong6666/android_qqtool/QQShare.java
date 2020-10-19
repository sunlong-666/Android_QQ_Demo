package com.github.sunlong6666.android_qqtool;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import org.json.JSONObject;
import java.util.ArrayList;

public class QQShare implements IUiListener
{

    private Activity activity;
    private Context context;
    QQShare(Activity activity,Context context)
    {
        this.activity = activity;
        this.context = context;
    }

    @Override
    public void onComplete(Object object)
    {
        JSONObject data = (JSONObject)object;
        Toast.makeText(context,"分享成功",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(UiError uiError)
    {
        Toast.makeText(context,"分享失败",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCancel()
    {
        Toast.makeText(context,"分享取消",Toast.LENGTH_SHORT).show();
    }

    public void shareToQQ(String title,String content)
    {
        if (title.isEmpty()) title = "标题：github/sunlong6666测试";
        if (content.isEmpty()) content = "内容：github/sunlong6666测试";
        Bundle bundle = new Bundle();
        bundle.putInt(com.tencent.connect.share.QQShare.SHARE_TO_QQ_KEY_TYPE, com.tencent.connect.share.QQShare.SHARE_TO_QQ_TYPE_DEFAULT);//分享的类型
        bundle.putString(com.tencent.connect.share.QQShare.SHARE_TO_QQ_TITLE,title);//分享标题
        bundle.putString(com.tencent.connect.share.QQShare.SHARE_TO_QQ_SUMMARY,content);//要分享的内容摘要
        bundle.putString(com.tencent.connect.share.QQShare.SHARE_TO_QQ_TARGET_URL,"http://wpa.qq.com/msgrd?v=3&uin=390859202&site=qq&menu=yes");//内容地址
        bundle.putString(com.tencent.connect.share.QQShare.SHARE_TO_QQ_IMAGE_URL,"https://q1.qlogo.cn/g?b=qq&nk=390859202&s=640");//分享的图片URL
        AppData.tencent.shareToQQ(activity,bundle,this);
    }

    public void shareToQZ(String title,String content)
    {
        if (title.isEmpty()) title = "标题：github/sunlong6666测试";
        if (content.isEmpty()) content = "内容：github/sunlong6666测试";
        Bundle bundle = new Bundle();
        bundle.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE,QzoneShare.SHARE_TO_QZONE_TYPE_NO_TYPE);
        bundle.putString(QzoneShare.SHARE_TO_QQ_TITLE,title);//分享标题
        bundle.putString(QzoneShare.SHARE_TO_QQ_SUMMARY,content);//分享的内容摘要
        bundle.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, "https://user.qzone.qq.com/390859202");//分享的链接
        //分享的图片, 以ArrayList<String>的类型传入，以便支持多张图片（注：图片最多支持9张图片，多余的图片会被丢弃）
        ArrayList<String> imageUrls = new ArrayList<>();
        imageUrls.add("https://q1.qlogo.cn/g?b=qq&nk=390859202&s=640");//添加一个图片地址
        bundle.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imageUrls);//分享的图片URL
        AppData.tencent.shareToQzone(activity,bundle,this);
    }

}
