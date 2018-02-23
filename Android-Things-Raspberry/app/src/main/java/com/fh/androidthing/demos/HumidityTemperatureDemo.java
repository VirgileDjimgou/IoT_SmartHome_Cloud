package com.fh.androidthing.demos;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.eon.androidthings.sensehatdriverlibrary.SenseHat;

import java.io.IOException;


public class HumidityTemperatureDemo {


    private final SensorManager sensorManager;

    public HumidityTemperatureDemo(SensorManager sensorManager) throws IOException {

        this.sensorManager = sensorManager;


        SenseHat senseHat = SenseHat.init(sensorManager);

        SensorEventListener humidityListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                System.out.println("HUM-Value:" + event.values[0]);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                System.out.println("HUM-ACUU:" + sensor + " acc:''" + accuracy);
            }
        };

        SensorEventListener temperatureListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                System.out.println("TEMP-Value:" + event.values[0]);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                System.out.println("TEMP-ACUU:" + sensor + " acc:''" + accuracy);
            }
        };


        senseHat.addHumidityTempatureSensorListener(humidityListener, temperatureListener);

    }

}
