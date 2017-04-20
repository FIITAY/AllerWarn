package itay.finci.org.allerwarn.allergies;

import android.content.Context;

import java.util.ArrayList;

import itay.finci.org.allerwarn.R;

/**
 * Created by itay on 19/02/17.
 */
public class AllergiesList {
    private static AllergiesList ourInstance = null;

    private ArrayList<Allergy> ala;
    private Context ctx;

    public static AllergiesList getInstance(Context ctxx) {
        if (ourInstance == null) {
            ourInstance = new AllergiesList(ctxx);
        }
        else {
            ourInstance.ctx = ctxx;
        }
        return ourInstance;
    }

    private AllergiesList(Context ctx) {
        this.ctx = ctx;
        ala= new ArrayList<Allergy>();
        ala.add(new Allergy(1,"Milk",ctx.getString(R.string.Milk)));
        ala.add(new Allergy(2,"Egg",ctx.getString(R.string.Egg)));
        ala.add(new Allergy(3,"Peanuts",ctx.getString(R.string.Peanuts)));
        ala.add(new Allergy(4,"Tree nuts",ctx.getString(R.string.Tree_nuts)));
        ala.add(new Allergy(5,"Fish",ctx.getString(R.string.Fish)));
        ala.add(new Allergy(6,"Shellfish",ctx.getString(R.string.Shellfish)));
        ala.add(new Allergy(7,"Wheat",ctx.getString(R.string.Wheat)));
        ala.add(new Allergy(8,"Soy",ctx.getString(R.string.Soy)));
    }

    public ArrayList<Allergy> getAla() {
        return ala;
    }

    public void setAla(ArrayList<Allergy> ala) {
        this.ala = ala;
    }

    public int size(){
        return this.ala.size();
    }

    public Allergy getAller(int id){
        return ala.get(id);
    }

}
