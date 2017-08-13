package itay.finci.org.allerwarn.dialogs;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import itay.finci.org.allerwarn.SecondMainActivity;
import itay.finci.org.allerwarn.user.UserList;


/**
 * Created by itay on 12/08/2017.
 */

public class NfcDialog extends DialogFragment {

    @Override
    public AlertDialog onCreateDialog(Bundle savedInstanceState) {

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("select an action:")
                .setPositiveButton("Read", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SecondMainActivity.message = null;
                        dialog.dismiss();

                    }
                })
                .setNegativeButton("Write", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SecondMainActivity.message = SecondMainActivity.nfcMger.createTextMessage(UserList.getInstance().getActiveUser().getByteCode());
                        dialog.dismiss();
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
