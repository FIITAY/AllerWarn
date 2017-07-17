package itay.finci.org.allerwarn.user;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

import itay.finci.org.allerwarn.FragmentChangeListener;
import itay.finci.org.allerwarn.R;
import itay.finci.org.allerwarn.fragments.DetailFragment;

/**
 * Created by itay on 27/01/17.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ContactViewHolder> {

    private List<UserInfo> contactList;
    private Context c;
    private Activity a;

    public UserAdapter(List<UserInfo> contactList, Context context, Activity activity) {
        this.contactList = contactList;
        c = context;
        a = activity;
    }


    @Override
    public int getItemCount() {
        return contactList.size();
    }

    @Override
    public void onBindViewHolder(final ContactViewHolder contactViewHolder, int i) {
        UserInfo ci = contactList.get(i);
        contactViewHolder.vName.setText(ci.name);
        contactViewHolder.vLName.setText(ci.lName);
        if (ci.name.length() + ci.lName.length() > 9) {
            String title = ci.name + " " + ci.lName;
            contactViewHolder.vTitle.setText(StringUtils.abbreviate(title, 10));
        } else {
            contactViewHolder.vTitle.setText(ci.name + " " + ci.lName);
        }
        if(UserList.getInstance().getActiveUserPosition() == i){
            contactViewHolder.vTitle.setBackgroundColor(Color.WHITE);
            contactViewHolder.rl.setBackgroundColor(Color.WHITE);
        }else{
            contactViewHolder.vTitle.setBackgroundColor(ContextCompat.getColor(c, R.color.bkg_card));
            contactViewHolder.rl.setBackgroundColor(ContextCompat.getColor(c, R.color.bkg_card));
        }
        contactViewHolder.ibInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle arguments = new Bundle();
                arguments.putString(DetailFragment.ARG_ITEM_ID, contactViewHolder.vName.getText().toString());
                DetailFragment df = new DetailFragment();
                df.setArguments(arguments);
                FragmentChangeListener fc = (FragmentChangeListener) a;
                fc.replaceFragment(df);
            }
        });
    }



    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_layout, viewGroup, false);

        return new ContactViewHolder(itemView);
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {

        protected TextView vName;
        protected TextView vLName;
        protected TextView vTitle;
        protected RelativeLayout rl;
        protected ImageButton ibInfo;

        public ContactViewHolder(View v) {
            super(v);
            vName =  (TextView) v.findViewById(R.id.txtName);
            vLName = (TextView)  v.findViewById(R.id.txtLName);
            vTitle = (TextView) v.findViewById(R.id.title);
            rl = (RelativeLayout) v.findViewById(R.id.relativeLayout);
            ibInfo = (ImageButton) v.findViewById(R.id.btInfo);

        }
    }
}
