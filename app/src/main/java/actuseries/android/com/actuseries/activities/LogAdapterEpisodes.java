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


class LogAdapterEpisodes extends BaseAdapter {
	private LayoutInflater inflater;
	private List<Episode> episodes;

	public LogAdapterEpisodes(List<Episode> epLi, Context c) {
		this.episodes = epLi;
		this.inflater = LayoutInflater.from(c);
	}

	@Override
	public int getCount() {
		return episodes.size();
	}

	@Override
	public Object getItem(int arg0) {
		return episodes.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	private class ViewHolder {
		TextView nomEpisode;
		TextView numEpisode;
	}

	@Override
	public View getView(int pos, View convertview, ViewGroup parent) {
		ViewHolder holder;
		if (convertview == null) {
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
}