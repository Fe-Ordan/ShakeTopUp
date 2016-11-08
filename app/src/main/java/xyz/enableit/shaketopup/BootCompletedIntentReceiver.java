package xyz.enableit.shaketopup;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by dinislam on 11/7/16.
 */
public class BootCompletedIntentReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            Intent pushIntent = new Intent(context, ShakeService.class);
            context.startService(pushIntent);
        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            Intent pushIntent = new Intent(context, ShakeService.class);
            context.startService(pushIntent);
        }
    }
}
