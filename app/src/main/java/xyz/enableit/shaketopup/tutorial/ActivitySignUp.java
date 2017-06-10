package xyz.enableit.shaketopup.tutorial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import xyz.enableit.shaketopup.R;

public class ActivitySignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    public void sentBackResult(View view) {
        Intent intent = new Intent();
        intent.putExtra("Name", "Milon");
        setResult(RESULT_OK, intent);
        finish();
    }
}
