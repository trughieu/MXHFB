package com.example.nhom04_nguyentrunghieu_caotuankiet_ngothithanhngan.Activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom04_nguyentrunghieu_caotuankiet_ngothithanhngan.Adapter.AdapterSearch;
import com.example.nhom04_nguyentrunghieu_caotuankiet_ngothithanhngan.R;
import com.example.nhom04_nguyentrunghieu_caotuankiet_ngothithanhngan.model.User;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SearchActivity extends AppCompatActivity {
    RecyclerView Search;
    AdapterSearch adapterSearch;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setTitle("");

        Search = findViewById(R.id.rcSeach);
        Search.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<User> options =
                new FirebaseRecyclerOptions.Builder<User>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("users"), User.class)
                        .build();

        adapterSearch = new AdapterSearch(options);
        Search.setAdapter(adapterSearch);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapterSearch.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapterSearch.stopListening();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        MenuItem item = menu.findItem(R.id.mnuSearch);

        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Search(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Search(s);
                return false;
            }
        });
//
        return super.onCreateOptionsMenu(menu);
    }

    private void Search(String s) {
        FirebaseRecyclerOptions<User> options =
                new FirebaseRecyclerOptions.Builder<User>()
                        .setQuery(FirebaseDatabase.getInstance()
                                .getReference().child("users").orderByChild("name").startAt(s).endAt(s + "\uf8ff"), User.class)
                        .build();

        adapterSearch = new AdapterSearch(options);
        adapterSearch.startListening();
        Search.setAdapter(adapterSearch);

    }
}