package itay.finci.org.allerwarn.user;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Locale;

import itay.finci.org.allerwarn.allergies.Allergy;

/**
 * <pre>
 * Created by itay on 17/01/17.
 * User Object
 * </pre>
 */
public class User implements java.io.Serializable {
    private  String name, lName,phone,ePhone;
    private ArrayList<Allergy>ala;

    /**
     * public constructor to make new user from data(Strings)
     * @param n user name
     * @param ln user last name
     * @param p user phone
     * @param ep user emergency phone
     */
    public User(String n, String ln, String p , String ep) {
        name =n;
        lName =ln;
        phone =p;
        ePhone =ep;
        ala = new ArrayList<Allergy>();
    }

    /**
     * public constructor to make new user from existing user
     * @param u the old user
     */
    public User(User u){
        name =u.getName();
        lName =u.getlName();
        phone =u.getPhone();
        ePhone =u.getePhone();
    }

    /**
     * get user name
     * @return user name
     */
    public String getName() {
        return name;
    }

    /**
     * set user name
     * @param name new user name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * get user last name
     * @return user last name
     */
    public String getlName() {
        return lName;
    }

    /**
     * set user last name
     * @param lName new user last name
     */
    public void setlName(String lName) {
        this.lName = lName;
    }

    /**
     * get user phone
     * @return user phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * set user phone
     * @param phone new user phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * get user emergency phone
     * @return user emergency phone
     */
    public String getePhone() {
        return ePhone;
    }

    /**
     * set user emergency phone
     * @param ePhone new user emergency phone
     */
    public void setePhone(String ePhone) {
        this.ePhone = ePhone;
    }

    /**
     * add allergy to user
     * @param a allergy object
     */
    public void addAlergy(Allergy a){
        ala.add(new Allergy(a));
    }

    /**
     * get user allergy in index
     * @param i index
     * @return Allergy object
     */
    public Allergy getAlergy(int i){
        return ala.get(i);
    }

    /**
     * get the size of the allergy ArrayList of that user
     * @return int size of that user
     */
    public int size(){
        return ala.size();
    }

    /**
     * delete user allergy
     * @param position the index in the ArrayList of the allergy
     */
    public void remAllergy(int position){
        ala.remove(position);
    }

    /**
     * <pre>
     *  get byte code to write nfc tag.
     *  the structure of the end massages is name . lname , phone , ephone , firstAllergy, secondAllergy, .... ,
     *  between each field there is comma.
     *  the last field is null
     * </pre>
     * @return byte[]
     */
    public byte[] getByteCode(){
        try {
            byte[] lang = new byte[0];
            lang = Locale.getDefault().getLanguage().getBytes("UTF-8");
            byte[] fn = (getName()+',').getBytes("UTF-8"); // Name in UTF-8
            byte[] ln = (getlName()+',').getBytes("UTF-8");
            byte[] ph1 = (getPhone()+',').getBytes("UTF-8");
            byte[] ph2 = (getePhone()+',').getBytes("UTF-8");
            String allergies="";
            for (int i = 0; i < size(); i++) {
                allergies= allergies+getAlergy(i).getId()+",";
            }
            byte[] allergiesbyte= allergies.getBytes("UTF-8");
            int langSize = lang.length;
            int textLength = fn.length + ln.length + ph1.length + ph2.length + allergiesbyte.length;
            ByteArrayOutputStream payload = new ByteArrayOutputStream(1 + langSize + textLength);
            payload.write((byte) (langSize & 0x1F));
            payload.write(lang, 0, langSize);
            //payload.write((byte) (fn.length & 0x1F));
            payload.write(fn, 0, fn.length);
           // payload.write((byte) (ln.length & 0x1F));
            payload.write(ln,0,ln.length);
            //payload.write((byte) (ph1.length & 0x1F));
            payload.write(ph1,0,ph1.length);
            //payload.write((byte) (ph2.length & 0x1F));
            payload.write(ph2,0,ph2.length);
            payload.write(allergiesbyte,0,allergiesbyte.length);
            return  payload.toByteArray();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
