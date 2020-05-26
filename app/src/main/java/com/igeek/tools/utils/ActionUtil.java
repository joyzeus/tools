package com.igeek.tools.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class ActionUtil {

    public static void openBrowser(Context context, String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
    }

    /**
     * 邮件分享
     *
     * @param context 上下文
     * @param title   邮件主题
     * @param content 邮件内容
     * @param address 邮件地址
     */
    public static void sendEmail(Context context, String title, String content, String address) {
        Uri uri = Uri.parse("mailto:" + address);
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, uri);
        // 设置对方邮件地址
        emailIntent.putExtra(Intent.EXTRA_EMAIL, address);
        // 设置标题内容
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, title);
        // 设置邮件文本内容
        emailIntent.putExtra(Intent.EXTRA_TEXT, content);
        context.startActivity(Intent.createChooser(emailIntent, "选择邮箱"));
    }

    public static void sendQQMessage(Context context, String qq) {
        try {
            //可以跳转到添加好友，如果qq号是好友了，直接聊天
            //uin是发送过去的qq号码
            String url = "mqqwpa://im/chat?chat_type=wpa&uin="+qq;
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
