package itay.finci.org.allerwarn;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import static android.R.id.list;

public class MainScreenFragment extends Fragment {
    ContactAdapter ca;
    List<ContactInfo> al;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main_screen, container, false);

        RecyclerView recList = (RecyclerView) v.findViewById(R.id.cardList);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        al =createList(UserList.getInstance().size());
        ca= new ContactAdapter(al, getContext());
        recList.setAdapter(ca);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recList); //set swipe to recylcerview

        rewrite();

        return v;
    }

    public void rewrite(){
        try {
            FileOutputStream fileOut = getActivity().openFileOutput("UserList.ser", Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            UserList u= UserList.getInstance();
            UserList.getInstance().write(out);
            out.close();
            fileOut.close();
            //System.out.printf("Serialized data is saved in /tmp/employee.ser");
        }catch(IOException i) {
            i.printStackTrace();
        }
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }
        @Override
        public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
            final int position = viewHolder.getAdapterPosition(); //get position which is swipe

            if (direction == ItemTouchHelper.RIGHT) {    //if swipe right

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext()); //alert for confirm to delete
                builder.setMessage("Are you sure to delete?");    //set message

                builder.setPositiveButton("REMOVE", new DialogInterface.OnClickListener() { //when click on DELETE
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ca.notifyItemRemoved(position);    //item removed from recylcerview
                        //list.remove(position);  //then remove item
                        UserList.getInstance().remove(position);
                        al.remove(position);
                        rewrite();
                        return;
                    }
                }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {  //not removing items if cancel is done
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ca.notifyItemRemoved(position + 1);    //notifies the RecyclerView Adapter that data in adapter has been removed at a particular position.
                        ca.notifyItemRangeChanged(position, ca.getItemCount());   //notifies the RecyclerView Adapter that positions of element in adapter has been changed from position(removed element index to end of list), please update it.
                        rewrite();
                        return;
                    }
                }).show();  //show alert dialog
            }
            if(direction == ItemTouchHelper.LEFT){ //if swipe left
                UserList.getInstance().setActiveUserInPosition(position);
                ca.notifyDataSetChanged();
            }
        }
    };


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
