package com.riossoftware.myrealstate;

import androidx.annotation.NonNull;

import java.util.List;

public class UserPojo {

    List propiedades;
    String name,email,token;


    public List getPropiedades() {
        return propiedades;
    }

    public void setPropiedades(List propiedades) {
        this.propiedades = propiedades;
    }

    public String getName(){ return name;}

    public void setName(String name){
        this.name=name;
    }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getToken() { return token;}

    public void setToken(String token) { this.token = token; }

    @Override
    public String toString() {
        return "name: "+name+
                "\nemail: "+email+
                "\ntoken: "+token;
    }
}
