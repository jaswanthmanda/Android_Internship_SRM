package com.example.readyouting;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class country_async extends AsyncTask<Void, Void, String> {

    String urllnd = "https://api.covidindiatracker.com/total.json";
    Context ct;
    RecyclerView recyclerView1213;


    public country_async(Context mainActivity, RecyclerView recyclerView){
        ct = mainActivity;
        this.recyclerView1213 = recyclerView;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Void... voids) {

        try {
            URL urlak = new URL(urllnd);
            HttpsURLConnection connection = (HttpsURLConnection) urlak.openConnection();
            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            StringBuilder builder = new StringBuilder();
            while ((line=bufferedReader.readLine()) !=null){
                builder.append(line);
            }
            return builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        List<Country_assign>  country_assignArrayList = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(s);
            String country_confirmed = jsonObject.optString("confirmed");
            String country_active = jsonObject.optString("active");
            String country_recovered = jsonObject.optString("recovered");
            String country_deaths = jsonObject.optString("deaths");

            Country_assign country_assign = new Country_assign(country_confirmed,country_active,country_recovered,country_deaths);
            country_assignArrayList.add(country_assign);

            recyclerView1213.setLayoutManager(new LinearLayoutManager(ct));
            recyclerView1213.setAdapter(new Recycler_view_country(ct,country_assignArrayList));
        }

        catch (JSONException e) {
            e.printStackTrace();
        }


    }
}