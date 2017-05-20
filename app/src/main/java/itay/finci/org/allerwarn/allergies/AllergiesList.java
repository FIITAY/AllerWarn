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

    public int size(){
        return this.ala.size();
    }

    public Allergy getAller(int id){
        return ala.get(id);
    }

    public Allergy getAller(String id){
        switch (id){
            case "1":
                return ala.get(0);
                //break;
            case "2":
                return ala.get(1);
               // break;
            case "3":
                return ala.get(2);
              //  break;
            case "4":
                return ala.get(3);
             //   break;
            case "5":
                return ala.get(4);
              //  break;
            case "6":
                return ala.get(5);
              //  break;
            case "7":
                return ala.get(6);
              //  break;
            case "8":
                return ala.get(7);
              //  break;
            case "Milk":
                return ala.get(0);
             //   break;
            case "Egg":
                return ala.get(1);
              //  break;
            case "Peanuts":
                return ala.get(2);
             //   break;
            case "Tree nuts":
                return ala.get(3);
              //  break;
            case "Fish":
                return ala.get(4);
              //  break;
            case "Shellfish":
                return ala.get(5);
              //  break;
            case "Wheat":
                return ala.get(6);
             //   break;
            case "Soy":
                return ala.get(7);
             //   break;
        }
        return null;
    }

}
