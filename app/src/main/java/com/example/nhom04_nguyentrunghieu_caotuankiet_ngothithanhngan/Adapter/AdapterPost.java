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
import com.example.nhom04_nguyentrunghieu_caotuankiet_ngothithanhngan.model.Notification;
import com.example.nhom04_nguyentrunghieu_caotuankiet_ngothithanhngan.model.Post;
import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class AdapterPost extends RecyclerView.Adapter<AdapterPost.ViewHolderPost> {
    Context context;
    ArrayList<Post> arrayList;
    FirebaseDatabase fDatabase;
    FirebaseAuth auth;

    public AdapterPost(ArrayList<Post> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    public AdapterPost(ArrayList<Post> arrayList) {
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public ViewHolderPost onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_post, parent, false);
        return new ViewHolderPost(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderPost holder, int position) {
        Post post = arrayList.get(position);
        fDatabase = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        if (post != null) {
//            StorageReference storageReference = FirebaseStorage.getInstance().getReference();
//
//            StorageReference avt = storageReference.child("avt/" + post.getAvt());
            /**
             * load avt
             */

            fDatabase.getReference().child("post").child(post.getPostId()).addListenerForSingleValueEvent(new ValueEventListener() {
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
             * load ava story
             */

//
//            avt.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                @Override
//                public void onSuccess(Uri uri) {
//                    Picasso.get().load(uri).into(holder.avtPost);
//
//                }
//            });

//            FirebaseDatabase.getInstance().getReference()
//                    .child("post")
//                    .child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    Post user = snapshot.getValue(Post.class);
//                    Glide.with(context).load(user.getAvt()).into(holder.avtPost);
//                }
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
//            Glide.with(context).load(post.getAvt()).into(holder.avtPost);


            /**
             * load hình ảnh bài viết
             */
//            StorageReference postImg = storageReference.child("posts/" +
//                    post.getPostImage());
//
//            postImg.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                @Override
//                public void onSuccess(Uri uri) {
//                    Picasso.get().load(uri).into(holder.img_post);
//                }
//            });

            fDatabase.getReference().child("post")
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
//            Glide.with(context).load(post.getPostImage()).into(holder.img_post);

            /**
             * load tên
             */
            holder.tvNamePost.setText(post.getPostedBy());

//            holder.tvContentPost.setText(post.getContent());
            /**
             * Load nội dung bài viết
             */
            if (post.getPostDescription().equals(""))
                holder.tvContentPost.setVisibility(View.GONE);
            else
                holder.tvContentPost.setText(post.getPostDescription());
            /**
             * load thời gian bài viết
             */

            String time = TimeAgo.using(post.getPostedAt());
            holder.tvTime.setText(time);
//            holder.tvTime.setText(post.getPostedAt());
            /**
             *load luot like
             */

            holder.tvCountLike.setText((post.getPostLike()) + " lượt thích");

            /**
             * load binh luan
             */

            holder.txtCmtCount.setText((post.getCommentCount()) + " bình luận");

            /**
             * load luot share
             */
            holder.txtShare.setText((post.getShare()) + " chia sẽ");

            /**
             * tang giam luot like
             * thêm thông báo like
             */
            Notification notification = new Notification();
            notification.setName(FirebaseAuth.getInstance().getUid());
            notification.setTime(new Date().getTime());
            notification.setNotification("Đã thích bài viết của bạn");
            FirebaseDatabase.getInstance().getReference()
                    .child("post")
                    .child(post.getPostId())
                    .child("like")
                    .child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        holder.btnLike.setImageResource(R.drawable.ic_like);
                        holder.tvLike.setTextColor(R.color.BLUE);
                        holder.reactButton.setOnClickListener(v -> {
//
                            FirebaseDatabase.getInstance().getReference()
                                    .child("post")
                                    .child(post.getPostId())
                                    .child("like")
                                    .child(FirebaseAuth.getInstance().getUid())
                                    .removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    FirebaseDatabase.getInstance().getReference()
                                            .child("post")
                                            .child(post.getPostId())
                                            .child("postLike")
                                            .setValue(post.getPostLike() - 1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            holder.btnLike.setImageResource(R.drawable.like);
                                            holder.tvLike.setTextColor(R.color.black);
                                            fDatabase.getReference()
                                                    .child("users")
                                                    .child(auth.getUid())
                                                    .child("Notification").push()
                                                    .setValue(notification).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {

                                                }
                                            });
                                        }
                                    });
                                }
                            });
                        });
                    } else {
                        holder.reactButton.setOnClickListener(v -> {
                            FirebaseDatabase.getInstance().getReference()
                                    .child("post")
                                    .child(post.getPostId())
                                    .child("like")
                                    .child(FirebaseAuth.getInstance().getUid())
                                    .setValue(true).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    FirebaseDatabase.getInstance().getReference()
                                            .child("post")
                                            .child(post.getPostId())
                                            .child("postLike")
                                            .setValue(post.getPostLike() + 1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            holder.btnLike.setImageResource(R.drawable.ic_like);
//                                            holder.tvLike.setTextColor(R.color.BLUE);
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
                    intent.putExtra("post", post);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
//                    v.getContext().startActivity(new Intent(v.getContext(), CommentActivity.class));

                }
            });
//
//            holder.btnCmt.setOnClickListener(v->{
//                Intent intent = new Intent(context, CommentActivity.class);
////                intent.putExtra("post", post);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent);
//            });

        }

    }

    @Override
    public int getItemCount() {

        return arrayList.size();
    }


    public class ViewHolderPost extends RecyclerView.ViewHolder {
        TextView tvNamePost, tvContentPost, btnCmt, tvTime, btnShare, txtCmtCount, tvCountLike, txtShare, tvLike;
        ImageView img_post, avtPost, btnLike, img_view_story;
        LinearLayout reactButton;


        public ViewHolderPost(View itemView) {
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
            reactButton = itemView.findViewById(R.id.reactButton);
            btnLike = itemView.findViewById(R.id.btn_like);
            tvLike = itemView.findViewById(R.id.txt_like);

//            reactButton.setReactions(FbReactions.reactions);
//            reactButton.setDefaultReaction(FbReactions.defaultReact);
//            reactButton.setEnableReactionTooltip(true);
        }
    }
}
