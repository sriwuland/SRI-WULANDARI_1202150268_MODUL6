package com.example.asus.sri_wulandari_1202150268_modul6;

/**
 * Created by Asus on 04/01/2018.
 */

public class databasePost {
    String image, judul, caption, user, key;

    public databasePost(){

    }
    public databasePost(String caption, String image, String judul, String user ){
        this.image = image;
        this.judul = judul;
        this.caption = caption;
        this.user = user;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getImage() {
        return image;
    }

    public String getJudul() {
        return judul;
    }

    public String getCaption() {
        return caption;
    }

    public String getUser() {
        return user;
    }

    public String getKey() {
        return key;
    }
}

}
