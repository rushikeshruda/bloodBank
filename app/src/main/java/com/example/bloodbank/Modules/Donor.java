package com.example.bloodbank.Modules;

public class Donor {
    String username, email, password, bloodGroup, mobileNo, id,lastdonated;

    public Donor() {
    }

    public Donor(String id, String username, String email, String password, String bloodGroup, String mobileNo, String lastdonated) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.bloodGroup = bloodGroup;
        this.mobileNo = mobileNo;
        this.lastdonated = lastdonated;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLastdonated() {
        return lastdonated;
    }

    public void setLastdonated(String lastdonated) {
        this.lastdonated = lastdonated;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }
}
