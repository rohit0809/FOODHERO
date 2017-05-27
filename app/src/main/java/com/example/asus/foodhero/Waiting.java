package com.example.asus.foodhero;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
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
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessagingService;

public class Waiting extends AppCompatActivity  {

    Button b1;
    private Double longit,lati;
    private static final int PROGRESS = 0x1;
    private Handler mHandler = new Handler();
    private ProgressBar mProgress;
    private int mProgressStatus = 0;
    ProgressDialog progressDoalog;
    private int progressBarStatus=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.waiting_for);
       lati=getIntent().getDoubleExtra("donlati",0);
        longit   =getIntent().getDoubleExtra("donlongi",0);
    //   Toast.makeText(this,(int)Math.round(lati)+"WAT",Toast.LENGTH_LONG).show();
// Toast.makeText(this,"WAIT",Toast.LENGTH_LONG).show();
        //   b1 = (Button) findViewById(R.id.button4);


        progressDoalog = new ProgressDialog(this);
        progressDoalog.setMax(100);
        progressDoalog.setMessage("Volunteer will be joining soon..");
        progressDoalog.setTitle("Please Wait for notification..");
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
                        Thread.sleep(600);
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
        FirebaseApp.initializeApp(Waiting.this, options, "secondary");
        FirebaseApp app = FirebaseApp.getInstance("secondary");
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
                        NotificationCompat.Builder mBuilder =
                                (NotificationCompat.Builder) new NotificationCompat.Builder(Waiting.this)
                                        .setSmallIcon(R.mipmap.xyz)
                                        .setContentTitle("Request Processed")
                                        .setDefaults(Notification.DEFAULT_SOUND)
                                        .setAutoCancel(true)
                                        .setContentText(child.child("volunteername").getValue(String.class)+" is arriving.."+" Click for details");
                        // NotificationCompat.Builder mBuilder;
                        Intent resultIntent = new Intent(Waiting.this, Resultactivity.class);
                        resultIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                       // Toast.makeText(Waiting.this,(int)Math.round(lati)+"WAT2",Toast.LENGTH_LONG).show();
                       resultIntent.putExtra("donlat",lati);
                       resultIntent.putExtra("donlong",longit);
// Because clicking the notification opens a new ("special") activity, there's
// no need to create an artificial back stack.
                        //   resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        /*dcndjkc*/
                        PendingIntent resultPendingIntent =
                                PendingIntent.getActivity(
                                        Waiting.this,
                                        0,
                                        resultIntent,
                                        PendingIntent.FLAG_UPDATE_CURRENT
                                );

                        mBuilder.setContentIntent(resultPendingIntent);
// Sets an ID for the notification
                        int mNotificationId = 001;
// Gets an instance of the NotificationManager service
                        NotificationManager mNotifyMgr =
                                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
// Builds the notification and issues it.
                        mNotifyMgr.notify(mNotificationId, mBuilder.build());


                    }
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
                }}

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });

   /*  b1.setOnClickListener(new View.OnClickListener() {
                                  @Override
                                  public void onClick(View v) {
                                      FirebaseOptions options = new FirebaseOptions.Builder()
                                              .setApplicationId("1:78886963576:android:ecf2087dcddfbb4e") // Required for Analytics.
                                              .setApiKey("AIzaSyBHMSIBI0ak-mXLUyD_f2n-j6Nyo6YJyBA") // Required for Auth.
                                              .setDatabaseUrl("https://foodhero-volunteer.firebaseio.com/") // Required for RTDB.
                                              .build();
                                      FirebaseApp.initializeApp(Waiting.this, options, "secondary");
                                      FirebaseApp app = FirebaseApp.getInstance("secondary");
// Get the database for the other app.
                                      FirebaseDatabase secondaryDatabase = FirebaseDatabase.getInstance(app);
                                      DatabaseReference mRef = secondaryDatabase.getReference().child("Request_Database");

                                  }
                              }
        );*/
    }}
