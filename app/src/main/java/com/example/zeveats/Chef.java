package com.example.zeveats;

public class Chef {
    private String Area, City, ConfirmPassword, EmailId, FirstName, House, LastName, MobileNo, Password, Pincode, State, Role;

    // Default constructor required for calls to DataSnapshot.getValue(Chef.class)
    public Chef() {
    }

    public Chef(String Area, String City, String ConfirmPassword, String EmailId, String FirstName, String House, String LastName, String MobileNo, String Password, String Pincode, String State, String Role) {
        this.Area = Area;
        this.City = City;
        this.ConfirmPassword = ConfirmPassword;
        this.EmailId = EmailId;
        this.FirstName = FirstName;
        this.House = House;
        this.LastName = LastName;
        this.MobileNo = MobileNo;
        this.Password = Password;
        this.Pincode = Pincode;
        this.State = State;
        this.Role = Role;
    }

    public String getArea() {
        return Area;
    }

    public void setArea(String area) {
        Area = area;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getConfirmPassword() {
        return ConfirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        ConfirmPassword = confirmPassword;
    }

    public String getEmailId() {
        return EmailId;
    }

    public void setEmailId(String emailId) {
        EmailId = emailId;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getHouse() {
        return House;
    }

    public void setHouse(String house) {
        House = house;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPincode() {
        return Pincode;
    }

    public void setPincode(String pincode) {
        Pincode = pincode;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }
}
