package com.ravisaharan.videocall.chat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ravisaharan.videocall.ApiClient;
import com.ravisaharan.videocall.adapter.CoachAdapterChat;
import com.ravisaharan.videocall.adapter.StudentAdapterChat;
import com.ravisaharan.videocall.api.ApiServiceCoach;
import com.ravisaharan.videocall.api.ApiServiceStudent;
import com.ravisaharan.videocall.databinding.ActivityChatBinding;

import com.ravisaharan.videocall.R;
import com.ravisaharan.videocall.home.HomeActivity;
import com.ravisaharan.videocall.model.Coache;
import com.ravisaharan.videocall.model.Student;
import com.ravisaharan.videocall.notification.Notification;
import com.ravisaharan.videocall.profile.ProfileActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Chat extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityChatBinding binding;

    private RecyclerView recyclerView;
    private CoachAdapterChat coachAdapterchat;
    private List<Coache> coacheList = new ArrayList<>();
    private ApiServiceCoach apiServiceCoach;

    private ApiServiceStudent apiServiceStudent;
    private StudentAdapterChat studentAdapterchat;
    private List<Student> studentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_chat);
//        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);



        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setSelectedItemId(R.id.chat);


        bottomNavigationView.setOnItemSelectedListener(
                item -> {
                    int itemId = item.getItemId(); /* obtain the selected item ID from your source */
                    Fragment selectedFragment = null;

                    if (itemId == R.id.chat) {
                        Intent intent = new Intent(Chat.this, Chat.class);
                        startActivity(intent);
                    } else if (itemId == R.id.profile) {
                        Intent intent1 = new Intent(Chat.this, ProfileActivity.class);
                        startActivity(intent1);
                    } else if (itemId == R.id.schedule) {
                        Intent intent1 = new Intent(Chat.this, Notification.class);
                        startActivity(intent1);

                    }else if (itemId == R.id.home) {
                        Intent intent1 = new Intent(Chat.this, HomeActivity.class);
                        startActivity(intent1);

                    } else {
//                    selectedFragment = new HomePage();
                    }

                    return true;
                });


//get session data to phân luồng user
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        int userId = sharedPreferences.getInt("studentloggedid", -1);

        SharedPreferences sharedPreferences1 = getSharedPreferences("UserSession", MODE_PRIVATE);
        int coachId = sharedPreferences.getInt("coacheloggedid", -1);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        apiServiceCoach = ApiClient.getRetrofitInstance().create(ApiServiceCoach.class);
        apiServiceStudent = ApiClient.getRetrofitInstance().create(ApiServiceStudent.class);


//        Toast.makeText(this, String.valueOf(coachId), Toast.LENGTH_SHORT).show();
        if (userId!=-1){
            // Fetch students from API

            fetchChatforStudent(userId);
        }else{
            // Fetch students from API
            fetchChatforCoach(coachId);
        }


    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_chat);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }


    private void fetchChatforCoach(int studentId) {
        apiServiceStudent.getListStudentChat((long)studentId).enqueue(new Callback<List<Student>>() {
            @Override
            public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Gán dữ liệu từ API vào danh sách coach
                    studentList = response.body();

                    // Khởi tạo adapter với dữ liệu
                    studentAdapterchat = new StudentAdapterChat(Chat.this, studentList, new StudentAdapterChat.OnItemActionListener() {
                        @Override
                        public void onEdit(Student student) {

                            Intent intent = new Intent(Chat.this, ChatContent.class);

                            intent.putExtra("studentName",String.valueOf(student.getStudent_id()));
                            startActivity(intent);

                        }

                        @Override
                        public void onDelete(Student student) {
                            Toast.makeText(Chat.this, "Xóa: " + student.getFullname(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    recyclerView.setAdapter(studentAdapterchat); // Gán adapter cho RecyclerView
                } else {
                    Toast.makeText(Chat.this, "Không thể lấy dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Student>> call, Throwable t) {
                Toast.makeText(Chat.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("API_ERROR", t.getMessage());
            }
        });
    }

    private void fetchChatforStudent(int studentId) {
        apiServiceCoach.getListCoachChat((long)studentId).enqueue(new Callback<List<Coache>>() {
            @Override
            public void onResponse(Call<List<Coache>> call, Response<List<Coache>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Gán dữ liệu từ API vào danh sách coach
                    coacheList = response.body();

                    // Khởi tạo adapter với dữ liệu
                    coachAdapterchat = new CoachAdapterChat(Chat.this, coacheList, new CoachAdapterChat.OnItemActionListener() {
                        @Override
                        public void onEdit(Coache coache) {

                            Intent intent1 = new Intent(Chat.this, ChatContent.class);
//                            Toast.makeText(Chat.this, "adscss"+ String.valueOf(coache.getCoachId()), Toast.LENGTH_SHORT).show();
                            intent1.putExtra("coachName", String.valueOf(coache.getCoachId()));
                            startActivity(intent1);

                        }

                        @Override
                        public void onDelete(Coache coache) {
                            Toast.makeText(Chat.this, "Xóa: " + coache.getFullname(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    recyclerView.setAdapter(coachAdapterchat); // Gán adapter cho RecyclerView
                } else {
                    Toast.makeText(Chat.this, "Không thể lấy dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Coache>> call, Throwable t) {
                Toast.makeText(Chat.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("API_ERROR", t.getMessage());
            }
        });
    }



}