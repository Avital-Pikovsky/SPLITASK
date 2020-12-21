package Adapters;

public class Admin {

    public boolean admin = true;
    public UserProfile userDetails;

    public Admin(){}


    public String getUserName() {
        return userDetails.userName;
    }

    public void setUserName(String userName) {
        userDetails.userName = userName;
    }

    public String getUserEmail() {
        return userDetails.userEmail;
    }

    public void setUserEmail(String userEmail) {
        userDetails.userEmail = userEmail;
    }

    public String getUserPhone() {
        return userDetails.userPhone;
    }

    public void setUserPhone(String userPhone) {
        userDetails.userPhone = userPhone;
    }
}
