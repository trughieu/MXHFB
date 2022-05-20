package com.example.nhom04_nguyentrunghieu_caotuankiet_ngothithanhngan.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.nhom04_nguyentrunghieu_caotuankiet_ngothithanhngan.Activity.LoginActivity;
import com.example.nhom04_nguyentrunghieu_caotuankiet_ngothithanhngan.Adapter.AdapterPost;
import com.example.nhom04_nguyentrunghieu_caotuankiet_ngothithanhngan.Adapter.AdapterProfile;
import com.example.nhom04_nguyentrunghieu_caotuankiet_ngothithanhngan.R;
import com.example.nhom04_nguyentrunghieu_caotuankiet_ngothithanhngan.model.Post;
import com.example.nhom04_nguyentrunghieu_caotuankiet_ngothithanhngan.model.User;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {

//
//
//    ActivityResultLauncher<Intent> mActivityLauncher = registerForActivityResult(
//            new ActivityResultContracts.StartActivityForResult(),
//            new ActivityResultCallback<ActivityResult>() {
//                @Override
//                public void onActivityResult(ActivityResult result) {
//                    if(result.getResultCode()==Activity.RESULT_OK){
//                        Intent intent=result.getData();
//
//                    }
//                }
//            }
//    );

    /**
     * load Data
     */
    FirebaseDatabase database;
    FirebaseAuth auth;
    FirebaseStorage storage;


    int UPLOAD_AVATAR = 11;
    int UPLOAD_COVER = 12;


    KenBurnsView imgCover;
    ImageView imgAvatar;
    TextView userName, description, followers, following, Friend;
    ImageView changeAvatar, changeCover, btnMenu;

    AdapterProfile adapterProfile;
    RecyclerView rvPost;
    ArrayList<Post> posts;
    FirebaseDatabase fDatabase;
    DatabaseReference dPost;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        posts = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        rvPost = view.findViewById(R.id.rv_post_profile);

        imgAvatar = view.findViewById(R.id.avatar);
        imgCover = view.findViewById(R.id.cover);
        userName = view.findViewById(R.id.tv_username);
        changeAvatar = view.findViewById(R.id.btn_change_avatar);
        changeCover = view.findViewById(R.id.btn_change_cover);
        description = view.findViewById(R.id.description);
        followers = view.findViewById(R.id.tv_followers);
        btnMenu = view.findViewById(R.id.btn_menu);
        following = view.findViewById(R.id.tv_following);
        Friend = view.findViewById(R.id.Friend);

        /**
         * load data post
         */

        adapterProfile = new AdapterProfile(posts, getContext());
        rvPost.setAdapter(adapterProfile);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvPost.setLayoutManager(layoutManager);
        rvPost.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        fDatabase = FirebaseDatabase.getInstance();
        dPost = fDatabase.getReference();

        Query qRestaurant = dPost.child("post1");
        qRestaurant.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                posts.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Post post = dataSnapshot.getValue(Post.class);
                    posts.add(post);
                }
                adapterProfile.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });




        database.getReference().child("users").child(auth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    User user = snapshot.getValue(User.class);
                    Glide.with(ProfileFragment.this).load(user.getAvatar()).into(imgAvatar);
                    Glide.with(ProfileFragment.this).load(user.getCover()).into(imgCover);
                    userName.setText(user.getName());
                    description.setText(user.getAbout());
                    followers.setText(String.valueOf(user.getFollowerCount()));
                    following.setText(String.valueOf(user.getFollowingCount()));
                    Friend.setText(String.valueOf(user.getFriend()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        changeCover.setOnClickListener(this);
        changeAvatar.setOnClickListener(this);
        btnMenu.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btn_change_avatar:
                intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, UPLOAD_AVATAR);

                break;
            case R.id.btn_change_cover:
                intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, UPLOAD_COVER);
                break;
            case R.id.btn_menu:
                showDialog();
                break;
        }
    }

    private void showDialog() {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheet_layout);

        LinearLayout logoutLayout = dialog.findViewById(R.id.layout_logout);

        logoutLayout.setOnClickListener(v -> {
            auth.signOut();
            startActivity(new Intent(getContext(), LoginActivity.class));
            if (dialog != null)
                dialog.dismiss();
            getActivity().finish();
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
        if (data.getData() != null && requestCode == UPLOAD_AVATAR) {
            Uri uri = data.getData();
            imgAvatar.setImageURI(uri);

            final StorageReference reference = storage.getReference().child("avtuser").child(auth.getUid());
            reference.putFile(uri).addOnSuccessListener(taskSnapshot -> {
                Toast.makeText(getContext(), "Đã cập nhật ảnh đại diện", Toast.LENGTH_SHORT).show();
                reference.getDownloadUrl().addOnSuccessListener(uri1 ->
                        database.getReference().child("users").child(auth.getUid()).child("avatar").setValue(uri1.toString()));
            });
        } else if (data.getData() != null && requestCode == UPLOAD_COVER) {
            Uri uri = data.getData();
            imgCover.setImageURI(uri);

            final StorageReference reference = storage.getReference().child("coveruser").child(auth.getUid());
            reference.putFile(uri).addOnSuccessListener(taskSnapshot -> {
                Toast.makeText(getContext(), "Đã cập nhật ảnh bìa thành công", Toast.LENGTH_SHORT).show();
                reference.getDownloadUrl().addOnSuccessListener(uri1 ->
                        database.getReference().child("users").child(auth.getUid()).child("cover").setValue(uri1.toString()));
            });
        }
    }
}
