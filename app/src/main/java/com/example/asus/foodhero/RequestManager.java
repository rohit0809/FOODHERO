package com.example.asus.foodhero;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.print.PrintDocumentAdapter;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.asus.foodhero.R.id.imageView;


public class RequestManager extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener,AdapterView.OnItemSelectedListener{
    private String[] arraySpinner;
 String ss;
    private Uri filePath;
    private static final int PICK_IMAGE_REQUEST = 234;
  private  Food_Item fd;
    RadioButton rb1,rb2;
    EditText e1,e2,e3,e4;
    Button b1,b2,b3;
    ImageView im;
    TextView tv,tv1;
    private StorageReference mStorageRef;
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                im.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void uploadFile(String id) {
        //if there is a file to upload
        if (filePath != null) {
            //displaying a progress dialog while upload is going on
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Processing");
            progressDialog.show();

            StorageReference riversRef = mStorageRef.child("images/"+id+".jpg");
            riversRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //if the upload is successfull
                            //hiding the progress dialog
                            progressDialog.dismiss();

                            //and displaying a success toast
                            Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            //if the upload is not successfull
                            //hiding the progress dialog
                            progressDialog.dismiss();

                            //and displaying error message
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //calculating progress percentage
                       @SuppressWarnings("VisibleForTests")   double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                            //displaying percentage in progress dialog
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                        }
                    });
        }
        //if there is not any file
        else {
            //you can display an error toast
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navdrawer);
       Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       setSupportActionBar(toolbar);

        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
tv1=(TextView)findViewById(R.id.textView3);
        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();

        categories.add("Sector 1 Saltlake");
        categories.add("Sector 2 Saltlake");
        categories.add("Sector 3 Saltlake");
        categories.add("Sector 5 Saltlake");
        categories.add("Kankurgachi");
        categories.add("Howrah");
        categories.add("Shobhabaazar");
        categories.add("Kestopur"); categories.add("Park Street"); categories.add(" New Town Rajarhat");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
       ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
              this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
      drawer.setDrawerListener(toggle);
        toggle.syncState();



        ;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference mostafa = ref.child("donors");
        mostafa.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
               Iterable<DataSnapshot> dsi=dataSnapshot.getChildren();
                for(DataSnapshot DS:dsi){
                    if(DS.child("donorid").getValue(String.class).equals(user.getUid())){
                       // Toast.makeText(RequestManager.this,DS.child("donorid").getValue(String.class),Toast.LENGTH_LONG).show();
                        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                        navigationView.setNavigationItemSelectedListener(RequestManager.this);
                        TextView t1=(TextView)navigationView.getHeaderView(0).findViewById(R.id.textView);
                        TextView t2=(TextView)navigationView.getHeaderView(0).findViewById(R.id.textViewd);
                        t2.setText(DS.child("name").getValue(String.class));
                        t1.setText(user.getEmail());
                    }

                }









            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    //    t2.setText(user.getEmail());

//t1.setText("Donor ID: "+user.getUid());
        b1=(Button)findViewById(R.id.button);
        Intent intent = getIntent();
            String location = "Current Location:" + intent.getExtras().getString("epuzzle");
        Toast.makeText(this, location, Toast.LENGTH_LONG).show();
        tv=(TextView)findViewById(R.id.textView4);
        tv.setText(location);
        b3=(Button)findViewById(R.id.button6);
        im=(ImageView)findViewById(R.id.imageView2);
        //rb1=(RadioButton)findViewById(R.id.radioButton);
        //rb2=(RadioButton)findViewById(R.id.radioButton2);
        mStorageRef = FirebaseStorage.getInstance().getReference();

        e1=(EditText)findViewById(R.id.editText2); //name
        e2=(EditText)findViewById(R.id.editText4); //quantity
        e3=(EditText)findViewById(R.id.editText5); //shelflife
        e4=(EditText)findViewById(R.id.editText7);//others
        //Upload
        b3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();



            }
        });
        //Choose Picture

        //DonateButton
        b1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    // Name, email address, and profile photo Url
                    String name = user.getDisplayName();
                    String email = user.getEmail();
                  //  Uri photoUrl = user.getPhotoUrl();

                    // The user's ID, unique to the Firebase project. Do NOT use this value to
                    // authenticate with your backend server, if you have one. Use
                    // FirebaseUser.getToken() instead.
                    String uid = user.getUid();
               //     Toast.makeText(RequestManager.this, name + email + " " + uid + " ", Toast.LENGTH_LONG).show();

                }
                FirebaseDatabase database= FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Food_Items");
                 fd=new Food_Item();

             //   RadioGroup rg=(RadioGroup)findViewById(R.id.radiobuttong);
               //  rg.addView(new RadioButton(Donation_Manager.this));
                //rg.addView(new RadioButton(Donation_Manager.this));

                fd.setName(e1.getText().toString());
                 //   int selectedrbid=rg.getCheckedRadioButtonId();
                  //   RadioButton srb=(RadioButton)findViewById(selectedrbid);
                RequestManager.this.arraySpinner = new String[] {
                        "Veg","Non-Veg"
                };


                fd.setQuantity(e2.getText().toString());
                fd.setShelflife(e3.getText().toString());
   fd.setLocation(tv.getText().toString());

                String id=myRef.push().getKey();

                FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();
                fd.setDonorid(user1.getEmail()
                );
                uploadFile(id);
                fd.setRequestid(id);
int status=0;
fd.setLandmark(spinner.getSelectedItem().toString());


                    myRef.child(id).setValue(fd);
                    Intent i = new Intent(RequestManager.this, Waiting.class);
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
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navdrawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_signout) {
            FirebaseAuth.getInstance().signOut();
            Intent i=new Intent(RequestManager.this,LoginActivity.class);
startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();
        ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
