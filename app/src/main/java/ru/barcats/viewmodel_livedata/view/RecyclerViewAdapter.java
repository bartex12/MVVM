package ru.barcats.viewmodel_livedata.view;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ru.barcats.viewmodel_livedata.R;
import ru.barcats.viewmodel_livedata.model.Photo;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private static final String TAG = "33333";
    private List<Photo> data;
    private OnPhotoClickListener onPhotoClickListener;

public interface OnPhotoClickListener{
    void onPhotoClick(String url);
}

public void setOnPhotoClickListener(OnPhotoClickListener onPhotoClickListener){
    this.onPhotoClickListener = onPhotoClickListener;
}

    RecyclerViewAdapter(List<Photo> photos){
        if (photos != null) {
            data = photos;
        } else {
            data = new ArrayList<>();
        }
        Log.d(TAG, "RecyclerViewAdapter - /конструктор/ data.size() = " + data.size());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_imag,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final String url = data.get(position).getUrl();
        //загружаем картинку в imageView по url с помощью библиотеки Picasso
        Picasso.get().load(url).into(holder.imageView);
        //при щелчке на картинке вызываем метод интерфейса и передаём url - у нас - в MainActivity
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPhotoClickListener.onPhotoClick(url);
            }
        });
    }

    @Override
    public int getItemCount() {
    //
        return data.size();
    }

    //*************  ViewHolder  ***********
    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
