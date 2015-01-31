package actuseries.android.com.actuseries.betaseries;

/**
 * Created by Clement on 10/12/2014.
 */
public enum RequestCategory {
    COMMENTS("comments"),
    MEMBERS("members"),
    PLANNING("planning"),
    SHOWS("shows"),
    EPISODES("episodes"),
    SUBTITLES("subtitles"),
    TIMELINE("timeline"),
    PICTURES("pictures");


    private String contenu;

    private RequestCategory(String contenu) {
        this.contenu = contenu;
    }

    public String getContenu() {
        return this.contenu;
    }

}
