package itay.finci.org.allerwarn;

import java.util.ArrayList;

/**
 * Created by itay on 17/01/17.
 */
public class UserList {
    private static UserList ourInstance = new UserList();
    private ArrayList<User> alu;
    public static UserList getInstance() {
        return ourInstance;
    }

    private UserList(){
        alu = new ArrayList<User>();
    }

    public void add(String n, String ln, String p, String ep){
        alu.add(new User(n,ln,p,ep));
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
}
