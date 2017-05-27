package com.example.asus.foodhero;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.R.attr.handle;

public class Resultactivity extends AppCompatActivity {
TextView tname;
    private static final int PROGRESS = 0x1;
    private Handler mHandler = new Handler();
    private ProgressBar mProgress;
    private int mProgressStatus = 0;
    private String lat,lon;
    ProgressDialog progressDoalog;
    private Double donlat,donlong;
    TextView temail;TextView tphone;TextView texpect;TextView titem;
private int progressBarStatus=0;

    Button b1,b2;
    private Handler progressBarHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        donlat=getIntent().getDoubleExtra("donlat",0);
        donlong=getIntent().getDoubleExtra("donlong",0);
      //  Toast.makeText(this,(int)Math.round(donlat)+"mC",Toast.LENGTH_LONG).show();
        // Obtain the SupportMapFragment and get notified when the map is rea
        setContentView(R.layout.assigned);
tname=(TextView)findViewById(R.id.textView9);
        b1=(Button)findViewById(R.id.button8);
        b2=(Button)findViewById(R.id.button5);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Resultactivity.this,FetchLocation.class);
               intent.putExtra("lat",donlat);
                intent.putExtra("long",donlong);
                startActivity(intent);
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tphone.getText().toString()));
                startActivity(intent);
            }
        });

        progressDoalog = new ProgressDialog(Resultactivity.this);
        progressDoalog.setMax(100);
        progressDoalog.setMessage("Fetching Details..");
        progressDoalog.setTitle("Please Wait");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDoalog.show();
        final Handler handle = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                progressDoalog.incrementProgressBy(4);
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (progressDoalog.getProgress() <= progressDoalog.getMax()) {
                        Thread.sleep(200);
                      handle.sendMessage(handle.obtainMessage());
                        if (progressDoalog.getProgress() == progressDoalog
                                .getMax()) {
                            progressDoalog.dismiss();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();




        temail=(TextView)findViewById(R.id.textView10);
        tphone=(TextView)findViewById(R.id.textView11);
        texpect=(TextView)findViewById(R.id.textView13);
        titem=(TextView)findViewById(R.id.textView12);
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setApplicationId("1:78886963576:android:ecf2087dcddfbb4e") // Required for Analytics.
                .setApiKey("AIzaSyBHMSIBI0ak-mXLUyD_f2n-j6Nyo6YJyBA") // Required for Auth.
                .setDatabaseUrl("https://foodhero-volunteer.firebaseio.com/") // Required for RTDB.
                .build();
        FirebaseApp.initializeApp(Resultactivity.this, options, "secondary");
        FirebaseApp app = FirebaseApp.getInstance("secondary");
// Get the database for the other app.
        FirebaseDatabase secondaryDatabase = FirebaseDatabase.getInstance(app);
        DatabaseReference mRef = secondaryDatabase.getReference().child("Request_Database");
        //Toast.makeText(Resultactivity.this,"Inside snapshot",Toast.LENGTH_SHORT).show();
        mRef.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children=dataSnapshot.getChildren();

                //String email = user.getEmail();
                boolean flag=false;

                for(DataSnapshot child: children){
                    String vid=child.child("donorid").getValue(String.class);
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if(vid.equals(user.getEmail())&&child.child("flag").getValue(String.class).equals("1")){
                        Toast.makeText(Resultactivity.this,"Inside snapshot",Toast.LENGTH_SHORT).show();
                        tname.setText(child.child("volunteername").getValue(String.class));
                       temail.setText(child.child("volid").getValue(String.class));
                       tphone.setText(child.child("volph").getValue(String.class));
                        texpect.setText(child.child("exp_time").getValue(String.class));
                        titem.setText(child.child("itemname").getValue(String.class));
lat=child.child("lat").getValue(String.class);
                        lon=child.child("long").getValue(String.class);
                    }

                }}

            @Override
            public void onCancelled(DatabaseError databaseError) {
//
            }


        });

    }
}
