package ru.barcats.viewmodel_livedata.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ru.barcats.viewmodel_livedata.R;
import ru.barcats.viewmodel_livedata.model.entities.Photo;
import ru.barcats.viewmodel_livedata.viewModel.MyViewModel;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "33333";
    private static final String PHOTO_URL = "PHOTO_URL";
    private int numberOfLaunch = 0;
    private boolean isRotate;
    private String search = null;
    private TextView textView1;
    private EditText editTextSearch;
    private MyViewModel modelFoto = null;
    private RecyclerView recyclerView;

    private static final int PAGE_NUMBER = 1;
    private static final int PAGE_SIZE_RESENT = 33;
    private static final int PAGE_SIZE_SEARCH = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null){
            isRotate = savedInstanceState.getBoolean("sear");
        }

        initViews();
        getPhotos();
        getNumberOfStart();
    }

    private void initViews() {
        textView1 = findViewById(R.id.text1);
        recyclerView = findViewById(R.id.recycledViewUrl);
        editTextSearch = findViewById(R.id.editTextSearch);
        Button buttonSearch = findViewById(R.id.buttonSearch);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search = editTextSearch.getText().toString();
                if (search.trim().isEmpty()){
                    Snackbar.make(view, view.getResources().getString(R.string.input_text),
                            Snackbar.LENGTH_SHORT).show();
                }else {
                    Log.d(TAG, "MainActivity initViews search = " + search);
                    //вызываем метод во ViewModel для отработки пользовательского действия
                    //modelFoto.loadData(search);
                    modelFoto.loadData(PAGE_NUMBER, PAGE_SIZE_RESENT);
                }
            }
        });
    }

    private void getPhotos() {

        GridLayoutManager layoutManager = new GridLayoutManager(getBaseContext(), 3);
        //адаптер для RecyclerView
       final PhotoPageAdapter adapter = new PhotoPageAdapter(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        //	конфигурация PagedList. Здесь указывается количество загружаемых данных
        PagedList.Config config = new PagedList.Config.Builder()
        //	использовать ли плейсхолдеры - заглушки на месте данных,
        //	если известно общее количество данных в списке
                .setEnablePlaceholders(false)
                //	размер страницы - порции загружаемых данных
                .setPageSize(5)
                //	число элементов данных, которые будут загружены при изначальном создании списка
                .setInitialLoadSizeHint(30)
                //	расстояние в позициях данных, при достижении которого при скролле
                //	будет активирована дальнейшая загрузка данных
                .setPrefetchDistance(10)
                .build();

        // Получаем от провайдера модель - это как бы вместо презентора
        modelFoto = ViewModelProviders.of(this).get(MyViewModel.class);

        //	Фабрика для создания DataSource
        //	нужна, так как LivePagedList создаёт DataSource самостоятельно
        //TODO сделать фабрику для DataSource
        MyPositionalDataSource dataSource = new MyPositionalDataSource(modelFoto);

        //создаём Executor для фоновой загрузки данных
        Executor fetchExecutor = Executors.newSingleThreadExecutor();

        //	билдер LivePagedList
        //	executor главного потока не нужен
        //	fetchExecutor не обязателен
        //	возвращает LivaData в которую будет приходить PagedList
        LiveData<PagedList<Photo>> pagedListLiveData =
                new LivePagedListBuilder<>(dataSource, config)
                        .setFetchExecutor(fetchExecutor)
                        .build();

        //	подписываемся на LivaData и обновляем адаптер 
        pagedListLiveData.observe(this, new Observer<PagedList<Photo>>() {
            @Override
            public void onChanged(PagedList<Photo> photos) {
                adapter.submitList(photos);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("sear", isRotate );
    }

    private void getNumberOfStart() {
       LiveData<Integer> startNumber = modelFoto.getNumber();
        startNumber.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer number) {
                Log.d(TAG, "***  MainActivity getNumberOfStart  *** number = " + number);
                //запоминаем число запусков, если нужно будет выводить
                numberOfLaunch = number;
                //если подходит номер запуска и это не простой поворот экрана, покажем диалог
                if (shouldShowRate(number)&&!isRotate) {
                    createRateDialog().show();
                }
                isRotate = true;
            }
        });
    }

    //диалог, имитирующий выставление оценки в PlayMarket
    private AlertDialog createRateDialog() {
        Log.d(TAG, "MainActivity AlertDialog");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.rate_proposal_message)
                .setPositiveButton(R.string.rate_proposal_yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //пишем в textView1 numberOfLaunch
                        textView1.setText(String.format(Locale.getDefault(),"%s%d",
                                getResources().getString(R.string.launch_), numberOfLaunch));
                    }
                })
                .setNegativeButton(R.string.rate_proposal_no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //пишем в textView1 *****
                        textView1.setText(getResources().getString(R.string.stars));
                    }
                });
        return builder.create();
    }

    private RecyclerViewAdapter.OnPhotoClickListener getOnPhotoClickListener() {
        return new RecyclerViewAdapter.OnPhotoClickListener() {
            @Override
            public void onPhotoClick(String url) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra(PHOTO_URL,url);
                startActivity(intent);
            }
        };
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

    private Boolean shouldShowRate(Integer launchNumber) {
        Log.d(TAG, "MainActivity shouldShowRate numberOfLaunch = " + numberOfLaunch );
        if (launchNumber == 2) {
            return true;
        } else {
            return (launchNumber - 2) % 4 == 0;
        }
    }
}
