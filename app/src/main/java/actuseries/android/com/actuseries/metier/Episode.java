package actuseries.android.com.actuseries.metier;

import android.util.Log;

import org.json.JSONObject;

/**
 * Created by Clement on 19/12/2014.
 */
public class Episode implements Comparable<Episode> {
    private int numEpisode;

    private int numSaison;
    private String descriptionEpisode;
    private String nomEpisode;
    private int id;
    private boolean vue;


    public Episode(String nomEpisode, int numEpisode, int saison, String description, int id, boolean vue) {
        this.numEpisode = numEpisode;
        this.numSaison = saison;
        this.descriptionEpisode = description;
        this.nomEpisode = nomEpisode;
        this.id = id;
        this.vue = vue;
    }

    public Episode(JSONObject ep) {
        try {
            this.nomEpisode = ep.getString("title");
            this.descriptionEpisode = ep.getString("description");
            this.numEpisode = ep.getInt("episode");
            this.numSaison = ep.getInt("season");
            this.id = ep.getInt("id");
            this.vue = ep.getJSONObject("user").getBoolean("seen");
        } catch(Exception e) {
            Log.e("ActuSeries", "erreur de creation d'episode", e);
        }
    }


    public int getNumEpisode() {
        return numEpisode;
    }

    public int getSaison() {
        return numSaison;
    }

    public boolean estVue() {
        return this.vue;
    }

    public void setVue(boolean vue) {
        this.vue = vue;
        //TODO AccesBetaseries.marqueVue(this);
    }

    public String getDescriptionEpisode() {
        return descriptionEpisode;
    }

    public String getNomEpisode() {
        return nomEpisode;
    }

    @Override
    public int compareTo(Episode another) {
        if(another.getNumEpisode() == this.getNumEpisode() && another.getSaison() == this.getSaison()) {
            return 0;
        }

        if(another.getSaison() < this.getSaison()) {
            return 1;
        } else if(another.getSaison() > this.getSaison() || another.getNumEpisode() > this.getNumEpisode()) {
            return -1;
        } else {
            return 1;
        }
    }
}
