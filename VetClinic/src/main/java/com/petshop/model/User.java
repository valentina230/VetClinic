package com.petshop.model;

public class User {
    private String name;
    private String lastName;
    private String email;
    private String phone;
    private String identification;
    private String address;

    

    public User(String name, String lastName, String email, String phone, String identification, String address) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.identification = identification;
        this.address = address;
    }

    

    public User() {
    }



    // Getters y setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastName;
    }

    public void setLastname(String lastname) {
        this.lastName = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    
}

