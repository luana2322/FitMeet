package com.ravisaharan.videocall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ravisaharan.videocall.R;
import com.ravisaharan.videocall.model.Coache;
import com.ravisaharan.videocall.model.Student;

import java.util.List;

public class StudentAdapterChat extends RecyclerView.Adapter<StudentAdapterChat.CoachViewHolder> {
    private Context context;
    private List<Student> studentsList;
    private StudentAdapterChat.OnItemActionListener
            listener;


    public interface OnItemActionListener {
        void onEdit(Student coache);

        void onDelete(Student coache);
    }

    public StudentAdapterChat(Context context, List<Student> coacheList, StudentAdapterChat.OnItemActionListener listener) {
        this.context = context;
        this.studentsList = coacheList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public StudentAdapterChat.CoachViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.coach_chat_item, parent, false);
        return new StudentAdapterChat.CoachViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentAdapterChat.CoachViewHolder holder, int position) {
        Student student = studentsList.get(position);

        // Hiển thị dữ liệu
        holder.user_name.setText(student.getFullname());
        holder.user_description.setText(student.getGender());

        // Sử dụng Glide để load ảnh
//        GenericLifecycleObserver.with(context).load(student.getPicture()).into(holder.imgAvatar);


        holder.full.setOnClickListener(v -> listener.onEdit(student));
    }

    @Override
    public int getItemCount() {
        return studentsList.size();
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