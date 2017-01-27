package itay.finci.org.allerwarn;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainScreenFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main_screen, container, false);

        RecyclerView recList = (RecyclerView) v.findViewById(R.id.cardList);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        ContactAdapter ca = new ContactAdapter(createList(UserList.getInstance().size()));
        recList.setAdapter(ca);

        return v;
    }

    private List<ContactInfo> createList(int size) {

        List<ContactInfo> result = new ArrayList<ContactInfo>();
        for (int i=0; i < size; i++) {
            ContactInfo ci = new ContactInfo();
            User u = UserList.getInstance().getUser(i);
            ci.name = u.getName();
            ci.lName = u.getlName();
            ci.phone = u.getPhone();
            ci.ePhone = u.getePhone();
            result.add(ci);

        }

        return result;
    }
}
