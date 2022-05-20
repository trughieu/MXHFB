package com.example.nhom04_nguyentrunghieu_caotuankiet_ngothithanhngan.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom04_nguyentrunghieu_caotuankiet_ngothithanhngan.R;
import com.example.nhom04_nguyentrunghieu_caotuankiet_ngothithanhngan.model.Notification;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.viewHolder> {


//    Context context;
    List<Notification> mdata;

    public NotificationAdapter( List<Notification> mdata) {
//        this.context = context;
        this.mdata = mdata;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_notification, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.viewHolder holder, int position) {
        Notification notification = mdata.get(position);

        holder.imgUser.setImageResource(notification.getProfile());
        holder.txtName.setText(notification.getName());
        holder.txtNotify.setText(notification.getNotification());
//        holder.txtTime.setText(notification.getTime());
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        ImageView imgUser;
        TextView txtName, txtNotify, txtTime;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            imgUser = itemView.findViewById(R.id.img_User_Notification);
            txtName = itemView.findViewById(R.id.tv_UserName_Notification);
            txtNotify = itemView.findViewById(R.id.tv_content_notify);
            txtTime = itemView.findViewById(R.id.tvTime_Notification);
        }
    }
}
