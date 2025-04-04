package com.huy.airport_app_java.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.huy.airport_app_java.R;
import com.huy.airport_app_java.models.Passenger;
import java.util.List;

public class PassengerAdapter extends RecyclerView.Adapter<PassengerAdapter.PassengerViewHolder> {
    private List<Passenger> passengerList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Passenger passenger);
    }

    public PassengerAdapter(List<Passenger> passengerList, OnItemClickListener listener) {
        this.passengerList = passengerList;
        this.listener = listener;
    }

    public void setPassengers(List<Passenger> passengers) {
        this.passengerList = passengers;
        notifyDataSetChanged();
    }

    @Override
    public PassengerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_passenger, parent, false);
        return new PassengerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PassengerViewHolder holder, int position) {
        Passenger passenger = passengerList.get(position);
        holder.bind(passenger, listener);
    }

    @Override
    public int getItemCount() {
        return passengerList != null ? passengerList.size() : 0;
    }

    public static class PassengerViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPassport;
        public PassengerViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvPassport = itemView.findViewById(R.id.tvPassport);
        }
        public void bind(final Passenger passenger, final OnItemClickListener listener) {
            tvName.setText(passenger.name);
            tvPassport.setText("Passport: " + passenger.passportNumber);
            itemView.setOnClickListener(v -> listener.onItemClick(passenger));
        }
    }
}
