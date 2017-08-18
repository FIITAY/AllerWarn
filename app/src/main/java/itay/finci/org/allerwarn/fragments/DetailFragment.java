package itay.finci.org.allerwarn.fragments;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.facebook.Profile;

import java.util.ArrayList;

import itay.finci.org.allerwarn.MainActivity;
import itay.finci.org.allerwarn.R;
import itay.finci.org.allerwarn.allergies.AllergiesList;
import itay.finci.org.allerwarn.allergies.Allergy;
import itay.finci.org.allerwarn.user.User;
import itay.finci.org.allerwarn.user.UserList;

public class DetailFragment extends Fragment {
    public static final String ARG_ITEM_ID = "item_id";
    private final static int TO_LONG_NAME = 1;
    private final static int EMPTY_EDIT_TEXT = 2;
    private final static int USER_SAME_TO_ANOTHER = 3;
    private final static int TWO_FILDS_SAME = 4;

    User u;
    View rootView;
    int state = 1;
    TextView tvTitle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        u = UserList.getInstance().getActiveUser();
        if (u != null) {

            tvTitle = (TextView) rootView.findViewById(R.id.title);


            if (Profile.getCurrentProfile() != null) {
                tvTitle.setText(Profile.getCurrentProfile().getFirstName() + " " + Profile.getCurrentProfile().getLastName());
            } else {
                tvTitle.setText(u.getName() + " " + u.getlName());
            }

            setButtonsListiners(rootView);

            setTextViews(rootView);


        }
        return rootView;
    }

    private void setTextViews(View rootView) {

        boolean[] hasAllergy = new boolean[8];
        ArrayList<Allergy> ala = UserList.getInstance().getActiveUser().getAla();
        for (Allergy a : ala) {
            for (int i = 0; i < AllergiesList.getInstance(getContext()).getAla().size(); i++) {

                if (a.getId() == AllergiesList.getInstance(getContext()).getAller(i).getId()) {
                    hasAllergy[i] = true;
                }
            }
        }
        TextView tvMilk = (TextView) rootView.findViewById(R.id.tvMilk);
        if (hasAllergy[AllergiesList.getInstance(getContext()).getAller("Milk").getId() - 1]) {
            tvMilk.setVisibility(View.VISIBLE);
        } else {
            tvMilk.setVisibility(View.GONE);
        }
        TextView tvEgg = (TextView) rootView.findViewById(R.id.tvEgg);
        if (hasAllergy[AllergiesList.getInstance(getContext()).getAller("Egg").getId() - 1]) {
            tvEgg.setVisibility(View.VISIBLE);
        } else {
            tvEgg.setVisibility(View.GONE);
        }
        TextView tvPeanuts = (TextView) rootView.findViewById(R.id.tvPeanuts);
        if (hasAllergy[AllergiesList.getInstance(getContext()).getAller("Peanuts").getId() - 1]) {
            tvPeanuts.setVisibility(View.VISIBLE);
        } else {
            tvPeanuts.setVisibility(View.GONE);
        }
        TextView tvTreeNuts = (TextView) rootView.findViewById(R.id.tvTreeNuts);
        if (hasAllergy[AllergiesList.getInstance(getContext()).getAller("Tree nuts").getId() - 1]) {
            tvTreeNuts.setVisibility(View.VISIBLE);
        } else {
            tvTreeNuts.setVisibility(View.GONE);
        }
        TextView tvFish = (TextView) rootView.findViewById(R.id.tvFish);
        if (hasAllergy[AllergiesList.getInstance(getContext()).getAller("Fish").getId() - 1]) {
            tvFish.setVisibility(View.VISIBLE);
        } else {
            tvFish.setVisibility(View.GONE);
        }
        TextView tvShellFish = (TextView) rootView.findViewById(R.id.tvShellFish);
        if (hasAllergy[AllergiesList.getInstance(getContext()).getAller("Shellfish").getId() - 1]) {
            tvShellFish.setVisibility(View.VISIBLE);
        } else {
            tvShellFish.setVisibility(View.GONE);
        }
        TextView tvWheat = (TextView) rootView.findViewById(R.id.tvWheat);
        if (hasAllergy[AllergiesList.getInstance(getContext()).getAller("Wheat").getId() - 1]) {
            tvWheat.setVisibility(View.VISIBLE);
        } else {
            tvWheat.setVisibility(View.GONE);
        }
        TextView tvSoy = (TextView) rootView.findViewById(R.id.tvSoy);
        if (hasAllergy[AllergiesList.getInstance(getContext()).getAller("Soy").getId() - 1]) {
            tvSoy.setVisibility(View.VISIBLE);
        } else {
            tvSoy.setVisibility(View.GONE);
        }

    }

    private void setButtonsListiners(final View rootView) {

        final Button btEditUser = (Button) rootView.findViewById(R.id.btDone);
        btEditUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewSwitcher switcher = (ViewSwitcher) rootView.findViewById(R.id.switcher);
                EditText etName = (EditText) switcher.findViewById(R.id.etName);
                EditText etLname = (EditText) switcher.findViewById(R.id.etLname);
                if (state == 1) {
                    state = 2;
                    btEditUser.setText("Done");
                    etName.setText(u.getName());
                    etLname.setText(u.getlName());
                    switcher.showNext();
                    rewrite();
                } else if (state == 2) {
                    boolean notnull = false;
                    int BUG = 0;
                    if (!etName.getText().toString().isEmpty() && !etLname.getText().toString().isEmpty()) {
                        notnull = true;
                    } else {
                        notnull = false;
                        BUG = EMPTY_EDIT_TEXT;
                    }
                    if (etName.getText().toString().length() +
                            etLname.getText().toString().length() >= 90) {
                        notnull = false;
                        BUG = TO_LONG_NAME;
                    }
                    if (etName.getText().toString().equalsIgnoreCase(etLname.getText().toString())) {
                        notnull = false;
                        BUG = TWO_FILDS_SAME;
                    }
                    if (notnull) {
                        state = 1;
                        btEditUser.setText("Edit User");
                        u.setName(etName.getText().toString());
                        u.setlName(etLname.getText().toString());
                        tvTitle.setText(u.getName() + " " + u.getlName());
                        switcher.showNext();
                    } else {
                        if (BUG == EMPTY_EDIT_TEXT) {
                            Snackbar.make(v, "You can not enter a empty filed", Snackbar.LENGTH_LONG).show();
                        } else if (BUG == TO_LONG_NAME) {
                            Snackbar.make(v, "The overall length of the user valuables is to long", Snackbar.LENGTH_LONG).show();
                        } else if (BUG == USER_SAME_TO_ANOTHER) {
                            Snackbar.make(v, "User is same as another user", Snackbar.LENGTH_LONG).show();
                        } else if (BUG == TWO_FILDS_SAME) {
                            Snackbar.make(v, "You cant enter the same word in the two fields", Snackbar.LENGTH_LONG).show();
                        } else {
                            Snackbar.make(v, "System bug try again", Snackbar.LENGTH_LONG).show();
                        }
                    }

                }

            }
        });
    }

    private void rewrite() {
        MainActivity.rewrite();
    }
}
