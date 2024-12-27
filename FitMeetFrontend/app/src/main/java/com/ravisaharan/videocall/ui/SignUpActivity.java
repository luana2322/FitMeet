package com.ravisaharan.videocall.ui;

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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ravisaharan.videocall.ApiClient;
import com.ravisaharan.videocall.R;
import com.ravisaharan.videocall.api.ApiServiceCoach;
import com.ravisaharan.videocall.api.ApiServiceStudent;
import com.ravisaharan.videocall.dto.CoachDto;
import com.ravisaharan.videocall.dto.CoachSigupDto;
import com.ravisaharan.videocall.home.HomeActivity;
import com.ravisaharan.videocall.model.Coache;
import com.ravisaharan.videocall.model.Student;
import com.ravisaharan.videocall.model.User;
import com.ravisaharan.videocall.repository.MainRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    private Spinner spinnerRole;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText EmailEditText;
    private EditText passwordRepeatEditText;
    private Button loginButton;
    private ApiServiceCoach apiServiceCoach;
    private ApiServiceStudent apiServiceStudent;
    private Student studentlogged;
    private Coache coachelogged;
    MainRepository mainRepository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setupRoleSpinner();
        spinnerRole = findViewById(R.id.spinnerRole);
        usernameEditText = findViewById(R.id.editTextUsername);
        passwordEditText = findViewById(R.id.editTextPassword);
        EmailEditText = findViewById(R.id.editTextEmail);
        passwordRepeatEditText = findViewById(R.id.editTextRepeatPassword);
        loginButton = findViewById(R.id.buttonlogin);
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
        String email = EmailEditText.getText().toString();
        String passwordrepeat = passwordRepeatEditText.getText().toString();
        String selectedRole = spinnerRole.getSelectedItem().toString(); // Lấy vai trò đã chọn

        // Kiểm tra nếu các trường thông tin không trống
        if (username.isEmpty() || password.isEmpty()) {
            // Hiển thị thông báo lỗi nếu có trường thông tin trống
            Toast.makeText(this, "Username and password cannot be empty.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kiểm tra vai trò và thực hiện hành động tương ứng
        if (selectedRole.equals("Coach")) {
CoachSigupDto coachSigupDto=new CoachSigupDto();
coachSigupDto.setCoachName(username);
coachSigupDto.setEmailcoach(email);
coachSigupDto.setPassworddtoRepeat(passwordrepeat);
coachSigupDto.setPassworddto(password);

            // Xử lý login cho Coach
            SignUpAsCoach(coachSigupDto);
        } else if (selectedRole.equals("Student")) {
            // Xử lý login cho Student
//            loginAsStudent(username, password);
        }
    }

    private void SignUpAsCoach(CoachSigupDto coachSigupDto) {
        // Logic login cho Coach (ví dụ: kiểm tra tài khoản Coach)
        // Bạn có thể thực hiện xác thực với API hoặc cơ sở dữ liệu
        // Sau khi login thành công, chuyển sang màn hình Coach



        apiServiceCoach.signUpCoach(coachSigupDto).enqueue(new Callback<Coache>() {
            @Override
            public void onResponse(Call<Coache> call, Response<Coache> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Gán dữ liệu từ API vào danh sách coach
                    coachelogged = response.body();

                    Intent intent = new Intent(SignUpActivity.this, LoginFirst.class);

                    startActivity(intent);
//                                    startActivity(new Intent(LoginFirst.this,HomeActivity.class));

                } else {
                    Toast.makeText(SignUpActivity.this, response.body().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Coache> call, Throwable t) {
                Toast.makeText(SignUpActivity.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("API_ERROR", t.getMessage());
            }

        });



    }


}