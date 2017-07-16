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
 * <pre>
 * Created by itay on 19/02/17.
 * this fragment is used for making a new user
 * </pre>
 */
public class EditUserFragment extends Fragment {
    EditText etName,etLName,etPhone,etEPhone;
    UserList ul;
    private final static int TO_LONG_NAME = 1;
    private final static int EMPTY_EDIT_TEXT = 2;
    private final static int USER_SAME_TO_ANOTHER = 3;
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
            /**
             * when btCommit is clicked the app is making the new user
             * @param v the app view
             */
            @Override
            public void onClick(View v) {
                boolean notnull = false;
                int BUG = 0;
                if (!etName.getText().toString().isEmpty() && !etLName.getText().toString().isEmpty() &&
                        !etPhone.getText().toString().isEmpty() && !etEPhone.getText().toString().isEmpty()) {
                    notnull = true;
                } else {
                    notnull = false;
                    BUG = EMPTY_EDIT_TEXT;
                }
                if (etName.getText().toString().length() +
                        etLName.getText().toString().length() +
                        etPhone.getText().toString().length() +
                        etEPhone.getText().toString().length() >= 90) {
                    notnull = false;
                    BUG = TO_LONG_NAME;
                }

                for (User u : UserList.getInstance().getAlu()) {
                    if (u.getName().equals(etName.getText().toString()) &&
                            u.getlName().equals(etLName.getText().toString()) &&
                            u.getPhone().equals(etPhone.getText().toString()) &&
                            u.getePhone().equals(etEPhone.getText().toString())) {
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

                    ul.getActiveUser().setName(etName.getText().toString());
                    ul.getActiveUser().setlName(etLName.getText().toString());
                    ul.getActiveUser().setPhone(etPhone.getText().toString());
                    ul.getActiveUser().setePhone(etEPhone.getText().toString());

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
