package com.example.readyouting;

import android.content.Context;
import android.os.AsyncTask;
import android.service.voice.VoiceInteractionService;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class Hourly_async extends AsyncTask<Void, Void, String> {



    Context context;
    RecyclerView recyclerView;
    String latitu, longitu;
    String urll;
    String timegg;
    int imageViewas;
    LinearLayoutManager linearLayoutManager;



    public Hourly_async(Context context, RecyclerView recyclerView,String latitu, String longitu,String timegg){
        this.context = context;
        this.recyclerView = recyclerView;
        this.latitu = latitu;
        this.longitu = longitu;
        this.timegg = timegg;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        int i;
        List<Hourly_assign> hourly_assignsList = new ArrayList<>();

        String time_hour = timegg.substring(0,2);
        Log.i("KJKJSKJ",""+time_hour);
        Log.i("KJKJSKJ",""+timegg);
        int time_integ_hour;
        String time_ampm;
        if (time_hour.equals("1:") || time_hour.equals("2:") || time_hour.equals("3:") || time_hour.equals("4:") ||time_hour.equals("5:") || time_hour.equals("6:") || time_hour.equals("7:") || time_hour.equals("8:") || time_hour.equals("9:") ){
            time_ampm = timegg.substring(8,10);
            String time_hour_1 = time_hour.substring(0,1);
            time_integ_hour = Integer.parseInt(time_hour_1);
        }
        else {
            time_ampm = timegg.substring(9,11);
            time_integ_hour = Integer.parseInt(time_hour);
        }



        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = jsonObject.getJSONArray("hourly");
            if ((jsonArray.length()) == 0){
                Toast.makeText(context, "Please turn on your mobile data", Toast.LENGTH_SHORT).show();
            }
            for (i=0;i<5;i++){
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                String tempe = String.valueOf(jsonObject1.getString("temp"));
                JSONArray jsonArray1 = jsonObject1.getJSONArray("weather");
                JSONObject jsonObject2 = jsonArray1.getJSONObject(0);
                String icon_item = jsonObject2.getString("icon");
                String desciption1 = jsonObject2.getString("main");
                String description2 = jsonObject2.getString("description");
                String description_total = ""+description2;
                if (time_integ_hour<11){
                    time_integ_hour++;
                }
                else if (time_integ_hour==11){
                    if (time_ampm.equals("AM")){
                        time_ampm = "PM";
                        time_integ_hour++;
                    }
                    else{
                        time_ampm = "AM";
                        time_integ_hour++;

                    }
                }
                else if (time_integ_hour>=12){
                    if (time_ampm.equals("AM")){
                        time_ampm = "PM";
                        time_integ_hour =time_integ_hour-12;
                        time_integ_hour++;
                    }
                    else{
                        time_ampm="PM";
                        time_integ_hour =time_integ_hour-12;
                        time_integ_hour++;
                    }
                }


                if (icon_item.equals("01d")){
                    imageViewas = R.drawable.oned;
                }
                else if (icon_item.equals("01n")){
                    imageViewas = R.drawable.onen;
                }
                else if (icon_item.equals("02d")){
                    imageViewas= R.drawable.twod;
                }
                else if (icon_item.equals("02n")){
                    imageViewas= R.drawable.twon;
                }
                else if (icon_item.equals("03d")){
                    imageViewas= R.drawable.threed;
                }else if (icon_item.equals("03n")){
                    imageViewas= R.drawable.threen;
                }else if (icon_item.equals("04d")){
                    imageViewas= R.drawable.fourd;
                }else if (icon_item.equals("04n")){
                    imageViewas= R.drawable.fourn;
                }else if (icon_item.equals("09d")){
                    imageViewas= R.drawable.nined;
                }else if (icon_item.equals("09n")){
                    imageViewas= R.drawable.ninen;
                }else if (icon_item.equals("10d")){
                    imageViewas= R.drawable.tend;
                }else if (icon_item.equals("10n")){
                    imageViewas= R.drawable.tenn;
                }else if (icon_item.equals("11d")){
                    imageViewas= R.drawable.elevend;
                }else if (icon_item.equals("11n")){
                    imageViewas= R.drawable.elevenn;
                }else if (icon_item.equals("13d")){
                    imageViewas= R.drawable.thirteend;
                }else if (icon_item.equals("13n")){
                    imageViewas= R.drawable.thirteenn;
                }else if (icon_item.equals("50d")){
                    imageViewas= R.drawable.fiftyd;
                }else if (icon_item.equals("50n")){
                    imageViewas= R.drawable.fiftyn;
                }

                String string_time_integ = String.valueOf(time_integ_hour);
                if (string_time_integ.equals("0")){
                    string_time_integ = "12";
                }


                String the_original_time = string_time_integ+" "+time_ampm;

                
                Float floatvalue_twmp = Float.parseFloat(tempe);
                double its_temp = floatvalue_twmp-273.00f;
                String temper = String.valueOf(its_temp);
                String final_temp = temper.substring(0,4);




                Hourly_assign assign = new Hourly_assign(final_temp+"°C",the_original_time,imageViewas,description_total);
                hourly_assignsList.add(assign);
            }

            linearLayoutManager = new LinearLayoutManager(context) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            };

            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(new Hourly_recycler(context,hourly_assignsList));

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Please turn on network and location", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        urll = "https://api.openweathermap.org/data/2.5/onecall?lat="+latitu+"&lon="+longitu+"&appid=cb87793129677d797031997f9a30f82e";
    }

    @Override
    protected String doInBackground(Void... voids) {

        try {
            URL url = new URL(urll);
            HttpsURLConnection httpsURLConnection= (HttpsURLConnection) url.openConnection();
            InputStream inputStream = httpsURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line= "";
            StringBuilder builder = new StringBuilder();

            while((line=bufferedReader.readLine()) !=null){
                builder.append(line);
            }
            return builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
