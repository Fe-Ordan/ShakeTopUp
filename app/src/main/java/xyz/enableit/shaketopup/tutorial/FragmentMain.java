package xyz.enableit.shaketopup.tutorial;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import xyz.enableit.shaketopup.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMain extends Fragment {


    private static final int RESULT_CODE = 100;

    public FragmentMain() {
        // Required empty public constructor
    }

    View mRootView;
    Button btnLoadActivityClick;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.fragment_main, container, false);
        Log.d("State", "OnCreate");
        btnLoadActivityClick = (Button) mRootView.findViewById(R.id.btn_fragment_main_load_activity);
        btnLoadActivityClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getActivity(), ActivitySignUp.class), RESULT_CODE);
            }
        });


        return mRootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("state", "Print Here");

        if (requestCode == RESULT_CODE) {
            if (resultCode == Activity.RESULT_OK) {
            }
        }
    }

    @Override
    public void onResume() {
        Log.d("State", "onResume");
        super.onResume();

    }


    @Override
    public void onPause() {

        Log.d("State", "onPause");
        super.onPause();
    }


    @Override
    public void onStart() {
        Log.d("State", "onStart");
        super.onStart();
    }


}
