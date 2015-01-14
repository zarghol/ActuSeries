package actuseries.android.com.actuseries.metier;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import actuseries.android.com.actuseries.betaseries.Request;

/**
 * Created by Clement on 19/12/2014.
 */
public class Serie {
    private String nomSerie;

    private List<Episode> episodes;

    private String url;
    private int id;
    private String url_banner;
    private Bitmap banner;
    private String description;
    private int dureeEpisode;
    private List<String> genres;
    private int id_thetvdb;
    private SerieStatus statut;
    private int anneeCreation;

    private boolean active;

    public Serie(String nom, boolean active, String url) {
        this.active = active;
        this.url = url;
        this.nomSerie = nom;

        this.url_banner = null;
        this.description = "";
        this.dureeEpisode = 0;
        this.genres = new ArrayList<>();
        this.id_thetvdb = -1;
        this.statut = SerieStatus.AUTRE;
        this.anneeCreation = 0;

        this.episodes = new ArrayList<>();
    }

    public Serie(JSONObject show) {
        try {
            this.nomSerie = show.getString("title");
            this.active = show.getJSONObject("user").getBoolean("archived");
            this.url = show.getString("resource_url");
            this.id = show.getInt("id");
            this.id_thetvdb = show.getInt("thetvdb_id");
            this.description = show.getString("description");
            this.anneeCreation = show.getInt("creation");
            this.genres = new ArrayList<>();
            JSONArray genresjson = show.getJSONArray("genres");
            for (int i = 0; i < genresjson.length(); i++) {
                this.genres.add(genresjson.getString(i));
            }

            this.statut = SerieStatus.valueOfByString(show.getString("status"));
            this.dureeEpisode = show.getInt("length");

            this.episodes = new ArrayList<>();
        } catch (JSONException e) {
            Log.e("ActuSeries", "erreur de creation de serie", e);
        }
    }

    public String getNomSerie() {
        return this.nomSerie;
    }

    public String getUrlBanner() {
        return this.url_banner;
    }

    public void setUrlBanner(String urlBanner) {
        this.url_banner = urlBanner;
    }

    public List<Episode> getEpisodes() {
        return this.episodes;
    }

    public int getId() {
        return id;
    }

    public void addEpisode(Episode e) {
        this.episodes.add(e);
    }

    public void trieEpisode() {
        Collections.sort(this.episodes);
    }


    public Bitmap getBanner() {
        return banner;
    }

    public void setBanner(Bitmap banner) {
        this.banner = banner;
    }

    public void clearEpisodes(){
        episodes.clear();
    }
}
