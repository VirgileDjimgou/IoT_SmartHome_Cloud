package com.fh.androidthing;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import com.eon.androidthings.sensehatdriverlibrary.SenseHat;
import com.eon.androidthings.sensehatdriverlibrary.devices.LedMatrix;
import com.fh.androidthing.demos.HumidityTemperatureDemo;
import com.fh.androidthing.demos.JoystickDemo;
import com.fh.androidthing.demos.TextScrollDemo;
import com.fh.androidthing.gui.IGui;
import com.fh.androidthing.uitils.NetworkUtils;

import org.apache.commons.lang3.exception.ExceptionUtils;

/**
 * Skeleton of an Android Things activity.
 * <p>
 * Android Things peripheral APIs are accessible through the class
 * PeripheralManagerService. For example, the snippet below will open a GPIO pin and
 * set it to HIGH:
 * <p>
 * <pre>{@code
 * PeripheralManagerService service = new PeripheralManagerService();
 * mLedGpio = service.openGpio("BCM6");
 * mLedGpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
 * mLedGpio.setValue(true);
 * }</pre>
 * <p>
 * For more complex peripherals, look for an existing user-space driver, or implement one if none
 * is available.
 *
 * @see <a href="https://github.com/androidthings/contrib-drivers#readme">https://github.com/androidthings/contrib-drivers#readme</a>
 */
public class demoMain extends Activity {

    private JoystickDemo joystickDemo;
    private TextScrollDemo textScrollDemo;

    private HumidityTemperatureDemo HumiditySensor;
    private TextView cursorCoordTextView;
    private TextView cursorColorTextView;
    private TextView ipAdressTextView;
    private TextView exceptionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {


            // first init Views, so that the following method could use the UI
            this.setContentView(R.layout.activity_home_demo);
            this.cursorCoordTextView = (TextView) this.findViewById(R.id.cursorCoordTextView);
            this.cursorColorTextView = (TextView) this.findViewById(R.id.cursorColorTextView);
            this.ipAdressTextView = (TextView) this.findViewById(R.id.ipAdressTextView);
            this.exceptionTextView = (TextView) this.findViewById(R.id.exceptionTextView);


            String myIP = "********************** IP: " + NetworkUtils.getIPAddress(true) + " **********************";
            this.ipAdressTextView.setText(myIP);
            System.out.println("**** myIP:" + myIP);


            // ********************
            SensorManager sensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
            SenseHat senseHat = SenseHat.init(sensorManager);
            final LedMatrix ledMatrix = senseHat.getLedMatrix();
            ledMatrix.draw(Color.RED);    // trun off


            /** Text-Scrolling
             */
            this.textScrollDemo = new TextScrollDemo(sensorManager, this.getAssets());

            // Simple Joystick demo
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


             // Simple Humidity Sensor demo ...
            this.HumiditySensor = new HumidityTemperatureDemo(sensorManager);



            /** MQTT ...work in progress...
             SenseHat.getInstance().addJoystickListener(new JoystickListener() {
            @Override public void stickMoved(JoystickDirectionEnum direction) throws IOException {
            if (direction == JoystickDirectionEnum.BUTTON_PRESSED) {
            try {
            MqttClient client = new MqttClient(//
            "tcp://iot.eclipse.org:1883",//
            "JavaSample",//
            new MemoryPersistence());
            //                client.setCallback(this);
            client.connect();

            MqttMessage message = new MqttMessage("Grüsse von AT".getBytes());
            client.publish("MQTT Examples", message);
            client.disconnect();
            client.close();
            } catch (MqttException e) {
            e.printStackTrace();
            final String ex = ExceptionUtils.getStackTrace(e);
            HomeActivity.this.runOnUiThread(new Runnable() {
            @Override public void run() {
            HomeActivity.this.exceptionTextView.setText(ex);
            }
            });

            }
            }
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

}
