package ru.barcats.viewmodel_livedata.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ru.barcats.viewmodel_livedata.R;
import ru.barcats.viewmodel_livedata.model.entities.Photo;
import ru.barcats.viewmodel_livedata.viewModel.MyViewModel;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "33333";
    public static final String PHOTO_URL = "PHOTO_URL";
    TextView textView1;
    ImageView imageView;
    EditText editTextSearch;
    Button buttonSearch;

    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter; //адаптер для RecyclerView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        // Получаем от провайдера модель
        MyViewModel model = ViewModelProviders.of(this).get(MyViewModel.class);
        //От модели получаем LiveData
        LiveData<List<Photo>> data = model.getData();
        // подписываемся на LiveData и ждем данные
        data.observe(this, new Observer<List<Photo>>() {
            @Override
            public void onChanged(List<Photo> photos) {
                //реализуем интерфейс адаптера, в  его методе onCityClick получим url картинки
                RecyclerViewAdapter.OnPhotoClickListener onPhotoClickListener =
                        new RecyclerViewAdapter.OnPhotoClickListener() {
                            @Override
                            public void onPhotoClick(String url) {
                                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                                intent.putExtra(PHOTO_URL,url);
                                startActivity(intent);
                            }
                        };
                //используем встроенный GridLayoutManager
                GridLayoutManager layoutManager = new GridLayoutManager(getBaseContext(), 3);
                recyclerViewAdapter = new RecyclerViewAdapter(photos);
                recyclerViewAdapter.setOnPhotoClickListener(onPhotoClickListener);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(recyclerViewAdapter);
            }
        });


    }
    private void initViews() {
        textView1 = findViewById(R.id.text1);
        recyclerView = findViewById(R.id.recycledViewUrl);
        editTextSearch = findViewById(R.id.editTextSearch);
        buttonSearch = findViewById(R.id.buttonSearch);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String search = editTextSearch.getText().toString();
                if (search.trim().isEmpty()){
                    Snackbar.make(view, view.getResources().getString(R.string.input_text),
                            Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "MainActivity onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "MainActivity onDestroy");
    }
}
