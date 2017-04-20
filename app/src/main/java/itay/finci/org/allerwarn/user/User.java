package itay.finci.org.allerwarn.user;

import java.util.ArrayList;

import itay.finci.org.allerwarn.allergies.Allergy;

/**
 * Created by itay on 17/01/17.
 */
public class User implements java.io.Serializable {
    private  String name, lName,phone,ePhone;
    private ArrayList<Allergy>ala;

    public User(String n, String ln, String p , String ep) {
        name =n;
        lName =ln;
        phone =p;
        ePhone =ep;
        ala = new ArrayList<Allergy>();
    }

    public User(User u){
        name =u.getName();
        lName =u.getlName();
        phone =u.getPhone();
        ePhone =u.getePhone();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getePhone() {
        return ePhone;
    }

    public void setePhone(String ePhone) {
        this.ePhone = ePhone;
    }

    public void addAlergy(Allergy a){
        ala.add(new Allergy(a));
    }

    public Allergy getAlergy(int i){
        return ala.get(i);
    }

    public int size(){
        return ala.size();
    }

    public void remAllergy(int position){
        ala.remove(position);
    }
}
