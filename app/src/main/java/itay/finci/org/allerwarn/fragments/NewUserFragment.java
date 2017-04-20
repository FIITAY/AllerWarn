package itay.finci.org.allerwarn.fragments;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import itay.finci.org.allerwarn.FragmentChangeListener;
import itay.finci.org.allerwarn.R;
import itay.finci.org.allerwarn.user.User;
import itay.finci.org.allerwarn.user.UserList;

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
                NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
                Menu nav_Menu = navigationView.getMenu();
                nav_Menu.findItem(R.id.nav_EditUser).setVisible(true);
                nav_Menu.findItem(R.id.nav_addAler).setVisible(true);
                nav_Menu.findItem(R.id.nav_nfcWrite).setVisible(true);
                nav_Menu.findItem(R.id.nav_nfcRead).setVisible(true);
                User u = new User(etName.getText().toString(),etLName.getText().toString(),
                        etPhone.getText().toString(),etEPhone.getText().toString());
                UserList.getInstance().add(u,true);
                MainScreenFragment msf =new MainScreenFragment();
                FragmentChangeListener fc =(FragmentChangeListener)getActivity();
                fc.replaceFragment(msf);
            }
        });

        return v;
    }
}
