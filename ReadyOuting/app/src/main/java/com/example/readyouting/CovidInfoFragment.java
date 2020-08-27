package com.example.readyouting;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.icu.text.RelativeDateTimeFormatter;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CovidInfoFragment extends Fragment {

    String latitudeeer, longitudeeer;
    TextView loading_textview, date_textview, time_textview;
    ProgressBar progressBaras;
    ScrollView scrollViewddd;
    LocationRequest locationRequest;
    Context mConfext;
    String provider;




    RecyclerView district_level_recycler, state_level_recycler, country_level_recycler;
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


    public CovidInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mConfext = context;

    }


    @Override
    public void onDetach() {
        super.onDetach();
        mConfext = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootgrroup = (ViewGroup) inflater.inflate(R.layout.fragment_covid_info, container, false);

        district_level_recycler = rootgrroup.findViewById(R.id.districtlevelrecycler);
        district_level_recycler.setNestedScrollingEnabled(false);
        state_level_recycler =rootgrroup.findViewById(R.id.statelevelrecycler);
        state_level_recycler.setNestedScrollingEnabled(false);
        country_level_recycler = rootgrroup.findViewById(R.id.countrylevelrecycler);
        country_level_recycler.setNestedScrollingEnabled(false);
        scrollViewddd = rootgrroup.findViewById(R.id.scrollHuks);
        date_textview = rootgrroup.findViewById(R.id.date_textview);
        time_textview = rootgrroup.findViewById(R.id.time_textview);
        loading_textview = rootgrroup.findViewById(R.id.loading_textview);
        progressBaras = rootgrroup.findViewById(R.id.progressingbarr);





        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(mConfext);

        locationRequest = LocationRequest.create();
        locationRequest.setInterval(100);
        locationRequest.setFastestInterval(100);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        return rootgrroup;
    }


    @Override
    public void onStart() {
        super.onStart();
        getDeviceLocation();
        if (ActivityCompat.checkSelfPermission(mConfext
                , Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            checkSettingsandUpdateLocation();
        } else {
            ActivityCompat.requestPermissions((Activity) mConfext, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        stopLocationUpdates();
    }

    private void stopLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }

    @Override
    public void onResume() {
        super.onResume();
        getDeviceLocation();
        if (ActivityCompat.checkSelfPermission(mConfext
                , Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            checkSettingsandUpdateLocation();
        } else {
            ActivityCompat.requestPermissions((Activity) mConfext, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }

    @Override
    public void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    private void checkSettingsandUpdateLocation() {

        LocationSettingsRequest request = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
                .build();

        SettingsClient client = LocationServices.getSettingsClient(mConfext);
        Task<LocationSettingsResponse> locationSettingsResponseTask = client.checkLocationSettings(request);
        locationSettingsResponseTask.addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                startLocationUpdates();
            }
        });

        locationSettingsResponseTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    ResolvableApiException apiException = (ResolvableApiException) e;
                    try {
                        apiException.startResolutionForResult((Activity) mConfext, 1001);
                    } catch (IntentSender.SendIntentException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(mConfext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mConfext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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

    public void getDeviceLocation()
    {

        scrollViewddd.setVisibility(View.GONE);
        progressBaras.setVisibility(View.VISIBLE);
        loading_textview.setVisibility(View.VISIBLE);

        if (ActivityCompat.checkSelfPermission(mConfext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mConfext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                Geocoder geocoder = new Geocoder(mConfext, Locale.getDefault());


                try {
                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    latitudeeer = String.valueOf(addresses.get(0).getSubAdminArea());
                    longitudeeer = String.valueOf(addresses.get(0).getAdminArea());
                    if (latitudeeer == null || longitudeeer == null){
                        Toast.makeText(mConfext, "latitudeer or longitudeneer in main activity is null", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        District_async districtAsync = new District_async(mConfext, district_level_recycler, latitudeeer,longitudeeer);
                        state_async state_asyncaa = new state_async(mConfext,state_level_recycler,longitudeeer);
                        country_async country_asynsd = new country_async(mConfext,country_level_recycler);
                        districtAsync.execute();
                        state_asyncaa.execute();
                        country_asynsd.execute();
                        country_level_recycler.setVisibility(View.VISIBLE);
                        state_level_recycler.setVisibility(View.VISIBLE);
                        district_level_recycler.setVisibility(View.VISIBLE);
                        Date datedaadf = Calendar.getInstance().getTime();
                        String dateTextView = DateFormat.getDateInstance().format(datedaadf);
                        String timeMainSring = DateFormat.getTimeInstance().format(datedaadf);
                        String time_hour = timeMainSring.substring(0,2);
                        String timeSubString1;
                        String timeSubString2;
                        date_textview.setText(dateTextView);
                        if (time_hour.equals("1:") || time_hour.equals("2:") || time_hour.equals("3:") || time_hour.equals("4:") ||time_hour.equals("5:") || time_hour.equals("6:") || time_hour.equals("7:") || time_hour.equals("8:") || time_hour.equals("9:") ){
                            timeSubString1 = timeMainSring.substring(0,4);
                            timeSubString2 = timeMainSring.substring(8,10);
                        }
                        else {
                            timeSubString1 = timeMainSring.substring(0,5);
                            timeSubString2 = timeMainSring.substring(8,11);
                        }
                        time_textview.setText(timeSubString1+" "+timeSubString2);
                    }
                    loading_textview.setVisibility(View.GONE);
                    progressBaras.setVisibility(View.GONE);
                    scrollViewddd.setVisibility(View.VISIBLE);
                }
                catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(mConfext, "Please check your network and location is enabled or not..", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}