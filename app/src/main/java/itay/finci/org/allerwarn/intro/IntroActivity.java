package itay.finci.org.allerwarn.intro;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.widget.EditText;
import android.widget.Toast;

import com.miz.introactivity.IntroFragment;

import itay.finci.org.allerwarn.R;
import itay.finci.org.allerwarn.SecondMainActivity;
import itay.finci.org.allerwarn.user.User;
import itay.finci.org.allerwarn.user.UserList;

/**
 * Created by itay on 22/07/2017.
 */

public class IntroActivity extends com.miz.introactivity.IntroActivity {
    @Override
    protected void initialize() {
        String description = "This is a very long and detailed description about absolutely nothing in particular.";
        String name;
        String lname;

        addIntroScreen(
                IntroFragment.newInstance("", "Swipe from right to left to start the making of your user!",
                        R.layout.intro_fragment_layout_1,
                        IntroFragment.RESOURCE_TYPE_LAYOUT),
                ContextCompat.getColor(this, R.color.colorPrimary)
        );

        addIntroScreen(
                IntroFragment.newInstance("", "now lets continue to last name",
                        R.layout.intro_fragment_layout_2,
                        IntroFragment.RESOURCE_TYPE_LAYOUT),
                ContextCompat.getColor(this, R.color.colorPrimary)
        );

        addIntroScreen(
                IntroFragment.newInstance("", "Good job you made your User",
                        R.layout.intro_fragment_layout_3,
                        IntroFragment.RESOURCE_TYPE_LAYOUT),
                ContextCompat.getColor(this, R.color.colorPrimaryDark)
        );

        setShowSkipButton(false);
        setShowNextButton(true);
        setSkipButtonTextColor(Color.WHITE);
        setNextButtonBackgroundColor(Color.WHITE);
        setNextButtonIconColor(Color.WHITE);
        setProgressCircleColor(Color.WHITE);
    }

    @Override
    protected void onDonePressed() {
        EditText etName = (EditText) findViewById(R.id.tv);
        EditText etlName = (EditText) findViewById(R.id.tv2);
        User u = new User(etName.getText().toString(), etlName.getText().toString());
        UserList.getInstance().add(u);
        startActivity(new Intent(this, SecondMainActivity.class));
    }

    @Override
    protected void onSkipPressed() {
        Toast.makeText(this, "You cant skip!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onNextPressed(int pagePosition) {
        //Toast.makeText(this, "onNextPressed() " + pagePosition, Toast.LENGTH_SHORT).show();
    }

}
