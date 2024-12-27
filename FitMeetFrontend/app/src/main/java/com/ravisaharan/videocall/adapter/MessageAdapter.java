package com.ravisaharan.videocall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.ravisaharan.videocall.R;
import com.ravisaharan.videocall.model.Chat;
import com.ravisaharan.videocall.model.User;
import com.ravisaharan.videocall.viewholder.ChatViewHolder;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<ChatViewHolder> {

    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;

    private Context context;
    private List<Chat> chats;
    private User currentUser;
    private User targetUser;

    public MessageAdapter(Context context, List<Chat> chats, User currentUser, User targetUser) {
        this.context = context;
        this.chats = chats;
        this.currentUser = currentUser;
        this.targetUser = targetUser;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        if (viewType == MSG_TYPE_RIGHT) {
            View view = LayoutInflater.from(context).inflate(R.layout.chat_item_right, viewGroup, false);
            ChatViewHolder chatViewHolder = new ChatViewHolder(view);
            chatViewHolder.setChatItemProfileImageViewType(ChatViewHolder.currentUserType);
            return chatViewHolder;
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.chat_item_left, viewGroup, false);
            ChatViewHolder chatViewHolder = new ChatViewHolder(view);
            chatViewHolder.setChatItemProfileImageViewType(ChatViewHolder.targetUserType);
            return chatViewHolder;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder chatViewHolder, int i) {

        Chat chat = chats.get(i);

        //set message text
        chatViewHolder.getChatItemMessageTextView().setText(chat.getContent());
        //set message text ends

        //set chat profile
        if (chatViewHolder.getChatItemProfileImageViewType() == ChatViewHolder.currentUserType) {
            if (currentUser.getImageURL().equals("null")) {
                if (currentUser.getGender().equals("Male")) {
                    chatViewHolder.getChatItemProfileImageView().setImageResource(R.drawable.profile_default_male);
                }
                if (currentUser.getGender().equals("Female")) {
                    chatViewHolder.getChatItemProfileImageView().setImageResource(R.drawable.profile_default_female);
                }
            }

        }
        if (chatViewHolder.getChatItemProfileImageViewType() == ChatViewHolder.targetUserType) {
            if (targetUser!=null){
                if (targetUser.getImageURL().equals("null")) {
                    if (targetUser.getGender().equals("Male")) {
                        chatViewHolder.getChatItemProfileImageView().setImageResource(R.drawable.profile_default_male);
                    }
                    if (targetUser.getGender().equals("Female")) {
                        chatViewHolder.getChatItemProfileImageView().setImageResource(R.drawable.profile_default_female);
                    }
                }
            }else {
                Toast.makeText(context, "Null messageadapter", Toast.LENGTH_SHORT).show();
            }

        }
        //set chat profile ends
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(chats.get(position).getSender().equals(currentUser.getId())){
            return MSG_TYPE_RIGHT;
        }else {
            return MSG_TYPE_LEFT;
        }
    }
}
