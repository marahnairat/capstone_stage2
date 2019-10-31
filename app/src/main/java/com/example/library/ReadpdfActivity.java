package com.example.library;

import android.content.Intent;
import android.net.Uri;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


public class ReadpdfActivity extends AppCompatActivity{


    public void readIt(View view) {
        String bookUrl= "https://wickedlysmart.com/headfirstdesignpatterns/HFDP_sampler_Chapter3.pdf";
        Intent n = new Intent(Intent.ACTION_VIEW, Uri.parse(bookUrl));
        https://www.shroffpublishers.com/images/detailed/9/9789352132775.jpg
        startActivity(n);

    }
}