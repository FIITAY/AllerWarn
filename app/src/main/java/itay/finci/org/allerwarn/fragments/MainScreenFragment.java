package itay.finci.org.allerwarn.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import itay.finci.org.allerwarn.R;
import itay.finci.org.allerwarn.user.User;
import itay.finci.org.allerwarn.user.UserAdapter;
import itay.finci.org.allerwarn.user.UserInfo;
import itay.finci.org.allerwarn.user.UserList;

/**
 * the main fragment that shopw the list of user's
 */
public class MainScreenFragment extends Fragment {
    public UserAdapter ca;
    List<UserInfo> al;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main_screen, container, false);

        RecyclerView recList = (RecyclerView) v.findViewById(R.id.cardList);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        al =createList(UserList.getInstance().size());
        ca= new UserAdapter(al, getContext());
        recList.setAdapter(ca);
        ca.notifyDataSetChanged();

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recList); //set swipe to recylcerview

        rewrite();
        return v;
    }

    /**
     * writes the userlist to the userlist file
     */
    private void rewrite(){
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

    /**
     * listiner to movement of objects
     */
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        /**
         * the swipe right\left listiner
         * @param viewHolder the RecyclerView
         * @param direction direction of swipe
         */
        @Override
        public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
            final int position = viewHolder.getAdapterPosition(); //get position which is swipe
            /**
             * if swiped right = delete user
             */
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
                        if(UserList.getInstance().size() >0) {
                            UserList.getInstance().setActiveUserInPosition(0);
                            ca.notifyDataSetChanged();
                        }else{
                            NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
                            Menu nav_Menu = navigationView.getMenu();
                            nav_Menu.findItem(R.id.nav_EditUser).setVisible(false);
                            nav_Menu.findItem(R.id.nav_addAler).setVisible(false);
                            nav_Menu.findItem(R.id.nav_nfcWrite).setVisible(false);
                        }
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
            /**
             * if swiped left = make user active
             */
            if(direction == ItemTouchHelper.LEFT){ //if swipe left
                UserList.getInstance().setActiveUserInPosition(position);
                ca.notifyDataSetChanged();
            }
        }
    };

    /**+
     * the UserAdapter use this function to make the data more compatable to hime
     * @param size the amount of user's
     * @return the List that the Adapter use
     */
    private List<UserInfo> createList(int size) {

        List<UserInfo> result = new ArrayList<UserInfo>();
        for (int i=0; i < size; i++) {
            UserInfo ci = new UserInfo();
            User u = UserList.getInstance().getUser(i);
            ci.name = u.getName();
            ci.lName = u.getlName();
            ci.phone = u.getPhone();
            ci.ePhone = u.getePhone();
            result.add(ci);

        }

        return result;
    }
    public void refresh(){
        ca.notifyDataSetChanged();
    }
}
