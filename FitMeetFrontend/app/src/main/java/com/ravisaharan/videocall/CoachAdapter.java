package com.ravisaharan.videocall;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ravisaharan.videocall.model.Coache;

import java.util.List;

public class CoachAdapter  extends RecyclerView.Adapter<CoachAdapter.CoachViewHolder>{
    private Context context;
    private List<Coache> coacheList;
    private OnItemActionListener
            listener;


    public interface OnItemActionListener {
        void onEdit(Coache coache);
        void onDelete(Coache coache);
    }

    public CoachAdapter(Context context, List<Coache> coacheList, OnItemActionListener listener) {
        this.context = context;
        this.coacheList = coacheList;
        this.listener = listener;
    }
    @NonNull
    @Override
    public CoachViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.coach_item, parent, false);
        return new CoachViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull CoachViewHolder holder, int position) {
        Coache coache = coacheList.get(position);

        // Hiển thị dữ liệu
        holder.tvFullname.setText(coache.getFullname());
        holder.txtcer.setText(coache.getSpecialties());

        // Sử dụng Glide để load ảnh
//        GenericLifecycleObserver.with(context).load(student.getPicture()).into(holder.imgAvatar);

        // Nút sửa
        holder.book.setOnClickListener(v -> listener.onEdit(coache));
    }

    @Override
    public int getItemCount() {
        return coacheList.size();
    }

    public static class CoachViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAvatar;
        TextView tvFullname, txtcer, star;
        Button book, btnDelete;

        public CoachViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.coachavatar);
            tvFullname = itemView.findViewById(R.id.coach_name);
            txtcer = itemView.findViewById(R.id.certiffil);
            star = itemView.findViewById(R.id.star);
            book = itemView.findViewById(R.id.book_now_button);

        }
    }
}
