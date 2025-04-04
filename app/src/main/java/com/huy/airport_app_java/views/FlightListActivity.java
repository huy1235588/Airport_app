package com.huy.airport_app_java.views;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.huy.airport_app_java.R;
import com.huy.airport_app_java.adapters.FlightAdapter;
import com.huy.airport_app_java.models.Flight;
import com.huy.airport_app_java.viewmodels.FlightListViewModel;
import java.util.Calendar;
import java.util.List;

public class FlightListActivity extends AppCompatActivity {
    // Các view trong layout
    private Button btnPickDate;  // Nút chọn ngày
    private RecyclerView rvFlights;  // RecyclerView hiển thị danh sách chuyến bay
    
    // Adapter và ViewModel
    private FlightAdapter flightAdapter;  // Adapter cho RecyclerView
    private FlightListViewModel flightListViewModel;  // ViewModel xử lý logic danh sách chuyến bay
    
    // Biến lưu trữ
    private String selectedDate;  // Ngày được chọn để hiển thị chuyến bay

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_list);
        
        // Ánh xạ các view từ layout
        btnPickDate = findViewById(R.id.btnPickDate);
        rvFlights = findViewById(R.id.rvFlights);
        
        // Cấu hình RecyclerView
        rvFlights.setLayoutManager(new LinearLayoutManager(this));
        flightAdapter = new FlightAdapter(null, flight -> {
            // Xử lý sự kiện click vào chuyến bay, mở PassengerListActivity với ID của chuyến bay
            Intent intent = new Intent(FlightListActivity.this, PassengerListActivity.class);
            intent.putExtra("flightId", flight.id);
            startActivity(intent);
        });
        rvFlights.setAdapter(flightAdapter);

        // Khởi tạo và quan sát ViewModel
        flightListViewModel = new ViewModelProvider(this).get(FlightListViewModel.class);
        flightListViewModel.flightsLiveData.observe(this, new Observer<List<Flight>>() {
            @Override
            public void onChanged(List<Flight> flights) {
                flightAdapter.setFlights(flights);
            }
        });

        // Xử lý sự kiện click nút chọn ngày
        btnPickDate.setOnClickListener(v -> {
            // Hiển thị DatePickerDialog để chọn ngày chuyến bay
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog dialog = new DatePickerDialog(FlightListActivity.this, (datePicker, year, month, dayOfMonth) -> {
                selectedDate = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth);
                flightListViewModel.loadFlights(selectedDate);
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            dialog.show();
        });
    }
}
