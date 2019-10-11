package com.example.bloodbank.Modules;

public class BloodBank {

    String bloodGroup,id;
    int amount;

    public  BloodBank(){}

    public BloodBank(String id,String bloodGroup, int amount) {
        this.bloodGroup = bloodGroup;
        this.amount = amount;
        this.id=id;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
