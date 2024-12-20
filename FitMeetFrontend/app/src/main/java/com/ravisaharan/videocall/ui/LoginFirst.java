package com.ravisaharan.videocall.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.RequestCallback;
import com.ravisaharan.videocall.ApiClient;
import com.ravisaharan.videocall.CoachAdapter;
import com.ravisaharan.videocall.R;
import com.ravisaharan.videocall.api.ApiServiceCoach;
import com.ravisaharan.videocall.api.ApiServiceStudent;
import com.ravisaharan.videocall.bookcoach.BookCoach;
import com.ravisaharan.videocall.databinding.ActivityLoginBinding;
import com.ravisaharan.videocall.databinding.ActivityLoginFirstBinding;
import com.ravisaharan.videocall.dto.CoachDto;
import com.ravisaharan.videocall.dto.StudentDto;
import com.ravisaharan.videocall.home.HomeActivity;
import com.ravisaharan.videocall.model.Coache;
import com.ravisaharan.videocall.model.Student;
import com.ravisaharan.videocall.repository.MainRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFirst extends AppCompatActivity {
    private Spinner spinnerRole;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private ApiServiceCoach apiServiceCoach;
    private ApiServiceStudent apiServiceStudent;
    private Student studentlogged;
    private Coache coachelogged;
    MainRepository mainRepository;

    ActivityLoginFirstBinding loginBinding;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_first);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        spinnerRole = findViewById(R.id.spinnerRole);
        usernameEditText = findViewById(R.id.editTextUsername);
        passwordEditText = findViewById(R.id.editTextPassword);
        loginButton = findViewById(R.id.buttonlogin);

        setupRoleSpinner();

        apiServiceCoach = ApiClient.getRetrofitInstance().create(ApiServiceCoach.class);
        apiServiceStudent = ApiClient.getRetrofitInstance().create(ApiServiceStudent.class);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performLogin();
            }
        });

    }

    private void setupRoleSpinner() {
        // Cài đặt dữ liệu cho spinner (giả sử có 3 vai trò: Admin, User, Guest)
        String[] roles = new String[]{"Coach", "Student"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, roles);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRole.setAdapter(adapter);
    }

    private void performLogin() {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String selectedRole = spinnerRole.getSelectedItem().toString(); // Lấy vai trò đã chọn

        // Kiểm tra nếu các trường thông tin không trống
        if (username.isEmpty() || password.isEmpty()) {
            // Hiển thị thông báo lỗi nếu có trường thông tin trống
            Toast.makeText(this, "Username and password cannot be empty.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kiểm tra vai trò và thực hiện hành động tương ứng
        if (selectedRole.equals("Coach")) {
            // Xử lý login cho Coach
            loginAsCoach(username, password);
        } else if (selectedRole.equals("Student")) {
            // Xử lý login cho Student
            loginAsStudent(username, password);
        }
    }

    private void loginAsCoach(String username, String password) {
        // Logic login cho Coach (ví dụ: kiểm tra tài khoản Coach)
        // Bạn có thể thực hiện xác thực với API hoặc cơ sở dữ liệu
        // Sau khi login thành công, chuyển sang màn hình Coach

        CoachDto coachDto=new CoachDto();
        coachDto.setCoachName(username);
        coachDto.setPassworddto(password);

        apiServiceCoach.loginCoach(coachDto).enqueue(new Callback<Coache>() {
            @Override
            public void onResponse(Call<Coache> call, Response<Coache> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Gán dữ liệu từ API vào danh sách coach
                    coachelogged = response.body();

                    Intent intent = new Intent(LoginFirst.this, HomeActivity.class);

                    //                    set session
                    SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("coacheloggedid",Math.toIntExact(coachelogged.getCoachId())); // Ví dụ: ID người dùng
                    editor.putInt("studentloggedid",-1); // Ví dụ: ID người dùng
                    editor.apply();

                 init(username);
                    startActivity(intent);
//                                    startActivity(new Intent(LoginFirst.this,HomeActivity.class));

                } else {
                    Toast.makeText(LoginFirst.this, "Không thể lấy dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Coache> call, Throwable t) {
                Toast.makeText(LoginFirst.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("API_ERROR", t.getMessage());
            }

        });



    }

    private void loginAsStudent(String username, String password) {
        StudentDto studentDto=new StudentDto();
        studentDto.setStudentName(username);
        studentDto.setPassworddto(password);
        // Logic login cho Student (ví dụ: kiểm tra tài khoản Student)
        // Bạn có thể thực hiện xác thực với API hoặc cơ sở dữ liệu
        // Sau khi login thành công, chuyển sang màn hình Student

        apiServiceStudent.loginStudent(studentDto).enqueue(new Callback<Student>() {
            @Override
            public void onResponse(Call<Student> call, Response<Student> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Gán dữ liệu từ API vào danh sách coach
                    studentlogged = response.body();


//                    set session
                    SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("studentloggedid", studentlogged.getStudent_id()); // Ví dụ: ID người dùng
                    editor.putInt("coacheloggedid",-1); // Ví dụ: ID người dùng
                    editor.apply();
                //                  end  set session

                    init(username);

                } else {
                    Toast.makeText(LoginFirst.this, "Không thể lấy dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Student> call, Throwable t) {
                Toast.makeText(LoginFirst.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("API_ERROR", t.getMessage());
            }

        });



        // Chuyển đến activity tiếp theo cho Student
        // Intent intent = new Intent(this, StudentActivity.class);
        // startActivity(intent);
    }

    public void init(String username){
        mainRepository= MainRepository.getInstance();

            PermissionX.init(this).
                    permissions(android.Manifest.permission.CAMERA,android.Manifest.permission.RECORD_AUDIO).
                    request(new RequestCallback() {
                        @Override
                        public void onResult(boolean allGranted, @NonNull List<String> grantedList, @NonNull List<String> deniedList) {
                            if(allGranted){
                                //login in here
                                mainRepository.login(username,getApplicationContext(),()->{
                                    //if successful then move to call activity

                                    Intent intent = new Intent(LoginFirst.this, HomeActivity.class);


//                                    intent.putExtra("studentloggedid", String.valueOf(studentlogged.getStudent_id()));
                                    startActivity(intent);

//                                    startActivity(new Intent(LoginFirst.this,CallActivity.class)  );



                                });
                            }
                        }
                    });



    }




}