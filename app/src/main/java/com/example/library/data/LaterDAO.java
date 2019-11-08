package com.example.library.data;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface LaterDAO {

    @Query("SELECT * FROM Later_on")
    LiveData<List<Book>> loadLater();

    @Insert
    void insertBook(Book e);

    @Delete
    void deleteBook(Book e);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateBook(Book e);

    @Query("SELECT * FROM Later_on WHERE name = :id")
    LiveData<Book> loadBookById(int id);
}