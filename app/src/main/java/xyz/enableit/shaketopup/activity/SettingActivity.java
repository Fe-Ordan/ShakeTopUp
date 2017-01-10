package xyz.enableit.shaketopup.activity;

import android.preference.PreferenceFragment;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.MenuItem;

import xyz.enableit.shaketopup.R;
import xyz.enableit.shaketopup.activity.AppCompatPreferenceActivity;

public class SettingActivity extends AppCompatPreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActionBar();
        getFragmentManager().beginTransaction().replace(android.R.id.content,
                                                                 new PrefsFragment()).commit();
    }

    private void setupActionBar() {

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
            //actionBar.setDisplayHomeAsUpEnabled();
        }
    }

    public static class PrefsFragment extends PreferenceFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            // Load the preferences from an XML resource
            addPreferencesFromResource(R.xml.pref_general);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
