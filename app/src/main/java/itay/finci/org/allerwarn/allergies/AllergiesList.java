package itay.finci.org.allerwarn.allergies;

import android.content.Context;

import java.util.ArrayList;

import itay.finci.org.allerwarn.R;

/**
 * <pre>
 * Created by itay on 19/02/17.
 * this class includ the allergies arraylist
 * the allergies id's and name table:
 * </pre>
 * <table >
 *  <tr>
 *      <th>Allergy id</th>
 *      <th>Allergy name</th>
 *  </tr>
 *  <tr>
 *      <th>1</th>
 *      <th>Milk</th>
 *  </tr>
 *  <tr>
 *      <th>2</th>
 *      <th>Egg</th>
 *  </tr>
 *  <tr>
 *      <th>3</th>
 *      <th>Peanuts</th>
 *  </tr>
 *  <tr>
 *      <th>4</th>
 *      <th>Tree nuts</th>
 *  </tr>
 *  <tr>
 *      <th>5</th>
 *      <th>Fish</th>
 *  </tr>
 *  <tr>
 *      <th>6</th>
 *      <th>Shellfish</th>
 *  </tr>
 *  <tr>
 *      <th>7</th>
 *      <th>Wheat</th>
 *  </tr>
 *  <tr>
 *      <th>8</th>
 *      <th>Soy</th>
 *  </tr>
 *  </table>

 */
public class AllergiesList {
    private static AllergiesList ourInstance = null;
    private ArrayList<Allergy> ala;
    private Context ctx;

    /**
     * public constructor
     * @param ctxx the context of the app to pass to the private constructor and get the string name of the allergies
     * @return AllergiesList object
     */
    public static AllergiesList getInstance(Context ctxx) {
        if (ourInstance == null) {
            ourInstance = new AllergiesList(ctxx);
        }
        else {
            ourInstance.ctx = ctxx;
        }
        return ourInstance;
    }

    /**
     * make the arraylist of the allergies
     * <table style="width:100%">
     *  <tr>
     *      <th>Allergy id</th>
     *      <th>Allergy name</th>
     *  </tr>
     *  <tr>
     *      <th>1</th>
     *      <th>Milk</th>
     *  </tr>
     *  <tr>
     *      <th>2</th>
     *      <th>Egg</th>
     *  </tr>
     *  <tr>
     *      <th>3</th>
     *      <th>Peanuts</th>
     *  </tr>
     *  <tr>
     *      <th>4</th>
     *      <th>Tree nuts</th>
     *  </tr>
     *  <tr>
     *      <th>5</th>
     *      <th>Fish</th>
     *  </tr>
     *  <tr>
     *      <th>6</th>
     *      <th>Shellfish</th>
     *  </tr>
     *  <tr>
     *      <th>7</th>
     *      <th>Wheat</th>
     *  </tr>
     *  <tr>
     *      <th>8</th>
     *      <th>Soy</th>
     *  </tr>
     *  </table>
     * @param ctx the context of the app to get the string name to show the allergy
     */
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

    /**
     * @return the allergy arraylist
     */
    public ArrayList<Allergy> getAla() {
        return ala;
    }

    /**
     * @return the size of the allergy arrayList
     */
    public int size(){
        return this.ala.size();
    }

    public Allergy getAller(int id){
        return ala.get(id);
    }

    /**
     * <pre>
     * get a id number or allergy name and output the allergy object
     * if the param id dose'nt mach to any allergy it's output null
     * </pre>
     * @param id string that contain number (between 1-8) or name of allergy
     * @return Aleergy object or null
     */
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
