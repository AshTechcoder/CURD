package com.example.curd.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.curd.Adapter.BookListAdapter;
import com.example.curd.Adapter.ReadAdapter;
import com.example.curd.Model.BookListModel;
import com.example.curd.Model.ReadModel;
import com.example.curd.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.myhexaville.smartimagepicker.ImagePicker;
import com.myhexaville.smartimagepicker.OnImagePickedListener;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class UpdateActivity extends AppCompatActivity implements BookListAdapter.BookInterface {

    private RecyclerView recyclerView;
    private List<ReadModel> readModelList = new ArrayList<>();
    private BookListAdapter bookListAdapter;

    private Dialog updateDialog, loadingDialog;
    private ImageView bookImage;
    private EditText bookTitle, bookAuthor, bookDescription;
    private Button createButton;

    private SwipeRefreshLayout swipeRefreshLayout;

    private String imageUrl, title, author, description, keyPosition;

    private Uri resultImageUri;
    private String downloadUrl;
    private ImagePicker imagePicker;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);


        swipeRefreshLayout = findViewById(R.id.swip_refresh1);

        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Update");


        ///Setting up loading dialog
        loadingDialog = new Dialog(this);
        loadingDialog.setContentView(R.layout.update_loading_dialog);
        loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.loading_rounded_cornner));
        loadingDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        loadingDialog.setCancelable(false);


        setUpdateDialog();

        recyclerView = findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);



//        readModelList.add(new ReadModel("https://m.media-amazon.com/images/I/41iers+HLSL.jpg","The Great Gatsby","F. Scott Fitzgerald","The Great Gatsby is a 1925 novel written by American author F. Scott Fitzgerald that follows a cast of characters living in the fictional towns of West Egg and East Egg on prosperous Long Island in the summer of 1922"));
//        readModelList.add(new ReadModel("https://m.media-amazon.com/images/I/41iers+HLSL.jpg","The Great Gatsby","F. Scott Fitzgerald","The Great Gatsby is a 1925 novel written by American author F. Scott Fitzgerald that follows a cast of characters living in the fictional towns of West Egg and East Egg on prosperous Long Island in the summer of 1922"));
//        readModelList.add(new ReadModel("https://m.media-amazon.com/images/I/41iers+HLSL.jpg","The Great Gatsby","F. Scott Fitzgerald","The Great Gatsby is a 1925 novel written by American author F. Scott Fitzgerald that follows a cast of characters living in the fictional towns of West Egg and East Egg on prosperous Long Island in the summer of 1922"));
//        readModelList.add(new ReadModel("https://m.media-amazon.com/images/I/41iers+HLSL.jpg","The Great Gatsby","F. Scott Fitzgerald","The Great Gatsby is a 1925 novel written by American author F. Scott Fitzgerald that follows a cast of characters living in the fictional towns of West Egg and East Egg on prosperous Long Island in the summer of 1922"));
//        readModelList.add(new ReadModel("https://m.media-amazon.com/images/I/41iers+HLSL.jpg","The Great Gatsby","F. Scott Fitzgerald","The Great Gatsby is a 1925 novel written by American author F. Scott Fitzgerald that follows a cast of characters living in the fictional towns of West Egg and East Egg on prosperous Long Island in the summer of 1922"));
//        readModelList.add(new ReadModel("https://m.media-amazon.com/images/I/41iers+HLSL.jpg","The Great Gatsby","F. Scott Fitzgerald","The Great Gatsby is a 1925 novel written by American author F. Scott Fitzgerald that follows a cast of characters living in the fictional towns of West Egg and East Egg on prosperous Long Island in the summer of 1922"));
//        readModelList.add(new ReadModel("https://m.media-amazon.com/images/I/41iers+HLSL.jpg","The Great Gatsby","F. Scott Fitzgerald","The Great Gatsby is a 1925 novel written by American author F. Scott Fitzgerald that follows a cast of characters living in the fictional towns of West Egg and East Egg on prosperous Long Island in the summer of 1922"));
//        readModelList.add(new ReadModel("https://m.media-amazon.com/images/I/41iers+HLSL.jpg","The Great Gatsby","F. Scott Fitzgerald","The Great Gatsby is a 1925 novel written by American author F. Scott Fitzgerald that follows a cast of characters living in the fictional towns of West Egg and East Egg on prosperous Long Island in the summer of 1922"));
//        readModelList.add(new ReadModel("https://m.media-amazon.com/images/I/41iers+HLSL.jpg","The Great Gatsby","F. Scott Fitzgerald","The Great Gatsby is a 1925 novel written by American author F. Scott Fitzgerald that follows a cast of characters living in the fictional towns of West Egg and East Egg on prosperous Long Island in the summer of 1922"));
//        readModelList.add(new ReadModel("https://m.media-amazon.com/images/I/41iers+HLSL.jpg","The Great Gatsby","F. Scott Fitzgerald","The Great Gatsby is a 1925 novel written by American author F. Scott Fitzgerald that follows a cast of characters living in the fictional towns of West Egg and East Egg on prosperous Long Island in the summer of 1922"));
//        readModelList.add(new ReadModel("https://m.media-amazon.com/images/I/41iers+HLSL.jpg","The Great Gatsby","F. Scott Fitzgerald","The Great Gatsby is a 1925 novel written by American author F. Scott Fitzgerald that follows a cast of characters living in the fictional towns of West Egg and East Egg on prosperous Long Island in the summer of 1922"));
//        readModelList.add(new ReadModel("https://m.media-amazon.com/images/I/41iers+HLSL.jpg","The Great Gatsby","F. Scott Fitzgerald","The Great Gatsby is a 1925 novel written by American author F. Scott Fitzgerald that follows a cast of characters living in the fictional towns of West Egg and East Egg on prosperous Long Island in the summer of 1922"));
//        readModelList.add(new ReadModel("https://m.media-amazon.com/images/I/41iers+HLSL.jpg","The Great Gatsby","F. Scott Fitzgerald","The Great Gatsby is a 1925 novel written by American author F. Scott Fitzgerald that follows a cast of characters living in the fictional towns of West Egg and East Egg on prosperous Long Island in the summer of 1922"));
//        readModelList.add(new ReadModel("https://m.media-amazon.com/images/I/41iers+HLSL.jpg","The Great Gatsby","F. Scott Fitzgerald","The Great Gatsby is a 1925 novel written by American author F. Scott Fitzgerald that follows a cast of characters living in the fictional towns of West Egg and East Egg on prosperous Long Island in the summer of 1922"));
//
//
//        final ReadAdapter readAdapter = new ReadAdapter(readModelList);
//        recyclerView.setAdapter(readAdapter);




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




        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);



        imagePicker = new ImagePicker(this, /* activity non null*/
                null, /* fragment nullable*/
                new OnImagePickedListener() {
                    @Override
                    public void onImagePicked(Uri imageUri) {

                        ///source   destination
                        UCrop.of(imageUri, destinationURI())
                                .withAspectRatio(1,1)
                                .start(UpdateActivity.this);

                    }
                });







    }




    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            if (direction == ItemTouchHelper.RIGHT){


                BookListAdapter.BookViewHolder bookViewHolder = (BookListAdapter.BookViewHolder) viewHolder;
                bookViewHolder.onSwipedUpdate();

                updateDialog.show();
            }

        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addBackgroundColor(ContextCompat.getColor(UpdateActivity.this, R.color.swipeBackgroundColor))
                    .addActionIcon(R.drawable.update_swipe_icon)
                    .addSwipeRightLabel("Update")
                    .setSwipeRightLabelColor(ContextCompat.getColor(UpdateActivity.this,R.color.whiteColor))
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

    }

    @Override
    public void handleSwipedData(int adapterPosition, String imageUrl, String title, String author, String description, String keyPosition) {

        this.keyPosition = keyPosition;

        Glide.with(this)
                .load(imageUrl)
                .into(bookImage);

        bookTitle.setText(title);
        bookAuthor.setText(author);
        bookDescription.setText(description);

    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imagePicker.handleActivityResult(resultCode,requestCode, data);

        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {

            resultImageUri = UCrop.getOutput(data);


            bookImage.setImageURI(resultImageUri);

        } else if (resultCode == UCrop.RESULT_ERROR) {

            final Throwable cropError = UCrop.getError(data);

        }
    }





    private void setUpdateDialog() {


        updateDialog = new Dialog(this);
        updateDialog.setContentView(R.layout.update_dialog);
        updateDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        updateDialog.setCancelable(true);

        bookImage = updateDialog.findViewById(R.id.imageView);
        bookTitle = updateDialog.findViewById(R.id.titleEditText);
        bookAuthor = updateDialog.findViewById(R.id.authorEditText);
        bookDescription = updateDialog.findViewById(R.id.descriptionEditText);
        createButton = updateDialog.findViewById(R.id.creteBtn);



        bookImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePicker.choosePicture(true /*show camera intents*/);
            }
        });


        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
                updateDialog.dismiss();
            }
        });

    }





    private void validateData() {

        if(bookTitle.getText().toString().isEmpty()){
            bookTitle.setError("Required");
            return;
        }

        if(bookAuthor.getText().toString().isEmpty()){
            bookAuthor.setError("Required");
            return;
        }

        if(bookDescription.getText().toString().isEmpty()){
            bookDescription.setError("Required");
            return;
        }

        if(bookImage.getDrawable() == null){
            Toast.makeText(UpdateActivity.this, "Select the image", Toast.LENGTH_SHORT).show();
            return;
        }

        updateData();
    }





    private void updateData() {

        loadingDialog.show();

        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        final StorageReference imageReference = storageReference.child("images").child(resultImageUri.getLastPathSegment());


        ///Here image has been uploaded
        UploadTask uploadTask = imageReference.putFile(resultImageUri);



        ///Here we r pulling the image url and stored into a downloadUrl variable
        Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                if(!task.isSuccessful()){
                    throw task.getException();
                }

                return imageReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {

                        if(task.isSuccessful()){

                            downloadUrl = task.getResult().toString();

                            updateTexctData();

                        }else {

                            loadingDialog.dismiss();
                            Toast.makeText(UpdateActivity.this, "Something went worng", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });


    }





    private void updateTexctData() {

        Map<String,Object> map = new HashMap<>();
        map.put("bookImageUrl",downloadUrl);
        map.put("bookTitle",bookTitle.getText().toString());
        map.put("bookAuthor",bookAuthor.getText().toString());
        map.put("bookDescription",bookDescription.getText().toString());


        FirebaseDatabase.getInstance().getReference()
                .child("Books").child(keyPosition)
                .updateChildren(map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()){

                            Snackbar.make(recyclerView, "Book Update Successfuly", Snackbar.LENGTH_SHORT).show();

                        }else {

                            Snackbar.make(recyclerView, "Something went wrong while Updating", Snackbar.LENGTH_SHORT).show();
                        }

                        loadingDialog.dismiss();
                    }
                });



    }





    private Uri destinationURI() {

        String destinationFileName = new StringBuilder(UUID.randomUUID().toString()).append(".jpg").toString();

        return Uri.fromFile(new File(getCacheDir(),destinationFileName));
    }




//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        imagePicker.handleActivityResult(resultCode,requestCode, data);
//
//        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
//
//            resultImageUri = UCrop.getOutput(data);
//
//            bookImageView.setImageURI(null);
//            bookImageView.setImageURI(resultImageUri);
//
//        } else if (resultCode == UCrop.RESULT_ERROR) {
//
//            final Throwable cropError = UCrop.getError(data);
//
//        }
//    }

}
