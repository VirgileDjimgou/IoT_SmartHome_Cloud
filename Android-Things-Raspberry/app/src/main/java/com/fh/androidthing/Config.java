/**
 * Created by Chatura Dilan Perera on 16/12/2016.
 */
package com.fh.androidthing;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by chichikolon on 01/23/2018.
 */
@IgnoreExtraProperties
public class Config {

    private int delay;

    public Config() {

    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }
}
