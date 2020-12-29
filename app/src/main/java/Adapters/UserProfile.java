package Adapters;

import java.util.ArrayList;
import java.util.HashMap;

public class UserProfile {

    public String userName;
    public  String userEmail;
    public String userPhone;
    public Boolean isAdmin;

    public UserProfile(String userName, String userEmail, String userPhone){
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPhone = userPhone;
        this.isAdmin = false;
//        listOfLA = null;
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

    public Boolean getIsAdmin(){return isAdmin;}

    public void setIsAdmin(Boolean isAdmin){this.isAdmin = isAdmin;}

}