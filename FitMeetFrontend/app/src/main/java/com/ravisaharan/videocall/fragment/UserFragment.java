package com.ravisaharan.videocall.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.ravisaharan.videocall.R;
import com.ravisaharan.videocall.adapter.UserAdapter;
import com.ravisaharan.videocall.config.InternetAddress;
import com.ravisaharan.videocall.model.User;
import com.ravisaharan.videocall.websocket.StompMessage;
import com.ravisaharan.videocall.websocket.StompMessageListener;
import com.ravisaharan.videocall.websocket.StompMessageSerializer;
import com.ravisaharan.videocall.websocket.TopicHandler;
import com.ravisaharan.videocall.websocket.socketclient.UserListListener;

import java.util.ArrayList;
import java.util.List;

public class UserFragment extends Fragment {

    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<User> userList;
    private UserListListener userListListener;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //get extras

        assert getArguments() != null;
        final User currentUser = (User) getArguments().getSerializable("CurrentUserKey");
        //get extras ends

        userList = new ArrayList<>();
        userListListener = new UserListListener();
        TopicHandler topicHandler = userListListener.subscribe("/topics/userList");
        topicHandler.addListener(new StompMessageListener() {
            @Override
            public void onMessage(final StompMessage message) {

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        userList.clear();
                        assert currentUser != null;
                        userList = StompMessageSerializer.putUserListStompMessageToListOfUsers(message, currentUser);
                        userAdapter = new UserAdapter(getContext(), userList, currentUser);
                        recyclerView.setAdapter(userAdapter);
                    }
                });
            }
        });
        userListListener.connect(InternetAddress.webSocketAddress);
        return view;
    }


}
