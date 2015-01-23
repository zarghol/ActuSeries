package actuseries.android.com.actuseries.activities;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import actuseries.android.com.actuseries.R;
import actuseries.android.com.actuseries.metier.Episode;


public class EpisodesLogAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<Episode> episodes;

    public EpisodesLogAdapter(List<Episode> episodes, Context c) {
        this.episodes = episodes;
        this.inflater = LayoutInflater.from(c);
    }

    @Override
    public int getCount() {
        return episodes.size();
    }

    @Override
    public Object getItem(int position) {
        return episodes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int pos, View convertview, ViewGroup parent) {
        ViewHolder holder;
        if(convertview == null) {
            holder = new ViewHolder();
            convertview = inflater.inflate(R.layout.episode_item, null);
            holder.nomEpisode = (TextView) convertview.findViewById(R.id.nomEpisode);
            holder.numEpisode = (TextView) convertview.findViewById(R.id.numEpisode);

        } else {
            holder = (ViewHolder) convertview.getTag();
        }

        Episode e = episodes.get(pos);
        holder.nomEpisode.setText(e.getNomEpisode());
        holder.numEpisode.setText(e.getSaison() + " x " + e.getNumEpisode());

        convertview.setTag(holder);
        return convertview;
    }

    private class ViewHolder {
        TextView nomEpisode;
        TextView numEpisode;
    }
}