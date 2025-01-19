package com.dtp.mylibrary;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FavoriteBookActivity extends AppCompatActivity {
    private OnBackPressedCallback onBackPressedCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_favorite_book);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RecyclerView bookRecView = findViewById(R.id.bookRecView);
        BookRecViewAdapter adapter = new BookRecViewAdapter(this);
        bookRecView.setAdapter(adapter);
        bookRecView.setLayoutManager(new LinearLayoutManager(this));

        adapter.setBooks(Utils.getFavoriteBooks());

        onBackPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent intent = new Intent(FavoriteBookActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        };

        OnBackPressedDispatcher dispatcher = getOnBackPressedDispatcher();
        dispatcher.addCallback(onBackPressedCallback);

    }
}