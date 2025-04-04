package com.huy.airport_app_java.viewmodels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.huy.airport_app_java.models.Revenue;
import com.huy.airport_app_java.repositories.RevenueRepository;
import android.os.AsyncTask;
import java.util.List;

public class RevenueViewModel extends AndroidViewModel {
    private RevenueRepository revenueRepository;
    public MutableLiveData<List<Revenue>> revenuesLiveData = new MutableLiveData<>();

    public RevenueViewModel(@NonNull Application application) {
        super(application);
        revenueRepository = new RevenueRepository();
    }

    public void loadRevenues(final String yearMonth) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                List<Revenue> revenues = revenueRepository.getRevenuesByMonth(yearMonth);
                revenuesLiveData.postValue(revenues);
            }
        });
    }
}
