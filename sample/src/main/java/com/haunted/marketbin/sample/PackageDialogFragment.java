package com.haunted.marketbin.sample;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.EditText;

public class PackageDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final EditText editPackageName = new EditText(getActivity());
        editPackageName.setText(MainActivity.demoAppId);
        editPackageName.setSingleLine();

        return new AlertDialog.Builder(getActivity())
                .setTitle("Test Package")
                .setView(editPackageName)
                .setNegativeButton(android.R.string.cancel, null)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        assert editPackageName.getText() != null;
                        MainActivity.demoAppId = editPackageName.getText().toString();
                    }
                })
                .show();
    }
}
