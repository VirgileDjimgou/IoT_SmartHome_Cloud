package com.msys.androidthing;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.anderson.dashboardview.view.DashboardView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by chichikolon on 01/23/2018.
 */
public class SensorEventsRasp extends AppCompatActivity {
    private static final String TAG = SensorEventsRasp.class.getSimpleName();
    private TextView txtDetails;
    private EditText inputName, inputEmail;
    private Button btnSave;
    private DashboardView Humidity;
    private DashboardView Temperatur;

    private DatabaseReference databaseReference;

    private String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sensor_events_rpi3);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        // get reference to 'users' node
        databaseReference = firebaseDatabase.getReference("employee");

        // store app title to 'app_title' node
        this.Humidity = (DashboardView) findViewById(R.id.hum);
        this.Temperatur = (DashboardView) findViewById(R.id.TEMP);

        // app_title change listener
        firebaseDatabase.getReference("TEMP-Value").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e(TAG, "Temperatur Raspberry  updated");

                String TemperaturRaspberry  = dataSnapshot.getValue(String.class);

                // update toolbar title
            }



            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Failed to read app title value.", error.toException());
            }
        });



        // listener humidity Sensor raspberry
        // app_title change listener
        firebaseDatabase.getReference("HUM-value").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e(TAG, "Humidity Sense Hat Raspberry  updated");

                String HumidityRaspberry  = dataSnapshot.getValue(String.class);

                // update toolbar title
            }



            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Failed to read app title value.", error.toException());
            }
        });

    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
