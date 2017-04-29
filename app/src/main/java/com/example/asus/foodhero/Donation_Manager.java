package com.example.asus.foodhero;

import android.content.Intent;
import android.print.PrintDocumentAdapter;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Donation_Manager extends AppCompatActivity{
    private String[] arraySpinner;
private String ss;
  private  Food_Item fd;
    RadioButton rb1,rb2;
    EditText e1,e2,e3,e4;
    Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_details);
        b1=(Button)findViewById(R.id.button);
        //rb1=(RadioButton)findViewById(R.id.radioButton);
        //rb2=(RadioButton)findViewById(R.id.radioButton2);

        e1=(EditText)findViewById(R.id.editText2); //name
        e2=(EditText)findViewById(R.id.editText4); //quantity
        e3=(EditText)findViewById(R.id.editText5); //shelflife
        e4=(EditText)findViewById(R.id.editText7);//others
        b1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database= FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Food_Items");
                 fd=new Food_Item();

             //   RadioGroup rg=(RadioGroup)findViewById(R.id.radiobuttong);
               //  rg.addView(new RadioButton(Donation_Manager.this));
                //rg.addView(new RadioButton(Donation_Manager.this));

                fd.setName(e1.getText().toString());
                 //   int selectedrbid=rg.getCheckedRadioButtonId();
                  //   RadioButton srb=(RadioButton)findViewById(selectedrbid);
                Donation_Manager.this.arraySpinner = new String[] {
                        "Veg","Non-Veg"
                };
                final Spinner s = (Spinner) findViewById(R.id.spinner);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(Donation_Manager.this,
                        android.R.layout.simple_spinner_item, arraySpinner);
                s.setAdapter(adapter);
                s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
                {

                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                        // TODO Auto-generated method stub
                        Donation_Manager.this.ss=s.getSelectedItem().toString();
                        Toast.makeText(getBaseContext(),ss , Toast.LENGTH_SHORT).show();
                        Donation_Manager.this.fd.setFoodType(ss);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub

                    }

                });

                fd.setQuantity(e2.getText().toString());
                fd.setShelflife(e3.getText().toString());
                String id=myRef.push().getKey();
                fd.setRequestid(id);
                myRef.child(id).setValue(fd);
                Intent i=new Intent(Donation_Manager.this,Waiting.class);
                startActivity(i);
            }
        });

        // Example of a call to a native method
        // TextView tv = (TextView) findViewById(R.id.sample_text);
        //tv.setText(stringFromJNI());
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    //public native String stringFromJNI();

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

}
