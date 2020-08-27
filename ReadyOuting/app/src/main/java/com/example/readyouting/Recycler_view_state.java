package com.example.readyouting;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Recycler_view_state extends RecyclerView.Adapter<Recycler_view_state.myViewHolder> {

    Context ct;
    List<State_assign> state_assigns;

    public Recycler_view_state(Context context, List<State_assign> state_assigns) {
        ct = context;
        this.state_assigns = state_assigns;
    }

    @NonNull
    @Override
    public Recycler_view_state.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ct).inflate(R.layout.row_state,parent,false);
        return new myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Recycler_view_state.myViewHolder holder, int position) {
        State_assign state_assignas = state_assigns.get(position);
        holder.state_textviewkk.setText(state_assignas.getState_come());
        holder.state_confirmed_textviewqqq.setText(state_assignas.getConfirmed());
        holder.state_active_textviewww.setText(state_assignas.getActive());
        holder.state_recoverd_textviwkh.setText(state_assignas.getRecovred());
        holder.state_deaths.setText(state_assignas.getDeaths());
    }

    @Override
    public int getItemCount() {
        return state_assigns.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView state_textviewkk, state_confirmed_textviewqqq, state_active_textviewww,state_recoverd_textviwkh,state_deaths;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            state_textviewkk = itemView.findViewById(R.id.state_textview);
            state_recoverd_textviwkh = itemView.findViewById(R.id.state_recovered_textview);
            state_confirmed_textviewqqq = itemView.findViewById(R.id.confiremed_state_textview);
            state_active_textviewww = itemView.findViewById(R.id.state_active_textview);
            state_deaths = itemView.findViewById(R.id.state_deceased_textview);
        }
    }
}