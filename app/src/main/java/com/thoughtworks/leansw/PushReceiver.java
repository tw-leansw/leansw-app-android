package com.thoughtworks.leansw;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.avos.avoscloud.PushService;

import org.json.JSONException;
import org.json.JSONObject;

public class PushReceiver extends BroadcastReceiver {
    private static final String TAG = "PushReceiver";
    public static final String ACTION_LEANSW_PUSH = "com.thoughtworks.leansw.push";
    public static final String EXTRA_LEANCLOUD_CHANNEL = "com.avos.avoscloud.Channel";
    public static final String EXTRA_LEANCLOUD_DATA = "com.avos.avoscloud.Data";
    public static final String KEY_ALERT = "alert";

    @Override
    public void onReceive(Context context, Intent intent) {
        switch (intent.getAction()) {
            case ACTION_LEANSW_PUSH:
                try {
                    final Intent intent1 = new Intent(context, MainActivity.class);
                    intent1.putExtras(intent.getExtras());
                    intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

                    // TODO different code for different actions
                    final int requestCode = 0;
                    final PendingIntent pendingIntent = PendingIntent.getActivity(context, requestCode, intent1, PendingIntent.FLAG_ONE_SHOT);
                    JSONObject json = new JSONObject(intent.getExtras().getString(EXTRA_LEANCLOUD_DATA));
                    final String alert = json.getString(KEY_ALERT);
                    final NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                            .setSmallIcon(R.drawable.ic_stat_name)
                            .setContentTitle(alert)
                            .setDefaults(NotificationCompat.DEFAULT_ALL)
                            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                            .setAutoCancel(true)
                            .setContentIntent(pendingIntent);
                    //
                    ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).notify(TAG, requestCode, builder.build());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            default:
                context.startService(new Intent(context, PushService.class));
        }
    }
}
