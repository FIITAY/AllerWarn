package itay.finci.org.allerwarn;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainScreenFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main_screen, container, false);
        TextView tvVersionNum = (TextView) v.findViewById(R.id.tvVersionCode);
        try {
            String s = getActivity().getApplicationContext().getPackageManager().getPackageInfo(getActivity().getApplicationContext()
                    .getPackageName(), 0).versionName;
            tvVersionNum.setText(s);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return v;
    }
}
