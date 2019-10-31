package com.example.library.data;


import android.os.AsyncTask;

import androidx.annotation.NonNull;
import android.util.Log;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class BooksAsyncTask extends AsyncTask<Void, Void, List<Book>> {
    private FirebaseFirestore db;
    private List<Book> books;

    public BooksAsyncTask(){
        books = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
    }

    @Override
    protected List<Book> doInBackground(Void... voids) {
        db.collection("books")
                .get()
                .addOnSuccessListener(new OnSuccessListener< QuerySnapshot >() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            Log.d("Fetch Book", document.getId() + " => " + document.getData());
                            Book e=document.toObject(Book.class);
                            e.setId(Integer.parseInt(document.getId()));
                            books.add(e);
                        }
                        // update based on adapter
                        onPostExecute(books);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                            Log.w("Fetch Book", "Error getting documents.", e);
                    }
                });
        return books;
    }

    protected void onPostExecute(List<Book> result) {
    }

}