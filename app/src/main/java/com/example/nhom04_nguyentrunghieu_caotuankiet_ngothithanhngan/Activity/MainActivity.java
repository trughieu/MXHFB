package com.example.nhom04_nguyentrunghieu_caotuankiet_ngothithanhngan.Activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.nhom04_nguyentrunghieu_caotuankiet_ngothithanhngan.Fragment.AddFragment;
import com.example.nhom04_nguyentrunghieu_caotuankiet_ngothithanhngan.Fragment.FriendFragment;
import com.example.nhom04_nguyentrunghieu_caotuankiet_ngothithanhngan.Fragment.HomeFragment;
import com.example.nhom04_nguyentrunghieu_caotuankiet_ngothithanhngan.Fragment.NotificationFragment;
import com.example.nhom04_nguyentrunghieu_caotuankiet_ngothithanhngan.Fragment.ProfileFragment;
import com.example.nhom04_nguyentrunghieu_caotuankiet_ngothithanhngan.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    FloatingActionButton buttonAddPost;
    FirebaseDatabase fDatabase;
    FirebaseAuth fAuth;
//    Toolbar toolbar;
//
//    NavController navController;
//    AppBarConfiguration appBarConfiguration;
//    NavigationView navigationView;
//    DrawerLayout drawer;
//    ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        toolbar=findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        fDatabase = FirebaseDatabase.getInstance();
        fAuth = FirebaseAuth.getInstance();

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setBackground(null);
        buttonAddPost = findViewById(R.id.post_Add);

//        Animator anim = AnimatorInflater.loadAnimator(this, R.animator.fade_out);
//        anim.setTarget(buttonAddPost);
//        anim.start();
/**
 * load animation
 */
//        Animation topAnim = AnimationUtils.loadAnimation(this, R.anim.anim_top);
//
//        buttonAddPost.setAnimation(topAnim);
//        drawer = findViewById(R.id.drawerLayout);


// bottom navigation


        display(R.id.mnuHome);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                display(item.getItemId());
                return true;
            }
        });
    }

    //
//    // hiển thị fragment
    void display(int id) {
        Fragment fragment = null;
        switch (id) {
            case R.id.mnuHome:
                fragment = new HomeFragment();
                break;
            case R.id.mnuFriend:
                fragment = new FriendFragment();
                break;
            case R.id.mnuNote:
                fragment = new NotificationFragment();
                break;
            case R.id.mnuProfile:
                fragment = new ProfileFragment();
                break;
        }
//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        ft.replace(R.id.content, fragment);
//        ft.commit();
//
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction().setCustomAnimations(
                R.anim.anim_right_to_left,
                R.anim.anim_fall_down
        ).replace(R.id.content, fragment);
        ft.commit();


        buttonAddPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().setCustomAnimations(
                        R.anim.slide_in_bottom,
                        R.anim.slide_out_bottom
                ).replace(R.id.content, new AddFragment());
                transaction.commit();
            }
        });

//
//        actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this,
//                drawer,
//                toolbar,
//                R.string.open,
//                R.string.close);
//
//        drawer.addDrawerListener(actionBarDrawerToggle);
//
//        appBarConfiguration = new AppBarConfiguration.Builder(R.id.settingFragment, R.id.navJetBackProfileFragment)
//                .setDrawerLayout(drawer)
//                .build();
//        navController = Navigation.findNavController(this, R.id.content_nav);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);
//
//    }
//
//    @Override
//    public boolean onSupportNavigateUp() {
//        return NavigationUI.navigateUp(navController, appBarConfiguration)
//                || super.onSupportNavigateUp();
//    }
//
//    @Override
//    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
//        super.onPostCreate(savedInstanceState);
//        actionBarDrawerToggle.syncState();
//    }

    }
}