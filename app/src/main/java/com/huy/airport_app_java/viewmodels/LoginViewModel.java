package com.huy.airport_app_java.viewmodels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.huy.airport_app_java.models.User;
import com.huy.airport_app_java.repositories.UserRepository;
import android.os.AsyncTask;

public class LoginViewModel extends AndroidViewModel {
    private UserRepository userRepository;
    public MutableLiveData<User> userLiveData = new MutableLiveData<>();

    public LoginViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository();
    }

    public void login(final String username, final String password) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                User user = userRepository.login(username, password);
                userLiveData.postValue(user);
            }
        });
    }
}
