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
import com.ravisaharan.videocall.model.CoachNotification;
import com.ravisaharan.videocall.model.Coache;
import com.ravisaharan.videocall.model.Student;
import com.ravisaharan.videocall.model.StudentNotification;

import java.util.List;

public class StudentBookAdapter   extends RecyclerView.Adapter<StudentBookAdapter.CoachViewHolder>{
    private Context context;
    private List<Student> studentLists;
    private StudentBookAdapter.OnItemActionListener
            listener;
    public interface OnItemActionListener {
        void onEdit(Student coache);

        void onDelete(Student coache);
    }

    public StudentBookAdapter(Context context, List<Student> studentLists, StudentBookAdapter.OnItemActionListener listener) {
        this.context = context;
        this.studentLists = studentLists;
        this.listener = listener;
    }
    @NonNull
    @Override
    public StudentBookAdapter.CoachViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_student_booked, parent, false);
        return new StudentBookAdapter.CoachViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull StudentBookAdapter.CoachViewHolder holder, int position) {
        Student student = studentLists.get(position);

        // Hiển thị dữ liệu
        holder.student_namr.setText(student.getFullname());
        holder.gender.setText(student.getGender());
        holder.mail.setText(student.getEmail());
//        holder.dateofbirth.setText(student.getDateofbirth());
//    "⭐\uFE0F "+
    }

    @Override
    public int getItemCount() {
        return studentLists.size();
    }

    public static class CoachViewHolder extends RecyclerView.ViewHolder {
        TextView student_namr,gender,mail,dateofbirth;

        public CoachViewHolder(@NonNull View itemView) {
            super(itemView);
            student_namr = itemView.findViewById(R.id.student_name);
            gender = itemView.findViewById(R.id.gender);
            mail = itemView.findViewById(R.id.mail);
            dateofbirth = itemView.findViewById(R.id.dateofbirth);
        }
    }
}
