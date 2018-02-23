package com.fh.androidthing;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.PeripheralManagerService;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.android.things.contrib.driver.sensehat.SenseHat;

import java.io.IOException;
import java.util.HashMap;

// import sense hat lib  ...
// import the SenseHat driver

import com.google.android.things.contrib.driver.sensehat.LedMatrix;


/**
 * Created by chichikolon on 01/23/2018.
 */

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = HomeActivity.class.getSimpleName();
    private static long intervalBetweenBlinksMs = 1000;

    private Handler mHandler = new Handler();
    private Gpio mLedGpio;

    private DatabaseReference mDatabase;
    private LedMatrix display;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FirebaseApp.initializeApp(this.getApplicationContext());
        mDatabase = FirebaseDatabase.getInstance().getReference();
        updateTemp_and_Hummidity();
        getDataInit();
        try{
            this.display = SenseHat.openDisplay();
            this.display.draw(Color.RED);
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Remove pending blink Runnable from the handler.
        mHandler.removeCallbacks(mBlinkRunnable);
        // Close the Gpio pin.
        Log.i(TAG, "Closing LED GPIO pin");
        try {
            mLedGpio.close();
        } catch (IOException e) {
            Log.e(TAG, "Error on PeripheralIO API", e);
        } finally {
            mLedGpio = null;
        }
    }

    private Runnable mBlinkRunnable = new Runnable() {
        @Override
        public void run() {
            // Exit Runnable if the GPIO is already closed
            if (mLedGpio == null) {
                return;
            }
            try {
                // Toggle the GPIO state
                mLedGpio.setValue(!mLedGpio.getValue());
                Log.d(TAG, "State set to " + mLedGpio.getValue());

                // Reschedule the same runnable in {#intervalBetweenBlinksMs} milliseconds
                mHandler.postDelayed(mBlinkRunnable, intervalBetweenBlinksMs);
            } catch (IOException e) {
                Log.e(TAG, "Error on PeripheralIO API", e);
            }
        }
    };




    private void getDataInit() {
        ValueEventListener dataListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Config config = dataSnapshot.getValue(Config.class);
                intervalBetweenBlinksMs = config.getDelay();
                PeripheralManagerService service = new PeripheralManagerService();
                try {
                    String pinName = BoardDefaults.getGPIOForLED();
                    mLedGpio = service.openGpio(pinName);
                    mLedGpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
                    Log.i(TAG, "Start blinking LED GPIO pin");
                    // Post a Runnable that continuously switch the state of the GPIO, blinking the
                    // corresponding LED
                    mHandler.post(mBlinkRunnable);
                } catch (IOException e) {
                    Log.e(TAG, "Error on PeripheralIO API", e);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "onCancelled", databaseError.toException());

            }
        };
        mDatabase.addValueEventListener(dataListener);
    }


    public void updateTemp_and_Hummidity(){

        mDatabase = FirebaseDatabase.getInstance().getReference().child("IOT_Device_RPi3").child("Sensors");
        HashMap map = new HashMap();
        map.put("Hum_Sensor_1" , "23");
        mDatabase.updateChildren(map);
    }


}
