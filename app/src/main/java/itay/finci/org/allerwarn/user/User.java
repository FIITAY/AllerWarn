package itay.finci.org.allerwarn.user;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Locale;

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

    public byte[] getByteCode(){
        try {
            byte[] lang = new byte[0];
            lang = Locale.getDefault().getLanguage().getBytes("UTF-8");
            byte[] fn = (getName()+',').getBytes("UTF-8"); // Name in UTF-8
            byte[] ln = (getlName()+',').getBytes("UTF-8");
            byte[] ph1 = (getPhone()+',').getBytes("UTF-8");
            byte[] ph2 = (getePhone()+',').getBytes("UTF-8");


            int langSize = lang.length;
            int textLength = fn.length + ln.length + ph1.length + ph2.length;
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
            return  payload.toByteArray();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
