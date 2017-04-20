package itay.finci.org.allerwarn.user;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import itay.finci.org.allerwarn.R;

/**
 * Created by itay on 27/01/17.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ContactViewHolder> {

    private List<UserInfo> contactList;
    private Context c;

    public UserAdapter(List<UserInfo> contactList, Context context) {
        this.contactList = contactList;
        c = context;
    }


    @Override
    public int getItemCount() {
        return contactList.size();
    }

    @Override
    public void onBindViewHolder(ContactViewHolder contactViewHolder, int i) {
        UserInfo ci = contactList.get(i);
        contactViewHolder.vName.setText(ci.name);
        contactViewHolder.vLName.setText(ci.lName);
        contactViewHolder.vPhone.setText(ci.phone);
        contactViewHolder.vEPhone.setText(ci.ePhone);
        contactViewHolder.vTitle.setText(ci.name + " " + ci.lName);
        if(UserList.getInstance().getActiveUserPosition() == i){
            contactViewHolder.vTitle.setBackgroundColor(Color.RED);
        }else{
            contactViewHolder.vTitle.setBackgroundColor(ContextCompat.getColor(c, R.color.bkg_card));
        }
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
        protected TextView vPhone;
        protected TextView vTitle;
        protected TextView vEPhone;

        public ContactViewHolder(View v) {
            super(v);
            vName =  (TextView) v.findViewById(R.id.txtName);
            vLName = (TextView)  v.findViewById(R.id.txtLName);
            vPhone = (TextView)  v.findViewById(R.id.txtPhone);
            vTitle = (TextView) v.findViewById(R.id.title);
            vEPhone = (TextView) v.findViewById(R.id.txtEPhone);
        }
    }
}
