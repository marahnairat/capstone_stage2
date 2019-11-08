package com.example.library;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.library.adapters.BookAdapter;
import com.example.library.data.AppDatabase;
import com.example.library.data.AppExecutors;
import com.example.library.data.Book;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookDetailsActivity extends AppCompatActivity {
    @BindView(R.id.readpdf)
    Button readpdf;
    @BindView(R.id.add_later)
    Button add_later;
    @BindView(R.id.add_fav)
    Button add_fav;
    @BindView(R.id.toolbar_title)
    TextView title;
    @BindView(R.id.bookcover)
    ImageView bookcover;
    @BindView(R.id.bookdesc)
    TextView bookdesc;
    private Book b;
    private AppDatabase db;
    private LiveData< Book > f;
    private boolean fav;

    private FirebaseAuth firebaseAuth;
    private BookAdapter mBooksAdapter;
    private Context context;
    private Bundle bundle;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookdetails_activity);
        ButterKnife.bind(this);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Slide slide = new Slide(Gravity.TOP);
            slide.addTarget(R.id.add_fav);
            getWindow().setEnterTransition(slide);
            getWindow().setExitTransition(new Explode());
        }
        //replace data

        db = AppDatabase.getInstance(getApplicationContext());

        String Extra = getIntent().getStringExtra(Book.class.getName());
        Gson gson = new Gson();
        if (savedInstanceState == null) {
            b = gson.fromJson(Extra, Book.class);
        }
//        Toast.makeText(BookDetailsActivity.this,
//                b.getDesc(), Toast.LENGTH_LONG).show();
        bookdesc.setText(b.getDesc());
        String imageUri =b.getCover_url();
        Picasso.with(this).load(imageUri).into(bookcover);

        //setSupportActionBar(mToolBar);
        title.setText(b.getName());
        //setTitle("");
        add_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppExecutors.getInstance().getDiskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (fav) {
                            //remove from database
                            db.laterDAO().deleteBook(b);
                        } else {
                            //add to database
                            //FavoriteMovie(m);
                            db.laterDAO().insertBook(b);
                        }
                    }
                });
                if (fav) {
                    add_fav.setText("Add to Favorite");
                    Toast.makeText(BookDetailsActivity.this,
                            "Removed from Favorites", Toast.LENGTH_SHORT).show();
                } else {
                    add_fav.setText("Remove from Favorite");
                    Toast.makeText(BookDetailsActivity.this,
                            "Added To Favorites", Toast.LENGTH_SHORT).show();
                }
            }
        });
        readpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bookUrl= b.getPdf_url();
                Intent n = new Intent(Intent.ACTION_VIEW, Uri.parse(bookUrl));
                startActivity(n);
            }
        });

    }

    @Override
    protected void onResume() {
        f = db.laterDAO().loadBookById(b.getId());
        f.observeForever(new Observer< Book >() {
            @Override
            public void onChanged(Book exercise) {
                if (f.getValue() != null) {
                    fav = true;
                    add_fav.setText("Remove from Favorite");
                } else {
                    fav = false;
                }
            }
        });
        if (fav) {
            add_fav.setText("Remove from Favorite");
        }
        super.onResume();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable("book", b);
        super.onSaveInstanceState(outState);
    }




}