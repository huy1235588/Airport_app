package com.huy.airport_app_java.viewmodels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.huy.airport_app_java.models.Passenger;
import com.huy.airport_app_java.repositories.PassengerRepository;
import android.os.AsyncTask;
import java.util.List;

public class PassengerListViewModel extends AndroidViewModel {
    private PassengerRepository passengerRepository;
    public MutableLiveData<List<Passenger>> passengersLiveData = new MutableLiveData<>();

    public PassengerListViewModel(@NonNull Application application) {
        super(application);
        passengerRepository = new PassengerRepository();
    }

    public void loadPassengers(final int flightId) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                List<Passenger> passengers = passengerRepository.getPassengersByFlightId(flightId);
                passengersLiveData.postValue(passengers);
            }
        });
    }
}
