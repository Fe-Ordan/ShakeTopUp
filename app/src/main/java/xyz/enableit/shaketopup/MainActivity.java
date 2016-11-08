package xyz.enableit.shaketopup;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 1;
    // The following are used for the shake detection
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;
    TelephonyInfo telephonyInfo;
    TelephonyManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        checkShowTutorial();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Assume thisActivity is the current activity
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_PHONE_STATE)) {

                //Show an explanation to the user *asynchronously* -- don't block
                //this thread waiting for the user's response! After the user
                //sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_PHONE_STATE},
                        MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

        telephonyInfo = TelephonyInfo.getInstance(this);
        manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        //start service
        Intent intent = new Intent(MainActivity.this, ShakeService.class);
        startService(intent);


        //calling from service need to recharge
        int launchFromService = getIntent().getIntExtra("launchFromService", 0);
        if (launchFromService == 1) {
            askForRecharge();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_PHONE_STATE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    telephonyInfo = TelephonyInfo.getInstance(this);
                    manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void askForRecharge() {

        if (telephonyInfo.isDualSIM()) {
            //get carrier
            TelephonyManager telemamanger = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            String getSimSerialNumber = telemamanger.getSimSerialNumber();
            String getSimNumber = telemamanger.getLine1Number();

            String carrierName = manager.getNetworkOperatorName();
            Toast.makeText(getApplicationContext(),"Dual serial"+ getSimSerialNumber +" sim"+getSimNumber +" car "+carrierName + "opr " + manager.getSimOperatorName(), Toast.LENGTH_SHORT).show();

        } else {
            TelephonyManager telemamanger = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            String getSimSerialNumber = telemamanger.getSimSerialNumber();
            String getSimNumber = telemamanger.getLine1Number();

            String carrierName = manager.getNetworkOperatorName();
            Toast.makeText(getApplicationContext(),"sin serial"+ getSimSerialNumber +" sim"+getSimNumber +" car "+carrierName + "opr " + manager.getSimOperatorName(), Toast.LENGTH_SHORT).show();
            dialForRecharge(carrierName);
        }

        /*//get carrier
        String carrierName = manager.getNetworkOperatorName();
        Toast.makeText(getApplicationContext(), carrierName + " khj " + manager.getSimOperatorName(), Toast.LENGTH_SHORT).show();
        dialForRecharge("" + manager.getSimOperatorName());*/

    }

    private void dialForRecharge(String carrier) {

        String dialNumber = "";
        switch (carrier) {
            case "B-LINK":
                dialNumber = "*" + "874" + Uri.encode("#");
                break;
            case "Grameenphone":
                dialNumber = "*" + "1010" + "*" + Uri.encode("#");
                break;
        }

        if (dialNumber.equals("")) {
            Toast.makeText(this, "Not found", Toast.LENGTH_SHORT).show();
        } else {
            //code for calling
            Intent intentEmergencyBalance = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + dialNumber));
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
            startActivity(intentEmergencyBalance);
        }


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void checkShowTutorial() {
        int oldVersionCode = PrefConstants.getAppPrefInt(this, "version_code");
        int currentVersionCode = SAppUtil.getAppVersionCode(this);
        if (currentVersionCode > oldVersionCode) {
            startActivity(new Intent(MainActivity.this, ProductTour2Activity.class));
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            PrefConstants.putAppPrefInt(this, "version_code", currentVersionCode);
            finish();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
