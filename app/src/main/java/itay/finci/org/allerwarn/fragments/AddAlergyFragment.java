package itay.finci.org.allerwarn.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import java.util.ArrayList;

import itay.finci.org.allerwarn.MainActivity;
import itay.finci.org.allerwarn.R;
import itay.finci.org.allerwarn.allergies.AllergiesList;
import itay.finci.org.allerwarn.allergies.Allergy;
import itay.finci.org.allerwarn.user.User;
import itay.finci.org.allerwarn.user.UserList;

/**
 * <pre>
 * Created by itay on 19/02/17.
 * Fragment that shows a list of Allergy object that you can add to your user,
 * swipe to the left for add allergy,
 * swipe to the right for removing allergy
 * </pre>
 */
public class AddAlergyFragment extends Fragment {
    User active;
    AllergiesList allergiesList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_alergy, container, false);
        active = UserList.getInstance().getActiveUser();

        switchSetup(v);

        return v;
    }

    private void switchSetup(View v) {

        boolean[] hasAllergy = new boolean[8];
        ArrayList<Allergy> ala = active.getAla();
        for (Allergy a : ala) {
            for (int i = 0; i < AllergiesList.getInstance(getContext()).getAla().size(); i++) {

                if (a.getId() == AllergiesList.getInstance(getContext()).getAller(i).getId()) {
                    hasAllergy[i] = true;
                }
            }
        }

        Switch shMilk = (Switch) v.findViewById(R.id.shMilk);
        if (hasAllergy[AllergiesList.getInstance(getContext()).getAller("Milk").getId() - 1]) {
            shMilk.setChecked(true);
        } else {
            shMilk.setChecked(false);
        }
        shMilk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    active.addAlergy(AllergiesList.getInstance(getContext()).getAller("Milk"));
                    rewrite();
                } else {
                    active.remAllergy(AllergiesList.getInstance(getContext()).getAller("Milk"));
                    rewrite();
                }
            }
        });
        Switch shEgg = (Switch) v.findViewById(R.id.shEgg);
        if (hasAllergy[AllergiesList.getInstance(getContext()).getAller("Egg").getId() - 1]) {
            shEgg.setChecked(true);
        } else {
            shEgg.setChecked(false);
        }
        shEgg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    active.addAlergy(AllergiesList.getInstance(getContext()).getAller("Egg"));
                    rewrite();
                } else {
                    active.remAllergy(AllergiesList.getInstance(getContext()).getAller("Egg"));
                    rewrite();
                }
            }
        });

        Switch shPeanuts = (Switch) v.findViewById(R.id.shPeanuts);
        if (hasAllergy[AllergiesList.getInstance(getContext()).getAller("Peanuts").getId() - 1]) {
            shPeanuts.setChecked(true);
        } else {
            shPeanuts.setChecked(false);
        }
        shPeanuts.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    active.addAlergy(AllergiesList.getInstance(getContext()).getAller("Peanuts"));
                    rewrite();
                } else {
                    active.remAllergy(AllergiesList.getInstance(getContext()).getAller("Peanuts"));
                    rewrite();
                }
            }
        });

        Switch shTreeNuts = (Switch) v.findViewById(R.id.shTreeNuts);
        if (hasAllergy[AllergiesList.getInstance(getContext()).getAller("Tree nuts").getId() - 1]) {
            shTreeNuts.setChecked(true);
        } else {
            shTreeNuts.setChecked(false);
        }
        shTreeNuts.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    active.addAlergy(AllergiesList.getInstance(getContext()).getAller("Tree nuts"));
                    rewrite();
                } else {
                    active.remAllergy(AllergiesList.getInstance(getContext()).getAller("Tree nuts"));
                    rewrite();
                }
            }
        });

        Switch shFish = (Switch) v.findViewById(R.id.shFish);
        if (hasAllergy[AllergiesList.getInstance(getContext()).getAller("Fish").getId() - 1]) {
            shFish.setChecked(true);
        } else {
            shFish.setChecked(false);
        }
        shFish.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    active.addAlergy(AllergiesList.getInstance(getContext()).getAller("Fish"));
                    rewrite();
                } else {
                    active.remAllergy(AllergiesList.getInstance(getContext()).getAller("Fish"));
                    rewrite();
                }
            }
        });

        Switch shShellFish = (Switch) v.findViewById(R.id.shShellFish);
        if (hasAllergy[AllergiesList.getInstance(getContext()).getAller("Shellfish").getId() - 1]) {
            shShellFish.setChecked(true);
        } else {
            shShellFish.setChecked(false);
        }
        shShellFish.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    active.addAlergy(AllergiesList.getInstance(getContext()).getAller("Shellfish"));
                    rewrite();
                } else {
                    active.remAllergy(AllergiesList.getInstance(getContext()).getAller("Shellfish"));
                    rewrite();
                }
            }
        });


        Switch shWheat = (Switch) v.findViewById(R.id.shWheat);
        if (hasAllergy[AllergiesList.getInstance(getContext()).getAller("Wheat").getId() - 1]) {
            shWheat.setChecked(true);
        } else {
            shWheat.setChecked(false);
        }
        shWheat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    active.addAlergy(AllergiesList.getInstance(getContext()).getAller("Wheat"));
                    rewrite();
                } else {
                    active.remAllergy(AllergiesList.getInstance(getContext()).getAller("Wheat"));
                    rewrite();
                }
            }
        });

        Switch shSoy = (Switch) v.findViewById(R.id.shSoy);
        if (hasAllergy[AllergiesList.getInstance(getContext()).getAller("Soy").getId() - 1]) {
            shSoy.setChecked(true);
        } else {
            shSoy.setChecked(false);
        }
        shSoy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    active.addAlergy(AllergiesList.getInstance(getContext()).getAller("Soy"));
                    rewrite();
                } else {
                    active.remAllergy(AllergiesList.getInstance(getContext()).getAller("Soy"));
                    rewrite();
                }
            }
        });

    }

    /**
     * use to write to the user file after you change something
     */
    private void rewrite() {
        MainActivity.rewrite();
    }
}
