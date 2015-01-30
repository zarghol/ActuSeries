package actuseries.android.com.actuseries.betaseries;

/**
 * Created by Clement on 10/12/2014.
 */
public enum RequestMethod {
    ADD("add"),
    AUTH("auth"),
    BADGES("badges"),
    DELETE("delete"),
    DESTROY("destroy"),
    DISPLAY("display"),
    DOWNLOADED("downloaded"),
    EPISODE("episode"),
    EPISODES("episodes"),
    FRIENDS("friends"),
    GENERAL("general"),
    HOME("home"),
    INFOS("infos"),
    ISACTIVE("is_active"),
    LAST("last"),
    MEMBER("member"),
    NOTE("note"),
    NOTIFICATIONS("notifications"),
    PICTURES("pictures"),
    POSTEPISODE("post/episode"),
    POSTMEMBER("post/member"),
    POSTSHOW("post/show"),
    RECOMMEND("recommend"),
    REMOVE("remove"),
    SCRAPER("scraper"),
    SEARCH("search_activity"),
    SHOWS("shows"),
    SIGNUP("signup"),
    WATCHED("watched");

    private String method;

    private RequestMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return this.method;
    }
}