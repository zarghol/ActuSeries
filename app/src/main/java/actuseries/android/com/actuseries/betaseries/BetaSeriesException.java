package actuseries.android.com.actuseries.betaseries;

import org.json.JSONObject;

/**
 * Created by Clement on 30/01/2015.
 */
public class BetaSeriesException extends Exception {
    private int codeError;
    private String message;

    public BetaSeriesException(int codeError, String message) {
        this.codeError = codeError;
        this.message = message;
    }

    public BetaSeriesException(JSONObject json) throws Exception {
        this.codeError = json.getInt("code");
        this.message = json.getString("text");
    }

    @Override
    public String getMessage() {
        return "Unable to satisfy request. " + this.codeError + " : " + this.message;
    }

    public String getPrintableMessage() {
        return this.message;
    }
}
