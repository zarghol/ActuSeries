package actuseries.android.com.actuseries.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import actuseries.android.com.actuseries.R;
import actuseries.android.com.actuseries.metier.Serie;

/**
 * Created by Clement on 08/01/2015.
 */
public class SeriesSimpleAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<Serie> series;

    public SeriesSimpleAdapter(List<Serie> series, Context c) {
        this.series = series;
        this.inflater = LayoutInflater.from(c);
    }

    @Override
    public int getCount() {
        return this.series.size();
    }

    @Override
    public Object getItem(int position) {
        return this.series.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.serie_simple_item, null);
            holder.nomSerie = (TextView) convertView.findViewById(R.id.serieItem_textView_title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.nomSerie.setText(this.series.get(position).getNomSerie());
        return convertView;
    }

    private class ViewHolder {
        TextView nomSerie;
    }
}