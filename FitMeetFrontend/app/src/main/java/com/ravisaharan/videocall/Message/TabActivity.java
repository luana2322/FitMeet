package com.ravisaharan.videocall.Message;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.ravisaharan.videocall.ApiClient;
import com.ravisaharan.videocall.CoachAdapter;
import com.ravisaharan.videocall.R;
import com.ravisaharan.videocall.adapter.ViewPagerAdapter;
import com.ravisaharan.videocall.api.ApiServiceCoach;
import com.ravisaharan.videocall.api.ApiServiceStudent;
import com.ravisaharan.videocall.api.ApiServiceUser;
import com.ravisaharan.videocall.bookcoach.BookCoach;
import com.ravisaharan.videocall.fragment.ChatFragment;
import com.ravisaharan.videocall.fragment.UserFragment;
import com.ravisaharan.videocall.home.HomeActivity;
import com.ravisaharan.videocall.model.Coache;
import com.ravisaharan.videocall.model.Student;
import com.ravisaharan.videocall.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TabActivity extends AppCompatActivity {

    private ApiServiceUser apiServiceUser;
    User currentUser1;
    private ApiServiceCoach apiServiceCoach;
    private ApiServiceStudent apiServiceStudent;
    Coache coache;
    Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tab);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });


        apiServiceUser = ApiClient.getRetrofitInstance().create(ApiServiceUser.class);
        apiServiceCoach = ApiClient.getRetrofitInstance().create(ApiServiceCoach.class);
        apiServiceStudent = ApiClient.getRetrofitInstance().create(ApiServiceStudent.class);


        //find views
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager viewPager = findViewById(R.id.view_pager);
        //find views ends

        //get extras
//        User currentUser = (User)getIntent().getSerializableExtra("CurrentUserKey");

        //get extras ends
//        User currentUser=fetchUser(34);

        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        int userId = sharedPreferences.getInt("studentloggedid", -1);

        SharedPreferences sharedPreferences1 = getSharedPreferences("UserSession", MODE_PRIVATE);
        int coachId = sharedPreferences.getInt("coacheloggedid", -1);
        if (userId != -1) {
            // Fetch students from API

            fetchgetStudent(userId, new StudentFetchCallback() {
                @Override
                public void onStudentFetched(Student user) {
                    // Khi dữ liệu người dùng đã được lấy thành công từ API, cập nhật giao diện
                    student = user;

                    fetchUserID(student.getFullname().trim(), new UserFetchCallback() {
                        @Override
                        public void onUserFetched(User user) {
                            // Khi dữ liệu người dùng đã được lấy thành công từ API, cập nhật giao diện
                            currentUser1 = user;


                            ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
                            ChatFragment chatFragment = new ChatFragment();
                            UserFragment userFragment = new UserFragment();
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("CurrentUserKey", currentUser1);
                            userFragment.setArguments(bundle);



                            viewPagerAdapter.addFragment(userFragment, "user");

                            //adding new tabs ends
                            viewPager.setAdapter(viewPagerAdapter);
                            tabLayout.setupWithViewPager(viewPager);


                        }

                        @Override
                        public void onFailure(String errorMessage) {
                            // Xử lý lỗi khi không thể lấy người dùng
                            Toast.makeText(TabActivity.this, "Không thể lấy dữ liệu: " + errorMessage, Toast.LENGTH_SHORT).show();
                        }

                    });

                }

                @Override
                public void onFailure(String errorMessage) {
                    // Xử lý lỗi khi không thể lấy người dùng
                    Toast.makeText(TabActivity.this, "Không thể lấy dữ liệu: " + errorMessage, Toast.LENGTH_SHORT).show();
                }

            });
        } else {
            // Fetch students from API
            fetchgetCoach(coachId, new CoachFetchCallback() {
                @Override
                public void onCoachFetched(Coache user) {
                    // Khi dữ liệu người dùng đã được lấy thành công từ API, cập nhật giao diện
                    coache = user;
                    // Cập nhật giao diện hoặc dữ liệu trong ứng dụng nếu cần
                    Toast.makeText(TabActivity.this, "", Toast.LENGTH_SHORT).show();

                    fetchUserID(coache.getFullname().trim(), new UserFetchCallback() {
                        @Override
                        public void onUserFetched(User user) {
                            // Khi dữ liệu người dùng đã được lấy thành công từ API, cập nhật giao diện
                            currentUser1 = user;


                            ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
                            ChatFragment chatFragment = new ChatFragment();
                            UserFragment userFragment = new UserFragment();
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("CurrentUserKey", currentUser1);
                            userFragment.setArguments(bundle);



                            viewPagerAdapter.addFragment(userFragment, "user");

                            //adding new tabs ends
                            viewPager.setAdapter(viewPagerAdapter);
                            tabLayout.setupWithViewPager(viewPager);


                        }

                        @Override
                        public void onFailure(String errorMessage) {
                            // Xử lý lỗi khi không thể lấy người dùng
                            Toast.makeText(TabActivity.this, "Không thể lấy dữ liệu: " + errorMessage, Toast.LENGTH_SHORT).show();
                        }

                    });
                }

                @Override
                public void onFailure(String errorMessage) {
                    // Xử lý lỗi khi không thể lấy người dùng
                    Toast.makeText(TabActivity.this, "Không thể lấy dữ liệu: " + errorMessage, Toast.LENGTH_SHORT).show();
                }

            });
        }


//        int userIdtossuccessChat=34;
//        fetchUser(userIdtossuccessChat, new UserFetchCallback() {
//            @Override
//            public void onUserFetched(User user) {
//                // Khi dữ liệu người dùng đã được lấy thành công từ API, cập nhật giao diện
//                currentUser1 = user;
//                // Cập nhật giao diện hoặc dữ liệu trong ứng dụng nếu cần
//                ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
//
//                //adding new tabs
//
//                ChatFragment chatFragment = new ChatFragment();
//                UserFragment userFragment = new UserFragment();
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("CurrentUserKey",currentUser1);
//                userFragment.setArguments(bundle);
//
//
////        viewPagerAdapter.addFragment(chatFragment,"chat");
//                viewPagerAdapter.addFragment(userFragment,"user");
//
//                //adding new tabs ends
//                viewPager.setAdapter(viewPagerAdapter);
//                tabLayout.setupWithViewPager(viewPager);
//
//            }
//
//            @Override
//            public void onFailure(String errorMessage) {
//                // Xử lý lỗi khi không thể lấy người dùng
//                Toast.makeText(TabActivity.this, "Không thể lấy dữ liệu: " + errorMessage, Toast.LENGTH_SHORT).show();
//            }
//
//        });


    }


    //    private User fetchUser(int userId) {
//        apiServiceUser.finduserbyid(userId).enqueue(new Callback<User>() {
//            @Override
//            public void onResponse(Call<User> call, Response<User> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    // Gán dữ liệu từ API vào danh sách coach
//                    currentUser1 = response.body();
//
//                    // Khởi tạo adapter với dữ liệu
// } else {
//                    Toast.makeText(TabActivity.this, "Không thể lấy dữ liệu", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<User> call, Throwable t) {
//                Toast.makeText(TabActivity.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//                Log.e("API_ERROR", t.getMessage());
//            }
//        });
//        return currentUser1;
//    }
    private void fetchUserID(String userName, UserFetchCallback callback) {
        apiServiceUser.findByName(userName).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Gọi callback để trả về dữ liệu người dùng
                    callback.onUserFetched(response.body());
                } else {
                    callback.onFailure("Không có dữ liệu");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }

    private interface UserFetchCallback {
        void onUserFetched(User user);

        void onFailure(String errorMessage);
    }

    // Hàm fetch user với callback
    private void fetchUser(int userId, UserFetchCallback callback) {
        apiServiceUser.finduserbyid(userId).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Gọi callback để trả về dữ liệu người dùng
                    callback.onUserFetched(response.body());
                } else {
                    callback.onFailure("Không có dữ liệu");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }

    private interface StudentFetchCallback {
        void onStudentFetched(Student user);

        void onFailure(String errorMessage);
    }

    private void fetchgetStudent(int userId, StudentFetchCallback callback) {
        apiServiceStudent.getStudentById((long) userId).enqueue(new Callback<Student>() {
            @Override
            public void onResponse(Call<Student> call, Response<Student> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Gọi callback để trả về dữ liệu người dùng
                    callback.onStudentFetched(response.body());
                } else {
                    callback.onFailure("Không có dữ liệu");
                }
            }

            @Override
            public void onFailure(Call<Student> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }

    private interface CoachFetchCallback {
        void onCoachFetched(Coache user);

        void onFailure(String errorMessage);
    }

    private void fetchgetCoach(int userId, CoachFetchCallback callback) {
        apiServiceCoach.getCoacheById((long) userId).enqueue(new Callback<Coache>() {
            @Override
            public void onResponse(Call<Coache> call, Response<Coache> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Gọi callback để trả về dữ liệu người dùng
                    callback.onCoachFetched(response.body());
                } else {
                    callback.onFailure("Không có dữ liệu");
                }
            }

            @Override
            public void onFailure(Call<Coache> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }


}