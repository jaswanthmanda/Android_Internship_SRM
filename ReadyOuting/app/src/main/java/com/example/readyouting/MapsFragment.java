package com.example.readyouting;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.content.Context.LOCATION_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

public class MapsFragment extends Fragment {
    private GoogleMap mMap;
    ToggleButton normalview;
    ToggleButton satelliteview;
    LocationManager manager;
    double lati, longi;
    EditText mSearch;
    Context mContiixt;
    String provider;

    public MapsFragment(){
        //Empty constructor required
    }




    private OnMapReadyCallback callback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(final GoogleMap googleMap) {
            mMap = googleMap;
            normalview.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        googleMap.setTrafficEnabled(true);
                    } else {
                        googleMap.setTrafficEnabled(false);
                    }
                }
            });
            // Add a marker in Sydney and move the
            LatLng sydney = new LatLng(lati, longi);
            mMap.clear();
            if (ActivityCompat.checkSelfPermission(mContiixt, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContiixt, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,12.0f));
            mMap.getUiSettings().setZoomControlsEnabled(true);
        }
    };


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        ViewGroup rootgroupcomeon = (ViewGroup) inflater.inflate(R.layout.fragment_maps, container, false);

        normalview = rootgroupcomeon.findViewById(R.id.nomarlview);
        satelliteview = rootgroupcomeon.findViewById(R.id.satelliteview);
        mSearch = rootgroupcomeon.findViewById(R.id.search_text_input);
        final String[] textFromauto = new String[1];


        satelliteview.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                } else {
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                }
            }
        });

        manager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);
        final LocationListener listener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                lati = location.getLatitude();
                longi = location.getLongitude();
                try {
                    if (!isAdded()){
                        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                                .findFragmentById(R.id.map);
                        mapFragment.getMapAsync(callback);
                    }
                }
                catch (Exception e){
                    Log.i("JHBBBJ",""+e.getMessage());
                }
            }

        };
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        if (ActivityCompat.checkSelfPermission(mContiixt, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContiixt, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        }
        manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5*10000, 10, (LocationListener) listener);
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5*10000, 10, (LocationListener) listener);
        innit();


        return rootgroupcomeon;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContiixt = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContiixt = null;
    }



    @Override
    public void onStart() {
        super.onStart();
        final LocationListener listener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                lati = location.getLatitude();
                longi = location.getLongitude();
                try {
                    SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                            .findFragmentById(R.id.map);
                    mapFragment.getMapAsync(callback);
                }
                catch (Exception e){
                    Log.i("JHKJJN",""+e.getMessage());
                }
            }
        };


        if (ActivityCompat.checkSelfPermission(mContiixt, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContiixt, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5*10000, 10, (LocationListener) listener);
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5*10000, 10, (LocationListener) listener);
    }

    @Override
    public void onResume() {
        super.onResume();
        final LocationListener listener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                lati = location.getLatitude();
                longi = location.getLongitude();
                try {
                    SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                            .findFragmentById(R.id.map);
                    mapFragment.getMapAsync(callback);
                }
                catch (Exception e){
                    Log.i("JGVUKJ",""+e.getMessage());
                }
            }
        };


        if (ActivityCompat.checkSelfPermission(mContiixt, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContiixt, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5*10000, 10, (LocationListener) listener);
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5*10000, 10, (LocationListener) listener);
    }




    @Override
    public void onStop() {
        super.onStop();
    }




    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void innit() {
        Log.d("THERESULT", "initializing");
        mSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE || keyEvent.getAction() == KeyEvent.ACTION_DOWN || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER) {
                    try {
                        geoLocate(mSearch.getText().toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                return false;
            }
        });
    }

    private void geoLocate(String toString) {

        Log.d("THERESULT", "geolocating...");
        String searchText = toString;

        Geocoder geocoder = new Geocoder(mContiixt);
        List<Address> addresses = new ArrayList<>();
        try {
            addresses = geocoder.getFromLocationName(searchText, 1);

        } catch (IOException e) {
            e.printStackTrace();
            Log.d("THERESULT", "Please turn on your location and data" );
        }

        if (addresses.size() > 0) {
            getKummuko(addresses);
        }

        if(addresses.size() == 0){
            Toast.makeText(mContiixt, "Sorry we don't have that location at present...", Toast.LENGTH_SHORT).show();
        }

    }

    private void getKummuko(List<Address> addresses) {
        Address address = addresses.get(0);
        double latitu = address.getLatitude();
        double longitu = address.getLongitude();
        LatLng kassydney = new LatLng(latitu,longitu);
        Log.d("THERESULT", "THE Location found: " + address);
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(kassydney).title("Your Destination"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(kassydney,12.0f));
        mMap.getUiSettings().setZoomControlsEnabled(true);

    }
}