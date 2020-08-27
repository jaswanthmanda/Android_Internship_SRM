package com.example.readyouting;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Recycler_view extends RecyclerView.Adapter<Recycler_view.myViewHolder> {

    List<District_assign> district_assigns;
    Context ct;

    public Recycler_view(Context ct, List<District_assign> district_assigns){

        this.ct = ct;
        this.district_assigns = district_assigns;
    }


    @NonNull
    @Override
    public Recycler_view.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(ct).inflate(R.layout.row_district, parent, false);

        return new myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        District_assign list = district_assigns.get(position);
        holder.districtss_confirmed.setText(list.getDistrict_confirmed());
        holder.districtss_recovered.setText(list.getDistrict_recovered());
        holder.districtss_deceased.setText(list.getDistrict_deceased());
        holder.distrctss_active.setText(list.getDistrict_active());
        if(list.getDitrct_main() != null){
            holder.districtstts_main.setText(list.getDitrct_main());
        }
    }

    @Override
    public int getItemCount() {
        return district_assigns.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView districtss_confirmed, districtss_recovered,distrctss_active,districtss_deceased,districtstts_main;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            districtss_confirmed = itemView.findViewById(R.id.confiremed_textview);
            distrctss_active = itemView.findViewById(R.id.active_textview);
            districtss_deceased = itemView.findViewById(R.id.deceased_textview);
            districtss_recovered = itemView.findViewById(R.id.recovered_textview);
            districtstts_main = itemView.findViewById(R.id.district_textview);

        }
    }
}