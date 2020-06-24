package com.example.curd.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.curd.R;

public class CollapsingActivity extends AppCompatActivity {

    private ImageView bookImage;
    private TextView bookTitle, bookAuthor, bookDescription;
    private String imageUrl, title, author, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collapsing);


        bookImage = findViewById(R.id.collapsingImage);
        bookAuthor = findViewById(R.id.authorText);
        bookDescription = findViewById(R.id.descriptionText);

        ///Setting up toolbar
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        imageUrl = getIntent().getStringExtra("imageUrl");
        title = getIntent().getStringExtra("title");
        author = getIntent().getStringExtra("author");
        description = getIntent().getStringExtra("description");


        Glide.with(this)
                .load(imageUrl)
                .into(bookImage);


        bookAuthor.setText(author);
        bookDescription.setText(description);

        getSupportActionBar().setTitle(title);
    }





    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);

    }

}
