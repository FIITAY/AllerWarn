package itay.finci.org.allerwarn.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import itay.finci.org.allerwarn.MainActivity;
import itay.finci.org.allerwarn.R;
import itay.finci.org.allerwarn.user.UserList;

/**
 * Created by itay on 13/08/2017.
 */

public class NFCfragment extends Fragment {
    View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.nfc_fragment, container, false);

        Button btRead = (Button) rootView.findViewById(R.id.btRead);
        Button btWrite = (Button) rootView.findViewById(R.id.btWrite);

        btRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.message = null;
                MainActivity.ReadNFC();
            }
        });

        btWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.message = MainActivity.nfcMger.createTextMessage(UserList.getInstance().getActiveUser().getByteCode());
                MainActivity.ReadNFC();
            }
        });

        return rootView;
    }
}
