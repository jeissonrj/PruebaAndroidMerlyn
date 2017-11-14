package com.example.jeisson.pruebaandroidmerlyn.interfaces;

import com.example.jeisson.pruebaandroidmerlyn.modelo.App;

import java.util.ArrayList;

/**
 * created by jeisson on 10/11/17.
 */

public interface ConsumirApiListener {
    void onConsumirApiStart();
    void onConsumirApiSuccess(ArrayList<App> apps);
}