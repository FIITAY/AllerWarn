package itay.finci.org.allerwarn.fragments;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
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
    EditText etName, etLName;
    private final static int TO_LONG_NAME = 1;
    private final static int EMPTY_EDIT_TEXT = 2;
    private final static int USER_SAME_TO_ANOTHER = 3;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.new_user_fragment, container, false);

        etName = (EditText) v.findViewById(R.id.etName);
        etLName = (EditText) v.findViewById(R.id.etLName);
        Button btCommit = (Button) v.findViewById(R.id.btCommit);

        btCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean notnull = false;
                int BUG = 0;
                if (!etName.getText().toString().isEmpty() && !etLName.getText().toString().isEmpty()) {
                    notnull = true;
                } else {
                    notnull = false;
                    BUG = EMPTY_EDIT_TEXT;
                }
                if (etName.getText().toString().length() +
                        etLName.getText().toString().length() >= 90) {
                    notnull = false;
                    BUG = TO_LONG_NAME;
                }

                for (User u : UserList.getInstance().getAlu()) {
                    if (u.getName().equals(etName.getText().toString()) &&
                            u.getlName().equals(etLName.getText().toString())) {
                        notnull = false;
                        BUG = USER_SAME_TO_ANOTHER;
                    }
                }

                if (notnull) {
                    NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
                    Menu nav_Menu = navigationView.getMenu();
                    nav_Menu.findItem(R.id.nav_EditUser).setVisible(true);
                    nav_Menu.findItem(R.id.nav_addAler).setVisible(true);
                    nav_Menu.findItem(R.id.nav_nfcWrite).setVisible(true);
                    User u = new User(etName.getText().toString(), etLName.getText().toString());
                    UserList.getInstance().add(u, true);
                    MainScreenFragment msf = new MainScreenFragment();
                    FragmentChangeListener fc = (FragmentChangeListener) getActivity();
                    fc.replaceFragment(msf);
                } else {
                    if (BUG == EMPTY_EDIT_TEXT) {
                        Snackbar.make(v, "You can not enter a empty filed", Snackbar.LENGTH_LONG).show();
                    } else if (BUG == TO_LONG_NAME) {
                        Snackbar.make(v, "The overall length of the user valuables is to long", Snackbar.LENGTH_LONG).show();
                    } else if (BUG == USER_SAME_TO_ANOTHER) {
                        Snackbar.make(v, "User is same as another user", Snackbar.LENGTH_LONG).show();
                    } else {
                        Snackbar.make(v, "System bug try again", Snackbar.LENGTH_LONG).show();
                    }
                }
            }
        });

        return v;
    }
}