package com.example.nhom04_nguyentrunghieu_caotuankiet_ngothithanhngan.Adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.nhom04_nguyentrunghieu_caotuankiet_ngothithanhngan.R;
import com.example.nhom04_nguyentrunghieu_caotuankiet_ngothithanhngan.model.User;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterSearch extends FirebaseRecyclerAdapter<User, AdapterSearch.myviewholder> {
    public AdapterSearch(@NonNull FirebaseRecyclerOptions<User> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull User model) {
        holder.name.setText(model.getName());
//        holder.course.setText(model.getCourse());
//        holder.email.setText(model.getEmail());
        Glide.with(holder.img.getContext()).load(model.getAvatar()).into(holder.img);
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search, parent, false);
        return new myviewholder(view);
    }


    class myviewholder extends RecyclerView.ViewHolder {
        CircleImageView img;
        TextView name, course, email;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.iv_user_search);
            name =  itemView.findViewById(R.id.tv_name_user_search);
//            course = itemView.findViewById(R.id.coursetext);
//            email = (TextView) itemView.findViewById(R.id.emailtext);
        }
    }
}
