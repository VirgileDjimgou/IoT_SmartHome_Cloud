package com.fh.androidthing.demos;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.eon.androidthings.sensehatdriverlibrary.SenseHat;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.HashMap;


import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HumidityTemperatureDemo {


    private final SensorManager sensorManager;

    public HumidityTemperatureDemo(SensorManager sensorManager ) throws IOException {

        this.sensorManager = sensorManager;

        SenseHat senseHat = SenseHat.init(sensorManager);

        SensorEventListener humidityListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                String ValHumidity = Float.toString(event.values[0]);
                System.out.println("HUM-Value:" + ValHumidity);
                updateTemp_and_Hummidity("HUM-value" , ValHumidity);

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                System.out.println("HUM-ACUU:" + sensor + " acc:''" + accuracy);
            }
        };

        SensorEventListener temperatureListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                String ValTemp = Float.toString(event.values[0]);
                System.out.println("TEMP" + event.values[0]);
                updateTemp_and_Hummidity("TEMP-Value" , ValTemp);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                System.out.println("TEMP-ACUU:" + sensor + " acc:''" + accuracy);
            }
        };


        senseHat.addHumidityTempatureSensorListener(humidityListener, temperatureListener);

    }


    public void updateTemp_and_Hummidity(String Label_value , String  Value_to_send){

        DatabaseReference ReadVal_on_Cloud;
        ReadVal_on_Cloud = FirebaseDatabase.getInstance().getReference();
        HashMap map = new HashMap();
        map.put(Label_value , Value_to_send);
        ReadVal_on_Cloud.updateChildren(map);
    }

}
