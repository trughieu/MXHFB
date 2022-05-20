package com.example.nhom04_nguyentrunghieu_caotuankiet_ngothithanhngan.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom04_nguyentrunghieu_caotuankiet_ngothithanhngan.Adapter.CommentAdapter;
import com.example.nhom04_nguyentrunghieu_caotuankiet_ngothithanhngan.R;
import com.example.nhom04_nguyentrunghieu_caotuankiet_ngothithanhngan.model.Comment;
import com.example.nhom04_nguyentrunghieu_caotuankiet_ngothithanhngan.model.Post;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommentActivity extends AppCompatActivity {
    FirebaseDatabase database;
    FirebaseAuth auth;
    CommentAdapter adapter;
    List<Comment> listCmt;
    Post post;
    ImageView btnPostCmt, ivBackCmt;
    EditText edtPostCmt;
    RecyclerView rcvCmt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);


        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        btnPostCmt = findViewById(R.id.iv_post_cmt);
        edtPostCmt = findViewById(R.id.edt_post_cmt);
        ivBackCmt = findViewById(R.id.iv_back_cmt);
        rcvCmt = findViewById(R.id.rcv_cmt);

        Intent intent = getIntent();
        post = (Post) intent.getSerializableExtra("post");

//
//        database.getReference().child("users").child(post.getPostedBy()).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                User user = snapshot.getValue(User.class);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//

        btnPostCmt.setOnClickListener(v -> {
            Comment comment = new Comment();
            comment.setCommentBody(edtPostCmt.getText().toString());
            comment.setCommentedAt(new Date().getTime());
            comment.setCommentedBy(FirebaseAuth.getInstance().getUid());

            database.getReference().child("post").child(post.getPostId())
                    .child("comments").push()
                    .setValue(comment).addOnSuccessListener(
                            new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    database.getReference()
                            .child("post")
                            .child(post.getPostId())
                            .child("commentCount")
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    int cmtCount = 0;
                                    if (snapshot.exists()) {
                                        cmtCount = snapshot.getValue(Integer.class);

                                    }
                                    database.getReference()
                                            .child("post")
                                            .child(post.getPostId())
                                            .child("commentCount")
                                            .setValue(cmtCount + 1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            edtPostCmt.setText("");
                                        }
                                    });
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                }
            });
        });
        listCmt = new ArrayList<>();
        database.getReference().child("post").child(post.getPostId()).child("comments")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (listCmt.size() != 0)
                            listCmt.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Comment comment = dataSnapshot.getValue(Comment.class);
                            listCmt.add(comment);
                        }
                        adapter = new CommentAdapter(CommentActivity.this, listCmt);
                        adapter.notifyDataSetChanged();
                        rcvCmt.setLayoutManager(new LinearLayoutManager(CommentActivity.this));
                        rcvCmt.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        ivBackCmt.setOnClickListener(v -> {
            finish();
        });
    }
}