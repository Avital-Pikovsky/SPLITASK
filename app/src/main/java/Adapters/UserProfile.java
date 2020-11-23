package Adapters;

import java.util.ArrayList;

public class UserProfile {

    public String userName;
    public  String userEmail;
    public String userPhone;
    public ArrayList<ArrayList<String>> lists;

    public UserProfile(String userName, String userEmail, String userPhone){
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPhone = userPhone;
        lists = null;
    }

    public UserProfile(){

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

    public ArrayList<ArrayList<String>> getLists() { return lists; }

    public void setLists(ArrayList<ArrayList<String>> newList){ this.lists = newList; }
}