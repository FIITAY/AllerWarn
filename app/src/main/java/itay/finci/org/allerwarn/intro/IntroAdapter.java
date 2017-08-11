package itay.finci.org.allerwarn.intro;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


/**
 * Created by itay on 11/08/2017.
 */

public class IntroAdapter extends FragmentPagerAdapter {

    public IntroAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return IntroFragment.newInstance(Color.parseColor("#E0C035"), position); // blue
            default:
                return IntroFragment.newInstance(Color.parseColor("#E0C035"), position); // green
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

}
