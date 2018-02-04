package com.msys.androidthing;

import com.google.firebase.database.IgnoreExtraProperties;
/**
 * Created by chichikolon on 01/23/2018.
 */

@IgnoreExtraProperties
public class LightStatus {

        public int delay;

        // Default constructor required for calls to
        // DataSnapshot.getValue(User.class)
        public LightStatus() {
        }

        public LightStatus(int delay) {
            this.delay = delay;
        }
}
