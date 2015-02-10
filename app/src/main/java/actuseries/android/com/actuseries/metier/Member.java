package actuseries.android.com.actuseries.metier;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Clement on 19/12/2014.
 */
public class Member {
    private String login;
    private final String token;
    private String avatarUrl;

    // Stats
    private int nbBadges;
    private int nbEpisodes;
    private double progress;
    private int nbSeasons;
    private int nbShows;

    private List<Serie> series;


    public Member(String token) {
        this.token = token;
        this.series = new ArrayList<>();
    }

    public void retrieveInformation(JSONObject member) {
        try {
            this.login = member.getString("login");
            this.avatarUrl = member.getString("avatar");

            JSONObject stats = member.getJSONObject("stats");
            this.nbBadges = stats.getInt("badges");
            this.nbEpisodes = stats.getInt("episodes");
            this.progress = stats.getDouble("progress");
            this.nbSeasons = stats.getInt("seasons");
            this.nbShows = stats.getInt("shows");



            JSONArray array = member.optJSONArray("shows");
            Log.d("actuseries", "dans fillMember : " + array);
            if(array != null) {
                this.addSeries(array);
            }
        } catch(JSONException e) {
            Log.e("actuseries", "erreur dans fillMember", e);
        }
    }

    public void addSeries(JSONArray series) {
        try {
            for(int i = 0; i < series.length(); i++) {
                JSONObject show = series.getJSONObject(i);
                this.addSerie(new Serie(show));
            }
        } catch (JSONException e) {
            Log.e("actuseries", "erreur dans addSeries", e);
        }
    }

    public String getToken() {
        return this.token;
    }

    public String getLogin() { return this.login; }

    public List<Serie> getSeries() {
        return this.series;
    }

    public void addSerie(Serie serie) {
        this.series.add(serie);
    }
}
