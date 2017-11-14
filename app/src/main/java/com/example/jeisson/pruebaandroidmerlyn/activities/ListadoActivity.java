package com.example.jeisson.pruebaandroidmerlyn.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.jeisson.pruebaandroidmerlyn.helpers.AdaptadorApps;
import com.example.jeisson.pruebaandroidmerlyn.modelo.App;
import com.example.jeisson.pruebaandroidmerlyn.helpers.ConeccionInternet;
import com.example.jeisson.pruebaandroidmerlyn.helpers.ConsumirApi;
import com.example.jeisson.pruebaandroidmerlyn.interfaces.ConsumirApiListener;
import com.example.jeisson.pruebaandroidmerlyn.R;
import com.example.jeisson.pruebaandroidmerlyn.databinding.ActivityListadoBinding;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import static com.example.jeisson.pruebaandroidmerlyn.helpers.Constantes.PUT_EXTRA_APP;

public class ListadoActivity extends AppCompatActivity implements ConsumirApiListener {

    private static final String TAG = ListadoActivity.class.getSimpleName();
    private final Context context = ListadoActivity.this;
    private ProgressDialog progressDialog;
    private ActivityListadoBinding binding;
    private AdaptadorApps adaptadorApps;
    private App app = new App();
    private ArrayList<App> apps = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_listado);
        Log.i(TAG, "onCreate()");
        ConeccionInternet coneccionInternet = new ConeccionInternet(context);
        //validacion del estado de internet para seleccionar fuente de la información
        if(coneccionInternet.isNetDisponible()) {
            try {
                new ConsumirApi(this).execute();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        else {
            Toast.makeText(context, "No hay conecion a Internet,\n Se cargaron las preferencias", Toast.LENGTH_LONG).show();
            binding.recyclerView.setLayoutManager(new LinearLayoutManager(context));
            adaptadorApps = new AdaptadorApps(context,leerPreferencias());
            binding.recyclerView.setAdapter(adaptadorApps);
        }
    }

    /**
     * evento generado desde la UI para seleccionar una posición del recyclerView
     * con el fin de iniciar una nueva actividad
     * @param view
     */
    public void app (View view){
        int posicion = Integer.valueOf(view.getTag().toString());
        app = apps.get(posicion);
        Intent intent = new Intent(context, DescripcionActivity.class);
        intent.putExtra(PUT_EXTRA_APP,app);
        startActivity(intent);
        overridePendingTransition(R.anim.alpha, R.anim.alpha);
    }

    /**
     * metodo ejecutado cuando inicia el servicio que consume el api
     */
    @Override
    public void onConsumirApiStart() {
        progressDialog = ProgressDialog.show(this, "Espere","Consumiendo API", true);
    }

    /**
     * metodo ejecutado cuando finaliza la serializacion de la información proveniente del API
     * @param apps
     */
    @Override
    public void onConsumirApiSuccess(ArrayList<App> apps) {
        this.apps = apps;
        crearPreferencias(apps);
        progressDialog.dismiss();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adaptadorApps = new AdaptadorApps(context,this.apps);
        binding.recyclerView.setAdapter(adaptadorApps);
    }

    /**
     * Guarda los datos obtenidos de la serialización en caché para consultar en caso
     * de perdida de conexion a internet
     * @param apps
     */
    private void crearPreferencias (ArrayList<App> apps){
        SharedPreferences configuracion = getSharedPreferences("preferencias",0);
        SharedPreferences.Editor editor = configuracion.edit();
        for (int i = 0 ; i < apps.size() ; i++){
            editor.putString("url_banner_img_"+String.valueOf(i),apps.get(i).getBanner_img());
            editor.putString("url_header_img_"+String.valueOf(i),apps.get(i).getHeader_img());
            editor.putString("display_name_"+String.valueOf(i),apps.get(i).getDisplay_name());
            editor.putString("url_icon_img_"+String.valueOf(i),apps.get(i).getIcon_img());
            editor.putString("url_description_"+String.valueOf(i),apps.get(i).getDescription());
        }
        editor.putInt("cantidad",apps.size());
        editor.apply();
    }

    /**
     * consulta y encapsula la informacion guardada en caché
     * @return
     */
    private ArrayList<App> leerPreferencias (){
        SharedPreferences configuracion = getSharedPreferences("preferencias",0);
        int cantidad = configuracion.getInt("cantidad",0);
        for (int i = 0 ; i < cantidad ; i++) {
            app = new App();
            app.setBanner_img(configuracion.getString("url_banner_img_"+String.valueOf(i),""));
            app.setHeader_img(configuracion.getString("url_header_img_"+String.valueOf(i),""));
            app.setDisplay_name(configuracion.getString("display_name_"+String.valueOf(i),""));
            app.setIcon_img(configuracion.getString("url_icon_img_"+String.valueOf(i),""));
            app.setDescription(configuracion.getString("url_description_"+String.valueOf(i),""));
            apps.add(app);
        }
        return apps;
    }
}
