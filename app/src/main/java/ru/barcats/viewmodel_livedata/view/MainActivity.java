package ru.barcats.viewmodel_livedata.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import ru.barcats.viewmodel_livedata.R;
import ru.barcats.viewmodel_livedata.viewModel.MyViewModel;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "33333";
    TextView tv;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView= findViewById(R.id.imageView);
        tv = findViewById(R.id.tv);

        // Получаем от провайдера модель
        MyViewModel model = ViewModelProviders.of(this).get(MyViewModel.class);
        //От модели получаем LiveData
        LiveData<String> data = model.getData();
        // подписываемся на LiveData и ждем данные
        data.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                tv.setText(s);
                Log.d(TAG, "MainActivity onCreate s = " + s);
                //загружаем картинку в ImageView
                Picasso.get().load(s).into(imageView);
            }
        });


    }

}
