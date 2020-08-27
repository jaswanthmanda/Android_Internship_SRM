package com.example.readyouting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Recycler_view_country extends RecyclerView.Adapter<Recycler_view_country.myViewHolder> {


    Context ctdd;
    List<Country_assign> countryAssignList;

    public Recycler_view_country(Context ctdd, List<Country_assign> countryAssignList){
        this.ctdd = ctdd;
        this.countryAssignList = countryAssignList;
    }


    @Override
    public Recycler_view_country.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctdd).inflate(R.layout.row_country,parent,false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Recycler_view_country.myViewHolder holder, int position) {
        Country_assign listru = countryAssignList.get(position);
        holder.countrty_mainn.setText("India");
        holder.tv_coutry_confirmed.setText(listru.getConfirmed_country());
        holder.tv_country_active.setText(listru.getActive_country());
        holder.tv_country_recoverd.setText(listru.getRecovered_country());
        holder.tv_country_deaths.setText(listru.getDeaths_country());
    }

    @Override
    public int getItemCount() {
        return countryAssignList.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView tv_country_active,tv_coutry_confirmed, tv_country_deaths,tv_country_recoverd,countrty_mainn;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_coutry_confirmed = itemView.findViewById(R.id.country_confiremed_textview);
            tv_country_active = itemView.findViewById(R.id.country_active_textview);
            tv_country_deaths = itemView.findViewById(R.id.country_deceased_textview);
            tv_country_recoverd = itemView.findViewById(R.id.country_recovered_textview);
            countrty_mainn = itemView.findViewById(R.id.country_textview);
        }
    }
}