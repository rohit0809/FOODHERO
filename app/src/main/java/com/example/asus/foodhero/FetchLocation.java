package com.example.asus.foodhero;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class FetchLocation extends AppCompatActivity {
    public String vlat,vlong,vlato,vlongo;
    private static final int PROGRESS = 0x1;
    private Handler mHandler = new Handler();
    private ProgressBar mProgress;
    private int mProgressStatus = 0;
    ProgressDialog progressDoalog;
    private int progressBarStatus=0;
    public int lat,lon;
    public Double longit,lati;
    TextView txt,txt1;
    Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fetch_location);

        longit=getIntent().getDoubleExtra("long",0);
        lati=getIntent().getDoubleExtra("lat",0);
        b1=(Button)findViewById(R.id.button6);
        txt=(TextView)findViewById(R.id.textView2);

        txt1=(TextView)findViewById(R.id.textView4);
        txt1.setVisibility(View.INVISIBLE);
        txt.setVisibility(View.INVISIBLE);

        progressDoalog = new ProgressDialog(FetchLocation.this);
        progressDoalog.setMax(100);
        progressDoalog.setMessage("Fetching Volunteer's Location..");
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
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setApplicationId("1:78886963576:android:ecf2087dcddfbb4e") // Required for Analytics.
                .setApiKey("AIzaSyBHMSIBI0ak-mXLUyD_f2n-j6Nyo6YJyBA") // Required for Auth.
                .setDatabaseUrl("https://foodhero-volunteer.firebaseio.com/") // Required for RTDB.
                .build();
        //nkj/////
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2=new Intent(FetchLocation.this,MapsActivity.class);
                i2.putExtra("vlat",txt.getText());
                i2.putExtra("vlon",txt1.getText());
                i2.putExtra("longit",longit);
                i2.putExtra("lati",lati);
                startActivity(i2);
            }
        });
        FirebaseApp.initializeApp(this, options, "secondary1");
        FirebaseApp app = FirebaseApp.getInstance("secondary1");
// Get the database for the other app.
        ///////
        FirebaseDatabase secondaryDatabase = FirebaseDatabase.getInstance(app);
        DatabaseReference mRef = secondaryDatabase.getReference().child("Request_Database");
//
        mRef.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    Iterable<DataSnapshot> children=dataSnapshot.getChildren();

                                                    //String email = user.getEmail();//
                                                    boolean flag=false;

                                                    for(DataSnapshot child: children){
                                                        String vid=child.child("donorid").getValue(String.class);
                                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                                        if(vid.equals(user.getEmail())&&child.child("flag").getValue(String.class).equals("1")){
                                                            // Toast.makeText(Waiting.this,"Oh yeah"+vid,Toast.LENGTH_SHORT).show();

                                                            vlato=(child.child("lat").getValue(String.class));

                                                            vlongo=(child.child("long").getValue(String.class));
                                                            ////       Toast.makeText(FetchLocation.this,child.child("flag").getValue(String.class)+"FL",Toast.LENGTH_LONG).show();

// Because clicking the notification opens a new ("special") activity, there's
// no need to create an artificial back stack.
                                                            //   resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        /*dcndjkc*/

                                                        }
                                                        lat=Integer.parseInt(vlato);
                                                        lon=Integer.parseInt(vlongo);
                                                       // Toast.makeText(FetchLocation.this,lat+"FL",Toast.LENGTH_LONG).show();
String str=""+lat;
                                                        String str1=""+lon;
                                                        txt.setText(str);
                                                        txt1.setText(str1);
                                                        //      Toast.makeText(MapsActivity.this,vlato+"FL",Toast.LENGTH_LONG).show();

                                                        /// Toast.makeText(MapsActivity.this,lat+lon+"FL",Toast.LENGTH_LONG).show();
//Toast.makeText(Waiting.this,vid,Toast.LENGTH_SHORT).show();
                   /* if(vid.equals(email))
                    {
                        String items=child.child("itemname").getValue(String.class);
                        String qty=child.child("quant").getValue(String.class);
                        String slife=child.child("shelflife").getValue(String.class);
                        String addres=child.child("add").getValue(String.class);
                        //Toast.makeText(Donor_details.this,"Hey "+did,Toast.LENGTH_LONG).show();
                        item.setText("Item: "+items);
                        quantity.setText("Number of persons it can feed: "+qty);
                        shelf.setText("Shelflife: "+slife);
                        address.setText("Address: "+addres);

                        //Map<String, String> has=new Map<String, String>();
                        //has.put("flag","1");
                        // child.getRef().child("flag").setValue("1");


                        flag=true;
                        break;
                    }
                }

                if(!flag)
                {
                    // Toast.makeText(Donor_details.this,"No Donor ",Toast.LENGTH_LONG).show();
                    item.setText("No Details Available. ");
                    quantity.setText("No Details Available. ");
                    shelf.setText("No Details Available.  ");
                    address.setText("No Details Available.  ");
                    b.setEnabled(false);
                }*/
                                                    }

                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {

                                                }


                                            }
        )
        ;
  /*      FirebaseOptions options = new FirebaseOptions.Builder()
                .setApplicationId("1:78886963576:android:ecf2087dcddfbb4e") // Required for Analytics.
                .setApiKey("AIzaSyBHMSIBI0ak-mXLUyD_f2n-j6Nyo6YJyBA") // Required for Auth.
                .setDatabaseUrl("https://foodhero-volunteer.firebaseio.com/") // Required for RTDB.
                .build();
        FirebaseApp.initializeApp(MapsActivity.this, options, "secondary");
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

                        longit=child.child("long").getValue(String.class);
                        lati=child.child("lat").getValue(String.class);
                        Toast.makeText(MapsActivity.this,longit,Toast.LENGTH_SHORT).show();


                    }

                }}

            @Override
            public void onCancelled(DatabaseError databaseError) {
//
            }


        });*/
        // Add a marker in Sydney and move the camera


    }


}

