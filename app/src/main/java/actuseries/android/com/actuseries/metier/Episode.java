package actuseries.android.com.actuseries.metier;

import android.util.Log;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import actuseries.android.com.actuseries.betaseries.AccesBetaseries;

/**
 * Created by Clement on 19/12/2014.
 */
public class Episode implements Comparable<Episode> {
    private int numEpisode;

    private int numSaison;
    private String descriptionEpisode;
    private String nomEpisode;
    private int noteEpisode;
    private double moyenneNoteEpisode;
    private int id;
    private Date date;
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
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            this.date = format.parse(ep.getString("date"));
            this.descriptionEpisode = ep.getString("description");
            this.numEpisode = ep.getInt("episode");
            this.numSaison = ep.getInt("season");
            this.id = ep.getInt("id");
            this.vue = ep.getJSONObject("user").getBoolean("seen");
            this.noteEpisode = ep.getJSONObject("note").getInt("user");
            this.moyenneNoteEpisode = ep.getJSONObject("note").getDouble("mean");
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
        AccesBetaseries.marqueVue(this);
    }

    public void toggleVue() {
        this.setVue(!this.vue);
    }

    public void setNoteEpisode(int note) {
        this.noteEpisode = note;
        AccesBetaseries.marqueNote(this);
    }



    public int getNoteEpisode() {
        return this.noteEpisode;
    }

    public Date getDate() {
        return this.date;
    }

    public String getDescriptionEpisode() {
        return descriptionEpisode;
    }

    public String getNomEpisode() {
        return nomEpisode;
    }

    public int getId() {
        return this.id;
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
