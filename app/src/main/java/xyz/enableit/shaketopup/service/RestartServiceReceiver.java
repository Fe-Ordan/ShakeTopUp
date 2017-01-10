package xyz.enableit.shaketopup.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import xyz.enableit.shaketopup.service.ShakeService;

/**
 * Created by dinislam on 11/7/16.
 */
public class RestartServiceReceiver extends BroadcastReceiver
{

    private static final String TAG = "RestartServiceReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(TAG, "onReceive");
        context.startService(new Intent(context.getApplicationContext(), ShakeService.class));
    }

}