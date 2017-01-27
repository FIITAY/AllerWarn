package itay.finci.org.allerwarn;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by itay on 27/01/17.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private List<ContactInfo> contactList;

    public ContactAdapter(List<ContactInfo> contactList) {
        this.contactList = contactList;
    }


    @Override
    public int getItemCount() {
        return contactList.size();
    }

    @Override
    public void onBindViewHolder(ContactViewHolder contactViewHolder, int i) {
        ContactInfo ci = contactList.get(i);
        contactViewHolder.vName.setText(ci.name);
        contactViewHolder.vLName.setText(ci.lName);
        contactViewHolder.vPhone.setText(ci.phone);
        contactViewHolder.vEPhone.setText(ci.ePhone);
        contactViewHolder.vTitle.setText(ci.name + " " + ci.lName);
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
