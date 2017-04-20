package itay.finci.org.allerwarn.fragments;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import itay.finci.org.allerwarn.R;
import itay.finci.org.allerwarn.allergies.AllergiesList;
import itay.finci.org.allerwarn.allergies.Allergy;
import itay.finci.org.allerwarn.allergies.AllergyAdapter;
import itay.finci.org.allerwarn.allergies.AllergyInfo;
import itay.finci.org.allerwarn.user.User;
import itay.finci.org.allerwarn.user.UserList;


public class AddAlergyFragment extends Fragment {
    AllergyAdapter ca;
    List<AllergyInfo> al;
    User active;
    AllergiesList allergiesList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_alergy, container, false);
        RecyclerView recList = (RecyclerView) v.findViewById(R.id.cardList2);
        allergiesList = AllergiesList.getInstance(getActivity().getApplicationContext());

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        al =createList(8);
        ca= new AllergyAdapter(al, getContext());
        recList.setAdapter(ca);
        ca.notifyDataSetChanged();

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recList); //set swipe to recylcerview

        active = UserList.getInstance().getActiveUser();

        return v;
    }

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

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }
        @Override
        public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
            final int position = viewHolder.getAdapterPosition(); //get position which is swipe

            if (direction == ItemTouchHelper.RIGHT) {    //if swipe right
                Allergy a = allergiesList.getAller(position);
                for (int i = 0; i < active.size(); i++) {
                    Allergy as = active.getAlergy(i);
                    if(as.getId() == a.getId()){
                        active.remAllergy(i);
                        rewrite();
                        ca.notifyDataSetChanged();
                        return;
                    }
                }
                ca.notifyDataSetChanged();
            }
            if(direction == ItemTouchHelper.LEFT){ //if swipe left
                Allergy a = allergiesList.getAller(position);
                System.out.println("alergy Id= " + a.getId());
                for (int i = 0; i < active.size(); i++) {
                    Allergy as = active.getAlergy(i);
                    System.out.println("user's alergy = "+as.getId());
                    if(as.getId() == a.getId()){
                        ca.notifyDataSetChanged();
                        return;
                    }
                }
                active.addAlergy(new Allergy(a));
                rewrite();
                ca.notifyDataSetChanged();
            }
        }
    };

    private List<AllergyInfo> createList(int size) {

        List<AllergyInfo> result = new ArrayList<AllergyInfo>();
        for (int i=0; i < size; i++) {
            AllergyInfo ci = new AllergyInfo();
            Allergy u = allergiesList.getAller(i);
            ci.name = u.getName();
            ci.lName = " ";
            ci.phone = " ";
            ci.ePhone = " ";
            ci.id = u.getId();
            result.add(ci);
        }

        return result;
    }

}
