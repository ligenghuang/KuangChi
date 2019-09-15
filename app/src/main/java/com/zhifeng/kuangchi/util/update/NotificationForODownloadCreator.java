package com.zhifeng.kuangchi.util.update;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;


import com.lgh.huanglib.util.data.ResUtil;
import com.zhifeng.kuangchi.R;

import org.lzh.framework.updatepluginlib.base.DownloadCallback;
import org.lzh.framework.updatepluginlib.base.DownloadNotifier;
import org.lzh.framework.updatepluginlib.model.Update;

import java.io.File;
import java.util.UUID;

/**
 * <pre>
 *     author : feijin_lgc
 *     e-mail : 595184932@qq.com
 *     time   : 2018/8/20 上午10:37
 *     desc   :     8.0适配
 *     version: 1.0
 * </pre>
 */
public class NotificationForODownloadCreator implements DownloadNotifier {
    @Override
    public DownloadCallback create(Update update, Activity activity) {
        // 返回一个UpdateDownloadCB对象用于下载时使用来更新界面。
        return new NotificationCB(activity);
    }

    private static class NotificationCB implements DownloadCallback {

        NotificationManager manager;
        Notification.Builder builder;
        int id;
        int preProgress;

        @TargetApi(Build.VERSION_CODES.O)
        NotificationCB(Activity activity) {
            this.manager = (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
            //创建 通知通道 channelid和channelname是必须的（自己命名就好）
            NotificationChannel channel = new NotificationChannel("1", "Channel1", NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableLights(false);//是否在桌面icon右上角展示小红点
            channel.setLightColor(Color.GREEN);//小红点颜色
            channel.setShowBadge(false); //是否在久按桌面图标时显示此渠道的通知
            channel.enableVibration(false);
            channel.setVibrationPattern(new long[]{0});
            manager.createNotificationChannel(channel);

            builder = new Notification.Builder(activity, "1"); //设置通知显示图标、文字等

            builder.setProgress(100, 0, false)
                    .setSmallIcon(activity.getApplicationInfo().icon)
                    .setAutoCancel(false)
                    .setContentText(ResUtil.getString(R.string.down_downing))
                    .setContentText(ResUtil.getString(R.string.down_title_name))
                    .build();
            id = Math.abs(UUID.randomUUID().hashCode());
        }

        @Override
        public void onDownloadStart() {
            // 下载开始时的通知回调。运行于主线程
            manager.notify(id, builder.build());
        }

        @Override
        public void onDownloadComplete(File file) {
            // 下载完成的回调。运行于主线程
            manager.cancel(id);
        }

        @Override
        public void onDownloadProgress(long current, long total) {
            // 下载过程中的进度信息。在此获取进度信息。运行于主线程
            int progress = (int) (current * 1f / total * 100);
            // 过滤不必要的刷新进度
            if (preProgress < progress) {
                preProgress = progress;
                builder.setProgress(100, progress, false);
                builder.setContentTitle(ResUtil.getString(R.string.down_title_name));
                builder.setContentText(ResUtil.getString(R.string.down_downing)+preProgress + "%");
                manager.notify(id, builder.build());
            }
        }

        @Override
        public void onDownloadError(Throwable t) {
            // 下载时出错。运行于主线程
            manager.cancel(id);
        }
    }
}
