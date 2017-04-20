package itay.finci.org.allerwarn.allergies;

/**
 * Created by itay on 24/02/17.
 */

public class Allergy {
    private int id;
    private String name;
    private String displayName;

    public Allergy(int id, String name, String displayName){
        this.id = id;
        this.name = new String(name);
        this.displayName = new String(displayName);
    }
    public Allergy(Allergy a){
        this.id = a.getId();
        this.name = a.getName();
        this.displayName=a.getDisplayName();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
