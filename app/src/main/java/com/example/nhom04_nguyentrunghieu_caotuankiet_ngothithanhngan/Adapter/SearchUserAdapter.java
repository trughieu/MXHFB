package com.example.nhom04_nguyentrunghieu_caotuankiet_ngothithanhngan.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.nhom04_nguyentrunghieu_caotuankiet_ngothithanhngan.R;
import com.example.nhom04_nguyentrunghieu_caotuankiet_ngothithanhngan.model.User;

import java.util.List;

public class SearchUserAdapter extends RecyclerView.Adapter<SearchUserAdapter.viewHolder> {

    Context context;
    List<User> mdata;

    public SearchUserAdapter(Context context, List<User> mdata) {
        this.context = context;
        this.mdata = mdata;
    }

    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_search, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        User user = mdata.get(position);

        Glide.with(context).load(user.getAvatar()).into(holder.imgUser);
        holder.txtName.setText(user.getName());
//
//        FirebaseDatabase.getInstance().getReference().child("users")
//                .child(user.getUserID())
//                .child("followers")
//                .child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.exists()){
////                    holder.btnFollow.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.bg_button_activate));
////                    holder.btnFollow.setText("Following");
////                    holder.btnFollow.setTextColor(context.getResources().getColor(R.color.gray));
////                    holder.btnFollow.setEnabled(false);
//                }
//                else{
//                    holder.btnFollow.setOnClickListener(v->{
//                        Follow follow = new Follow();
//                        follow.setFollowedBy(FirebaseAuth.getInstance().getUid());
//                        follow.setFollowedAt(new Date().getTime());
//
//                        Following following = new Following();
//                        following.setFollowing(user.getUserID());
//                        following.setFollowedAt(new Date().getTime());
//
//                        FirebaseDatabase.getInstance().getReference()
//                                .child("Users")
//                                .child(user.getUserID())
//                                .child("followers")
//                                .child(FirebaseAuth.getInstance().getUid())
//                                .setValue(follow).addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void unused) {
//                                FirebaseDatabase.getInstance().getReference()
//                                        .child("Users")
//                                        .child(user.getUserID())
//                                        .child("followerCount")
//                                        .setValue(user.getFollowerCount() + 1).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                    @Override
//                                    public void onSuccess(Void unused) {
//                                        holder.btnFollow.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.bg_button_activate));
//                                        holder.btnFollow.setText("Following");
//                                        holder.btnFollow.setTextColor(context.getResources().getColor(R.color.gray));
//                                        holder.btnFollow.setEnabled(false);
//                                    }
//                                });
//                            }
//                        });
//
//                        FirebaseDatabase.getInstance().getReference()
//                                .child("Users")
//                                .child(FirebaseAuth.getInstance().getUid())
//                                .addListenerForSingleValueEvent(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                if(snapshot.exists()){
//                                    User curUser = snapshot.getValue(User.class);
//
//                                    FirebaseDatabase.getInstance().getReference()
//                                            .child("Users")
//                                            .child(FirebaseAuth.getInstance().getUid())
//                                            .child("following")
//                                            .child(user.getUserID())
//                                            .setValue(following).
//                                            addOnSuccessListener(new OnSuccessListener<Void>() {
//                                        @Override
//                                        public void onSuccess(Void unused) {
//                                            FirebaseDatabase.getInstance().getReference()
//                                                    .child("Users")
//                                                    .child(FirebaseAuth.getInstance().getUid())
//                                                    .child("followingCount")
//                                                    .setValue(curUser.getFollowingCount()+1).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                @Override
//                                                public void onSuccess(Void unused) {
////                                                    holder.btnFollow.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.bg_button_activate));
////                                                    holder.btnFollow.setText("Following");
////                                                    holder.btnFollow.setTextColor(context.getResources().getColor(R.color.gray));
////                                                    holder.btnFollow.setEnabled(false);
//                                                }
//                                            });
//                                        }
//                                    });
//                                }
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError error) {
//
//                            }
//                        });
//
//                    });
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        ImageView imgUser;
        TextView txtName;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            imgUser = itemView.findViewById(R.id.iv_user_search);
            txtName = itemView.findViewById(R.id.tv_name_user_search);
        }
    }
}
