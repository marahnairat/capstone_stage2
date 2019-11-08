package com.example.library;


import android.app.ActivityOptions;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.library.adapters.BookAdapter;
import com.example.library.data.Book;
import com.example.library.data.BooksAsyncTask;
import com.example.library.data.LaterViewModel;
import com.example.library.widget.LaterReadWidget;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class libraryActivity extends AppCompatActivity {
    @BindView(R.id.fav) Button fav;
    @BindView(R.id.wread) Button wread;
    @BindView(R.id.don)Button don;
    @BindView(R.id.allbooks)Button allbooks;

    @BindView(R.id.toolbar)
    Toolbar myToolBar;
    @BindView(R.id.toolbar_title)
    TextView title;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private FirebaseAuth firebaseAuth;
    private BookAdapter mBooksAdapter;
    private ArrayList<Book> books = new ArrayList<>();
    private Context context;
    private Bundle bundle;
    private List<Book> later = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);
        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            bundle = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
            Slide slide = new Slide(Gravity.BOTTOM);
            slide.addTarget(R.id.recycler_view);
            getWindow().setEnterTransition(slide);
        }

        firebaseAuth = FirebaseAuth.getInstance();

        setSupportActionBar(myToolBar);
        title.setText(R.string.book_list);
        setTitle("");
        context = getApplicationContext();
        initRecyclerView();
        setUpViewModel();
        callTheAsyncTask();
        allbooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context = getApplicationContext();
                callTheAsyncTask();
            }
        });
        wread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context = getApplicationContext();
                callTheAsyncTask();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.all:
                callTheAsyncTask();
                break;
            case R.id.wread:
                mBooksAdapter.setBooks(later);
                mBooksAdapter.notifyDataSetChanged();
                break;
            case R.id.log_out:
                LogOut();
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }
    private void LogOut() {
        firebaseAuth.signOut();
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        finish();
    }


    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mBooksAdapter = new BookAdapter(books, context);
        mRecyclerView.setAdapter(mBooksAdapter);

        mBooksAdapter.setItemClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
                int position = viewHolder.getAdapterPosition();
                Intent n = new Intent(context, BookDetailsActivity.class);
                Gson gson = new Gson();
                n.putExtra(Book.class.getName(), gson.toJson(mBooksAdapter.getBooks().get(position)));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(n, bundle);
                }
            }
        });
    }

    private void callTheAsyncTask(){
        new BooksAsyncTask(){
            @Override
            protected void onPostExecute(List<Book> result) {
                super.onPostExecute(result);
                mBooksAdapter.setBooks(result);
                mBooksAdapter.notifyDataSetChanged();
            }
        }.execute();
    }

    private void setUpViewModel(){
        Log.d("MyLog","setUpViewModel");
        LaterViewModel viewModel = ViewModelProviders.of(this).get(LaterViewModel.class);
        viewModel.getLater().observe(this, new Observer<List<Book>>() {
            @Override
            public void onChanged(List<Book> books) {
                later.clear();
                later.addAll(books);
                mBooksAdapter.notifyDataSetChanged();
                for (int i=0; i< books.size();i++)
                    Log.d("MyLogWidget", books.get(i).getId()+"");
                addLaterToWidget(books);
            }
        });
    }

    private void addLaterToWidget(List<Book> b){
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(
                new ComponentName(context, LaterReadWidget.class));
        LaterReadWidget.updateAppWidget(context, appWidgetManager, appWidgetIds, b);
    }
}
