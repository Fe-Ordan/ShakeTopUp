package xyz.enableit.shaketopup.tutorial;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import xyz.enableit.shaketopup.R;

public class ActivityMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tut);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentMain()).commit();
    }
}
