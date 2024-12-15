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
import com.ravisaharan.videocall.model.CoachNotification;
import com.ravisaharan.videocall.model.Coache;

import java.util.List;

public class CoachNotificationAdapter extends RecyclerView.Adapter<CoachNotificationAdapter.CoachViewHolder> {
private Context context;
private List<CoachNotification> coacheList;
private CoachNotificationAdapter.OnItemActionListener
        listener;


public interface OnItemActionListener {
    void onEdit(CoachNotification coache);

    void onDelete(CoachNotification coache);
}

public CoachNotificationAdapter(Context context, List<CoachNotification> coacheList, CoachNotificationAdapter.OnItemActionListener listener) {
    this.context = context;
    this.coacheList = coacheList;
    this.listener = listener;
}

@NonNull
@Override
public CoachNotificationAdapter.CoachViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(context).inflate(R.layout.notification_item, parent, false);
    return new CoachNotificationAdapter.CoachViewHolder(view);
}

@Override
public void onBindViewHolder(@NonNull CoachNotificationAdapter.CoachViewHolder holder, int position) {
    CoachNotification coache = coacheList.get(position);

    // Hiển thị dữ liệu
    holder.title.setText(coache.getTitle());
//    "⭐\uFE0F "+
    holder.context.setText("⭐"+coache.getContext());

    // Sử dụng Glide để load ảnh
//        GenericLifecycleObserver.with(context).load(student.getPicture()).into(holder.imgAvatar);


}

@Override
public int getItemCount() {
    return coacheList.size();
}

public static class CoachViewHolder extends RecyclerView.ViewHolder {
    TextView title,context;

    public CoachViewHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.title);
        context = itemView.findViewById(R.id.subtitle1);
    }
}
}