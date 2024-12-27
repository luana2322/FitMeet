package com.ravisaharan.videocall.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ravisaharan.videocall.R;


public class ChatViewHolder extends RecyclerView.ViewHolder {


    public static final int currentUserType = 0;
    public static final int targetUserType = 1;

    private TextView chatItemMessageTextView;
    private ImageView chatItemProfileImageView;
    private int chatItemProfileImageViewType;

    public ChatViewHolder(@NonNull View itemView) {
        super(itemView);
        chatItemMessageTextView = itemView.findViewById(R.id.chatItemMessageTextView);
        chatItemProfileImageView = itemView.findViewById(R.id.chatItemProfileImageView);
    }

    public TextView getChatItemMessageTextView() {
        return chatItemMessageTextView;
    }


    public ImageView getChatItemProfileImageView() {
        return chatItemProfileImageView;
    }

    public int getChatItemProfileImageViewType() {
        return chatItemProfileImageViewType;
    }

    public void setChatItemProfileImageViewType(int chatItemProfileImageViewType) {
        this.chatItemProfileImageViewType = chatItemProfileImageViewType;
    }
}
