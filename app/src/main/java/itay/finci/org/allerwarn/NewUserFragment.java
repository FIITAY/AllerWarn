package itay.finci.org.allerwarn;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by itay on 06/01/17.
 */

public class NewUserFragment extends Fragment {
    EditText etName,etLName,etPhone,etEPhone;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.new_user_fragment, container, false);

        etName = (EditText) v.findViewById(R.id.etName);
        etLName = (EditText) v.findViewById(R.id.etLName);
        etPhone = (EditText) v.findViewById(R.id.etPhone);
        etEPhone = (EditText) v.findViewById(R.id.etEPhone);
        Button btCommit = (Button) v.findViewById(R.id.btCommit);

        btCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserList.getInstance().add(etName.getText().toString(),etLName.getText().toString(),
                        etPhone.getText().toString(),etEPhone.getText().toString());
                MainScreenFragment msf =new MainScreenFragment();
                FragmentChangeListener fc =(FragmentChangeListener)getActivity();
                fc.replaceFragment(msf);
            }
        });

        return v;
    }
}
