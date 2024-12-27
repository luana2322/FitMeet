package com.ravisaharan.videocall.Message;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.ravisaharan.videocall.R;
import com.ravisaharan.videocall.adapter.MessageAdapter;
import com.ravisaharan.videocall.config.InternetAddress;
import com.ravisaharan.videocall.databinding.ActivityChatContentBinding;
import com.ravisaharan.videocall.databinding.ActivityMessageBinding;
import com.ravisaharan.videocall.home.HomeActivity;
import com.ravisaharan.videocall.model.Chat;
import com.ravisaharan.videocall.model.User;
import com.ravisaharan.videocall.profile.ProfileActivity;
import com.ravisaharan.videocall.repository.MainRepository;
import com.ravisaharan.videocall.utils.DataModelType;
import com.ravisaharan.videocall.websocket.StompMessage;
import com.ravisaharan.videocall.websocket.StompMessageListener;
import com.ravisaharan.videocall.websocket.StompMessageSerializer;
import com.ravisaharan.videocall.websocket.TopicHandler;
import com.ravisaharan.videocall.websocket.socketclient.ChatDeliver;
import com.ravisaharan.videocall.websocket.socketclient.ChatListener;

import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends AppCompatActivity implements MainRepository.Listener{
    ActivityMessageBinding callBinding;
    MainRepository mainRepository;

    public boolean isCameraMuted = false;
    public boolean isMicMuted = false;
    ImageView call_icon;
    String callName = "";



    TextView targetUserNameTextView;
    TextView caller_name;
    EditText messageEditText;
    ImageButton messageSendButton;
    ImageButton backtochat;
    User currentUser;
    User targetUser;

    ChatDeliver chatDeliver;
    ChatListener chatListener;

    MessageAdapter messageAdapter;
    List<Chat> chats;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
//add 2
        callBinding = ActivityMessageBinding.inflate(getLayoutInflater());
        setContentView(callBinding.getRoot());
        //get extras
        currentUser = (User) getIntent().getSerializableExtra("CurrentUserKey");
        targetUser = (User) getIntent().getSerializableExtra("TargetUserKey");
        //get extras ends


        //find view
        targetUserNameTextView = findViewById(R.id.targetUserNameTextView);
        caller_name = findViewById(R.id.caller_name);
        messageEditText = findViewById(R.id.messageEditText);
        messageSendButton = findViewById(R.id.messageSendButton);
//        Toolbar toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recycler_view);
        //find view ends

        //set targetUserNameTextView
        targetUserNameTextView.setText(targetUser.getUsername());
        //set targetUserNameTextView ends



        backtochat=findViewById(R.id.backtohome);
        backtochat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MessageActivity.this, TabActivity.class);
                startActivity(intent1);
            }
        });
        //config toolbar
//        setActionBar(toolbar);
//        setActionBar().setTitle("");
//        setActionBar().setDisplayHomeAsUpEnabled(true);

        //config recycler view
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        //config recycler view ends


        //start listening to chat list for current user and target user
        chatListener = new ChatListener(currentUser.getId(), targetUser.getId());
        TopicHandler topicHandler = chatListener.subscribe("/topics/event/" + currentUser.getId() + "/" + targetUser.getId());

        topicHandler.addListener(new StompMessageListener() {
            @Override
            public void onMessage(final StompMessage message) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (message.getContent().equals("[]")) {
                            return;
                        }
                        StompMessageSerializer stompMessageSerializer = new StompMessageSerializer();
                        chats = new ArrayList<>();
                        chats = stompMessageSerializer.putChatListStompMessageToListOfChats(message);
                        messageAdapter = new MessageAdapter(MessageActivity.this, chats, currentUser, targetUser);
                        recyclerView.setAdapter(messageAdapter);
                    }
                });
            }
        });
        chatListener.connect(InternetAddress.webSocketAddress);

        //use deliver to invoke initial response
        chatDeliver = new ChatDeliver(currentUser.getId(), targetUser.getId(), "");
        chatDeliver.connect(InternetAddress.webSocketAddress);
        chatDeliver.disconnect();

        //start listening to chat list for current user and target user ends
//video call function
        init(targetUser.getUsername());
        //send message
        messageSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if (messageEditText.getText().toString().equals("")) {
                        Toast.makeText(MessageActivity.this, "You can not send empty message", Toast.LENGTH_SHORT).show();
                    } else {
                        chatDeliver = new ChatDeliver(currentUser.getId(), targetUser.getId(), messageEditText.getText().toString());
                        chatDeliver.connect(InternetAddress.webSocketAddress);
                        chatDeliver.disconnect();
                    }



                messageEditText.setText("");
            }
        });
        //send message ends
    }

    private void setSupportActionBar(Toolbar toolbar) {
    }
    public void init(String username) {
        mainRepository = MainRepository.getInstance();

        callBinding.callvideoicon.setOnClickListener(v -> {
            //make call request
            mainRepository.sendCallRequest(username, () -> {
                Toast.makeText(this, "Couldn't find the above target", Toast.LENGTH_SHORT).show();
            });
        });

        mainRepository.initLocalView(callBinding.localView);
        mainRepository.initRemoteView(callBinding.remoteView);
        mainRepository.listener = this;

        mainRepository.subscribeForLatestEvent(data -> {
            if (data.getType() == DataModelType.StartCall) {
                runOnUiThread(() -> {
                    String text = data.getSender() + " is calling you..";
//                    callBinding.incomingNameTV.setText(text);
                    callBinding.tvHeader.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.GONE);
                    callBinding.bottom.setVisibility(View.GONE);
                    messageEditText.setVisibility(View.GONE);
                    messageSendButton.setVisibility(View.GONE);
                    messageSendButton.setVisibility(View.GONE);
                    callBinding.incomingCallLayout.setVisibility(View.VISIBLE);
                    caller_name.setText(data.getSender());
                    callBinding.acceptButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //start call
                            callBinding.tvHeader.setVisibility(View.GONE);
                            mainRepository.startCall(data.getSender());
                            callBinding.incomingCallLayout.setVisibility(View.GONE);
                             recyclerView.setVisibility(View.GONE);
                           callBinding.bottom.setVisibility(View.GONE);
                             messageEditText.setVisibility(View.GONE);
                            messageSendButton.setVisibility(View.GONE);
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

            startActivity(getIntent());
        });

    }
    @Override
    public void webrtcConnected() {
        runOnUiThread(() -> {
            callBinding.incomingCallLayout.setVisibility(View.GONE);

            LinearLayout tvHeader = findViewById(R.id.tvHeader);
            ViewGroup.LayoutParams params = tvHeader.getLayoutParams();
            params.height = 0;  // Thay đổi chiều cao thành 0 khi ẩn
            tvHeader.setLayoutParams(params);
            tvHeader.setVisibility(View.GONE);

//           callBinding.whoToCallLayout.setVisibility(View.GONE);
            callBinding.callLayout.setVisibility(View.VISIBLE);
        });

    }

    @Override
    public void webrtcClose() {
        runOnUiThread(this::finish);
    }
}
