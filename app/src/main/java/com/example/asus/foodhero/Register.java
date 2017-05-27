package com.example.asus.foodhero;

import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Timer;


public class Register extends AppCompatActivity implements OnClickListener{

    Button b1;
    EditText name;
    TextView tdid;
    String donorid;
    EditText contact;
    EditText email1;
    boolean flag=true;
    EditText password1;
    EditText e5;
    private FirebaseAuth mAuth;
    static String did=" ";
    private FirebaseAuth.AuthStateListener mAuthListener;
    public  void createAccount(String email, String password) {
        // Log.d(TAG, "cprivate reateAccount:" + email);
     //   if (!validateForm()) {
       //     return;
        //}

//      showProgressDialog();

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //   Log.d(TAG, "createUserWithEmail:success");    //  Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Register.this, "Signupdone.",
                                    Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();


                                 tdid.setText(user.getUid().toString());
                            Donor d=new Donor();
                            d.setName(name);
                            d.setContact(contact);
                            d.setEmail(email1);
                            d.setPassword(password1);

                         //   Toast.makeText(Register.this,"testtttttt",Toast.LENGTH_SHORT).show();

                            FirebaseDatabase database= FirebaseDatabase.getInstance();
                            DatabaseReference myRef = database.getReference("donors");
                          //  String id=myRef.push().getKey();
                            //mAuth=FirebaseAuth.getInstance();
                            //String di=mAuth.getCurrentUser().getToken(true).toString();
                            d.setDonorid(tdid.getText().toString());
                            myRef.child(user.getUid()).setValue(d);



                            Toast.makeText(Register.this,"Successfully Registered",Toast.LENGTH_LONG).show();
                            Intent i=new Intent(Register.this,LoginActivity.class);
                            startActivity(i);
                            //  updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            //  Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Register.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //    updateUI(null);
                        }

                        // [START_EXCLUDE]
                        //                     hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        mAuth = FirebaseAuth.getInstance();
        String donorid;
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in

                    //  Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    //   Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
        tdid=(TextView)findViewById(R.id.textView2);
        b1=(Button)findViewById(R.id.button2);
        b1.setOnClickListener(this);
name=(EditText)findViewById(R.id.editText);
        contact=(EditText)findViewById(R.id.editText3);
        email1=(EditText)findViewById(R.id.editText8);
        password1=(EditText)findViewById(R.id.editText9);
        e5=(EditText)findViewById(R.id.editText10);



        // Example of a call to a native method
        // TextView tv = (TextView) findViewById(R.id.sample_text);
        //tv.setText(stringFromJNI());
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
 //   public native String stringFromJNI();

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }
//
    @Override
    public void onClick(View v) {
        // Write a message to the database
        if(e5.getText().toString().equals(password1.getText().toString())){

            createAccount(email1.getText().toString(), e5.getText().toString());

        // go go!
          //  This code will only be executed onc
          //  await Task.Delay(2000);


    }
    else{
            Toast.makeText(this,"Passwords do not match, Please Try Again",Toast.LENGTH_LONG).show();
        }

    }
}