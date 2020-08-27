package com.example.readyouting;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import static android.content.Context.LOCATION_SERVICE;

public class GPSBroadcastReceiver extends BroadcastReceiver {

    RelativeLayout relativefail,relativesucc;
    MainActivity2 mainActivity2;
    CoordinatorLayout coordinatorLayout;
    RelativeLayout relativeLayout;

    public GPSBroadcastReceiver(RelativeLayout relativesucc, RelativeLayout relativefail){
        this.relativefail = relativefail;
        this.relativesucc = relativesucc;
    }

    public GPSBroadcastReceiver(CoordinatorLayout coordinatorLayout, RelativeLayout relativeLayout){
        this.relativeLayout = relativeLayout;
        this.coordinatorLayout = coordinatorLayout;
    }



    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            LocationManager locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                //isGPSEnabled = true;
                relativesucc.setVisibility(View.VISIBLE);
                relativefail.setVisibility(View.GONE);


            } else {
                //isGPSEnabled = false;
                relativefail.setVisibility(View.VISIBLE);
                relativesucc.setVisibility(View.GONE);
                Toast.makeText(context, "OISkjdskjn", Toast.LENGTH_SHORT).show();
            }

        }catch (Exception ex){
        }
    }
}
