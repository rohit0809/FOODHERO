package com.example.asus.foodhero;

import android.widget.EditText;

/**
 * Created by Saurav on 4/28/2017.
 */

public class Donor {
    String  name;
    String  contact;
    String  email;
    String  password;
    String donorid;
    public String getDonorid() {
        return donorid;
    }

    public void setDonorid(String donorid) {
        this.donorid = donorid;
    }



    public String getName() {
        return name.toString();
    }

    public void setName(EditText name) {
        this.name = name.getText().toString();
    }

    public String getContact() {
        return contact.toString();
    }

    public void setContact(EditText contact) {
        this.contact = contact.getText().toString();
    }

    public String getEmail() {
        return email.toString();
    }

    public void setEmail(EditText email) {
        this.email = email.getText().toString();
    }

    public String getPassword() {
        return password.toString();
    }

    public void setPassword(EditText password) {
        this.password = password.getText().toString();
    }



}
