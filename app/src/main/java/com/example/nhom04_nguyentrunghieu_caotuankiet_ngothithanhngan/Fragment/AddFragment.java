package com.example.nhom04_nguyentrunghieu_caotuankiet_ngothithanhngan.Fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.nhom04_nguyentrunghieu_caotuankiet_ngothithanhngan.R;
import com.example.nhom04_nguyentrunghieu_caotuankiet_ngothithanhngan.model.Post;
import com.example.nhom04_nguyentrunghieu_caotuankiet_ngothithanhngan.model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddFragment extends Fragment implements View.OnClickListener {


    /**
     * Data
     */
    Uri uri;
    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseStorage storage;
    CircleImageView ivAvtPost;
    TextView tv_name_post;
    LinearLayout layout_add_more;
    AppCompatButton btn_post;
    EditText post_description;
    ImageView post_image, iv_back;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddFragment newInstance(String param1, String param2) {
        AddFragment fragment = new AddFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        btn_post = view.findViewById(R.id.btn_post);
        layout_add_more = view.findViewById(R.id.layout_add_more);
        ivAvtPost = view.findViewById(R.id.iv_avt_post);
        tv_name_post = view.findViewById(R.id.tv_name_user_post);
        post_description = view.findViewById(R.id.post_description);
        post_image = view.findViewById(R.id.post_image);
        iv_back = view.findViewById(R.id.iv_back);
        btn_post.setOnClickListener(this);
        layout_add_more.setOnClickListener(this);

        database.getReference().child("users").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    User user = snapshot.getValue(User.class);
                    Glide.with(getContext()).load(user.getAvatar()).into(ivAvtPost);
                    tv_name_post.setText(user.getName());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        post_description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String description = post_description.getText().toString();
                if (!description.isEmpty()) {
                    btn_post.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.bg_button));
                    btn_post.setTextColor(getContext().getResources().getColor(R.color.white));
                    btn_post.setEnabled(true);
                } else {
                    btn_post.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.bg_button_activate));
                    btn_post.setTextColor(getContext().getResources().getColor(R.color.gray));
                    btn_post.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction()
                        .replace(R.id.content, new HomeFragment());
                transaction.commit();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_add_more:
                showDialog();
                break;
            case R.id.btn_post:
                postImage();
                postImage1();
                break;
        }
    }

//        database.getReference().child("users").child(auth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
//        @Override
//        public void onDataChange(@NonNull DataSnapshot snapshot) {
//            if (snapshot.exists()) {
//                User user = snapshot.getValue(User.class);
//                Glide.with(ProfileFragment.this).load(user.getAvatar()).into(imgAvatar);
//                Glide.with(ProfileFragment.this).load(user.getCover()).into(imgCover);
//                userName.setText(user.getName());
//                description.setText(user.getAbout());
//                followers.setText(String.valueOf(user.getFollowerCount()));
//                following.setText(String.valueOf(user.getFollowingCount()));
//            }
//        }
//
//        @Override
//        public void onCancelled(@NonNull DatabaseError error) {
//
//        }
//    });
//        changeCover.setOnClickListener(this);
//        changeAvatar.setOnClickListener(this);
//        btnMenu.setOnClickListener(this);
//}


    private void postImage() {
        ProgressDialog dialog = ProgressDialog.show(getContext(), "",
                "Đang tải ảnh lên... chờ xíuu", true);

        dialog.show();
        final StorageReference reference = storage.getReference().child("post")
                .child(FirebaseAuth.getInstance().getUid())
                .child(new Date().getTime() + "");
        reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Post post = new Post();
//                        String key = database.getReference().child("post").push().getKey();
                        String id = FirebaseAuth.getInstance().getUid();
//                        String img=FirebaseDatabase.getInstance("avatar");
//                        database.getReference().child("users").child(auth.getUid()).child("avatar");
//                        String key = database.getReference().child("post").child("avatar");
                        DatabaseReference img = database.getReference("users").child(auth.getUid());
                        Log.d("img", "onSuccess: " + database.getReference().child("users").child(auth.getUid()).child("avatar"));
                        // Read form the database
//
                        img.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String value = snapshot.getValue(String.class);
                                post.setAvt(value);
                            }


                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Log.d("tes", "fail", error.toException());
                            }
                        });
//                        Log.d("test", "onSuccess: " +String.valueOf(database.getReference().child("users").child(auth.getUid())));


//                         String[] img = {""};
//                        database.getReference().child("users").child(auth.getUid()).child("avatar").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//                            @Override
//                            public void onComplete(@NonNull Task<DataSnapshot> task) {
//                                img[0] = task.getResult().getValue().toString();
//
//                                Log.d("test", "onSuccess: "+task.getResult().getValue().toString());
//                            }
//                        });
                        post.setPostImage(uri.toString());
                        post.setPostDescription(post_description.getText().toString());
                        post.setPostedAt(new Date().getTime());
                        post.setPostLike(5);
                        post.setCommentCount(0);
                        post.setShare(0);
//                        post.setAvt();
                        post.setPostedBy(tv_name_post.getText().toString());
                        post.setPostId(id);
                        database.getReference()
                                .child("post")
                                .child(id).setValue(post).
                                addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        FragmentTransaction fr = getParentFragmentManager().beginTransaction();
                                        fr.replace(R.id.content, new HomeFragment());
                                        fr.commit();
                                        dialog.cancel();
                                    }
                                });
                    }
                });
            }
        });
//

    }

    private void postImage1() {
        final StorageReference reference = storage.getReference().child("post1")
                .child(FirebaseAuth.getInstance().getUid())
                .child(new Date().getTime() + "");
        reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Post post = new Post();
                        String key = database.getReference().child("post1").push().getKey();
                        String img = String.valueOf(database.getInstance().getReference().child("users").child(auth.getUid()).child("avatar").push().getDatabase());
                        Log.d("test", "onSuccess: " + database.getInstance().getReference().child("users").child(auth.getUid()).child("avatar").push().getDatabase());


//                         String[] img = {""};
//                        database.getReference().child("users").child(auth.getUid()).child("avatar").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//                            @Override
//                            public void onComplete(@NonNull Task<DataSnapshot> task) {
//                                img[0] = task.getResult().getValue().toString();
//
//                                Log.d("test", "onSuccess: "+task.getResult().getValue().toString());
//                            }
//                        });
                        post.setPostImage(uri.toString());
                        post.setPostDescription(post_description.getText().toString());
                        post.setPostedAt(new Date().getTime());
                        post.setPostLike(5);
                        post.setCommentCount(0);
                        post.setShare(0);
                        post.setAvt(img);
                        post.setPostedBy(tv_name_post.getText().toString());
                        post.setPostId(key);
                        database.getReference()
                                .child("post1")
                                .child(key).setValue(post).
                                addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
//                                        FragmentTransaction fr = getParentFragmentManager().beginTransaction();
//                                        fr.replace(R.id.content, new HomeFragment());
//                                        fr.commit();
//                                        dialog.cancel();
                                    }
                                });
                    }
                });
            }
        });
//

    }

    private void showDialog() {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheet_post_layout);

        LinearLayout addPhoto = dialog.findViewById(R.id.addphoto);
        LinearLayout tagPerson = dialog.findViewById(R.id.tagperson);
        LinearLayout emoji = dialog.findViewById(R.id.emoji);
        LinearLayout checkin = dialog.findViewById(R.id.checkin);
        LinearLayout stream = dialog.findViewById(R.id.stream);

        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 10);
                dialog.dismiss();

            }
        });
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data.getData() != null) {
            uri = data.getData();

            post_image.setImageURI(uri);
            post_image.setVisibility(View.VISIBLE);

            btn_post.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.bg_button));
            btn_post.setTextColor(getContext().getResources().getColor(R.color.white));
            btn_post.setEnabled(true);
        }
    }

}