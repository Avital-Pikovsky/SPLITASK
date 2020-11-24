package Adapters;

import java.util.ArrayList;
import java.util.HashMap;

public class UserProfile {

    public String userName;
    public  String userEmail;
    public String userPhone;
    public HashMap<String, ListAdapter> listMap;

    public UserProfile(String userName, String userEmail, String userPhone){
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPhone = userPhone;
        listMap = null;
    }

    public UserProfile(){

    }

    public void addToMap(String name, ListAdapter newList){
    listMap.put(name,newList);

    //for future, what if there is already list with that name?
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public HashMap<String, ListAdapter> getLists() { return listMap; }

    public void setLists(HashMap<String, ListAdapter> newMap){ this.listMap = newMap; }
}