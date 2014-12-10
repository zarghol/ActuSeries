package actuseries.android.com.actuseries.betaseries;

import android.util.Log;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Clement on 10/12/2014.
 */
public class Request {
    private final static String betaseriesAPIUrl = "http://api.betaseries.com/";
    private final static String betaseriesUserAgent = "Android BetaSeries Library - ClemNonn (1.0)";
    // reseaux : session
    // vrai requete : NSMutableRequest

    private String apiKey;
    private String token;

    private String userAgent;
    private int timeout;

    private RequestCategory category;
    private RequestMethod method;
    private String chaineObject;
    private HashMap<String, String> options;

    public Request() {
        this.apiKey = "";
        this.token = "";

        this.userAgent = betaseriesUserAgent;
        this.timeout = 20;

        this.category = RequestCategory.TIMELINE;
        this.method = RequestMethod.HOME;
        this.chaineObject = null;
        this.options = new HashMap<>();

        // initializing the requete
        // initializing the session
    }

    public void send(CompletionHandlerData completionHandler) {
        InputStream is = null;
        try {
            URL url = new URL(this.urlStringForRequest());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(this.timeout);
            conn.setConnectTimeout(this.timeout);

            switch (this.method) {
                case DELETE:
                    conn.setRequestMethod("DELETE");
                    break;

                case ADD:
                    conn.setRequestMethod("PUT");
                    break;

                default:
                    conn.setRequestMethod("GET");
            }

            conn.setRequestProperty("User-Agent", this.userAgent);
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            Log.d("uniweb", "The response is: " + response);
            is = conn.getInputStream();

            JSONObject json = new JSONObject(IOUtils.toString(is));
            JSONObject root = json.getJSONObject("root");

            if (!root.getJSONObject("errors").isNull("error")) {
                Exception e = new Exception("code : " + root.getJSONObject("errors").getJSONObject("error").getInt("code"));

                completionHandler.handleError(e);
            } else {
                completionHandler.completionMethod(json);
            }

        } catch (Exception e) {
            completionHandler.handleError(e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    completionHandler.handleError(e);
                }
            }
        }

    }

    private String urlStringForRequest() {
        StringBuilder builder = new StringBuilder(betaseriesAPIUrl + this.category.getContenu() + "/" + this.method.getMethod());
        if (this.chaineObject != null) {
            builder.append("/" + this.chaineObject);
        }

        builder.append(".json?key=" + this.apiKey + "&token=" + this.token + "&");

        for (Map.Entry entree : this.options.entrySet()) {
            builder.append(entree.getKey() + "=" + entree.getValue() + "&");
        }

        return builder.toString();
    }

}
