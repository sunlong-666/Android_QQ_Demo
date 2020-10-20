package com.github.sunlong6666.android_qqtool;

import com.tencent.tauth.Tencent;

public class AppData
{

    // Tencent类是SDK的主要实现类，开发者可通过Tencent类访问腾讯开放的OpenAPI。
    public static Tencent tencent;

    //SCOPE，应用需要获得哪些API的权限,由“,”分隔。例如:SCOPE = “get_user_info,add_t”;所有权限用“all”。
    public static String SCOPE = "all";

    //APP的ID，通过腾讯开放平台获取
    public final static String APP_ID = "101889943";

}
