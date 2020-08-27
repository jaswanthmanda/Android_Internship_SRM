package com.example.readyouting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Hourly_recycler extends RecyclerView.Adapter<Hourly_recycler.onMyviewholder> {


    Context context;
    List<Hourly_assign> hourly_assigns;




    public Hourly_recycler(Context context, List<Hourly_assign> hourly_assignsList) {
        this.context = context;
        hourly_assigns = hourly_assignsList;
    }

    @NonNull
    @Override
    public Hourly_recycler.onMyviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.hourly_recycler,parent,false);

        return new onMyviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Hourly_recycler.onMyviewholder holder, int position) {
        Hourly_assign list = hourly_assigns.get(position);
        holder.temperature.setText(list.getTemperature());
        holder.timeee.setText(list.getTime());
        holder.iconjj.setImageResource(list.getImageView());
        holder.iconjj.animate();
        holder.description_co.setText(list.getDescription_tot());
    }

    @Override
    public int getItemCount() {
        return hourly_assigns.size();
    }

    public class onMyviewholder extends RecyclerView.ViewHolder {
        TextView temperature, timeee, description_co;
        ImageView iconjj;

        public onMyviewholder(@NonNull View itemView) {
            super(itemView);
            temperature = itemView.findViewById(R.id.hourly_update_textview);
            timeee = itemView.findViewById(R.id.time_textview);
            iconjj = (ImageView) itemView.findViewById(R.id.hourly_icon_image_view);
            description_co = itemView.findViewById(R.id.hourly_description);
        }
    }
}
