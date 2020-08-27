package com.example.readyouting;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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
import com.kwabenaberko.openweathermaplib.constants.Lang;
import com.kwabenaberko.openweathermaplib.constants.Units;
import com.kwabenaberko.openweathermaplib.implementation.OpenWeatherMapHelper;
import com.kwabenaberko.openweathermaplib.implementation.callbacks.CurrentWeatherCallback;
import com.kwabenaberko.openweathermaplib.models.currentweather.CurrentWeather;

import org.w3c.dom.Text;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class WeatherFragment extends Fragment {



    TextView city1122, tempeee112,description_weather, humidity;
    String temop1222;
    ImageView currentWeatherIcon;
    ScrollView scrollViewhuks;
    ProgressBar progressBar;
    LinearLayout linearLayout;
    FusedLocationProviderClient fusedLocationProviderClient;
    LocationRequest locationRequest;
    RecyclerView hourlyrecyclerView;
    int icon_searcher;
    List<Address> addresses;
    TextView loadingweatehr_textview;
    private Context mContext;
    String provider;

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

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }


    public WeatherFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        locationRequest = LocationRequest.create();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(500);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(mContext);

    }


    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onStart() {
        super.onStart();
        getWeather();
        if (ActivityCompat.checkSelfPermission(mContext
                , Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            checkSettingsandUpdateLocation();
        } else {
            ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }

    }

    private void checkSettingsandUpdateLocation() {

        LocationSettingsRequest request = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
                .build();

        SettingsClient client = LocationServices.getSettingsClient(mContext);
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
                        apiException.startResolutionForResult((Activity) mContext, 1001);
                    } catch (IntentSender.SendIntentException ex) {
                        ex.printStackTrace();
                        Log.i("KJSKJSD","SKJSsd");
                    }
                }
            }
        });
    }

    private void startLocationUpdates() {
        try {
            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
        catch (Exception e){
            Log.i("BKJBKJ",""+e.getMessage());
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        stopLocationUpdates();
    }

    private void stopLocationUpdates(){
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);

    }

    @Override
    public void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    @Override
    public void onResume() {
        super.onResume();
        getWeather();
        if (ActivityCompat.checkSelfPermission(mContext
                , Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            checkSettingsandUpdateLocation();
        } else {
            ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());

    }

    private void getWeather() {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location != null) {
                    try {
                        Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
                        addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 44);
                        OpenWeatherMapHelper helper = new OpenWeatherMapHelper(getString(R.string.api_key));
                        progressBar.setVisibility(View.VISIBLE);
                        scrollViewhuks.setVisibility(View.GONE);
                        helper.setUnits(Units.METRIC);
                        helper.setLang(Lang.ENGLISH);

                        Date calendar = Calendar.getInstance().getTime();
                        String dateFormat = DateFormat.getTimeInstance().format(calendar);
                        Log.i("FSJSN",""+dateFormat);
                        String timeell = String.valueOf(dateFormat);

                        Hourly_async async = new Hourly_async(mContext,hourlyrecyclerView,String.valueOf(addresses.get(0).getLatitude()),String.valueOf(addresses.get(0).getLongitude()),timeell);
                        async.execute();
                        helper.getCurrentWeatherByGeoCoordinates(addresses.get(0).getLatitude(), addresses.get(0).getLongitude(), new CurrentWeatherCallback() {
                            @Override
                            public void onSuccess(CurrentWeather currentWeather) {

                                Log.v("THROWINGERRO", "Coordinates: " + currentWeather.getCoord().getLat() + ", " + currentWeather.getCoord().getLon() + "\n"
                                        + "Weather Description: " + currentWeather.getWeather().get(0).getDescription() + "\n"
                                        + "Temperature: " + currentWeather.getMain().getTempMax() + "\n"
                                        + "Wind Speed: " + currentWeather.getWind().getSpeed() + "\n"
                                        + "City, Country: " + currentWeather.getName() + ", " + currentWeather.getSys().getCountry()+","+currentWeather.getWeather().get(0).getIcon()
                                );



                                city1122.setText(currentWeather.getName());
                                temop1222 = String.valueOf(currentWeather.getMain().getTemp()+"°C");
                                tempeee112.setText(temop1222);
                                String humidity123 = String.valueOf(currentWeather.getMain().getHumidity()+" humidities(unit)");
                                String descriptionTextview = String.valueOf(""+currentWeather.getWeather().get(0).getDescription());
                                String icon_item = String.valueOf(currentWeather.getWeather().get(0).getIcon());
                                if (icon_item.equals("01d")){
                                    icon_searcher = R.drawable.oned;
                                }
                                else if (icon_item.equals("01n")){
                                    icon_searcher = R.drawable.onen;
                                }
                                else if (icon_item.equals("02d")){
                                    icon_searcher= R.drawable.twod;
                                }
                                else if (icon_item.equals("02n")){
                                    icon_searcher= R.drawable.twon;
                                }else if (icon_item.equals("03d")){
                                    icon_searcher= R.drawable.threed;
                                }else if (icon_item.equals("03n")){
                                    icon_searcher= R.drawable.threen;
                                }else if (icon_item.equals("04d")){
                                    icon_searcher= R.drawable.fourd;
                                }else if (icon_item.equals("04n")){
                                    icon_searcher= R.drawable.fourn;
                                }else if (icon_item.equals("09d")){
                                    icon_searcher= R.drawable.nined;
                                }else if (icon_item.equals("09n")){
                                    icon_searcher= R.drawable.ninen;
                                }else if (icon_item.equals("10d")){
                                    icon_searcher= R.drawable.tend;
                                }else if (icon_item.equals("10n")){
                                    icon_searcher= R.drawable.tenn;
                                }else if (icon_item.equals("11d")){
                                    icon_searcher= R.drawable.elevend;
                                }else if (icon_item.equals("11n")){
                                    icon_searcher= R.drawable.elevenn;
                                }else if (icon_item.equals("13d")){
                                    icon_searcher= R.drawable.thirteend;
                                }else if (icon_item.equals("13n")){
                                    icon_searcher= R.drawable.thirteenn;
                                }else if (icon_item.equals("50d")){
                                    icon_searcher= R.drawable.fiftyd;
                                }else if (icon_item.equals("50n")){
                                    icon_searcher= R.drawable.fiftyn;
                                }

                                currentWeatherIcon.setImageResource(icon_searcher);
                                humidity.setText(humidity123);
                                description_weather.setText(descriptionTextview);
                                progressBar.setVisibility(View.GONE);
                                scrollViewhuks.setVisibility(View.VISIBLE);


                            }

                            @Override
                            public void onFailure(Throwable throwable) {
                                Toast.makeText(mContext, "The Failure reason: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                Log.v("THROWINGERRO", "The failure reason: " + throwable.getMessage());


                            }
                        });
                    } catch (Exception e) {
                        try {
                            Toast.makeText(mContext, "Please turn on your network", Toast.LENGTH_SHORT).show();
                            Toast.makeText(mContext, "If you turned on your location please wait for 7 seconds before proceeding....", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                            Log.v("LANLADA", "The exception reason" + e);
                        }
                        catch (Exception af){
                            af.printStackTrace();
                            Log.i("NNSMDS",af.getMessage());
                        }
                    }
                } else if (location == null) {
                    Log.i("LANLADA", "" + location);
                   try {

                       Intent intent = new Intent(mContext,MainActivity.class);
                       startActivity(intent);
                       Toast.makeText(mContext, "As you have enabled your location please wait for few seconds before clicking on it."+"\n"+"Thankyou for Understanding", Toast.LENGTH_LONG).show();
                   }
                   catch (Exception e){
                       e.printStackTrace();
                       Log.i("lLNLNDKSK",e.getMessage());
                   }
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootgroup = (ViewGroup) inflater.inflate(R.layout.fragment_weather, container, false);
        city1122 = rootgroup.findViewById(R.id.city223);
        tempeee112 = rootgroup.findViewById(R.id.temperature112);
        progressBar = rootgroup.findViewById(R.id.progressCaluculator);
        scrollViewhuks = rootgroup.findViewById(R.id.scrollweatherhuks);
        linearLayout = rootgroup.findViewById(R.id.linearLayout2);
        hourlyrecyclerView = rootgroup.findViewById(R.id.hourly_recycler);
        description_weather = rootgroup.findViewById(R.id.description_weather);
        loadingweatehr_textview = rootgroup.findViewById(R.id.loadingweather_textview);
        currentWeatherIcon = rootgroup.findViewById(R.id.current_weather_icon);
        humidity = rootgroup.findViewById(R.id.humidity_weather);
        // Inflate the layout for this fragment
        return rootgroup;

    }
}