package actuseries.android.com.actuseries.betaseries;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
        this.timeout = 4000;

        this.category = RequestCategory.TIMELINE;
        this.method = RequestMethod.HOME;
        this.chaineObject = null;
        this.options = new HashMap<>();
        this.httpMethod = HttpMethod.GET;
    }

    public JSONObject send() throws Exception {
        InputStream is = null;
        JSONObject retour = null;
        try {
            HttpsURLConnection conn = this.createConnection();
            conn.connect();
            Log.d("actuseries", "connecté pour url : " + this.httpMethod.getMethod() + " " + this.urlStringForRequest());
            int response = conn.getResponseCode();
            Log.d("actuseries", "The response is: " + response);

            if(response != 200) {
                is = conn.getErrorStream();
            } else {
                is = conn.getInputStream();
            }

            JSONObject json = new JSONObject(IOUtils.toString(is));

            if(json.getJSONArray("errors").length() > 0) {
                BetaSeriesException exception = new BetaSeriesException(json.getJSONArray("errors").getJSONObject(0));
                throw exception;
            } else {
                retour = json;
            }
        } finally {
            if(is != null) {
                is.close();
            }
        }
        return retour;
    }

    public Bitmap getImage() throws Exception {
        InputStream is = null;

        Bitmap retour = null;
        try {
            HttpsURLConnection conn = this.createConnection();
            conn.connect();
            Log.d("actuseries", "connecté pour url :" + this.urlStringForRequest());
            int response = conn.getResponseCode();
            Log.d("actuseries", "The response is: " + response);
            if(response != 200) {
                Exception e = new Exception("code : " + response + " : " + conn.getResponseMessage());
                throw e;
            }

            is = conn.getInputStream();
            // TODO voir si on peut optimisater les images : java.lang.OutOfMemoryError si beaucoup de séries
           /* BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 8;*/
            retour = BitmapFactory.decodeStream(is);
        } finally {
            if(is != null) {
                is.close();
            }
        }
        return retour;
    }

    private HttpsURLConnection createConnection() {
        try {
            URL url = new URL(this.urlStringForRequest());
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setConnectTimeout(this.timeout);
            conn.setRequestMethod(this.httpMethod.getMethod());

            conn.setRequestProperty("User-Agent", this.userAgent);
            conn.setRequestProperty("X-BetaSeries-Key", this.apiKey);
            conn.setRequestProperty("X-BetaSeries-Version", betaseriesVersionAPI);

            if(this.token != null) {
                conn.setRequestProperty("X-BetaSeries-Token", this.token);
            }
            conn.setDoInput(true);
            return conn;
        } catch(IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String urlStringForRequest() {
        StringBuilder builder = new StringBuilder(betaseriesAPIUrl + this.category.getContenu() + "/" + this.method.getMethod());
        if(this.chaineObject != null) {
            builder.append("/" + this.chaineObject);
        }

        if(this.options.size() > 0) {
            builder.append("?");

            for(Map.Entry entree : this.options.entrySet()) {
                builder.append(entree.getKey() + "=" + entree.getValue() + "&");
            }

            builder.deleteCharAt(builder.length() - 1);
        }

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

    public void setHttpMethod(HttpMethod method) { this.httpMethod = method; }

    public void setChaineObject(String chaineObject) {
        this.chaineObject = chaineObject;
    }

    public void addOption(String nameOption, String option) {
        this.options.put(nameOption, option);
    }
}
