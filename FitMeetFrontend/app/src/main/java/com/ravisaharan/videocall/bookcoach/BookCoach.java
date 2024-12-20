package com.ravisaharan.videocall.bookcoach;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.ravisaharan.videocall.ApiClient;
import com.ravisaharan.videocall.api.ApiServiceCoach;
import com.ravisaharan.videocall.api.ApiServiceSchedule;
import com.ravisaharan.videocall.chat.Chat;
import com.ravisaharan.videocall.databinding.ActivityBookCoachBinding;

import com.ravisaharan.videocall.R;
import com.ravisaharan.videocall.dto.ScheduleDto;
import com.ravisaharan.videocall.home.HomeActivity;
import com.ravisaharan.videocall.model.Coache;
import com.ravisaharan.videocall.model.Schedule;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookCoach extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityBookCoachBinding binding;
    private TextView tvSelectedTime, coachName, description, henkham, goikham,tuvan, chungchi  ;
    private Button btnSelectTime, btn_book;
    private ApiServiceCoach apiService;
    private ApiServiceSchedule apiServiceSchedule;
    String coachId;
    ImageButton backtohome;
    String selectedDateTime;
    int userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityBookCoachBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        tvSelectedTime = findViewById(R.id.tv_selected_time);
        btnSelectTime = findViewById(R.id.btn_select_time);


        Intent intent = getIntent();
        coachId = intent.getStringExtra("coachId");
//        Toast.makeText(this,"hello"+ coachId, Toast.LENGTH_SHORT).show();

        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
         userId = sharedPreferences.getInt("studentloggedid", -1);


        apiService = ApiClient.getRetrofitInstance().create(ApiServiceCoach.class);
        apiServiceSchedule = ApiClient.getRetrofitInstance().create(ApiServiceSchedule.class);
        fetchCoaches(coachId);

        backtohome=findViewById(R.id.backtohome);
        backtohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý sự kiện khi Button được nhấn
                Intent intent = new Intent(BookCoach.this, HomeActivity.class);

                startActivity(intent);
            }
        });
        
        btn_book=findViewById(R.id.btn_book_appointment);
        btn_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý sự kiện khi Button được nhấn
             if (selectedDateTime==null){
                 Toast.makeText(BookCoach.this, "Vui lòng chọn thời gian cuộc hẹn!!!!!", Toast.LENGTH_SHORT).show();
             }
                ScheduleDto scheduleDto=new ScheduleDto(userId,Integer.parseInt(coachId),selectedDateTime);
                createSchedule(scheduleDto);
                Toast.makeText(BookCoach.this, "Tạo cuộc hẹn thành công!!!", Toast.LENGTH_SHORT).show();
                try {
                    // Tạm dừng trong 1 giây (1000 milliseconds)
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // Xử lý nếu luồng bị gián đoạn
                    e.printStackTrace();
                }
                Intent intent = new Intent(BookCoach.this, Chat.class);

                startActivity(intent);
             }
        });


        btnSelectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimePicker();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_book_coach);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }


    private void showDateTimePicker() {
        // Lấy thời gian hiện tại
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // Hiển thị DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, selectedYear, selectedMonth, selectedDay) -> {
            // Cập nhật ngày đã chọn
            calendar.set(selectedYear, selectedMonth, selectedDay);

            // Hiển thị TimePickerDialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, (timeView, selectedHour, selectedMinute) -> {
                // Cập nhật giờ đã chọn
                calendar.set(Calendar.HOUR_OF_DAY, selectedHour);
                calendar.set(Calendar.MINUTE, selectedMinute);

                // Hiển thị thời gian đã chọn
                 selectedDateTime = String.format("%02d/%02d/%04d %02d:%02d",
                        selectedDay, selectedMonth + 1, selectedYear, selectedHour, selectedMinute);
                tvSelectedTime.setText("Thời gian đã chọn: " + selectedDateTime);
            }, hour, minute, true);

            timePickerDialog.show();
        }, year, month, day);

        datePickerDialog.show();
    }


    private void fetchCoaches(String coachId) {
        apiService.getCoacheById(Long.parseLong(coachId)).enqueue(new Callback<Coache>() {
            @Override
            public void onResponse(Call<Coache> call, Response<Coache> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Coache coach = response.body();
                    // Xử lý kết quả
                    coachName=findViewById(R.id.coach_name);
                    description=findViewById(R.id.description);
                    henkham=findViewById(R.id.henkham);
                    goikham=findViewById(R.id.goikham);
                    tuvan=findViewById(R.id.tuvan);
                    chungchi=findViewById(R.id.certiffil);
                    btn_book=findViewById(R.id.btn_book_appointment);


                    coachName.setText(coach.getFullname());
                    description.setText(coach.getDescription());
                    btn_book.setText("Book Appointment ($"+ coach.getPriceperhour()+")");
                    chungchi.setText(coach.getSpecialties());
                } else {
                    System.err.println("Response failed: " + response.code());

                    Toast.makeText(BookCoach.this, "coach name"+response.code(), Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onFailure(Call<Coache> call, Throwable t) {
                Toast.makeText(BookCoach.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("API_ERROR", t.getMessage());
            }
        });

    }

    private void createSchedule(ScheduleDto scheduleDto) {
        apiServiceSchedule.createSchedule(scheduleDto).enqueue(new Callback<Schedule>() {
            @Override
            public void onResponse(Call<Schedule> call, Response<Schedule> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Schedule schedule = response.body();
                    // Xử lý kết quả

                } else {
                    System.err.println("Response failed: " + response.code());

                    Toast.makeText(BookCoach.this, "coach name"+response.code(), Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onFailure(Call<Schedule> call, Throwable t) {
                Toast.makeText(BookCoach.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("API_ERROR", t.getMessage());
            }
        });

    }

}