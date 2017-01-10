package xyz.enableit.shaketopup.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.util.Log;

import xyz.enableit.shaketopup.ShakeDetector;

/**
 * Created by dinislam on 11/6/16.
 */
public class ShakeService extends Service {

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;


    public ShakeService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // ShakeDetector initialization
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

            @Override
            public void onShake(int count) {

                Intent i = getApplicationContext().getPackageManager().
                        getLaunchIntentForPackage("xyz.enableit.shaketopup");
                i.putExtra("launchFromService",1);
                getApplicationContext().startActivity(i);

            }
        });
        mSensorManager.registerListener(mShakeDetector, mAccelerometer, SensorManager.SENSOR_DELAY_UI);

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("TAG", "onStartCommand");
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sendBroadcast(new Intent("YouWillNeverKillMe"));
    }
}