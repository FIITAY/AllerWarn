package itay.finci.org.allerwarn.user;

import android.content.Context;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

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
    private ArrayList<User> alu;
    private User activeUser;
    private int activeUserPosition=-1;
    public static UserList getInstance() {
        return ourInstance;
    }

    /**
     * private constructor
     */
    private UserList(){
        alu = new ArrayList<User>();
    }

    /**
     * make new user from raw data
     * @param n user name
     * @param ln user last name
     */
    public void add(String n, String ln) {
        alu.add(new User(n, ln));
    }

    /**
     * make new user from old user
     * @param u old user
     */
    public void add(User u){
        add(u, true);
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
        alu.add(u);
        setActiveUserInPosition(alu.indexOf(u));
    }

    /**
     * <pre>
     *     make new user from old user.
     *     if the boolean is true making the new user active user.
     * </pre>
     * @param u old user.
     * @param sa boolean to check if you want the new user active.
     */
    public void add(User u, boolean sa){
        User i = new User(u.getName(), u.getlName());
        alu.add(i);
        if(sa){
            setActiveUserInPosition(alu.indexOf(i));
        }
    }

    /**
     * remove user in index
     * @param num index
     */
    public void remove(int num){
        alu.remove(num);
    }

    /**
     * get user in index
     * @param index int index
     * @return user
     */
    public User getUser(int index){
        return alu.get(index);
    }

    /**
     * size of the User ArrayList
     * @return ArrayList.size()
     */
    public int size(){
        return alu.size();
    }

    /**
     * return if the User ArrayList is empty
     * @return
     */
    public boolean isEmpty(){
        return alu.isEmpty();
    }

    /**
     * write the User ArrayList intu o
     * @param o ObjectOutputStream
     */
    public void write(ObjectOutputStream o){
        try {
            o.writeObject(alu);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * set the active user in position
     * @param position the index of the new active user
     */
    public void setActiveUserInPosition(int position){
        activeUser = alu.get(position);
        activeUserPosition = position;
    }

    /**
     * get the Active user
     * @return the active user object
     */
    public User getActiveUser(){
        return activeUser;
    }

    /**
     * get the index of the active user
     * @return int index
     */
    public int getActiveUserPosition(){
        return activeUserPosition;
    }

    /**
     * <pre>
     * get the index of given user
     * error is if you make user with same details but its not pointing on the same memory space.
     * </pre>
     * @param u given user
     * @return int index
     */
    public int getIndexOfUser(User u){
        return alu.indexOf(u);
    }

    public ArrayList<User> getAlu() {
        return alu;
    }

    public User getUser(String name) {
        for (User u : alu) {
            if (u.getName() == name) {
                return u;
            }
        }
        return null;
    }
}
