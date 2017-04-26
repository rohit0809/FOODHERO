package com.example.asus.foodhero;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Waiting extends AppCompatActivity {
    private static int TIME_OUT = 3500;
    Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.waiting_for);
        b1=(Button)findViewById(R.id.button4);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(Waiting.this,GetVolunteer.class);
                startActivity(i);
                finish();
            }
        }, TIME_OUT);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i1=new Intent(Waiting.this,Food_Details.class);
                startActivity(i1);
            }
        });
    }
}
