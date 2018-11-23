package com.example.admin.loginapp;

/**
 * Created by Admin on 10/31/2018.
 */

public class UserInfo {

    public String name;
    public String address;
    public String age;

    public UserInfo()
    {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public UserInfo(String name, String address, String age)

    {
        this.name = name;
        this.address = address;
        this.age = age;
    }
}
