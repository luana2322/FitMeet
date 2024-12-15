package com.ravisaharan.videocall.notification;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ravisaharan.videocall.ApiClient;
import com.ravisaharan.videocall.R;
import com.ravisaharan.videocall.adapter.CoachAdapterChat;
import com.ravisaharan.videocall.adapter.CoachNotificationAdapter;
import com.ravisaharan.videocall.adapter.StudentAdapterChat;
import com.ravisaharan.videocall.adapter.StudentNotificationAdapter;
import com.ravisaharan.videocall.api.ApiServiceCoach;
import com.ravisaharan.videocall.api.ApiServiceStudent;
import com.ravisaharan.videocall.chat.Chat;
import com.ravisaharan.videocall.chat.ChatContent;
import com.ravisaharan.videocall.home.HomeActivity;
import com.ravisaharan.videocall.model.CoachNotification;
import com.ravisaharan.videocall.model.Coache;
import com.ravisaharan.videocall.model.Student;
import com.ravisaharan.videocall.model.StudentNotification;
import com.ravisaharan.videocall.profile.ProfileActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Notification extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<CoachNotification> coachNotificationlist = new ArrayList<>();
    private ApiServiceCoach apiServiceCoach;

    private ApiServiceStudent apiServiceStudent;
    private List<StudentNotification> studentNotificationlist = new ArrayList<>();
    private StudentNotificationAdapter studentNotificationAdapter;
    private CoachNotificationAdapter coachNotificationAdapter;
    ImageView back_button;
    BottomNavigationView bottomNavigationView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_notification);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });


//get session data to phân luồng user
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        int userId = sharedPreferences.getInt("studentloggedid", -1);

        SharedPreferences sharedPreferences1 = getSharedPreferences("UserSession", MODE_PRIVATE);
        int coachId = sharedPreferences.getInt("coacheloggedid", -1);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        apiServiceCoach = ApiClient.getRetrofitInstance().create(ApiServiceCoach.class);
        apiServiceStudent = ApiClient.getRetrofitInstance().create(ApiServiceStudent.class);

        back_button=findViewById(R.id.backtohome);



         bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setSelectedItemId(R.id.schedule);

        bottomNavigationView.setOnItemSelectedListener(
                item -> {
                    int itemId = item.getItemId(); /* obtain the selected item ID from your source */
                    Fragment selectedFragment = null;

                    if (itemId == R.id.chat) {
                        Intent intent = new Intent(Notification.this, Chat.class);
                        startActivity(intent);
                    } else if (itemId == R.id.profile) {
                        Intent intent1 = new Intent(Notification.this, ProfileActivity.class);
                        startActivity(intent1);
                    } else if (itemId == R.id.schedule) {
                        Intent intent1 = new Intent(Notification.this, Notification.class);
                        startActivity(intent1);

                    } else if (itemId == R.id.home) {
                        Intent intent1 = new Intent(Notification.this, HomeActivity.class);
                        startActivity(intent1);

                    } else {
//                    selectedFragment = new HomePage();
                    }

                    return true;
                });
//        Toast.makeText(this, String.valueOf(coachId), Toast.LENGTH_SHORT).show();
        if (userId!=-1){
            // Fetch students from API

            fetchNotificationforStudent(userId);
        }else{
            // Fetch students from API
            fetchNotificationforCoach(coachId);
        }


    }




    private void fetchNotificationforStudent(int studentId) {
        apiServiceStudent.getListStudentNotification((long)studentId).enqueue(new Callback<List<StudentNotification>>() {
            @Override
            public void onResponse(Call<List<StudentNotification>> call, Response<List<StudentNotification>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Gán dữ liệu từ API vào danh sách coach
                    studentNotificationlist = response.body();

                    // Khởi tạo adapter với dữ liệu
                    studentNotificationAdapter = new StudentNotificationAdapter(Notification.this, studentNotificationlist, new StudentNotificationAdapter.OnItemActionListener() {

                        @Override
                        public void onEdit(StudentNotification coache) {


                        }

                        @Override
                        public void onDelete(StudentNotification coache) {

                        }
                    });
                    recyclerView.setAdapter(studentNotificationAdapter); // Gán adapter cho RecyclerView

                 } else {
                    Toast.makeText(Notification.this, "Không thể lấy dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<StudentNotification>> call, Throwable t) {
                Toast.makeText(Notification.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("API_ERROR", t.getMessage());
            }
        });
    }

    private void fetchNotificationforCoach(int studentId) {
        apiServiceCoach.getListCoachNotification((long)studentId).enqueue(new Callback<List<CoachNotification>>() {
            @Override
            public void onResponse(Call<List<CoachNotification>> call, Response<List<CoachNotification>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Gán dữ liệu từ API vào danh sách coach
                    coachNotificationlist = response.body();

                    // Khởi tạo adapter với dữ liệu
                    coachNotificationAdapter = new CoachNotificationAdapter(Notification.this, coachNotificationlist, new CoachNotificationAdapter.OnItemActionListener() {

                        @Override
                        public void onEdit(CoachNotification coache) {


                        }

                        @Override
                        public void onDelete(CoachNotification coache) {

                        }
                    });
                    recyclerView.setAdapter(coachNotificationAdapter); // Gán adapter cho RecyclerView
                } else {
                    Toast.makeText(Notification.this, "Không thể lấy dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<CoachNotification>> call, Throwable t) {
                Toast.makeText(Notification.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("API_ERROR", t.getMessage());
            }
        });
    }


}