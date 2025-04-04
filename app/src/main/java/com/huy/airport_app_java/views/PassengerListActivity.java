package com.huy.airport_app_java.views;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.huy.airport_app_java.R;
import com.huy.airport_app_java.adapters.PassengerAdapter;
import com.huy.airport_app_java.models.Passenger;
import com.huy.airport_app_java.viewmodels.PassengerListViewModel;
import java.util.List;

public class PassengerListActivity extends AppCompatActivity {
    // Các view trong layout
    private RecyclerView rvPassengers;  // RecyclerView hiển thị danh sách hành khách
    
    // Adapter và ViewModel
    private PassengerAdapter passengerAdapter;  // Adapter cho RecyclerView
    private PassengerListViewModel passengerListViewModel;  // ViewModel xử lý logic danh sách hành khách
    
    // Biến lưu trữ
    private int flightId;  // ID của chuyến bay

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_list);
        
        // Ánh xạ và cấu hình RecyclerView
        rvPassengers = findViewById(R.id.rvPassengers);
        rvPassengers.setLayoutManager(new LinearLayoutManager(this));
        
        // Khởi tạo adapter với callback xử lý click vào hành khách
        passengerAdapter = new PassengerAdapter(null, passenger -> {
            // Xử lý sự kiện click vào hành khách, mở PassengerDetailActivity
            Intent intent = new Intent(PassengerListActivity.this, PassengerDetailActivity.class);
            intent.putExtra("passengerId", passenger.id);
            startActivity(intent);
        });
        rvPassengers.setAdapter(passengerAdapter);

        // Lấy ID chuyến bay từ intent và khởi tạo ViewModel
        flightId = getIntent().getIntExtra("flightId", -1);
        passengerListViewModel = new ViewModelProvider(this).get(PassengerListViewModel.class);
        
        // Quan sát dữ liệu hành khách từ ViewModel
        passengerListViewModel.passengersLiveData.observe(this, new Observer<List<Passenger>>() {
            @Override
            public void onChanged(List<Passenger> passengers) {
                passengerAdapter.setPassengers(passengers);
            }
        });
        
        // Tải danh sách hành khách của chuyến bay
        passengerListViewModel.loadPassengers(flightId);
    }
}
