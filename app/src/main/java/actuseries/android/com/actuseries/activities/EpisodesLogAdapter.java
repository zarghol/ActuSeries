package actuseries.android.com.actuseries.activities;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import actuseries.android.com.actuseries.R;
import actuseries.android.com.actuseries.metier.Episode;



public class EpisodesLogAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<Episode> episodes;
    private SerieDetailActivity activity;

    public EpisodesLogAdapter(List<Episode> episodes, Context c, SerieDetailActivity activity) {
        this.episodes = episodes;
        this.inflater = LayoutInflater.from(c);
        this.activity = activity;
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
            holder.nomEpisode = (TextView) convertview.findViewById(R.id.episodeItem_textView_title);
            holder.numEpisode = (TextView) convertview.findViewById(R.id.episodeItem_textView_number);
            holder.boutonVueEpisode = (ImageButton) convertview.findViewById(R.id.episodeItem_imageButton_watched);
        } else {
            holder = (ViewHolder) convertview.getTag();
        }

        Episode e = episodes.get(pos);
        holder.nomEpisode.setText(e.getNomEpisode());
        holder.numEpisode.setText(e.getSaison() + " x " + e.getNumEpisode());

        holder.boutonVueEpisode.setOnClickListener(this.activity);
        if (e.getDate().after(new Date())) {
            holder.boutonVueEpisode.setEnabled(false);
            holder.boutonVueEpisode.setAlpha(0.0f);
        } else {
            holder.boutonVueEpisode.setAlpha(e.estVue() ? 0.5f : 1.0f);
            holder.boutonVueEpisode.setEnabled(!e.estVue());
        }

        convertview.setTag(holder);
        return convertview;
    }

    private class ViewHolder {
        TextView nomEpisode;
        TextView numEpisode;
        ImageButton boutonVueEpisode;
    }
}