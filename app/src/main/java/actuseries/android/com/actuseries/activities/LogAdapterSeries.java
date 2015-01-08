package actuseries.android.com.actuseries.activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import actuseries.android.com.actuseries.R;
import actuseries.android.com.actuseries.metier.Serie;

/**
 * Created by Clement on 08/01/2015.
 */
class LogAdapterSerie extends BaseAdapter {
    private LayoutInflater inflater;
    private List<Serie> series;

    public LogAdapterSerie(List<Serie> series, Context c) {
        this.series = series;
        this.inflater = LayoutInflater.from(c);
    }

    @Override
    public int getCount() {
        return this.series.size();
    }

    @Override
    public Object getItem(int arg0) {
        return this.series.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    private class ViewHolder {
        TextView nomSerie;
        ImageView banniereView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.serie_item, null);
            holder.nomSerie = (TextView) convertView.findViewById(R.id.nom_serie);
            holder.banniereView = (ImageView) convertView.findViewById(R.id.bannerView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.nomSerie.setText(this.series.get(position).getNomSerie());
        Bitmap bm = afficheBanniere(this.series.get(position).getUrlBanner());
        holder.banniereView.setImageBitmap(bm);
        return convertView;
    }

    private Bitmap afficheBanniere(String s) {
        try {
            URL url = new URL(s);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setUseCaches(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            return BitmapFactory.decodeStream(is);
        } catch (Exception e) {
            Log.e("actuseries", "erreur de récupération de banniere", e);
        }
        return null;
    }
}