package com.dtp.mylibrary;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

public class BookActivity extends AppCompatActivity {
    public static final String BOOK_ID_KEY = "bookId";
    private Button btnCurrentlyReading, btnWantToRead, btnAlreadyRead, btnAddToFavorites;
    private TextView tvBookName, tvAuthor, tvPages, tvLongDesc;
    private ImageView imgBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_book);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();

        Intent intent = getIntent();
        if (null != intent) {
            int bookId = intent.getIntExtra(BOOK_ID_KEY, -1);
            if (bookId != -1) {
                Book book = Utils.getInstance().getBookById(bookId);
                if (null != book) {
                    setData(book);

                    handleAlreadyReadBook(book);
                    handleWantToReadBooks(book);
                    handleCurrentlyReadingBooks(book);
                    handleAddToFavorites(book);
                }
            }
        }
    }

    private void handleAlreadyReadBook(final Book book) {
        boolean isAlreadyRead = false;

        for (Book b : Utils.getAlreadyReadBooks()) {
            if (b.getId() == book.getId()) {
                isAlreadyRead = true;
                break;
            }
        }

        if (isAlreadyRead) {
            btnAlreadyRead.setEnabled(false);
        } else {
            btnAlreadyRead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Utils.getInstance().addToAlreadyRead(book)) {
                        Toast.makeText(BookActivity.this, "Book added", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(BookActivity.this, AlreadyReadBookActivity.class);
                        startActivity(intent);
                    }
                }
            });
        }
    }

    private void handleWantToReadBooks(final Book book) {
        boolean isAlreadyWantToRead = false;

        for (Book b : Utils.getWantToReadBooks()) {
            if (b.getId() == book.getId()) {
                isAlreadyWantToRead = true;
                break;
            }
        }

        if (isAlreadyWantToRead) {
            btnWantToRead.setEnabled(false);
        } else {
            btnWantToRead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Utils.getInstance().addToWantToRead(book)) {
                        Toast.makeText(BookActivity.this, "Book added", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(BookActivity.this, WantToReadBookActivity.class);
                        startActivity(intent);
                    }
                }
            });
        }
    }

    private void handleCurrentlyReadingBooks(final Book book) {
        boolean isAlreadyReading = false;

        for (Book b : Utils.getAlreadyReadBooks()) {
            if (b.getId() == book.getId()) {
                isAlreadyReading = true;
                break;
            }
        }

        if (isAlreadyReading) {
            btnCurrentlyReading.setEnabled(false);
        } else {
            btnCurrentlyReading.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Utils.getInstance().addToCurrentlyRead(book)) {
                        Toast.makeText(BookActivity.this, "Book added", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(BookActivity.this, CurrentlyReadingActivity.class);
                        startActivity(intent);
                    }
                }
            });
        }
    }

    private void handleAddToFavorites(final Book book) {
        boolean isAlreadyFavorites = false;

        for (Book b : Utils.getAlreadyReadBooks()) {
            if (b.getId() == book.getId()) {
                isAlreadyFavorites = true;
                break;
            }
        }

        if (isAlreadyFavorites) {
            btnAddToFavorites.setEnabled(false);
        } else {
            btnAddToFavorites.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Utils.getInstance().addToFavorites(book)) {
                        Toast.makeText(BookActivity.this, "Book added", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(BookActivity.this, FavoriteBookActivity.class);
                        startActivity(intent);
                    }
                }
            });
        }
    }

    private void setData(Book book) {
        tvBookName.setText(book.getName());
        tvAuthor.setText(book.getAuthor());
        tvPages.setText(String.valueOf(book.getPages()));
        tvLongDesc.setText(book.getLongDesc());

        Glide.with(this)
                .asBitmap()
                .load(book.getImageUrl())
                .into(imgBook);
    }

    private void initViews() {
        btnCurrentlyReading = findViewById(R.id.btnCurrentlyReading);
        btnWantToRead = findViewById(R.id.btnWantToRead);
        btnAlreadyRead = findViewById(R.id.btnAlreadyRead);
        btnAddToFavorites = findViewById(R.id.btnAddToFavorites);

        tvBookName = findViewById(R.id.tvBookName);
        tvAuthor = findViewById(R.id.tvAuthor);
        tvPages = findViewById(R.id.tvPages);
        tvLongDesc = findViewById(R.id.tvLongDesc);

        imgBook = findViewById(R.id.imgBook);
    }
}