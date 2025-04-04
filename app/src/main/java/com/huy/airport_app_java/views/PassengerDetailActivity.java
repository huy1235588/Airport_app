package com.huy.airport_app_java.views;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.huy.airport_app_java.R;
import com.huy.airport_app_java.models.Passenger;
import com.huy.airport_app_java.viewmodels.PassengerDetailViewModel;

public class PassengerDetailActivity extends AppCompatActivity {
    // Các trường nhập liệu
    private EditText etName;  // Tên hành khách
    private EditText etDateOfBirth;  // Ngày sinh
    private EditText etPassportNumber;  // Số hộ chiếu
    private EditText etNationality;  // Quốc tịch
    private EditText etPhoneNumber;  // Số điện thoại
    private EditText etSeatNumber;  // Số ghế
    
    // Các spinner chọn thông tin
    private Spinner spinnerDeparture;  // Điểm khởi hành
    private Spinner spinnerDestination;  // Điểm đến
    private Spinner spinnerCabinClass;  // Hạng vé
    
    // Các nút chức năng
    private Button btnUpdate;  // Nút cập nhật
    private Button btnDelete;  // Nút xóa
    
    // ViewModel và biến lưu trữ
    private PassengerDetailViewModel detailViewModel;  // ViewModel xử lý logic chi tiết hành khách
    private int passengerId;  // ID của hành khách
    private Passenger currentPassenger;  // Đối tượng hành khách hiện tại

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_detail);
        
        // Ánh xạ các view từ layout
        etName = findViewById(R.id.etName);
        etDateOfBirth = findViewById(R.id.etDateOfBirth);
        etPassportNumber = findViewById(R.id.etPassportNumber);
        etNationality = findViewById(R.id.etNationality);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        etSeatNumber = findViewById(R.id.etSeatNumber);
        spinnerDeparture = findViewById(R.id.spinnerDeparture);
        spinnerDestination = findViewById(R.id.spinnerDestination);
        spinnerCabinClass = findViewById(R.id.spinnerCabinClass);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        // Lấy ID hành khách từ intent và khởi tạo ViewModel
        passengerId = getIntent().getIntExtra("passengerId", -1);
        detailViewModel = new ViewModelProvider(this).get(PassengerDetailViewModel.class);
        
        // Quan sát dữ liệu hành khách từ ViewModel
        detailViewModel.passengerLiveData.observe(this, new Observer<Passenger>() {
            @Override
            public void onChanged(Passenger passenger) {
                if(passenger != null) {
                    currentPassenger = passenger;
                    // Hiển thị thông tin hành khách lên các trường nhập liệu
                    etName.setText(passenger.name);
                    etDateOfBirth.setText(passenger.dateOfBirth);
                    etPassportNumber.setText(passenger.passportNumber);
                    etNationality.setText(passenger.nationality);
                    etPhoneNumber.setText(passenger.phoneNumber);
                    etSeatNumber.setText(passenger.seatNumber);
                    // Cấu hình các spinner (được lược bỏ cho ngắn gọn)
                }
            }
        });
        detailViewModel.loadPassenger(passengerId);

        // Xử lý sự kiện click nút cập nhật
        btnUpdate.setOnClickListener(v -> {
            if(currentPassenger != null) {
                // Cập nhật thông tin hành khách từ các trường nhập liệu
                currentPassenger.name = etName.getText().toString().trim();
                currentPassenger.dateOfBirth = etDateOfBirth.getText().toString().trim();
                currentPassenger.passportNumber = etPassportNumber.getText().toString().trim();
                currentPassenger.nationality = etNationality.getText().toString().trim();
                currentPassenger.phoneNumber = etPhoneNumber.getText().toString().trim();
                currentPassenger.seatNumber = etSeatNumber.getText().toString().trim();
                // Cập nhật lựa chọn từ các spinner nếu cần (được lược bỏ cho ngắn gọn)
                detailViewModel.updatePassenger(currentPassenger);
                Toast.makeText(PassengerDetailActivity.this, "Updated", Toast.LENGTH_SHORT).show();
            }
        });

        // Xử lý sự kiện click nút xóa
        btnDelete.setOnClickListener(v -> {
            if(currentPassenger != null) {
                detailViewModel.deletePassenger(currentPassenger);
                Toast.makeText(PassengerDetailActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
