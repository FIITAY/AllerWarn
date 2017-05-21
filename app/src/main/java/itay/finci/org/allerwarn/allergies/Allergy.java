package itay.finci.org.allerwarn.allergies;

/**
 * <pre>
 * Created by itay on 24/02/17.
 * Allergy object class
 * each Allergy contain: id, name, display name
 * </pre>
 */

public class Allergy {
    private int id;
    private String name;
    private String displayName;

    /**
     * <pre>
     * public constructor
     * make new Allergy with the parms, not from old Allergy
     * </pre>
     * @param id the id # of the allergy
     * @param name the english name for in code use
     * @param displayName the name of the allergy as displayed in the app
     */
    public Allergy(int id, String name, String displayName){
        this.id = id;
        this.name = new String(name);
        this.displayName = new String(displayName);
    }

    /**
     * <pre>
     * public constructor
     * make new allergy object from old allergy object
     * </pre>
     * @param a old allergy object
     */
    public Allergy(Allergy a){
        this.id = a.getId();
        this.name = a.getName();
        this.displayName=a.getDisplayName();
    }

    /**
     * get the id # of the allergy
     * @return id # of the allergy
     */
    public int getId() {
        return id;
    }

    /**
     * set the id # of the allergy
     * @param id the new id # for the allergy
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * get the name of the allergy for in code use.
     * @return the name english name of the allergy for in code use
     */
    public String getName() {
        return name;
    }

    /**
     * set the english name for in code use of the allergy
     * @param name the new english name of the allergy for in code use
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * get the Display name of the allergy for showing the allergy in the user side language
     * @return Display name of the allergy at the user side language
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * set the display name of the allergy for showing the allergy in the user side language
     * @param displayName the new Display name of the allergy at the user side language
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
