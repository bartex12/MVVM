package ru.barcats.viewmodellivedata.view;

import android.os.Bundle;
import android.os.Handler;

import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import ru.barcats.viewmodellivedata.R;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "33333";
    private boolean doubleBackToExitPressedOnce;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navController = Navigation.findNavController(this,R.id.main_nav_host_fragment);
    }

    @Override
    public void onBackPressed() {
        //если метка фрагмента fragment_fragment_main, закрываем программу по двойному щелчку Назад
        if (Objects.requireNonNull(navController.getCurrentDestination().getLabel())
                .equals(getResources().getString(R.string.fr_main_name)) ){
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }
            this.doubleBackToExitPressedOnce = true;
            Snackbar.make(findViewById(android.R.id.content),
                    Objects.requireNonNull(this).getString(R.string.forExit),
                    Snackbar.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }else {
            super.onBackPressed();
        }
    }
}
