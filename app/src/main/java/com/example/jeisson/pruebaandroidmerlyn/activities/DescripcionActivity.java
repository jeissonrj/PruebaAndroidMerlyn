package com.example.jeisson.pruebaandroidmerlyn.activities;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jeisson.pruebaandroidmerlyn.modelo.App;
import com.example.jeisson.pruebaandroidmerlyn.R;
import com.example.jeisson.pruebaandroidmerlyn.databinding.ActivityDescripcionBinding;

import static com.example.jeisson.pruebaandroidmerlyn.helpers.Constantes.PUT_EXTRA_APP;

public class DescripcionActivity extends AppCompatActivity {

    private static final String TAG = DescripcionActivity.class.getSimpleName();
    private final Context context = DescripcionActivity.this;
    private ActivityDescripcionBinding binding;
    private App app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_descripcion);
        app = getIntent().getExtras().getParcelable(PUT_EXTRA_APP);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("");
        FloatingActionButton fab = findViewById(R.id.fab);
        ImageView icono = findViewById(R.id.icono);
        TextView nombreApp = findViewById(R.id.nombreApp);
        WebView webView = findViewById(R.id.webView);
        fab.setBackgroundTintList(null);
        String titulo = app.getDisplay_name();

        if (app != null) {
            nombreApp.setText(app.getDisplay_name());
            Glide.with(context).load(app.getHeader_img()).into(binding.imageCollapsing);
            Glide.with(context).load(app.getIcon_img()).into(icono);
            webView.loadData(app.getDescription(), "text/html", "UTF-8");
        }
    }
}
