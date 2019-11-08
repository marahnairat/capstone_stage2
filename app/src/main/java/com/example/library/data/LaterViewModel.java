package com.example.library.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class LaterViewModel extends AndroidViewModel {

    private final LiveData< List<Book> > LaterRead;

    public LaterViewModel(@NonNull Application application) {
        super(application);
        AppDatabase db = AppDatabase.getInstance(this.getApplication());
        LaterRead = db.laterDAO().loadLater();
    }

    public LiveData<List<Book>> getLater() {
        return LaterRead;
    }
}