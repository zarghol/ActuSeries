package actuseries.android.com.actuseries.betaseries;

import android.util.Log;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Clement on 10/12/2014.
 */
public class Request {
    private final static String betaseriesAPIUrl = "https://api.betaseries.com/";
    private final static String betaseriesUserAgent = "Android BetaSeries Library - ClemNonn (1.0)";
    private final static String betaseriesVersionAPI = "2.4";
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
    private HttpMethod httpMethod;

    public Request() {
        this.apiKey = "";
        this.token = "";

        this.userAgent = betaseriesUserAgent;
        this.timeout = 200;

        this.category = RequestCategory.TIMELINE;
        this.method = RequestMethod.HOME;
        this.chaineObject = null;
        this.options = new HashMap<>();
        this.httpMethod = HttpMethod.GET;
    }

    public <T> T send(CompletionHandlerData<T> completionHandler) {
        InputStream is = null;
        try {
            HttpsURLConnection conn = this.createConnection();
            Log.d("actuseries", "connexion créé");
            conn.connect();
            Log.d("actuseries", "connecté");

            int response = conn.getResponseCode();
            Log.d("actuseries", "The response is: " + response);
            is = conn.getInputStream();

            JSONObject json = new JSONObject(IOUtils.toString(is));

            if (!json.getJSONObject("errors").isNull("error")) {
                Exception e = new Exception("code : " + json.getJSONObject("errors").getJSONObject("error").getInt("code"));

                completionHandler.handleError(e);
            } else {
                return completionHandler.completionMethod(json);
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
            return null;
        }

    }

    private HttpsURLConnection createConnection() {
        try {
            URL url = new URL(this.urlStringForRequest());
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setReadTimeout(this.timeout);
            conn.setConnectTimeout(this.timeout);
            conn.setRequestMethod(this.httpMethod.getMethod());

            conn.setRequestProperty("User-Agent", this.userAgent);
            conn.setRequestProperty("X-BetaSeries-Key", this.apiKey);
            conn.setRequestProperty("X-BetaSeries-Version", betaseriesVersionAPI);

            if (!this.token.equals("")) {
                conn.setRequestProperty("X-BetaSeries-Token", this.token);
            }
            conn.setDoInput(true);

            return conn;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String urlStringForRequest() {
        StringBuilder builder = new StringBuilder(betaseriesAPIUrl + this.category.getContenu() + "/" + this.method.getMethod());
        if (this.chaineObject != null) {
            builder.append("/" + this.chaineObject);
        }

        if (this.options.size() > 0) {
            builder.append("?");
        }

        for (Map.Entry entree : this.options.entrySet()) {
            builder.append(entree.getKey() + "=" + entree.getValue() + "&");
        }

        builder.deleteCharAt(builder.length()-1);

        return builder.toString();
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setCategory(RequestCategory category) {
        this.category = category;
    }

    public void setMethod(RequestMethod method) {
        this.method = method;
    }

    public void setChaineObject(String chaineObject) {
        this.chaineObject = chaineObject;
    }

    public void addOption(String nameOption, String option) {
        this.options.put(nameOption, option);
    }
}
