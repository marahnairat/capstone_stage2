package com.example.library.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.library.R;
import com.example.library.data.Book;

import java.util.ArrayList;
import java.util.List;

public class LaterReadWidget extends AppWidgetProvider {

    private static List<Book> books = new ArrayList<>();

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                       int[] appWidgetIds, List< Book > laterRead) {

        Log.d("MyLog","widgetUpdate");
        books = laterRead;
        for (int appWidgetId : appWidgetIds) {
            // Construct the RemoteViews object
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.will_read_activity);
            Intent intent = new Intent(context, widgetListAdapter.class);
            views.setRemoteAdapter(R.id.widget_willread_list, intent);
            ComponentName component = new ComponentName(context, LaterReadWidget.class);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_willread_list);
            appWidgetManager.updateAppWidget(component, views);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    public static List<Book> getBooks(){
        return books;
    }
}
