package com.example.curd.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.curd.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.myhexaville.smartimagepicker.ImagePicker;
import com.myhexaville.smartimagepicker.OnImagePickedListener;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class CreateActivity extends AppCompatActivity {

    private ImageView bookImageView;
    private EditText bookTitle, bookAuthor, bookDescription;
    private Button createButton;

    private Dialog loadingDialog;
    private Uri resultImageUri;
    private String downloadUrl;
    private ImagePicker imagePicker;
    private final int PERMISSION_CODE = 69;

    private String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);


        bookImageView = (ImageView) findViewById(R.id.book_imageView);
        bookTitle = findViewById(R.id.title_Edittext);
        bookAuthor = findViewById(R.id.author_Edittext);
        bookDescription = findViewById(R.id.description_Edittext);
        createButton = findViewById(R.id.create_button);



        ///Setting up toolbar
        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Create");



        ///Setting up loading dialog
        loadingDialog = new Dialog(this);
        loadingDialog.setContentView(R.layout.loading_dialog);
        loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.loading_rounded_cornner));
        loadingDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        loadingDialog.setCancelable(false);



        bookImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /// if permission is not granted then if condition will execute
                if (!hasPermissions(CreateActivity.this, PERMISSIONS)) {

                    ActivityCompat.requestPermissions(CreateActivity.this, PERMISSIONS, PERMISSION_CODE);

                }
                /// if permission is granted the else will execute
                else {

                    imagePicker.choosePicture(true /*show camera intents*/);
                }
            }
        });




        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validateData();

            }
        });





        imagePicker = new ImagePicker(this, /* activity non null*/
                null, /* fragment nullable*/
                new OnImagePickedListener() {
                    @Override
                    public void onImagePicked(Uri imageUri) {

                               ///source   destination
                        UCrop.of(imageUri, destinationURI())
                                .withAspectRatio(1,1)
                                .start(CreateActivity.this);

                    }
                });

    } ///onCrearte





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

        if(bookImageView.getDrawable() == null){
            Toast.makeText(CreateActivity.this, "Select the image", Toast.LENGTH_SHORT).show();
            return;
        }

        uploadData();
    }





    private void uploadData() {

        loadingDialog.show();

        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        final StorageReference imageReference = storageReference.child("images").child(resultImageUri.getLastPathSegment());

        ///Here image has been uploaded
        UploadTask uploadTask = imageReference.putFile(resultImageUri);

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

                            uploadTexctData();

                        }else {

                            loadingDialog.dismiss();
                            Toast.makeText(CreateActivity.this, "Something went worng", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });


    }





    private void uploadTexctData() {

        HashMap<String,Object> map = new HashMap<>();
        map.put("bookImageUrl",downloadUrl);
        map.put("bookTitle",bookTitle.getText().toString());
        map.put("bookAuthor",bookAuthor.getText().toString());
        map.put("bookDescription",bookDescription.getText().toString());

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.getReference().child("Books").push().setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){

                    Toast.makeText(CreateActivity.this, bookTitle.getText().toString()+" Book is successfully created", Toast.LENGTH_SHORT).show();
                }else {

                    Toast.makeText(CreateActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }

                loadingDialog.dismiss();

            }
        });
    }





    ///creating CURD directory and temp.png file in root folder
    ///creating destination path or uri
    private Uri destinationURI(){

//        String dri = Environment.getExternalStorageDirectory()+ File.separator+"CURD";
//        File dirFile = new File(dri);
//        dirFile.mkdir();
//
//        String file = dri+File.separator+"temp.png";
//        File tempFile = new File(file);
//        try {
//
//            tempFile.createNewFile();
//
//        } catch (IOException e) {
//
//            e.printStackTrace();
//        }

        String destinationFileName = new StringBuilder(UUID.randomUUID().toString()).append(".jpg").toString();

        return Uri.fromFile(new File(getCacheDir(),destinationFileName));
    }





    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }





    ///permission handeing method
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissionsList[], int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissionsList, grantResults);
        imagePicker.handlePermission(requestCode, grantResults);

        switch (requestCode) {
            case PERMISSION_CODE:{

                if (grantResults.length > 0) {

                    boolean flag = true;

                    for (int i = 0; i < permissionsList.length; i++) {

                        if(grantResults[i] == PackageManager.PERMISSION_DENIED){

                            //
                            flag = false;
                        }

                    }

                    if(flag){

                        ///all permissions are granted
                        imagePicker.choosePicture(true /*show camera intents*/);
                    }


                }
                return;
            }
        }
    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imagePicker.handleActivityResult(resultCode,requestCode, data);

        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {

            resultImageUri = UCrop.getOutput(data);

            bookImageView.setImageURI(null);
            bookImageView.setImageURI(resultImageUri);

        } else if (resultCode == UCrop.RESULT_ERROR) {

            final Throwable cropError = UCrop.getError(data);

        }
    }





    ///this method handles <- back arrow button
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }


}
