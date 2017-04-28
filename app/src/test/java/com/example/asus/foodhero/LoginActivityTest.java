package com.example.asus.foodhero;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Saurav on 4/27/2017.
 */
public class LoginActivityTest {
    private int output1;
    private int expected1;

    @Test
    public void isEmailValid () throws Exception {

String input="singhsourav206@gmail.com";
        boolean output;
        boolean expected=true;
        LoginActivity l1=new LoginActivity();
       output=l1.isEmailValid(input);
        if(output==expected){
            output1=1;
            expected1=1;
        }
        else
        {
            output1=0;
            expected1=1;
        }
        assertEquals(expected1,output1,0);
    }

    @Test
    public void isPasswordValid() throws Exception {
        String input="abcde";

        boolean output;
        boolean expected=true;
        LoginActivity l1=new LoginActivity();
        output=l1.isPasswordValid(input);
        if(output==expected){
            output1=1;
            expected1=1;
        }
        else
        {
            output1=0;
            expected1=1;
        }
        assertEquals(expected1,output1,0);
    }

}