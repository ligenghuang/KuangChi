package com.zhifeng.kuangchi.util.update;

import android.app.Activity;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.core.app.NotificationCompat;

import com.lgh.huanglib.util.data.ResUtil;
import com.zhifeng.kuangchi.R;

import org.lzh.framework.updatepluginlib.base.CheckNotifier;
import org.lzh.framework.updatepluginlib.model.Update;

import java.util.UUID;

/**
 * 简单实现：使用通知对用户提示：检查到有更新
 */
public class NotificationUpdateCreator extends CheckNotifier {
    private final static String ACTION_UPDATE = "action.update.shot";
    private final static String ACTION_CANCEL = "action.update.cancel";
    private RequestUpdateReceiver requestUpdateReceiver;
    private Update update;
    NotificationManager manager;
    int id;

    @Override
    public Dialog create(Activity context) {
        requestUpdateReceiver = new RequestUpdateReceiver();
        registerReceiver(context);
        createNotification(context);
        this.update = update;

        // 由于需要使用通知实现。此处返回null即可
        return null;
    }

    private void createNotification(Context context) {
        manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setAutoCancel(true)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(ResUtil.getString(R.string.down_title_name))
                .setContentText(ResUtil.getString(R.string.down_update))
                .setDeleteIntent(createCancelPendingIntent(context))
                .setContentIntent(createOneShotPendingIntent(context));

        Notification notification = builder.build();
        id = Math.abs(UUID.randomUUID().hashCode());
        manager.notify(id,notification);
    }

    private PendingIntent createCancelPendingIntent(Context context) {
        Intent intent = new Intent();
        intent.setAction(ACTION_CANCEL);
        return PendingIntent.getBroadcast(context,100,intent, PendingIntent.FLAG_CANCEL_CURRENT);
    }

    private PendingIntent createOneShotPendingIntent(Context context) {
        Intent intent = new Intent();
        intent.setAction(ACTION_UPDATE);
        return PendingIntent.getBroadcast(context,100,intent, PendingIntent.FLAG_CANCEL_CURRENT);
    }

    public class RequestUpdateReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            System.out.println(intent);
            unregisterReceiver(context);
            if (ACTION_UPDATE.equals(intent.getAction())) {
                // 发送下载请求。继续更新流程
                sendDownloadRequest();
            } else {
                // 中断更新流程并通知用户取消更新
                sendUserCancel();
            }
            manager.cancel(id);
        }
    }

    private void registerReceiver (Context context) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_UPDATE);
        filter.addAction(ACTION_CANCEL);
        context.registerReceiver(requestUpdateReceiver, filter);
    }

    private void unregisterReceiver (Context context) {
        context.unregisterReceiver(requestUpdateReceiver);
    }
}
