package itay.finci.org.allerwarn.allergies;

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
import itay.finci.org.allerwarn.user.User;
import itay.finci.org.allerwarn.user.UserList;

/**
 * Created by itay on 24/02/17.
 */

public class AllergyAdapter extends RecyclerView.Adapter<AllergyAdapter.ContactViewHolder> {
    private List<AllergyInfo> allergyList;
    private Context c;

    public AllergyAdapter(List<AllergyInfo> contactList, Context context) {
        this.allergyList = contactList;
        c = context;
    }


    @Override
    public int getItemCount() {
        return allergyList.size();
    }

    @Override
    public void onBindViewHolder(AllergyAdapter.ContactViewHolder contactViewHolder, int i) {
        System.out.println("called onBindViewHolder for " + i);
        AllergyInfo ci = allergyList.get(i);
        contactViewHolder.vName.setText(ci.name);
        contactViewHolder.vLName.setText(ci.lName);
        contactViewHolder.vPhone.setText(ci.phone);
        contactViewHolder.vEPhone.setText(ci.ePhone);
        contactViewHolder.vTitle.setText(ci.name + " " + ci.lName);
        User active = UserList.getInstance().getActiveUser();
        for (int j = 0; j < active.size(); j++) {
            if(ci.id == active.getAlergy(j).getId()){
                contactViewHolder.vTitle.setBackgroundColor(Color.RED);
                break;
            }else{
                contactViewHolder.vTitle.setBackgroundColor(ContextCompat.getColor(c, R.color.bkg_card));
            }
        }
    }



    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_layout, viewGroup, false);

        return new AllergyAdapter.ContactViewHolder(itemView);
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
