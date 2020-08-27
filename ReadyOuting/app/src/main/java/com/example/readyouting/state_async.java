package com.example.readyouting;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class state_async extends AsyncTask<Void,Void,String> {

    String urll = "https://api.covidindiatracker.com/state_data.json";
    String state_comred;
    Context context;
    RecyclerView recyclerView;

    public state_async(Context mainActivity, RecyclerView recyclerView, String state_comred){
        context = mainActivity;
        this.recyclerView= recyclerView;
        this.state_comred = state_comred;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            URL urllam = new URL(urll);
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) urllam.openConnection();
            InputStream inputStream = httpsURLConnection.getInputStream();
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
        int i=0;
        List<State_assign> state_assigns = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(s);
            for (i= 0;i<jsonArray.length();i++){
                Log.i("JAJALAS","entered in the loop");
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String jsonStatekkgfs = String.valueOf(jsonObject.optString("state"));
                if (jsonStatekkgfs.contains(""+state_comred) == true ){
                    String state_comrreed = String.valueOf(jsonStatekkgfs);
                    String state_confirmed = String.valueOf(jsonObject.optString("confirmed"));
                    String state_active = String.valueOf(jsonObject.optString("active"));
                    String state_recovered = String.valueOf(jsonObject.optString("recovered"));
                    String state_deaths = String.valueOf(jsonObject.optString("deaths"));
                    State_assign state_assigndad = new State_assign(state_comrreed,state_confirmed,state_active,state_recovered,state_deaths);
                    state_assigns.add(state_assigndad);
                    break;
                }
            }

            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new Recycler_view_state(context,state_assigns));

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(context, "Please turn on your network and location if you done please wait for 5 seconds", Toast.LENGTH_SHORT).show();
        }
    }

}