package com.example.colornote;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import com.example.colornote.observer.Publisher;
import com.example.colornote.ui.ListOfNotesFragment;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private final Publisher publisher = new Publisher();
    private Navigation navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigation = new Navigation(getSupportFragmentManager());
        initToolbar();
        getNavigation().addFragment(ListOfNotesFragment.newInstance(), false);
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public Navigation getNavigation() {
        return navigation;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem search = menu.findItem(R.id.menu_search);
        SearchView searchText = (SearchView) search.getActionView();
        searchText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(MainActivity.this, query, Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
        MenuItem color = menu.findItem(R.id.color);
        color.setOnMenuItemClickListener(item -> {
            Toast.makeText(MainActivity.this, R.string.color, Toast.LENGTH_SHORT).show();
            return true;
        });
        MenuItem sort = menu.findItem(R.id.menu_sort);
        sort.setOnMenuItemClickListener(item -> {
            Toast.makeText(MainActivity.this, R.string.menu_sort, Toast.LENGTH_SHORT).show();
            return true;
        });

        MenuItem photo = menu.findItem(R.id.foto);
        photo.setOnMenuItemClickListener(item -> {
            Toast.makeText(MainActivity.this, R.string.menu_add_photo, Toast.LENGTH_SHORT).show();
            return true;
        });

        MenuItem sinhr = menu.findItem(R.id.sinhr);
        sinhr.setOnMenuItemClickListener(item -> {
            Toast.makeText(MainActivity.this, R.string.sinhr, Toast.LENGTH_SHORT).show();
            return true;
        });

        MenuItem napominanie = menu.findItem(R.id.napominanie);
        napominanie.setOnMenuItemClickListener(item -> {
            Toast.makeText(MainActivity.this, R.string.napominanie, Toast.LENGTH_SHORT).show();
            return true;
        });
        MenuItem send = menu.findItem(R.id.pereslat);
        send.setOnMenuItemClickListener(item -> {
            Toast.makeText(MainActivity.this, R.string.pereslat, Toast.LENGTH_SHORT).show();
            return true;
        });
        MenuItem setting = menu.findItem(R.id.setting);
        setting.setOnMenuItemClickListener(item -> {
            Toast.makeText(MainActivity.this, R.string.setting, Toast.LENGTH_SHORT).show();
            return true;
        });
        MenuItem clear = menu.findItem(R.id.clear);
        clear.setOnMenuItemClickListener(item -> {
            Toast.makeText(MainActivity.this, R.string.clear, Toast.LENGTH_SHORT).show();
            return true;
        });
        return true;
    }
}