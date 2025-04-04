package com.huy.airport_app_java.viewmodels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.huy.airport_app_java.models.Flight;
import com.huy.airport_app_java.repositories.FlightRepository;
import android.os.AsyncTask;
import java.util.List;

public class FlightListViewModel extends AndroidViewModel {
    private FlightRepository flightRepository;
    public MutableLiveData<List<Flight>> flightsLiveData = new MutableLiveData<>();

    public FlightListViewModel(@NonNull Application application) {
        super(application);
        flightRepository = new FlightRepository();
    }

    public void loadFlights(final String date) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                List<Flight> flights = flightRepository.getFlightsByDate(date);
                flightsLiveData.postValue(flights);
            }
        });
    }
}
