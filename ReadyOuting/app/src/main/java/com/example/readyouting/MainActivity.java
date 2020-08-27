package com.example.readyouting;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.kwabenaberko.openweathermaplib.models.common.Main;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MainActivity extends AppCompatActivity {
    Button button2;
    LocationRequest locationRequest;
    RelativeLayout relativeLayoutsucc, relativeLayoutfail;
    TextView clickhere;
    Network network;
    private ConnectivityManager mConnectivitymanager;
    private ConnectivityManager.NetworkCallback mNetworkCallback;

    String provider;


    private GPSBroadcastReceiver gpsBroadcastReceiver;

    FusedLocationProviderClient fusedLocationProviderClient;
    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            if (locationResult == null) {
                return;
            }
            for (Location location : locationResult.getLocations()) {
                Log.i("LOCAT", "" + location.toString());
                Log.i("ITSLATI", "" + location.getLatitude());

            }
        }
    };

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clickhere = findViewById(R.id.click_here_textview);
        button2 = findViewById(R.id.navigate_activity_button);
        relativeLayoutfail = findViewById(R.id.relativelsyoutfail);
        relativeLayoutsucc = findViewById(R.id.relativelayoutsucc);
        gpsBroadcastReceiver = new GPSBroadcastReceiver(relativeLayoutsucc, relativeLayoutfail);
        registerReceiver(gpsBroadcastReceiver, new IntentFilter());
        registerReceiver(gpsBroadcastReceiver, new IntentFilter("android.location.PROVIDERS_CHANGED"));
        mConnectivitymanager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);




        final NetworkRequest networkRequest = new NetworkRequest.Builder().build();
        mNetworkCallback = new ConnectivityManager.NetworkCallback() {
            @Override
            public void onLost(@NonNull Network network) {
                super.onLost(network);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        relativeLayoutfail.setVisibility(View.VISIBLE);
                        relativeLayoutsucc.setVisibility(View.GONE);
                    }
                });
            }

            @Override
            public void onAvailable(@NonNull Network network) {
                super.onAvailable(network);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        relativeLayoutfail.setVisibility(View.GONE);
                        relativeLayoutsucc.setVisibility(View.VISIBLE);
                    }
                });
            }

            @Override
            public void onUnavailable() {
                super.onUnavailable();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        relativeLayoutfail.setVisibility(View.VISIBLE);
                        relativeLayoutsucc.setVisibility(View.GONE);
                    }
                });

            }
        };


        mConnectivitymanager.registerNetworkCallback(networkRequest, mNetworkCallback);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(MainActivity.this
                        , Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                    startActivity(intent);
                } else {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }

            }
        });

        clickhere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MainActivity.this, InstructionsActivity.class);
                startActivity(intent1);
            }
        });


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        locationRequest = LocationRequest.create();
        locationRequest.setInterval(10);
        locationRequest.setFastestInterval(10);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


    }






    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (ActivityCompat.checkSelfPermission(MainActivity.this
                , Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            checkSettingsandUpdateLocation();

        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }


    }


    @Override
    protected void onStop() {
        super.onStop();
        stopLocationUpdates();
        try{
            mConnectivitymanager.unregisterNetworkCallback(mNetworkCallback);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void stopLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(gpsBroadcastReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        gpsBroadcastReceiver = new GPSBroadcastReceiver(relativeLayoutsucc, relativeLayoutfail);
        registerReceiver(gpsBroadcastReceiver, new IntentFilter());
        registerReceiver(gpsBroadcastReceiver, new IntentFilter("android.location.PROVIDERS_CHANGED"));



        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        gpsBroadcastReceiver = new GPSBroadcastReceiver(relativeLayoutsucc, relativeLayoutfail);
        registerReceiver(gpsBroadcastReceiver, new IntentFilter());
        registerReceiver(gpsBroadcastReceiver, new IntentFilter("android.location.PROVIDERS_CHANGED"));
    }

    @Override
    protected void onRestart() {
        super.onRestart();



        gpsBroadcastReceiver = new GPSBroadcastReceiver(relativeLayoutsucc, relativeLayoutfail);
        registerReceiver(gpsBroadcastReceiver, new IntentFilter());
        registerReceiver(gpsBroadcastReceiver, new IntentFilter("android.location.PROVIDERS_CHANGED"));


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }


    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    private void checkSettingsandUpdateLocation() {

        LocationSettingsRequest request = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
                .build();

        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> locationSettingsResponseTask = client.checkLocationSettings(request);
        locationSettingsResponseTask.addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                startLocationUpdates();
                relativeLayoutsucc.setVisibility(View.VISIBLE);
                relativeLayoutfail.setVisibility(View.GONE);
            }
        });

        locationSettingsResponseTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                relativeLayoutfail.setVisibility(View.VISIBLE);
                if (e instanceof ResolvableApiException) {
                    ResolvableApiException apiException = (ResolvableApiException) e;
                    try {
                        apiException.startResolutionForResult(MainActivity.this, 1001);
                    } catch (IntentSender.SendIntentException ex) {
                        ex.printStackTrace();
                        relativeLayoutsucc.setVisibility(View.GONE);
                        relativeLayoutfail.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }


    public void getLocationListener(){

    }

}