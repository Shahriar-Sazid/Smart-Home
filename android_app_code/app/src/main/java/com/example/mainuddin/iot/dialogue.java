package com.example.mainuddin.iot;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ligl.android.widget.iosdialog.IOSDialog;

import static android.provider.ContactsContract.Intents.Insert.ACTION;
import static com.example.mainuddin.iot.Delegate.theMainActivity;

public class dialogue extends AppCompatActivity {

    //static final String ACTION = "com.google.android.c2dm.intent.RECEIVE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dialogue);
         //hide activity title
        displayAlert();


    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    private void displayAlert(){
        Handler handler = new Handler(Looper.getMainLooper());

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                IOSDialog.Builder iosDialog = new IOSDialog.Builder(dialogue.this);
                iosDialog.setTitle("FIRE ALARM!!!!!!!!!");
                iosDialog.setMessage("RUN OUT");
                iosDialog.create();
                iosDialog.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // dialog.dismiss();
                                //Intent myIntent2 = new Intent(getApplicationContext(), dialogue.class);
                                //dialogue.this.finish();
                                FirebaseDatabase database1 = FirebaseDatabase.getInstance();
                                DatabaseReference myRef22 = database1.getReference().child("noti").child("fire");
                                myRef22.setValue(0);


                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(0);


                            }
                        });

                iosDialog.show();
            }
        }, 10 );
    }



}
