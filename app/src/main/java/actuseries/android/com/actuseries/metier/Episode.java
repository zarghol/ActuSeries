package actuseries.android.com.actuseries.metier;

/**
 * Created by Clement on 19/12/2014.
 */
public class Episode {
    private int numEpisode;

    private int saison;
    private String descriptionEpisode;
    private String nomEpisode;


    public Episode(String nomEpisode, int numEpisode, int saison, String description) {
        this.numEpisode = numEpisode;
        this.saison = saison;
        this.descriptionEpisode = description;
        this.nomEpisode = nomEpisode;
    }


    public int getNumEpisode() {
        return numEpisode;
    }

    public int getSaison() {
        return saison;
    }

    public String getDescriptionEpisode() {
        return descriptionEpisode;
    }

    public String getNomEpisode() {
        return nomEpisode;
    }
}
