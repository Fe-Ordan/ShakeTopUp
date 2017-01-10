package xyz.enableit.shaketopup.dialog;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.preference.PreferenceManager;

import xyz.enableit.shaketopup.R;

/**
 * Created by dinislam on 1/10/17.
 * email : milon@strativ.se
 */

public class DialogOperatorChooser extends DialogFragment {

    private SharedPreferences sharedPref;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.operator_dialog_title);

        //list of items
        String[] items = getResources().getStringArray(R.array.operator_list);
        builder.setSingleChoiceItems(items, 0,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // item selected logic

                        sharedPref = PreferenceManager
                                .getDefaultSharedPreferences(getActivity());
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString(getString(R.string.operator), "" + which);
                        editor.commit();

                        dialog.dismiss();

                    }
                });
        builder.setCancelable(false);

        return builder.create();
    }


}
