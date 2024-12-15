package com.ravisaharan.videocall.chat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.ravisaharan.videocall.R;
import com.ravisaharan.videocall.api.ApiServiceCoach;
import com.ravisaharan.videocall.api.ApiServiceStudent;
import com.ravisaharan.videocall.bookcoach.BookCoach;
import com.ravisaharan.videocall.databinding.ActivityCallBinding;
import com.ravisaharan.videocall.databinding.ActivityChatContentBinding;
import com.ravisaharan.videocall.home.HomeActivity;
import com.ravisaharan.videocall.model.Coache;
import com.ravisaharan.videocall.model.Student;
import com.ravisaharan.videocall.repository.MainRepository;
import com.ravisaharan.videocall.ui.CallActivity;
import com.ravisaharan.videocall.ui.LoginActivity;
import com.ravisaharan.videocall.utils.DataModelType;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatContent extends AppCompatActivity implements MainRepository.Listener {
    ActivityChatContentBinding callBinding;
    MainRepository mainRepository;

    public boolean isCameraMuted = false;
    public boolean isMicMuted = false;
    ImageView call_icon;
    String callName = "";
    String id_people_call;
    TextView nameChat;
    private ApiServiceCoach apiServiceCoach;

    private ApiServiceStudent apiServiceStudent;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat_content);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        callBinding = ActivityChatContentBinding.inflate(getLayoutInflater());
        setContentView(callBinding.getRoot());


        call_icon = findViewById(R.id.callvideoicon);
        nameChat=findViewById(R.id.tv_title);
        apiServiceCoach = ApiClient.getRetrofitInstance().create(ApiServiceCoach.class);
        apiServiceStudent = ApiClient.getRetrofitInstance().create(ApiServiceStudent.class);


        //get session data to phân luồng user
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        int userId = sharedPreferences.getInt("studentloggedid", -1);

        SharedPreferences sharedPreferences1 = getSharedPreferences("UserSession", MODE_PRIVATE);
        int coachId = sharedPreferences.getInt("coacheloggedid", -1);

        Intent intent = getIntent();

        if (intent.getStringExtra("studentName") != null) {
            id_people_call = intent.getStringExtra("studentName");
            fetchnameCallforCoach(id_people_call, () -> {
                // Thực hiện hành động khi đã có callName
                nameChat.setText(callName);
            });
        }

        if (intent.getStringExtra("coachName") != null) {
            id_people_call = intent.getStringExtra("coachName");
            fetchnameCallforStudent(id_people_call, () -> {
                // Thực hiện hành động khi đã có callName
//                Toast.makeText(this, "Coach Name: " + callName, Toast.LENGTH_SHORT).show();
            });
        }
//initlogin(callName);

        mainRepository = MainRepository.getInstance();
        call_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý khi ImageView được click
                init(callName);
            }
        });

    }


    public void init(String username) {

//        callBinding.callBtn.setOnClickListener(v->{
//            //make call request
//            mainRepository.sendCallRequest(callBinding.targetUserNameEt.getText().toString(),()->{
//                Toast.makeText(this, "Couldn't find the above target", Toast.LENGTH_SHORT).show();
//            });
//        });
        //make call request
        mainRepository.sendCallRequest(username, () -> {
            Toast.makeText(this, "Couldn't find the above target", Toast.LENGTH_SHORT).show();
        });

        mainRepository.initLocalView(callBinding.localView);
        mainRepository.initRemoteView(callBinding.remoteView);
        mainRepository.listener = this;

        mainRepository.subscribeForLatestEvent(data -> {
            if (data.getType() == DataModelType.StartCall) {
                runOnUiThread(() -> {
                    String text = data.getSender() + " is calling you..";
                    callBinding.incomingNameTV.setText(text);
                    callBinding.incomingCallLayout.setVisibility(View.VISIBLE);
                    callBinding.acceptButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //start call
                            mainRepository.startCall(data.getSender());
                            callBinding.incomingCallLayout.setVisibility(View.GONE);
                        }
                    });
                    callBinding.rejectButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            callBinding.incomingCallLayout.setVisibility(View.GONE);
                        }
                    });
                });
            }
        });

        callBinding.switchCameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainRepository.switchCamera();
            }
        });

        callBinding.micButton.setOnClickListener(v -> {
            if (isMicMuted) {
                callBinding.micButton.setImageResource(R.drawable.ic_baseline_mic_off_24);
            } else {
                callBinding.micButton.setImageResource(R.drawable.ic_baseline_mic_24);
            }
            mainRepository.toggleAudio(isMicMuted);
            isMicMuted = !isMicMuted;
        });

        callBinding.videoButton.setOnClickListener(v -> {
            if (isCameraMuted) {
                callBinding.videoButton.setImageResource(R.drawable.ic_baseline_videocam_off_24);
            } else {
                callBinding.videoButton.setImageResource(R.drawable.ic_baseline_videocam_24);
            }
            mainRepository.toggleVideo(isCameraMuted);
            isCameraMuted = !isCameraMuted;
        });

        callBinding.endCallButton.setOnClickListener(v -> {
            mainRepository.endCall();
            finish();
        });

    }

    @Override
    public void webrtcConnected() {
        runOnUiThread(() -> {
            callBinding.incomingCallLayout.setVisibility(View.GONE);
            callBinding.whoToCallLayout.setVisibility(View.GONE);
            callBinding.callLayout.setVisibility(View.VISIBLE);
        });

    }

    @Override
    public void webrtcClose() {
        runOnUiThread(this::finish);
    }


    private void fetchnameCallforStudent(String coachId, Runnable onComplete) {
        apiServiceCoach.getCoacheById(Long.parseLong(coachId)).enqueue(new Callback<Coache>() {
            @Override
            public void onResponse(Call<Coache> call, Response<Coache> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Coache coach = response.body();
                    callName = coach.getFullname();

                    // Xử lý kết quả
                    if (onComplete != null) {
                        onComplete.run();
                    }
                } else {
                    System.err.println("Response failed: " + response.code());

                    Toast.makeText(ChatContent.this, "coach name" + response.code(), Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onFailure(Call<Coache> call, Throwable t) {
                Toast.makeText(ChatContent.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("API_ERROR", t.getMessage());
            }
        });

    }


    private void fetchnameCallforCoach(String coachId, Runnable onComplete) {
        apiServiceStudent.getStudentById(Long.parseLong(coachId)).enqueue(new Callback<Student>() {
            @Override
            public void onResponse(Call<Student> call, Response<Student> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Student student = response.body();
                    callName = student.getFullname();
                    // Xử lý kết quả
                    Log.d("API_SUCCESS", "Student name: " + callName);

                    // Gọi callback sau khi cập nhật xong
                    if (onComplete != null) {
                        onComplete.run();
                    }
                } else {
                    System.err.println("Response failed: " + response.code());

                    Toast.makeText(ChatContent.this, "coach name" + response.code(), Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onFailure(Call<Student> call, Throwable t) {
                Toast.makeText(ChatContent.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("API_ERROR", t.getMessage());
            }
        });

    }

    public void initlogin(String username) {
        mainRepository = MainRepository.getInstance();


        PermissionX.init(this).
                permissions(android.Manifest.permission.CAMERA, android.Manifest.permission.RECORD_AUDIO).
                request(new RequestCallback() {
                    @Override
                    public void onResult(boolean allGranted, @NonNull List<String> grantedList, @NonNull List<String> deniedList) {
                        if (allGranted) {
                            //login in here
                            mainRepository.login(username, getApplicationContext(), () -> {
                                //if successful then move to call activity

                            });
                        }
                    }
                });


    }

}