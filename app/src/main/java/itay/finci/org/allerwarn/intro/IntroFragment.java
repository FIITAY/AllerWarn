package itay.finci.org.allerwarn.intro;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import itay.finci.org.allerwarn.MainActivity;
import itay.finci.org.allerwarn.R;
import itay.finci.org.allerwarn.user.User;
import itay.finci.org.allerwarn.user.UserList;

/**
 * Created by itay on 11/08/2017.
 */

public class IntroFragment extends Fragment {


    private static final String BACKGROUND_COLOR = "backgroundColor";
    private static final String PAGE = "page";
    private final static int TO_LONG_NAME = 1;
    private final static int EMPTY_EDIT_TEXT = 2;
    private final static int USER_SAME_TO_ANOTHER = 3;
    private final static int TWO_FILDS_SAME = 4;

    private int mBackgroundColor, mPage;

    public static IntroFragment newInstance(int backgroundColor, int page) {
        IntroFragment frag = new IntroFragment();
        Bundle b = new Bundle();
        b.putInt(BACKGROUND_COLOR, backgroundColor);
        b.putInt(PAGE, page);
        frag.setArguments(b);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!getArguments().containsKey(BACKGROUND_COLOR))
            throw new RuntimeException("Fragment must contain a \"" + BACKGROUND_COLOR + "\" argument!");
        mBackgroundColor = getArguments().getInt(BACKGROUND_COLOR);

        if (!getArguments().containsKey(PAGE))
            throw new RuntimeException("Fragment must contain a \"" + PAGE + "\" argument!");
        mPage = getArguments().getInt(PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Select a layout based on the current page
        int layoutResId;
        switch (mPage) {
            case 0:
                layoutResId = R.layout.intro_fragment_layout_1;
                break;
            default:
                layoutResId = R.layout.intro_fragment_layout_2;
        }

        // Inflate the layout resource file
        View view = getActivity().getLayoutInflater().inflate(layoutResId, container, false);

        // Set the current page index as the View's tag (useful in the PageTransformer)
        view.setTag(mPage);

        if (layoutResId == R.layout.intro_fragment_layout_2) {
            buttons(view);
        }

        return view;
    }


    private void buttons(final View view) {
        Button btDone = (Button) view.findViewById(R.id.btDone);
        btDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etName = (EditText) view.findViewById(R.id.etName);
                EditText etLname = (EditText) view.findViewById(R.id.etLName);
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
                    User u = new User(etName.getText().toString(), etLname.getText().toString());
                    UserList.getInstance().setActiveUser(u);
                    rewrite();
                    startActivity(new Intent(getContext(), MainActivity.class));
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
        });
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

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set the background color of the root view to the color specified in newInstance()
        View background = view.findViewById(R.id.intro_background);
        background.setBackgroundColor(mBackgroundColor);
    }

}
