package itay.finci.org.allerwarn.user;

import android.content.Context;

import java.io.IOException;
import java.io.ObjectOutputStream;

import itay.finci.org.allerwarn.allergies.AllergiesList;
import itay.finci.org.allerwarn.allergies.Allergy;

/**
 * <pre>
 * Created by itay on 17/01/17.
 *  include the user ArrayList and all the add functions
 * </pre>
 */
public class UserList implements  java.io.Serializable{
    private static UserList ourInstance = new UserList();
    private User activeUser;
    public static UserList getInstance() {
        return ourInstance;
    }

    /**
     * private constructor
     */
    private UserList(){
    }

    public void setActiveUser(User u) {
        activeUser = u;
    }

    /**
     * <pre>
     *     make new user from nfc record string.
     *     split the string where there is comma
     *     make new user from that data structure:
     *     <table>
     *         <tu>
     *             <th>part number</th>
     *             <th>part data</th>
     *         </tu>
     *         <tu>
     *             <th>0</th>
     *             <th>name</th>
     *         </tu>
     *         <tu>
     *             <th>1</th>
     *             <th>last name</th>
     *         </tu>
     *         <tu>
     *             <th>2</th>
     *             <th>phone</th>
     *         </tu>
     *         <tu>
     *             <th>3</th>
     *             <th>emergency phone</th>
     *         </tu>
     *         <tu>
     *             <th>4 until end</th>
     *             <th>allergies</th>
     *         </tu>
     *     </table>
     *     last part is null so when you adding allergy you need to check if the allergy is null
     * </pre>
     * @param user the ndef string record
     * @param cxt app context
     */
    public void add(String user, Context cxt){
        int index=0;
        byte[] b= user.getBytes();
        String[] parts = user.split(",");
        String name=parts[0];
        String lname=parts[1];
        User u = new User(name, lname);
        AllergiesList al= AllergiesList.getInstance(cxt);
        for (int i = 2; i < parts.length; i++) {
            Allergy a = al.getAller(parts[i]);
            if(null != a){
                u.addAlergy(a);
            }
        }
        activeUser = u;
    }
    /**
     * write the User ArrayList into
     * @param o ObjectOutputStream
     */
    public void write(ObjectOutputStream o){
        try {
            o.writeObject(activeUser);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * get the Active user
     * @return the active user object
     */
    public User getActiveUser(){
        return activeUser;
    }
}
