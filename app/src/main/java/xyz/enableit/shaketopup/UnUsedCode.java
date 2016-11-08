package xyz.enableit.shaketopup;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;

/**
 * Created by dinislam on 11/7/16.
 */
public class UnUsedCode {

      /* @Override
    public void onPause() {
        // Add the following line to unregister the Sensor Manager onPause
        mSensorManager.unregisterListener(mShakeDetector);
        super.onPause();

          @Override
    public void onResume() {
        super.onResume();
        // Add the following line to register the Session Manager Listener onResume
        mSensorManager.registerListener(mShakeDetector, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
    }
    }*/

  /*  // ShakeDetector initialization
    mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    mAccelerometer = mSensorManager
            .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    mShakeDetector = new ShakeDetector();
    mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

        @Override
        public void onShake(int count) {
            Toast.makeText(getApplicationContext(), "shake", Toast.LENGTH_SHORT).show();
        }
    });*/



/*
    //code for calling
    Intent intentEmergencyBalance = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "*874#"));
    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
        // TODO: Consider calling
        //    ActivityCompat#requestPermissions
        // here to request the missing permissions, and then overriding
        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
        //                                          int[] grantResults)
        // to handle the case where the user grants the permission. See the documentation
        // for ActivityCompat#requestPermissions for more details.
        return;
    }
    startActivity(intentEmergencyBalance);*/



}
