package com.ravisaharan.videocall.chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ravisaharan.videocall.R;
import com.ravisaharan.videocall.databinding.ActivityCallBinding;
import com.ravisaharan.videocall.databinding.ActivityVideoCallBinding;
import com.ravisaharan.videocall.repository.MainRepository;
import com.ravisaharan.videocall.utils.DataModelType;

public class VideoCall extends AppCompatActivity implements MainRepository.Listener{
    ActivityVideoCallBinding videocallBinding;
    MainRepository mainRepository;
    String coachName;
    public boolean isCameraMuted=false;
    public boolean isMicMuted=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_video_call);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        Intent intent1 = getIntent();
        coachName = intent1.getStringExtra("coachName");

        videocallBinding = ActivityVideoCallBinding.inflate(getLayoutInflater());
        setContentView(videocallBinding.getRoot());
        init();
    }

    public void init(){
        mainRepository=MainRepository.getInstance();
            //make call request
            mainRepository.sendCallRequest(coachName,()->{
                Toast.makeText(this, "Couldn't find the above target", Toast.LENGTH_SHORT).show();
            });

        mainRepository.initLocalView(videocallBinding.localView);
        mainRepository.initRemoteView(videocallBinding.remoteView);
        mainRepository.listener=this;

        mainRepository.subscribeForLatestEvent(data->{
            if(data.getType()== DataModelType.StartCall) {
                runOnUiThread(()->{
                    String text = data.getSender() + " is calling you..";
                    videocallBinding.incomingNameTV.setText(text);
                    videocallBinding.incomingCallLayout.setVisibility(View.VISIBLE);
                    videocallBinding.acceptButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //start call
                            mainRepository.startCall(data.getSender());
                            videocallBinding.incomingCallLayout.setVisibility(View.GONE);
                        }
                    });
                    videocallBinding.rejectButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            videocallBinding.incomingCallLayout.setVisibility(View.GONE);
                        }
                    });
                });
            }
        });

        videocallBinding.switchCameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainRepository.switchCamera();
            }
        });

        videocallBinding.micButton.setOnClickListener(v->{
            if(isMicMuted){
                videocallBinding.micButton.setImageResource(R.drawable.ic_baseline_mic_off_24);
            }else{
                videocallBinding.micButton.setImageResource(R.drawable.ic_baseline_mic_24);
            }
            mainRepository.toggleAudio(isMicMuted);
            isMicMuted=!isMicMuted;
        });

        videocallBinding.videoButton.setOnClickListener(v->{
            if(isCameraMuted){
                videocallBinding.videoButton.setImageResource(R.drawable.ic_baseline_videocam_off_24);
            }else{
                videocallBinding.videoButton.setImageResource(R.drawable.ic_baseline_videocam_24);
            }
            mainRepository.toggleVideo(isCameraMuted);
            isCameraMuted=!isCameraMuted;
        });

        videocallBinding.endCallButton.setOnClickListener(v->{
            mainRepository.endCall();
            finish();
        });

    }

    @Override
    public void webrtcConnected() {
        runOnUiThread(()->{
            videocallBinding.incomingCallLayout.setVisibility(View.GONE);
            videocallBinding.whoToCallLayout.setVisibility(View.GONE);
            videocallBinding.callLayout.setVisibility(View.VISIBLE);
        });

    }

    @Override
    public void webrtcClose() {
        runOnUiThread(this::finish);
    }
}