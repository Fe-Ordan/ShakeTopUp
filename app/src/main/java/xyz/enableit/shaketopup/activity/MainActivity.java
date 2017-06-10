package xyz.enableit.shaketopup.activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import xyz.enableit.shaketopup.dialog.DialogOperatorChooser;
import xyz.enableit.shaketopup.fragment.AudioFragment;
import xyz.enableit.shaketopup.fragment.FragmentVAS;
import xyz.enableit.shaketopup.offer.FragmentOffer;
import xyz.enableit.shaketopup.util.PrefConstants;
import xyz.enableit.shaketopup.R;
import xyz.enableit.shaketopup.util.SAppUtil;
import xyz.enableit.shaketopup.fragment.FragmentShortCode;
import xyz.enableit.shaketopup.service.ShakeService;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private int launchFromService, selectedOperator;
    private SharedPreferences sharedPref;

    //private enum Operator {Grameenphone, Banglalink, Robi, Airtel, Teletalk}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        checkShowTutorial();


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //check operator has been selected or not
        sharedPref = PreferenceManager
                .getDefaultSharedPreferences(this);

        selectedOperator = Integer.parseInt(sharedPref.getString(getString(R.string.operator), "-1"));
        if (selectedOperator == -1) {
            DialogOperatorChooser chooser = new DialogOperatorChooser();
            chooser.show(getSupportFragmentManager(), "MainActivity");
        }

        //set home fragment
        changeFragment(new FragmentOffer());


        //start service
        Intent intent = new Intent(MainActivity.this, ShakeService.class);
        if (sharedPref.getBoolean(getString(R.string.pref_shake_top_up), true)) {
            startService(intent);
        } else {
            stopService(intent);
        }

        //calling from service need to recharge
        launchFromService = getIntent().getIntExtra("launchFromService", 0);
        if (launchFromService == 1) {
            //check setting from preference
            dialForRecharge(selectedOperator);
            Log.d("Service Call", "" + selectedOperator);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    private void dialForRecharge(int carrier) {

        String dialNumber = "";
        switch (carrier) {
            //GP
            case 0:
                dialNumber = "*" + "1010" + "*" + Uri.encode("#");
                break;
            //BL
            case 1:
                dialNumber = "*" + "874" + Uri.encode("#");
                break;
            //Ro
            case 2:
                //*8811*1*1*1#
                dialNumber = "*" + "8811" + "*" + "1" + "*" + "1" + "*" + "1" + Uri.encode("#");
                break;
            //Air
            case 3:
                dialNumber = "*141*10" + Uri.encode("#");
                break;
            //Tel
            case 4:
                dialNumber = "*1122" + Uri.encode("#");
                break;
        }

        Log.d("Number", "" + Uri.parse("tel:" + dialNumber));
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
                startActivity(intentEmergencyBalance);
            }


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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        Fragment fragment = null;

        if (id == R.id.nav_short_code) {
            fragment = new FragmentShortCode();
        } else if (id == R.id.nav_offer) {
            fragment = new FragmentVAS();
        } else if (id == R.id.nav_news) {
            fragment = new FragmentOffer();
        } else if (id == R.id.nav_setting) {
            Intent i = new Intent(this, SettingActivity.class);
            startActivity(i);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        } else if (id == R.id.nav_share) {
            fragment = new AudioFragment();
        }

        drawer.closeDrawer(GravityCompat.START);
        changeFragment(fragment);

        return true;
    }

    public void changeFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }

}
