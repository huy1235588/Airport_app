package com.huy.airport_app_java.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.huy.airport_app_java.R;
import com.huy.airport_app_java.models.Flight;
import java.util.List;

public class FlightAdapter extends RecyclerView.Adapter<FlightAdapter.FlightViewHolder> {
    private List<Flight> flightList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Flight flight);
    }

    public FlightAdapter(List<Flight> flightList, OnItemClickListener listener) {
        this.flightList = flightList;
        this.listener = listener;
    }

    public void setFlights(List<Flight> flights) {
        this.flightList = flights;
        notifyDataSetChanged();
    }

    @Override
    public FlightViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_flight, parent, false);
        return new FlightViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FlightViewHolder holder, int position) {
        Flight flight = flightList.get(position);
        holder.bind(flight, listener);
    }

    @Override
    public int getItemCount() {
        return flightList != null ? flightList.size() : 0;
    }

    public static class FlightViewHolder extends RecyclerView.ViewHolder {
        TextView tvFlightCode, tvDeparture, tvDestination, tvDepartureTime, tvLandingTime;
        public FlightViewHolder(View itemView) {
            super(itemView);
            tvFlightCode = itemView.findViewById(R.id.tvFlightCode);
            tvDeparture = itemView.findViewById(R.id.tvDeparture);
            tvDestination = itemView.findViewById(R.id.tvDestination);
            tvDepartureTime = itemView.findViewById(R.id.tvDepartureTime);
            tvLandingTime = itemView.findViewById(R.id.tvLandingTime);
        }
        public void bind(final Flight flight, final OnItemClickListener listener) {
            tvFlightCode.setText(flight.flightCode);
            tvDeparture.setText("From: " + flight.departure);
            tvDestination.setText("To: " + flight.destination);
            tvDepartureTime.setText("Dep: " + flight.departureTime);
            tvLandingTime.setText("Arr: " + flight.landingTime);
            itemView.setOnClickListener(v -> listener.onItemClick(flight));
        }
    }
}
