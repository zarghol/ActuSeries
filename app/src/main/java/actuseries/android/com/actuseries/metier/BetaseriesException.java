package actuseries.android.com.actuseries.metier;

import org.json.JSONObject;

/**
 * Created by Clement on 30/01/2015.
 */
public class BetaseriesException extends Exception {
    private int codeError;
    private String message;

    public BetaseriesException(int codeError, String message) {
        this.codeError = codeError;
        this.message = message;
    }

    public BetaseriesException(JSONObject json) throws Exception{
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
