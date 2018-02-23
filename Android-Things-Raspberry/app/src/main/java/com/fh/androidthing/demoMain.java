package com.fh.androidthing;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.eon.androidthings.sensehatdriverlibrary.SenseHat;
import com.eon.androidthings.sensehatdriverlibrary.devices.LedMatrix;
import com.fh.androidthing.demos.HumidityTemperatureDemo;
import com.fh.androidthing.demos.JoystickDemo;
import com.fh.androidthing.demos.TextScrollDemo;
import com.fh.androidthing.gui.IGui;
import com.fh.androidthing.uitils.NetworkUtils;
import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.PeripheralManagerService;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.eon.androidthings.sensehatdriverlibrary.SenseHat;
import com.eon.androidthings.sensehatdriverlibrary.devices.LedMatrix;
import com.eon.androidthings.sensehatdriverlibrary.devices.fonts.BlackWhiteFont;
import com.eon.androidthings.sensehatdriverlibrary.devices.fonts.LEDFont;

import java.io.IOException;
import java.util.HashMap;

import static android.content.ContentValues.TAG;

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

import java.io.IOException;
import java.util.HashMap;


public class demoMain extends Activity {

    private JoystickDemo joystickDemo;
    private TextScrollDemo textScrollDemo;
    private HumidityTemperatureDemo HumiditySensor;

    private TextView cursorCoordTextView;
    private TextView cursorColorTextView;
    private TextView ipAdressTextView;
    private TextView exceptionTextView;
    private TextView Ent_Name;
    private TextView ValFireBaseView;

    private static final String TAG = demoMain.class.getSimpleName();
    private static long intervalBetweenBlinksMs = 1000;

    private Handler mHandler = new Handler();
    private Gpio mLedGpio;

    private DatabaseReference mDatabase;
    private com.google.android.things.contrib.driver.sensehat.LedMatrix display;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        try {

            // first init Views, so that the following method could use the UI
            this.setContentView(R.layout.activity_home_demo);

            // Init firebase  ...

            // init Widget  Object  ..
            this.Ent_Name = (TextView) this.findViewById(R.id.dev_nam);
            this.cursorCoordTextView = this.findViewById(R.id.cursorCoordTextView);
            this.cursorColorTextView = this.findViewById(R.id.cursorColorTextView);
            this.ipAdressTextView = this.findViewById(R.id.ipAdressTextView);
            this.exceptionTextView = this.findViewById(R.id.exceptionTextView);
            this.ValFireBaseView = this.findViewById(R.id.val_firebase);

            // Read the  Ip Adresse and send that on  Cloud Firebase

            this.Ent_Name.setText("sdbgjksdfhjsdfghiisgf");
            String myIP = "********************** IP: " + NetworkUtils.getIPAddress(true) + " **********************";
            this.ipAdressTextView.setText(myIP);
            System.out.println("**** myIP:" + myIP);


            // ********************
            SensorManager sensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
            // SenseHat senseHat = SenseHat.init(sensorManager);
            // final LedMatrix ledMatrix = senseHat.getLedMatrix();
            // ledMatrix.draw(Color.RED);    // trun off

            // Init Firebase and post data to the Cloud  ...      FirebaseApp.initializeApp(this.getApplicationContext());
            mDatabase = FirebaseDatabase.getInstance().getReference();
            this.HumiditySensor = new HumidityTemperatureDemo(sensorManager);
            updateTemp_and_Hummidity("Ip Device  . " , myIP );
            updateTemp_and_Hummidity("Latitude" , "56.35");
            updateTemp_and_Hummidity("Longitude"  , "88,23");

            //

            getDataInit();

            /** Text-Scrolling
             */
            // this.textScrollDemo = new TextScrollDemo(sensorManager, this.getAssets());

            // Simple Joystick demo

            /*
             this.joystickDemo = new JoystickDemo(sensorManager, new IGui() {
            @Override public void setCursorInformations(final String xCoord, final String yCoord, final String color)

            {

            demoMain.this.runOnUiThread(new Runnable() {
            @Override public void run() {
            String coord = xCoord + "/" + yCoord;
            demoMain.this.cursorCoordTextView.setText(coord);
            demoMain.this.cursorColorTextView.setText(color);
                 }
            });

            }
            });
            */


        } catch (Exception e) {
            // TODO Exception Handling
            e.printStackTrace();
            String ex = ExceptionUtils.getStackTrace(e);
            this.exceptionTextView.setText(ex);
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

                Log.i("Info Break Point" , "First  Break point");

                try{

                    if(dataSnapshot.child("Latitude")!=null){
                        ValFireBaseView.setText(dataSnapshot.child("Latitude").getValue().toString());
                    }
                    System.out.println(config.getDelay());
                    intervalBetweenBlinksMs = config.getDelay();
                }catch(Exception ex){
                    ex.printStackTrace();
                }

                Log.i("Info Break Point" , "Second Break point");

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

    // Sensor updater-publischer    methode  ......

    public void updateTemp_and_Hummidity(String Label_value , String  Value_to_send){

        DatabaseReference ReadVal_on_Cloud;
        ReadVal_on_Cloud = FirebaseDatabase.getInstance().getReference();
        HashMap map = new HashMap();
        map.put(Label_value , Value_to_send);
        ReadVal_on_Cloud.updateChildren(map);
    }


    /// Sensor and Actuator Subcriber
    public void UpdateSubscriber_Sensor(){

    }

}
