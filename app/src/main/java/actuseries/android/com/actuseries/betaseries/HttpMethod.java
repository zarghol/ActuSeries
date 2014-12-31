package actuseries.android.com.actuseries.betaseries;

/**
 * Created by Clement on 31/12/2014.
 */
public enum HttpMethod {
    GET("GET"),
    PUT("PUT"),
    POST("POST"),
    DELETE("DELETE");

    private String contenu;

    private HttpMethod(String contenu) {
        this.contenu = contenu;
    }

    public String getMethod() {
        return this.contenu;
    }
}
