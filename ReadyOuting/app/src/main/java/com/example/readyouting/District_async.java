package com.example.readyouting;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;

public class District_async extends AsyncTask<Void, Void, String>{


    String url = "https://api.covid19india.org/state_district_wise.json";
    Context ct;
    RecyclerView rv;
    ProgressDialog progressDialog;
    String camellll;
    String districttrr_main;
    String state_search22;




    public District_async(Context mainActivity, RecyclerView recyclerView, String itsdistrict, String state_search22) {
        ct = mainActivity;

        this.state_search22= state_search22;
        rv = recyclerView;
        camellll = itsdistrict;
        Log.i("HEEEXCEPTION", ""+camellll);
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(ct);
        progressDialog.setMessage("Loading Please wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }


    @Override
    protected String doInBackground(Void... voids) {
        try {
            URL u = new URL(url);
            final HttpsURLConnection connection = (HttpsURLConnection) u.openConnection();
            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            StringBuilder builder = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line);
            }

            return builder.toString();
        } catch (Exception e) {
            Toast.makeText(ct, "Please check your network connectivity and location enabled", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return null;
    }






    @Override
    protected void onPostExecute(String s){
        super.onPostExecute(s);
        progressDialog.dismiss();
        List<District_assign> district_assignList = new ArrayList<>();
        try {


            JSONObject object = new JSONObject(s);
            JSONObject stateunassigned = object.getJSONObject(""+state_search22);
            JSONObject districtdata = stateunassigned.getJSONObject("districtData");

            districttrr_main = camellll;
            JSONObject unassigned = districtdata.getJSONObject(""+districttrr_main);
            String active = String.valueOf(unassigned.getInt("active"));
            String confirmed = String.valueOf(unassigned.getInt("confirmed"));
            String recoverd = String.valueOf(unassigned.getInt("recovered"));
            String deceased = String.valueOf(unassigned.getInt("deceased"));



            District_assign assign = new District_assign(camellll,confirmed,active,recoverd,deceased);
            district_assignList.add(assign);


            rv.setLayoutManager(new LinearLayoutManager(ct));
            rv.setAdapter(new Recycler_view(ct,district_assignList));
        }

        catch (Exception e) {
            Toast.makeText(ct, "Please check your network connectivity and location enabled", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}