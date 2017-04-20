package itay.finci.org.allerwarn.user;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by itay on 17/01/17.
 */
public class UserList implements  java.io.Serializable{
    private static UserList ourInstance = new UserList();
    private ArrayList<User> alu;
    private User activeUser;
    private int activeUserPosition=-1;
    public static UserList getInstance() {
        return ourInstance;
    }

    private UserList(){
        alu = new ArrayList<User>();
    }
    public void add(String n, String ln, String p, String ep){
        alu.add(new User(n,ln,p,ep));
    }
    public void add(User u){
        add(u,false);
    }
    public void add(User u, boolean sa){
        User i = new User(u.getName(), u.getlName(), u.getPhone(), u.getePhone());
        alu.add(i);
        if(sa){
            setActiveUserInPosition(alu.indexOf(i));
        }
    }
    public void remove(int num){
        alu.remove(num);
    }
    public User getUser(int index){
        return alu.get(index);
    }
    public int size(){
        return alu.size();
    }
    public boolean isEmpty(){
        return alu.isEmpty();
    }
    public void write(ObjectOutputStream o){
        try {
            o.writeObject(alu);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void setActiveUserInPosition(int position){
        activeUser = alu.get(position);
        activeUserPosition = position;
    }
    public User getActiveUser(){
        return activeUser;
    }
    public int getActiveUserPosition(){
        return activeUserPosition;
    }
    public int getIndexOfUser(User u){
        return alu.indexOf(u);
    }
}
