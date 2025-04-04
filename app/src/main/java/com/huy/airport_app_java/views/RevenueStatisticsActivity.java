package com.huy.airport_app_java.views;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.huy.airport_app_java.R;
import com.huy.airport_app_java.models.Revenue;
import com.huy.airport_app_java.viewmodels.RevenueViewModel;
//import com.github.mikephil.charting.charts.BarChart;
//import com.github.mikephil.charting.data.BarData;
//import com.github.mikephil.charting.data.BarDataSet;
//import com.github.mikephil.charting.data.BarEntry;
import java.util.ArrayList;
import java.util.List;

public class RevenueStatisticsActivity extends AppCompatActivity {
    // Các spinner chọn thời gian
    private Spinner spinnerYear;  // Spinner chọn năm
    private Spinner spinnerMonth;  // Spinner chọn tháng
    
    // ViewModel xử lý logic thống kê doanh thu
    private RevenueViewModel revenueViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revenue_statistics);
        
        // Ánh xạ các view từ layout
        spinnerYear = findViewById(R.id.spinnerYear);
        spinnerMonth = findViewById(R.id.spinnerMonth);
//        barChart = findViewById(R.id.barChart);

        // Cấu hình spinner năm với dữ liệu mẫu
        String[] years = {"2023", "2024"};
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, years);
        spinnerYear.setAdapter(yearAdapter);

        // Cấu hình spinner tháng với dữ liệu mẫu
        String[] months = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        ArrayAdapter<String> monthAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, months);
        spinnerMonth.setAdapter(monthAdapter);

        // Khởi tạo và quan sát ViewModel
        revenueViewModel = new ViewModelProvider(this).get(RevenueViewModel.class);
        revenueViewModel.revenuesLiveData.observe(this, new Observer<List<Revenue>>() {
            @Override
            public void onChanged(List<Revenue> revenues) {
//                // Convert revenue data into chart entries
//                List<BarEntry> entries = new ArrayList<>();
//                for (int i = 0; i < revenues.size(); i++) {
//                    entries.add(new BarEntry(i+1, (float) revenues.get(i).amount));
//                }
//                BarDataSet dataSet = new BarDataSet(entries, "Daily Revenue");
//                BarData data = new BarData(dataSet);
//                barChart.setData(data);
//                barChart.invalidate(); // Refresh chart
            }
        });

        // Tải dữ liệu doanh thu dựa trên lựa chọn mặc định từ spinner
        String selectedYear = spinnerYear.getSelectedItem().toString();
        String selectedMonth = spinnerMonth.getSelectedItem().toString();
        String yearMonth = selectedYear + "-" + selectedMonth;
        revenueViewModel.loadRevenues(yearMonth);
    }
}
