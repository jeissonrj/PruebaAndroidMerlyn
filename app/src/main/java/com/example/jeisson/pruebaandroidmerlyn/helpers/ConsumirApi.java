package com.example.jeisson.pruebaandroidmerlyn.helpers;

import android.os.AsyncTask;
import android.util.Log;

import com.example.jeisson.pruebaandroidmerlyn.interfaces.ConsumirApiListener;
import com.example.jeisson.pruebaandroidmerlyn.modelo.App;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;

/**
 * created by jeisson on 10/11/17.
 * Clase encargade de consumir el servicio en el cual se consulta el API en segundo plano
 */
public class ConsumirApi{

    private static final String TAG = ConsumirApi.class.getSimpleName();
    private static final String DIRECCION_URL_API = "https://www.reddit.com/reddits.json";
    private ConsumirApiListener listener;
    private URL url;
    private App app = new App();

    public ConsumirApi(ConsumirApiListener listener) {
        this.listener = listener;
    }

    public void execute() throws UnsupportedEncodingException{
        listener.onConsumirApiStart();
        new DownloadRawData().execute(DIRECCION_URL_API);
    }

    /**
     * Realiza la consulta al API y encapsula la respuesta para su posterior serialización
     */
    private class DownloadRawData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            Log.i(TAG, "DownloadRawData");
            String link = params[0];
            try {
                url = new URL(link);
                InputStream is = url.openConnection().getInputStream();
                StringBuffer buffer = new StringBuffer();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                buffer.append(reader.readLine());
                String linea;
                while ((linea = reader.readLine()) != null) {
                    buffer.append(linea + "\n");
                }
                return buffer.toString();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String respuesta) {
            try {
                parsearJson(respuesta);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Analiza la respuesta del API en formato Json, separa la información y la encapsula
     * en el modelo
     * @param respuesta
     * @throws Exception
     */
    private void parsearJson(String respuesta) throws Exception {
        Log.i(TAG,"parsearJson");
        if (respuesta == null)
            return;
        System.out.println(respuesta);
        Gson gson = new GsonBuilder().create();
        JSONObject jsonRespuesta = new JSONObject(respuesta);
        JSONObject jsonData = jsonRespuesta.getJSONObject("data");
        JSONArray jsonApps = jsonData.getJSONArray("children");
        ArrayList<App> apps= new ArrayList<>();
        for (int i=0; i < jsonApps.length(); i++){
            JSONObject jsonChilren = jsonApps.getJSONObject(i);
            JSONObject jsonApp = jsonChilren.getJSONObject("data");
            app = gson.fromJson(String.valueOf(jsonApp), App.class);
            apps.add(app);
        }
        System.out.println("serialización exitosa");
        listener.onConsumirApiSuccess(apps);
    }

}
