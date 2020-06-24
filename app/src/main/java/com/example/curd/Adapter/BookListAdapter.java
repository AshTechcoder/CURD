package com.example.curd.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.curd.Model.BookListModel;
import com.example.curd.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.ObservableSnapshotArray;
import com.google.firebase.database.DataSnapshot;

public class BookListAdapter extends FirebaseRecyclerAdapter<BookListModel, BookListAdapter.BookViewHolder> {

    BookListModel bookListModel = new BookListModel();
    BookInterface bookInterface;

    public BookListAdapter(@NonNull FirebaseRecyclerOptions<BookListModel> options, BookInterface bookInterface) {
        super(options);
        this.bookInterface = bookInterface;
    }



    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_book_item, parent, false);

        return new BookViewHolder(view);
    }



    @Override
    protected void onBindViewHolder(@NonNull BookViewHolder holder, int position, @NonNull BookListModel bookListModel) {

        holder.setData(bookListModel.getBookImageUrl(),bookListModel.getBookTitle(),bookListModel.getBookAuthor(),bookListModel.getBookDescription(), getRef(position).getKey());


    }


    public class BookViewHolder extends RecyclerView.ViewHolder{

        private String imageUrl, title, author, description, keyPosition;
        private ImageView bookImage;
        private TextView bookTitle, bookAuthor, bookDescription;




        public BookViewHolder(@NonNull View itemView) {
            super(itemView);

            bookImage = itemView.findViewById(R.id.book_image);
            bookTitle = itemView.findViewById(R.id.book_title_text);
            bookAuthor = itemView.findViewById(R.id.book_author_text);
            bookDescription = itemView.findViewById(R.id.book_description_text);
        }




        private void setData(final String imageUrl, final String title, final String author, final String description, String keyPosition){

            this.imageUrl = imageUrl;
            this.title = title;
            this.author = author;
            this.description = description;
            this.keyPosition = keyPosition;



            Glide.with(itemView.getContext())
                    .load(imageUrl)
                    .into(bookImage);


            bookTitle.setText(title);
            bookAuthor.setText(author);
            bookDescription.setText(description);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    bookInterface.onItemClick(getAdapterPosition(), imageUrl, title, author, description);
                }
            });

        }


        public void onSwipedUpdate(){

            bookInterface.handleSwipedData(getAdapterPosition(), imageUrl, title, author, description, keyPosition);
        }




        public void deleteItem(){

            bookInterface.handleDeleteItem(getSnapshots().getSnapshot(getAdapterPosition()), title);

        }


    }



    public interface BookInterface{

        void handleDeleteItem(DataSnapshot snapshot, String title);
        void onItemClick(int position, String imageUrl, String title, String author, String description);
        void handleSwipedData(int adapterPosition, String imageUrl, String title, String author, String description, String keyPosition);

    }
}
