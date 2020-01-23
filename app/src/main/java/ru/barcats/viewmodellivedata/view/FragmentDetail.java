package ru.barcats.viewmodellivedata.view;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import ru.barcats.viewmodellivedata.R;


public class FragmentDetail extends Fragment {

    private TextView tvURL;
    private ImageView imageViewDetail;
    private String url = "";

    public FragmentDetail() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = getArguments();
        if (arguments != null) {
            url =arguments.getString(getString(R.string.url));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvURL = view.findViewById(R.id.textView2);
        tvURL.setText(url);

        imageViewDetail = view.findViewById(R.id.imageViewDetail);
        Picasso.get().load(url).into(imageViewDetail);
    }
}
