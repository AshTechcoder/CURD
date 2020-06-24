package com.example.curd.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.curd.Model.ReadModel;
import com.example.curd.R;

import java.util.List;

public class ReadAdapter extends RecyclerView.Adapter<ReadAdapter.viewholder> {

    private List<ReadModel> readModelList;

    public ReadAdapter(List<ReadModel> readModelList) {
        this.readModelList = readModelList;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_book_item,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        holder.setData(readModelList.get(position).getImageUrl(),readModelList.get(position).getBookTitle(),readModelList.get(position).getBookAuthor(),readModelList.get(position).getBookDescription());

    }

    @Override
    public int getItemCount() {
        return readModelList.size();
    }



     class viewholder extends RecyclerView.ViewHolder{

        private ImageView bookImage;
        private TextView bookTitle, bookAuthor, bookDescription;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            bookImage = itemView.findViewById(R.id.book_image);
            bookTitle = itemView.findViewById(R.id.book_title_text);
            bookAuthor = itemView.findViewById(R.id.book_author_text);
            bookDescription = itemView.findViewById(R.id.book_description_text);
//            bookRating = itemView.findViewById(R.id.book_rating);
        }


        private void setData(String url, String title, String author, String description){

            Glide.with(itemView.getContext())
                    .load(url)
                    .into(bookImage);

            bookTitle.setText(title);
            bookAuthor.setText(author);
            bookDescription.setText(description);
//            bookRating.setText(String.valueOf(rating));
        }
    }
}
