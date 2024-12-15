package com.ravisaharan.videocall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ravisaharan.videocall.R;
import com.ravisaharan.videocall.model.Coache;

import java.util.List;

public class CoachAdapterChat extends RecyclerView.Adapter<CoachAdapterChat.CoachViewHolder> {
    private Context context;
    private List<Coache> coacheList;
    private CoachAdapterChat.OnItemActionListener
            listener;


    public interface OnItemActionListener {
        void onEdit(Coache coache);

        void onDelete(Coache coache);
    }

    public CoachAdapterChat(Context context, List<Coache> coacheList, CoachAdapterChat.OnItemActionListener listener) {
        this.context = context;
        this.coacheList = coacheList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CoachAdapterChat.CoachViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.coach_chat_item, parent, false);
        return new CoachAdapterChat.CoachViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CoachAdapterChat.CoachViewHolder holder, int position) {
        Coache coache = coacheList.get(position);

        // Hiển thị dữ liệu
        holder.user_name.setText(coache.getFullname());
        holder.user_description.setText(coache.getDescription());

        // Sử dụng Glide để load ảnh
//        GenericLifecycleObserver.with(context).load(student.getPicture()).into(holder.imgAvatar);


        holder.full.setOnClickListener(v -> listener.onEdit(coache));
    }

    @Override
    public int getItemCount() {
        return coacheList.size();
    }

    public static class CoachViewHolder extends RecyclerView.ViewHolder {
        ImageView profile_image;
        TextView user_name, user_description;
        LinearLayout full;

        public CoachViewHolder(@NonNull View itemView) {
            super(itemView);
            profile_image = itemView.findViewById(R.id.profile_image);
            user_name = itemView.findViewById(R.id.user_name);
            user_description = itemView.findViewById(R.id.user_description);
            full=itemView.findViewById(R.id.fullviewchat);
        }
    }
}