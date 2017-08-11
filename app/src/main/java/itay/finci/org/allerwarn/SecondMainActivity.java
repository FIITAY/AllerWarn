package itay.finci.org.allerwarn;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import itay.finci.org.allerwarn.fragments.DetailFragment;
import itay.finci.org.allerwarn.intro.IntroActivity;
import itay.finci.org.allerwarn.user.UserList;

public class SecondMainActivity extends AppCompatActivity
        implements FragmentChangeListener {

    private TextView mTextMessage;
    public static boolean show = true;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //TODO implement function to the home icon
                    return true;
                case R.id.navigation_dashboard:
                    //TODO implement function to the dashboard icon
                    return true;
                case R.id.navigation_notifications:
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onStart() {
        super.onStart();
        show = false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        show = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (UserList.getInstance().isEmpty()) {
            getSupportActionBar().hide();
            startActivity(new Intent(this, IntroActivity.class));
        } else {
            getSupportActionBar().show();
            setContentView(R.layout.activity_second_main);
            show = false;
            Bundle arguments = new Bundle();
            arguments.putString(DetailFragment.ARG_ITEM_ID, UserList.getInstance()
                    .getActiveUser().getName());
            DetailFragment df = new DetailFragment();
            df.setArguments(arguments);
            System.out.println(show);
            this.replaceFragment(df);
            BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
            navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
            navigation.setSelectedItemId(R.id.navigation_dashboard);
        }
    }

    /**
     * replace fragment function
     *
     * @param fragment fragment object to change to
     */
    @Override
    public void replaceFragment(Fragment fragment) {
        //FragmentManager fragmentManager = getSupportFragmentManager();;
        //FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, fragment, null);
        ft.detach(fragment);
        ft.attach(fragment);
        ft.commit();
    }

}
