package actuseries.android.com.actuseries.metier;

/**
 * Created by Clement on 19/12/2014.
 */
public class Episode {
    private int numEpisode;

    private int saison;
    private String descriptionEpisode;


    public Episode(int numEpisode, int saison, String description) {
        this.numEpisode = numEpisode;
        this.saison = saison;
        this.descriptionEpisode = description;
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
}
