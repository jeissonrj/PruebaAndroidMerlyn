package com.example.jeisson.pruebaandroidmerlyn.helpers;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jeisson.pruebaandroidmerlyn.R;
import com.example.jeisson.pruebaandroidmerlyn.modelo.App;

import java.util.ArrayList;

/**
 * created by jeisson on 13/11/17.
 * Encargado de organizar la información contenida en el arrayList para su respectivavisualización
 * dinamica en el recyclerView
 */

public class AdaptadorApps extends RecyclerView.Adapter<AdaptadorApps.PlaceViewHolder> {

    private Context context;
    private ArrayList<App> apps;
    private String imagen;
    private Bitmap img;

    public AdaptadorApps(Context context, ArrayList<App> apps) {
        this.context = context;
        this.apps = apps;
    }

    @Override
    public PlaceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_app, parent, false);
        return new PlaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlaceViewHolder holder, int position) {
        App app = apps.get(position);
        String nombreApp = app.getDisplay_name();
        holder.nombreApp.setText(nombreApp);
        holder.cardView.setTag(String.valueOf(position));
        imagen = app.getBanner_img();
        Glide.with(context).load(imagen).into(holder.imgBanner);
    }

    public Bitmap getBitmap (Bitmap bitmap){
        return bitmap;
    }

    @Override
    public int getItemCount() {
        if(apps == null)
        return 0;
        return apps.size();
    }

    class PlaceViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView nombreApp;
        ImageView imgBanner;

        private PlaceViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            nombreApp = itemView.findViewById(R.id.nombreApp);
            imgBanner = itemView.findViewById(R.id.imgBanner);
        }
    }
}
