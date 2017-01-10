package xyz.enableit.shaketopup.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import xyz.enableit.shaketopup.PrefConstants;
import xyz.enableit.shaketopup.R;
import xyz.enableit.shaketopup.SAppUtil;
import xyz.enableit.shaketopup.fragment.FragmentHome;
import xyz.enableit.shaketopup.service.ShakeService;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    int launchFromService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        checkShowTutorial();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Todo will decide later
        operatorChooserDialog();

        //set home fragment
        changeFragment(new FragmentHome());

        //start service
        Intent intent = new Intent(MainActivity.this, ShakeService.class);
        startService(intent);


      //  getSupportFragmentManager().beginTransaction().replace(R.id.container, new FragmentHome()).commit();

        //calling from service need to recharge
        launchFromService = getIntent().getIntExtra("launchFromService", 0);
        if (launchFromService == 1) {
            //check setting from preference
            dialForRecharge(0);
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
            case 0:
                dialNumber = "*" + "874" + Uri.encode("#");
                break;
            case 1:
                dialNumber = "*" + "1010" + "*" + Uri.encode("#");
                break;
            case 2:
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

    public void operatorChooserDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(R.string.operator_dialog_title);

        //list of items
        String[] items = getResources().getStringArray(R.array.operator_list);
        builder.setSingleChoiceItems(items, 0,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // item selected logic
                    }
                });

        String positiveText = getString(android.R.string.ok);
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // positive button logic
                    }
                });

        String negativeText = getString(android.R.string.cancel);
        builder.setNegativeButton(negativeText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // negative button logic
                    }
                });

        AlertDialog dialog = builder.create();
        // display dialog
        dialog.show();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            //startActivity(new Intent(MainActivity.this,SettingsActivity.class));
        } else if (id == R.id.nav_gallery) {
           // startActivity(new Intent(MainActivity.this,ActivitySetting.class));
            Intent i = new Intent(this, Main2Activity.class);
            startActivity(i);

        } else if (id == R.id.nav_slideshow) {
            //startActivity(new Intent(MainActivity.this,SettingsExampleActivity.class));

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void changeFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }


}
