package com.example.note.PassWord;

import io.realm.RealmObject;

public class UserAndPass extends RealmObject {
    private String user;
    private String pass;

    public UserAndPass(String user, String pass) {
        this.user = user;
        this.pass = pass;
    }


    public UserAndPass() {
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void coppyFromUserAndPassToRealm(UserAndPass userAndPass){
        this.setUser(userAndPass.getUser());
        this.setPass(userAndPass.getPass());
    }
}
