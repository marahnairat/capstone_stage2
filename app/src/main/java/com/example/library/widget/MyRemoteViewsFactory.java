package com.example.library.widget;

import android.content.Context;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.library.R;
import com.example.library.data.Book;
import android.util.Log;

import java.util.List;

public class MyRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context context;
    private List< Book > books;

    public MyRemoteViewsFactory(Context context){
        this.context = context;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
        books = LaterReadWidget.getBooks();
    }
    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return books.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_card);
        Book b = books.get(i);
        views.setTextViewText(R.id.book_info, b.getName()+" : "+b.getPdf_url());
        Log.d("MyLog",b.getName());
        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}