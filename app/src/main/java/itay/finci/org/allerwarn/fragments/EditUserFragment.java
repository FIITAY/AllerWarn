package itay.finci.org.allerwarn.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import itay.finci.org.allerwarn.FragmentChangeListener;
import itay.finci.org.allerwarn.R;
import itay.finci.org.allerwarn.fragments.MainScreenFragment;
import itay.finci.org.allerwarn.user.UserList;

public class EditUserFragment extends Fragment {
    EditText etName,etLName,etPhone,etEPhone;
    UserList ul;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.new_user_fragment, container, false);
        ul=UserList.getInstance();
        etName = (EditText) v.findViewById(R.id.etName);
        etLName = (EditText) v.findViewById(R.id.etLName);
        etPhone = (EditText) v.findViewById(R.id.etPhone);
        etEPhone = (EditText) v.findViewById(R.id.etEPhone);
        Button btCommit = (Button) v.findViewById(R.id.btCommit);

        etName.setText(ul.getActiveUser().getName());
        etLName.setText(ul.getActiveUser().getlName());
        etPhone.setText(ul.getActiveUser().getPhone());
        etEPhone.setText(ul.getActiveUser().getePhone());

        btCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ul.getActiveUser().setName(etName.getText().toString());
                ul.getActiveUser().setlName(etLName.getText().toString());
                ul.getActiveUser().setPhone(etPhone.getText().toString());
                ul.getActiveUser().setePhone(etEPhone.getText().toString());

                MainScreenFragment msf =new MainScreenFragment();
                FragmentChangeListener fc =(FragmentChangeListener)getActivity();
                fc.replaceFragment(msf);
            }
        });
        return v;
    }
}
