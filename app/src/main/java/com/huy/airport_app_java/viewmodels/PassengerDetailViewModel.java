package com.huy.airport_app_java.viewmodels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.huy.airport_app_java.models.Passenger;
import com.huy.airport_app_java.repositories.PassengerRepository;
import android.os.AsyncTask;

public class PassengerDetailViewModel extends AndroidViewModel {
    private PassengerRepository passengerRepository;
    public MutableLiveData<Passenger> passengerLiveData = new MutableLiveData<>();

    public PassengerDetailViewModel(@NonNull Application application) {
        super(application);
        passengerRepository = new PassengerRepository();
    }

    public void loadPassenger(final int passengerId) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                Passenger passenger = passengerRepository.getPassengerById(passengerId);
                passengerLiveData.postValue(passenger);
            }
        });
    }

    public void updatePassenger(final Passenger passenger) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                passengerRepository.updatePassenger(passenger);
            }
        });
    }

    public void deletePassenger(final Passenger passenger) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                passengerRepository.deletePassenger(passenger);
            }
        });
    }
}
