package com.example.mainuddin.iot;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.ligl.android.widget.iosdialog.IOSDialog;
import com.suke.widget.SwitchButton;

public class MainActivity extends AppCompatActivity {


    SwitchButton onOffSwitch1 ;
    SwitchButton onOffSwitch2 ;
    SwitchButton onOffSwitch3 ;
    SwitchButton fire ;
    DatabaseReference myRef1;
    DatabaseReference myRef2;
    DatabaseReference myRef3;
    DatabaseReference myRef4;

    private static final String TAG = "init";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Delegate.theMainActivity = this;
        //Toolbar toolbar =  findViewById(R.layout.bottom_nav);
       // setSupportActionBar(toolbar);


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
  //      fab.setOnClickListener(new View.OnClickListener() {
         //   @Override
        //    public void onClick(View view) {
              //  Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                      //  .setAction("Action", null).show();
           // }
        //});



        FirebaseMessaging.getInstance().subscribeToTopic("all");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef1 = database.getReference("123").child("status").child("001");
        myRef2 = database.getReference("123").child("status").child("002");
        //myRef2 = database.getReference("bulbe2");
        //myRef3 = database.getReference("fan1");
        myRef4 = database.getReference().child("noti").child("fire");


        onOffSwitch1 = (SwitchButton) findViewById(R.id.bulbe1switch1);


        onOffSwitch1.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                //TODO do your job
                //onOffSwitch1.setChecked();
                FirebaseDatabase database1 = FirebaseDatabase.getInstance();
                DatabaseReference myRef12 = database1.getReference("123").child("status").child("001");

                if(isChecked){
                    myRef12.setValue(1);
                }else {
                    myRef12.setValue(0);
                }
                Toast.makeText(MainActivity.this, "clicked", Toast.LENGTH_SHORT).show();

            }
        });

        onOffSwitch2 = (SwitchButton) findViewById(R.id.bulbe2switch2);


        onOffSwitch2.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                //TODO do your job
                FirebaseDatabase database1 = FirebaseDatabase.getInstance();
                DatabaseReference myRef22 = database1.getReference("123").child("status").child("002");

                if(isChecked){
                    myRef22.setValue(1);
                }else {
                    myRef22.setValue(0);
                }
                Toast.makeText(MainActivity.this, "clicked", Toast.LENGTH_SHORT).show();
            }
        });


       /* onOffSwitch3 = (SwitchButton) findViewById(R.id.fan1switch1);


        onOffSwitch3.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                //TODO do your job
                FirebaseDatabase database1 = FirebaseDatabase.getInstance();
                DatabaseReference myRef32 = database1.getReference("fan1");

                if(isChecked){
                    myRef32.setValue(1);
                }else {
                    myRef32.setValue(0);
                }
                Toast.makeText(MainActivity.this, "clicked", Toast.LENGTH_SHORT).show();
            }
        });*/







        myRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue().toString();
                Log.d(TAG, "Value is: " + value);
                if(value.equals("1"))onOffSwitch1.setChecked(true);
                else onOffSwitch1.setChecked(false);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
       myRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue().toString();
                Log.d(TAG, "Value is: " + value);
                if(value.equals("1"))onOffSwitch2.setChecked(true);
                else onOffSwitch2.setChecked(false);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        /*myRef3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue().toString();
                Log.d(TAG, "Value is: " + value);
                if(value.equals("1"))onOffSwitch3.setChecked(true);
                else onOffSwitch3.setChecked(false);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });*/



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_exit) {
            System.exit(0);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
