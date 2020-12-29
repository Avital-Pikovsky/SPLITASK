package Adapters;

public class UserProfile {

    public String userName;
    public  String userEmail;
    public String userPhone;
    public String isAdmin;

    public UserProfile(String userName, String userEmail, String userPhone){
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPhone = userPhone;
        this.isAdmin = "false";
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

    public String getIsAdmin(){return isAdmin;}

    public void setIsAdmin(String isAdmin){this.isAdmin = isAdmin;}

}