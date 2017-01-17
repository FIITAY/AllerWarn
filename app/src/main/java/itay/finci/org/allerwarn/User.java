package itay.finci.org.allerwarn;

/**
 * Created by itay on 17/01/17.
 */
public class User {
    private  String name, lName,phone,ePhone;

    public User(String n, String ln, String p , String ep){
        name =n;
        lName =ln;
        phone =p;
        ePhone =ep;
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
}
