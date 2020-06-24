package com.example.curd.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.curd.Adapter.BookListAdapter;
import com.example.curd.Adapter.ReadAdapter;
import com.example.curd.Model.BookListModel;
import com.example.curd.Model.ReadModel;
import com.example.curd.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ReadActivity extends AppCompatActivity implements BookListAdapter.BookInterface {


    private RecyclerView recyclerView;
    private List<ReadModel> readModelList = new ArrayList<>();
    private BookListAdapter bookListAdapter;

    private Dialog loadingDialog;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        swipeRefreshLayout = findViewById(R.id.swip_refresh);

        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Read");


//        loadingDialog();

        recyclerView = findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);



        FirebaseRecyclerOptions<BookListModel> options =
                new FirebaseRecyclerOptions.Builder<BookListModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Books"), BookListModel.class)
                        .build();


        bookListAdapter = new BookListAdapter(options,this);
        recyclerView.setAdapter(bookListAdapter);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                bookListAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });


//        readModelList.add(new ReadModel("https://m.media-amazon.com/images/I/41iers+HLSL.jpg","The Great Gatsby","F. Scott Fitzgerald","The Great Gatsby is a 1925 novel written by American author F. Scott Fitzgerald that follows a cast of characters living in the fictional towns of West Egg and East Egg on prosperous Long Island in the summer of 1922",4.4));
//        readModelList.add(new ReadModel("https://m.media-amazon.com/images/I/41iers+HLSL.jpg","The Great Gatsby","F. Scott Fitzgerald","The Great Gatsby is a 1925 novel written by American author F. Scott Fitzgerald that follows a cast of characters living in the fictional towns of West Egg and East Egg on prosperous Long Island in the summer of 1922",4.4));
//        readModelList.add(new ReadModel("https://m.media-amazon.com/images/I/41iers+HLSL.jpg","The Great Gatsby","F. Scott Fitzgerald","The Great Gatsby is a 1925 novel written by American author F. Scott Fitzgerald that follows a cast of characters living in the fictional towns of West Egg and East Egg on prosperous Long Island in the summer of 1922",4.4));
//        readModelList.add(new ReadModel("https://m.media-amazon.com/images/I/41iers+HLSL.jpg","The Great Gatsby","F. Scott Fitzgerald","The Great Gatsby is a 1925 novel written by American author F. Scott Fitzgerald that follows a cast of characters living in the fictional towns of West Egg and East Egg on prosperous Long Island in the summer of 1922",4.4));
//        readModelList.add(new ReadModel("https://m.media-amazon.com/images/I/41iers+HLSL.jpg","The Great Gatsby","F. Scott Fitzgerald","The Great Gatsby is a 1925 novel written by American author F. Scott Fitzgerald that follows a cast of characters living in the fictional towns of West Egg and East Egg on prosperous Long Island in the summer of 1922",4.4));
//        readModelList.add(new ReadModel("https://m.media-amazon.com/images/I/41iers+HLSL.jpg","The Great Gatsby","F. Scott Fitzgerald","The Great Gatsby is a 1925 novel written by American author F. Scott Fitzgerald that follows a cast of characters living in the fictional towns of West Egg and East Egg on prosperous Long Island in the summer of 1922",4.4));
//        readModelList.add(new ReadModel("https://m.media-amazon.com/images/I/41iers+HLSL.jpg","The Great Gatsby","F. Scott Fitzgerald","The Great Gatsby is a 1925 novel written by American author F. Scott Fitzgerald that follows a cast of characters living in the fictional towns of West Egg and East Egg on prosperous Long Island in the summer of 1922",4.4));
//        readModelList.add(new ReadModel("https://m.media-amazon.com/images/I/41iers+HLSL.jpg","The Great Gatsby","F. Scott Fitzgerald","The Great Gatsby is a 1925 novel written by American author F. Scott Fitzgerald that follows a cast of characters living in the fictional towns of West Egg and East Egg on prosperous Long Island in the summer of 1922",4.4));
//        readModelList.add(new ReadModel("https://m.media-amazon.com/images/I/41iers+HLSL.jpg","The Great Gatsby","F. Scott Fitzgerald","The Great Gatsby is a 1925 novel written by American author F. Scott Fitzgerald that follows a cast of characters living in the fictional towns of West Egg and East Egg on prosperous Long Island in the summer of 1922",4.4));
//        readModelList.add(new ReadModel("https://m.media-amazon.com/images/I/41iers+HLSL.jpg","The Great Gatsby","F. Scott Fitzgerald","The Great Gatsby is a 1925 novel written by American author F. Scott Fitzgerald that follows a cast of characters living in the fictional towns of West Egg and East Egg on prosperous Long Island in the summer of 1922",4.4));
//        readModelList.add(new ReadModel("https://m.media-amazon.com/images/I/41iers+HLSL.jpg","The Great Gatsby","F. Scott Fitzgerald","The Great Gatsby is a 1925 novel written by American author F. Scott Fitzgerald that follows a cast of characters living in the fictional towns of West Egg and East Egg on prosperous Long Island in the summer of 1922",4.4));
//        readModelList.add(new ReadModel("https://m.media-amazon.com/images/I/41iers+HLSL.jpg","The Great Gatsby","F. Scott Fitzgerald","The Great Gatsby is a 1925 novel written by American author F. Scott Fitzgerald that follows a cast of characters living in the fictional towns of West Egg and East Egg on prosperous Long Island in the summer of 1922",4.4));
//        readModelList.add(new ReadModel("https://m.media-amazon.com/images/I/41iers+HLSL.jpg","The Great Gatsby","F. Scott Fitzgerald","The Great Gatsby is a 1925 novel written by American author F. Scott Fitzgerald that follows a cast of characters living in the fictional towns of West Egg and East Egg on prosperous Long Island in the summer of 1922",4.4));
//        readModelList.add(new ReadModel("https://m.media-amazon.com/images/I/41iers+HLSL.jpg","The Great Gatsby","F. Scott Fitzgerald","The Great Gatsby is a 1925 novel written by American author F. Scott Fitzgerald that follows a cast of characters living in the fictional towns of West Egg and East Egg on prosperous Long Island in the summer of 1922",4.4));
//
//
//        ReadAdapter readAdapter = new ReadAdapter(readModelList);
//        recyclerView.setAdapter(readAdapter);


    }

//    private void loadingDialog() {
//
//        loadingDialog = new Dialog(this);
//        loadingDialog.setContentView(R.layout.loading_dialog);
//        loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.loading_rounded_cornner));
//        loadingDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
//        loadingDialog.setCancelable(false);
//    }


    @Override
    protected void onStart() {
        super.onStart();
        bookListAdapter.startListening();
    }


//    @Override
//    protected void onStop() {
//        super.onStop();
//        bookListAdapter.stopListening();
//    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);

    }




    ////BookInterface Methods///////


    @Override
    public void handleDeleteItem(DataSnapshot snapshot, String title) {

    }

    @Override
    public void onItemClick(int position, String imageUrl, String title, String author, String description) {

        Intent bookIntent = new Intent(ReadActivity.this,CollapsingActivity.class);
        bookIntent.putExtra("imageUrl",imageUrl);
        bookIntent.putExtra("title",title);
        bookIntent.putExtra("author",author);
        bookIntent.putExtra("description",description);
        startActivity(bookIntent);
    }

    @Override
    public void handleSwipedData(int adapterPosition, String imageUrl, String title, String author, String description, String keyPosition) {

    }




}
