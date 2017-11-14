package com.example.jeisson.pruebaandroidmerlyn.helpers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * created by jeisson on 14/11/17.
 * Realiza la verificacion del estado de la red, si tiene o no servicio de internet
 */

public class ConeccionInternet {

    private Context context;

    public ConeccionInternet(Context context) {
        this.context = context;
    }

    public boolean isNetDisponible() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo actNetInfo = null;
        if (connectivityManager != null) {
            actNetInfo = connectivityManager.getActiveNetworkInfo();
        }
        return (actNetInfo != null && actNetInfo.isConnected());
    }

}
