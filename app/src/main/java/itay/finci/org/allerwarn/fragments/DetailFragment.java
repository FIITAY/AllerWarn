package itay.finci.org.allerwarn.fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import itay.finci.org.allerwarn.FragmentChangeListener;
import itay.finci.org.allerwarn.R;
import itay.finci.org.allerwarn.SecondMainActivity;
import itay.finci.org.allerwarn.user.User;
import itay.finci.org.allerwarn.user.UserList;

public class DetailFragment extends Fragment {
    public static final String ARG_ITEM_ID = "item_id";
    User u;
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        u = UserList.getInstance().getUser(getArguments().getString(ARG_ITEM_ID));
        if (u != null) {

            TextView tvTitle = (TextView) rootView.findViewById(R.id.title);
            tvTitle.setText(u.getName() + " " + u.getlName());


            setButtonsListiners(rootView);


        }
        return rootView;
    }

    private void setButtonsListiners(final View rootView) {
        Button btDelete = (Button) rootView.findViewById(R.id.btDelete);
        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext()); //alert for confirm to delete
                builder.setMessage("Are you sure to delete?");    //set message

                builder.setPositiveButton("REMOVE", new DialogInterface.OnClickListener() { //when click on DELETE
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        UserList.getInstance().remove(UserList.getInstance().getIndexOfUser(u));
                        rewrite();
                        if (UserList.getInstance().size() > 0) {
                            UserList.getInstance().setActiveUserInPosition(0);
                        } else {
                            NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
                            Menu nav_Menu = navigationView.getMenu();
                            nav_Menu.findItem(R.id.nav_EditUser).setVisible(false);
                            nav_Menu.findItem(R.id.nav_addAler).setVisible(false);
                            nav_Menu.findItem(R.id.nav_nfcWrite).setVisible(false);
                        }
                        MainScreenFragment msf = new MainScreenFragment();
                        FragmentChangeListener fc = (FragmentChangeListener) getActivity();
                        fc.replaceFragment(msf);
                        return;
                    }
                }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {  //not removing items if cancel is done
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        rewrite();
                        return;
                    }
                }).show();  //show alert dialog
            }
        });

        Button btSetActiveUser = (Button) rootView.findViewById(R.id.btSetActive);
        btSetActiveUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserList.getInstance().setActiveUserInPosition(UserList.getInstance().getIndexOfUser(u));
                Snackbar.make(v, "User set as active ", Snackbar.LENGTH_SHORT).show();
                v.findViewById(R.id.btSetActive).setEnabled(false);
                Button temp = (Button) rootView.findViewById(R.id.btSetActive);
                temp.setEnabled(false);
                temp.setTextColor(Color.GRAY);
            }
        });
        if (UserList.getInstance().getActiveUser().getName().equals(u.getName())) {
            btSetActiveUser.setEnabled(false);
            btSetActiveUser.setTextColor(Color.GRAY);
        } else {
            btSetActiveUser.setEnabled(true);
            btSetActiveUser.setTextColor(Color.RED);
        }

        if (!SecondMainActivity.show) {
            btDelete.setVisibility(View.GONE);
            btSetActiveUser.setVisibility(View.GONE);
        } else {

            btDelete.setVisibility(View.VISIBLE);
            btSetActiveUser.setVisibility(View.VISIBLE);
        }
    }

    private void rewrite() {
        try {
            FileOutputStream fileOut = getActivity().openFileOutput("UserList.ser", Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            UserList u = UserList.getInstance();
            UserList.getInstance().write(out);
            out.close();
            fileOut.close();
            //System.out.printf("Serialized data is saved in /tmp/employee.ser");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
}
