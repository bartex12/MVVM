package ru.barcats.viewmodel_livedata.view;

import androidx.appcompat.app.AppCompatActivity;
import ru.barcats.viewmodel_livedata.R;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Objects;

public class DetailActivity extends AppCompatActivity {

    private static final String PHOTO_URL = "PHOTO_URL";
    TextView tvURL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvURL = findViewById(R.id.textView2);

        ImageView imageViewDetail = findViewById(R.id.imageViewDetail);
        String url = Objects.requireNonNull(getIntent().getExtras()).getString(PHOTO_URL);
        Picasso.get().load(url).into(imageViewDetail);

        tvURL.setText(url);
    }
}
