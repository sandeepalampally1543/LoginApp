package com.example.admin.loginapp;

public class Users {

    public Users()
    {

    }
    public String name;
    public String image;
    public String thumb_nail;

    public Users(String name,String image,String thumb_nail)
    {
        this.name = name;
        this.image = image;
        this.thumb_nail = thumb_nail;
    }

    public String getThumb_nail() {
        return thumb_nail;
    }

    public void setThumb_nail(String thumb_nail) {
        this.thumb_nail = thumb_nail;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


}
