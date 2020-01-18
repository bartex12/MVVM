package ru.barcats.viewmodel_livedata.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import ru.barcats.viewmodel_livedata.R;
import ru.barcats.viewmodel_livedata.model.entities.Photo;

public class PhotoPageAdapter extends PagedListAdapter<Photo, PhotoPageAdapter.PhotoViewHolder> {

    private static final String TAG = "33333";
    private List<Photo> data;

    protected PhotoPageAdapter(Context context) {
        super(new DiffUtilCallback());
    }


    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_imag, parent, false);
        PhotoViewHolder holder = new PhotoViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        Photo photo = getItem(position);
        if (photo == null){
            holder.imageView.setImageResource(R.drawable.turtle1_12);
        }else {
            final String url = photo.getUrl();
            //загружаем картинку в imageView по url с помощью библиотеки Picasso
            Picasso.get().load(url).into(holder.imageView);
        }
    }

    //*************  ViewHolder  ***********
    class PhotoViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        PhotoViewHolder(@NonNull View itemView) {
           super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }

    static  class DiffUtilCallback extends DiffUtil.ItemCallback<Photo>{

        @Override
        public boolean areItemsTheSame(@NonNull Photo oldItem, @NonNull Photo newItem) {
            return oldItem.getUrl()== newItem.getUrl();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Photo oldItem, @NonNull Photo newItem) {
            return oldItem.equals(newItem);
        }
    }
}
