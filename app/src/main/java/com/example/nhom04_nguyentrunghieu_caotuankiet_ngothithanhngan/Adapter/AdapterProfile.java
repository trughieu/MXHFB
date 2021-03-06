package com.example.nhom04_nguyentrunghieu_caotuankiet_ngothithanhngan.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.nhom04_nguyentrunghieu_caotuankiet_ngothithanhngan.Activity.CommentActivity;
import com.example.nhom04_nguyentrunghieu_caotuankiet_ngothithanhngan.R;
import com.example.nhom04_nguyentrunghieu_caotuankiet_ngothithanhngan.model.Post;
import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdapterProfile extends RecyclerView.Adapter<AdapterProfile.ViewHolderPostProfile> {

    Context context;
    ArrayList<Post> arrayList;
    FirebaseDatabase fDatabase;

    public AdapterProfile(ArrayList<Post> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }
    public AdapterProfile(ArrayList<Post> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolderPostProfile onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_post, parent, false);
        return new AdapterProfile.ViewHolderPostProfile(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderPostProfile holder, int position) {
        Post post = arrayList.get(position);
        fDatabase = FirebaseDatabase.getInstance();
        if (post != null) {
            /**
             * load avt
             */

            fDatabase.getReference().child("post1").child(post.getPostId()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Post user = snapshot.getValue(Post.class);

                    Glide.with(context).load(user.getAvt()).into(holder.avtPost);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            /**
             * load h??nh ???nh b??i vi???t
             */

            fDatabase.getReference().child("post1")
                    .child(post.getPostId()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Post user = snapshot.getValue(Post.class);
                    Glide.with(context).load(user.getPostImage()).into(holder.img_post);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            /**
             * load t??n
             */
            holder.tvNamePost.setText(post.getPostedBy());

//            holder.tvContentPost.setText(post.getContent());
            /**
             * Load n???i dung b??i vi???t
             */
            if (post.getPostDescription().equals(""))
                holder.tvContentPost.setVisibility(View.GONE);
            else
                holder.tvContentPost.setText(post.getPostDescription());
            /**
             * load th???i gian b??i vi???t
             */

            String time = TimeAgo.using(post.getPostedAt());
            holder.tvTime.setText(time);
//            holder.tvTime.setText(post.getPostedAt());
            /**
             *load luot like
             */

            holder.tvCountLike.setText((post.getPostLike()) + " l?????t th??ch");

            /**
             * load binh luan
             */

            holder.txtCmtCount.setText((post.getCommentCount()) + " b??nh lu???n");

            /**
             * load luot share
             */
            holder.txtShare.setText((post.getShare()) + " chia s???");

            /**
             * tang giam luot like
             */

            FirebaseDatabase.getInstance().getReference()
                    .child("post1")
                    .child(post.getPostId())
                    .child("like")
                    .child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {

                        holder.reactButton.setOnClickListener(v -> {
                            holder.btnLike.setImageResource(R.drawable.like);
                            FirebaseDatabase.getInstance().getReference()
                                    .child("post1")
                                    .child(post.getPostId())
                                    .child("like")
                                    .child(FirebaseAuth.getInstance().getUid())
                                    .removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    FirebaseDatabase.getInstance().getReference()
                                            .child("post1")
                                            .child(post.getPostId())
                                            .child("postLike")
                                            .setValue(post.getPostLike() - 1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                        }
                                    });
                                }
                            });
                        });
                    } else {

                        holder.reactButton.setOnClickListener(v -> {
                            holder.btnLike.setImageResource(R.drawable.ic_like);
                            holder.tvLike.setTextColor(R.color.BLUE);
                            FirebaseDatabase.getInstance().getReference()
                                    .child("post1")
                                    .child(post.getPostId())
                                    .child("like")
                                    .child(FirebaseAuth.getInstance().getUid())
                                    .setValue(true).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    FirebaseDatabase.getInstance().getReference()
                                            .child("post1")
                                            .child(post.getPostId())
                                            .child("postLike")
                                            .setValue(post.getPostLike() + 1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {

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
            /**
             *
             */
            holder.btnCmt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CommentActivity.class);
                    intent.putExtra("post1", post);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }
            });
        }
    }

    @Override
    public int getItemCount() {

        return arrayList.size();
    }

    public class ViewHolderPostProfile extends RecyclerView.ViewHolder {
        TextView tvNamePost, tvContentPost, btnCmt, tvTime, btnShare, txtCmtCount, tvCountLike, txtShare, tvLike;
        ImageView img_post, avtPost, btnLike;
        LinearLayout reactButton;

        public ViewHolderPostProfile(@NonNull View itemView) {
            super(itemView);
            avtPost = itemView.findViewById(R.id.avtPost);
            tvNamePost = itemView.findViewById(R.id.namePost);
            txtCmtCount = itemView.findViewById(R.id.tv_count_cmt);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvCountLike = itemView.findViewById(R.id.tv_like);
            txtShare = itemView.findViewById(R.id.tv_count_share);
            tvContentPost = itemView.findViewById(R.id.content_post);
            img_post = itemView.findViewById(R.id.img_post);
            btnCmt = itemView.findViewById(R.id.iv_cmt);
            btnShare = itemView.findViewById(R.id.iv_share);
            btnLike = itemView.findViewById(R.id.btn_like);
            tvLike = itemView.findViewById(R.id.txt_like);
            reactButton = itemView.findViewById(R.id.reactButton);

        }
    }
}
