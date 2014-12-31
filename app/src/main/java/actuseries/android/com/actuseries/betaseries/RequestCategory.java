package actuseries.android.com.actuseries.betaseries;

/**
 * Created by Clement on 10/12/2014.
 */
public enum RequestCategory {
    COMMENTS("comments"),
    MEMBERS("members"),
    PLANNING("planning"),
    SHOWS("shows"),
    SUBTITLES("subtitles"),
    TIMELINE("timeline"),
    // pas l'air d'être fonctionnel de leur coté
    PICTURES("pictures");


    private String contenu;

    private RequestCategory(String contenu) {
        this.contenu = contenu;
    }

    public String getContenu() {
        return this.contenu;
    }

}
