package com.example.asus.foodhero;

import android.widget.EditText;

/**
 * Created by Saurav on 4/28/2017.
 */

public class Donor {
    String  e1;
    String  e2;
    String  e3;
    String  e4;
    String  e5;

    public String getE1() {
        return e1.toString();
    }

    public void setE1(EditText e1) {
        this.e1 = e1.getText().toString();
    }

    public String getE2() {
        return e2.toString();
    }

    public void setE2(EditText e2) {
        this.e2 = e2.getText().toString();
    }

    public String getE3() {
        return e3.toString();
    }

    public void setE3(EditText e3) {
        this.e3 = e3.getText().toString();
    }

    public String getE4() {
        return e4.toString();
    }

    public void setE4(EditText e4) {
        this.e4 = e4.getText().toString();
    }



}
