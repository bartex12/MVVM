package ru.barcats.viewmodellivedata.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import ru.barcats.viewmodellivedata.R;
import ru.barcats.viewmodellivedata.model.entities.Photo;

public class PhotoPageAdapter extends PagedListAdapter<Photo, PhotoPageAdapter.PhotoViewHolder> {

    private static final String TAG = "33333";
    private OnPageClickListener onPageClickListener;

     PhotoPageAdapter() {
        super(new DiffUtilCallback()); // для оптимального обновления адаптера
    }

    public interface OnPageClickListener {
        void onPageClick(String url);
    }

     void setOnPageClickListener(OnPageClickListener onPageClickListener){
        this.onPageClickListener = onPageClickListener;
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_imag, parent, false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        final Photo photo = getItem(position);
        //Log.d(TAG, "onBindViewHolder, position = " + position);
        if (photo == null){
            holder.imageView.setImageResource(R.drawable.turtle1_12);
        }else {
            final String url = photo.getUrl();
            //загружаем картинку в imageView по url с поворотом на 30 градусов
            // с помощью библиотеки Picasso
            Picasso.get()
                    .load(url)
                    .placeholder(R.drawable.post)
                    .error(R.drawable.mistake)
                    .resize(200, 200) // изменяем размер картинки до указанной ширины и высоты
                    .rotate(30) // указываем градус, на который следует повернуть картинку
                    .into(holder.imageView);
        }
        //устанавливаем слушатель щелчка на фото, при щелчке передаём url фото для вывода в деталях
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String url = Objects.requireNonNull(photo).getUrl();
                onPageClickListener.onPageClick(url);
            }
        });
    }

    //*************  ViewHolder  ***********
    class PhotoViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        PhotoViewHolder(@NonNull View itemView) {
           super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }

    // для оптимального обновления адаптера
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
