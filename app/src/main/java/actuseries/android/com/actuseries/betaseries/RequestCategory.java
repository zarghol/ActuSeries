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
    TIMELINE("timeline");


    private String contenu;

    private RequestCategory(String contenu) {
        this.contenu = contenu;
    }

    public String getContenu() {
        return this.contenu;
    }


    // peut-etre inutile
    public static RequestCategory fromContenu(String contenu) {
        switch (contenu) {
            case "comments":
                return COMMENTS;

            case "members":
                return MEMBERS;

            case "planning":
                return PLANNING;

            case "shows":
                return SHOWS;

            case "subtitles":
                return SUBTITLES;

            case "timeline":
                return TIMELINE;

            default:
                return null;
        }
    }
}
