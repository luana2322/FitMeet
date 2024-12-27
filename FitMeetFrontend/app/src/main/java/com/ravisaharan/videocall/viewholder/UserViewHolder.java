package com.ravisaharan.videocall.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ravisaharan.videocall.R;
import com.ravisaharan.videocall.model.User;


// a user view holder that waiting to be inflated
public class UserViewHolder extends RecyclerView.ViewHolder {
    private TextView userNameTextView;
    private ImageView userProfileImageView;
    private ImageView statusOnImageView;
    private ImageView statusOffImageView;
    private User targetUser;


    public UserViewHolder(@NonNull View itemView) {
        super(itemView);

        userNameTextView = itemView.findViewById(R.id.userNameTextView);
        userProfileImageView = itemView.findViewById(R.id.userProfileImageView);
        statusOnImageView = itemView.findViewById(R.id.statusOnImageView);
        statusOffImageView = itemView.findViewById(R.id.statusOffImageView);
        targetUser= new User();
    }

    public TextView getUserNameTextView() {
        return userNameTextView;
    }

    public ImageView getStatusOnImageView() {
        return statusOnImageView;
    }

    public ImageView getStatusOffImageView() {
        return statusOffImageView;
    }

    public ImageView getUserProfileImageView() {
        return userProfileImageView;
    }


    public User getTargetUser() {
        return targetUser;
    }

    public void setTargetUser(User targetUser) {
        this.targetUser = targetUser;
    }
}
