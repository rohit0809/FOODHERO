package com.example.asus.foodhero;

import android.content.Intent;
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


public class Login extends AppCompatActivity implements OnClickListener{
//
    Button b1;
EditText e1;
    EditText e2;
    EditText e3;
    EditText e4;
    EditText e5;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        b1=(Button)findViewById(R.id.button2);
        b1.setOnClickListener(this);
e1=(EditText)findViewById(R.id.editText);
        e2=(EditText)findViewById(R.id.editText3);
        e3=(EditText)findViewById(R.id.editText8);
        e4=(EditText)findViewById(R.id.editText9);
        e5=(EditText)findViewById(R.id.editText10);



        // Example of a call to a native method
        // TextView tv = (TextView) findViewById(R.id.sample_text);
        //tv.setText(stringFromJNI());
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    public void onClick(View v) {
        // Write a message to the database
        if(e5.getText().toString().equals(e4.getText().toString())){


        Donor d=new Donor();
        d.setE1(e1);
        d.setE2(e2);
        d.setE3(e3);
        d.setE4(e4);

        Toast.makeText(this,"testtttttt",Toast.LENGTH_SHORT).show();
      FirebaseDatabase database= FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");
        String id=myRef.push().getKey();
        myRef.child(id).setValue(d);//myRef.setValue(d);



            Toast.makeText(this,"Successfully Registered",Toast.LENGTH_LONG).show();
      Intent i=new Intent(this,LoginActivity.class);
      startActivity(i);
    }
    else{
            Toast.makeText(this,"Passwords do not match, Please Try Again",Toast.LENGTH_LONG).show();
        }

    }
}