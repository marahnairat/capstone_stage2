package com.example.library.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.library.R;
import com.example.library.data.Book;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder>{

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.book_name)
        TextView name;

        private ViewHolder(View itemView) {
            super(itemView);
            itemView.setTag(this);
            itemView.setOnClickListener(onBooksClickListener);
            ButterKnife.bind(this, itemView);
        }
    }

    private List< Book > books;
    private View.OnClickListener onBooksClickListener;
    private Context context;

    // Pass in the tasks array into the constructor
    public BookAdapter(List<Book> books , Context context) {
        this.books = books;
        this.context = context;
    }

    public void setBooks(List<Book> books){
        this.books = books;
    }

    public List<Book> getBooks(){
        return books;
    }

    public void setItemClickListener(View.OnClickListener clickListener) {
        onBooksClickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View TaskView = inflater.inflate(R.layout.activity_library, parent, false);

        // Return a new holder instance
        return new ViewHolder(TaskView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Book e = books.get(position);
        holder.name.setText(e.getName());
    }

    @Override
    public int getItemCount() {
        return books.size();
    }
}