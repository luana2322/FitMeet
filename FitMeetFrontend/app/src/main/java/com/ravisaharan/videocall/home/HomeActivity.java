package com.ravisaharan.videocall.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ravisaharan.videocall.ApiClient;
import com.ravisaharan.videocall.CoachAdapter;
import com.ravisaharan.videocall.R;
import com.ravisaharan.videocall.api.ApiServiceCoach;
import com.ravisaharan.videocall.bookcoach.BookCoach;
import com.ravisaharan.videocall.chat.Chat;
import com.ravisaharan.videocall.databinding.ActivityHomeBinding;
import com.ravisaharan.videocall.model.Coache;
import com.ravisaharan.videocall.notification.Notification;
import com.ravisaharan.videocall.profile.ProfileActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity
{
    ActivityHomeBinding biding;
    private RecyclerView recyclerView;
    private CoachAdapter coachAdapter;
    private List<Coache> coacheList = new ArrayList<>();
    private ApiServiceCoach apiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setSelectedItemId(R.id.home);



        // Initialize RecyclerView and Adapter
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize Retrofit API service
        apiService = ApiClient.getRetrofitInstance().create(ApiServiceCoach.class);

//get session data to phân luồng user
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        int userId = sharedPreferences.getInt("studentloggedid", -1);

        SharedPreferences sharedPreferences1 = getSharedPreferences("UserSession", MODE_PRIVATE);
        int coachId = sharedPreferences.getInt("coacheloggedid", -1);


        if (userId!=-1){
    // Fetch students from API
    fetchCoaches();
}else{
    Toast.makeText(this, "Đây là Coach", Toast.LENGTH_SHORT).show();
}


        bottomNavigationView.setOnItemSelectedListener(
                item -> {
                    int itemId = item.getItemId(); /* obtain the selected item ID from your source */
                    Fragment selectedFragment = null;

                    if (itemId == R.id.chat) {
                        Intent intent = new Intent(HomeActivity.this, Chat.class);
                        startActivity(intent);
                    } else if (itemId == R.id.profile) {
                        Intent intent1 = new Intent(HomeActivity.this, ProfileActivity.class);
                        startActivity(intent1);
                    } else if (itemId == R.id.schedule) {
                        Intent intent1 = new Intent(HomeActivity.this, Notification.class);
                        startActivity(intent1);

                    } else {
//                    selectedFragment = new HomePage();
                    }

                    return true;
                });

    }


    private void fetchCoaches() {
        apiService.getCoachs().enqueue(new Callback<List<Coache>>() {
            @Override
            public void onResponse(Call<List<Coache>> call, Response<List<Coache>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Gán dữ liệu từ API vào danh sách coach
                    coacheList = response.body();

                    // Khởi tạo adapter với dữ liệu
                    coachAdapter = new CoachAdapter(HomeActivity.this, coacheList, new CoachAdapter.OnItemActionListener() {
                        @Override
                        public void onEdit(Coache coache) {

                            Intent intent = new Intent(HomeActivity.this, BookCoach.class);

                            intent.putExtra("coachId", coache.getCoachId().toString());
                            startActivity(intent);

                        }

                        @Override
                        public void onDelete(Coache coache) {
                            Toast.makeText(HomeActivity.this, "Xóa: " + coache.getFullname(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    recyclerView.setAdapter(coachAdapter); // Gán adapter cho RecyclerView
                } else {
                    Toast.makeText(HomeActivity.this, "Không thể lấy dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Coache>> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("API_ERROR", t.getMessage());
            }
        });
    }




}