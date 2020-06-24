package com.example.curd.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.curd.Adapter.BookListAdapter;
import com.example.curd.Adapter.ReadAdapter;
import com.example.curd.Model.BookListModel;
import com.example.curd.Model.ReadModel;
import com.example.curd.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.ObservableSnapshotArray;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class DeleteActivity extends AppCompatActivity implements BookListAdapter.BookInterface {


    private RecyclerView recyclerView;
    private List<ReadModel> readModelList = new ArrayList<>();

    private BookListAdapter bookListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        Toolbar toolbar = findViewById(R.id.tool_bar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Delete");


        recyclerView = findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);




        FirebaseRecyclerOptions<BookListModel> options =
                new FirebaseRecyclerOptions.Builder<BookListModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Books"), BookListModel.class)
                        .build();


        bookListAdapter = new BookListAdapter(options, this);
        recyclerView.setAdapter(bookListAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);




    }




    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            if(direction == ItemTouchHelper.LEFT){

                BookListAdapter.BookViewHolder bookViewHolder = (BookListAdapter.BookViewHolder) viewHolder;
                bookViewHolder.deleteItem();

            }

        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addBackgroundColor(ContextCompat.getColor(DeleteActivity.this, R.color.swipeBackgroundColor))
                    .addActionIcon(R.drawable.delete_swipe_icon)
                    .addSwipeLeftLabel("Delete")
                    .setSwipeLeftLabelColor(ContextCompat.getColor(DeleteActivity.this,R.color.whiteColor))
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };




    @Override
    protected void onStart() {
        super.onStart();
        bookListAdapter.startListening();

    }


//    @Override
//    protected void onStop() {
//        super.onStop();
//        bookListAdapter.stopListening();
//
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
    public void handleDeleteItem(DataSnapshot snapshot, final String title) {


        final DatabaseReference databaseReference = snapshot.getRef();
        final BookListModel bookListModel = snapshot.getValue(BookListModel.class);


        databaseReference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()){

                    Snackbar.make(recyclerView, title + " is Deleted ", Snackbar.LENGTH_LONG)
                            .setAction("Undo", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    databaseReference.setValue(bookListModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){

                                                Snackbar snackbar = Snackbar.make(recyclerView, title + " is Undo ", Snackbar.LENGTH_SHORT);
                                                snackbar.show();
                                            }
                                        }
                                    });
                                }
                            })
                            .setActionTextColor(ContextCompat.getColor(DeleteActivity.this, R.color.blueColor))
                            .show();

                }else {

                    Toast.makeText(DeleteActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();

                }
            }
        });



    }




    @Override
    public void onItemClick(int position, String imageUrl, String title, String author, String description) {

    }

    @Override
    public void handleSwipedData(int adapterPosition, String imageUrl, String title, String author, String description, String keyPosition) {

    }



}
