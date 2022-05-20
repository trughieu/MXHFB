package com.example.nhom04_nguyentrunghieu_caotuankiet_ngothithanhngan.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.nhom04_nguyentrunghieu_caotuankiet_ngothithanhngan.R;
import com.example.nhom04_nguyentrunghieu_caotuankiet_ngothithanhngan.model.Friend;
import com.example.nhom04_nguyentrunghieu_caotuankiet_ngothithanhngan.model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.List;

public class AdapterFriend extends RecyclerView.Adapter<AdapterFriend.viewHolder> {

    List<User> arrayList;
    Context context;

    public AdapterFriend(Context context, List<User> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_friend_request, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        User user = arrayList.get(position);

        Glide.with(context).load(user.getAvatar()).into(holder.imgUser);
        holder.txtName.setText(user.getName());

        holder.btnRemove.setOnClickListener(view -> {
            holder.itemView.setVisibility(View.GONE);
        });


        FirebaseDatabase.getInstance().getReference().child("users")
                .child(user.getUserID())
                .child("friendof")
                .child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    holder.btnAdd.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.bg_button_activate));
//                    holder.btnAdd.setText("Following");
//                    holder.btnAdd.setTextColor(context.getResources().getColor(R.color.gray));
//                    holder.btnAdd.setEnabled(false);
                    holder.txtNote.setText("Các bạn đã là bạn bè");
//                    holder.itemView.setVisibility(View.GONE);
                } else {
                    holder.btnAdd.setOnClickListener(v -> {
                        Friend friend = new Friend();
                        friend.setFriendOf(FirebaseAuth.getInstance().getUid());
                        friend.setTime(new Date().getTime());

                        FirebaseDatabase.getInstance().getReference()
                                .child("users")
                                .child(user.getUserID())
                                .child("friendof")
                                .child(FirebaseAuth.getInstance().getUid())
                                .setValue(friend).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                FirebaseDatabase.getInstance().getReference()
                                        .child("users")
                                        .child(user.getUserID())
                                        .child("friend")
                                        .setValue(user.getFriend() + 1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        holder.btnAdd.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.bg_button_activate));
//                                        holder.btnAdd.setText("Following");
                                        holder.btnAdd.setVisibility(View.GONE);
                                        holder.btnRemove.setVisibility(View.GONE);
                                        holder.txtNote.setVisibility(View.VISIBLE);
//                                        holder.txtNote.setText("Các bạn đã là bạn bè");
//                                        holder.btnAdd.setEnabled(false);
                                        Toast.makeText(context, "da chap nhan", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        ImageView imgUser;
        TextView txtName, txtNote;
        AppCompatButton btnAdd, btnRemove;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            imgUser = itemView.findViewById(R.id.img_user_request);
            txtName = itemView.findViewById(R.id.tv_user_request);
            txtNote = itemView.findViewById(R.id.note);
            btnAdd = itemView.findViewById(R.id.btn_Add_Friend);
            btnRemove = itemView.findViewById(R.id.btn_Remove_Friend);
        }
    }
}
