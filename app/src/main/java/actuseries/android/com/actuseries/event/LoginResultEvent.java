package actuseries.android.com.actuseries.event;

/**
 * Created by charly on 14/01/2015.
 */
public class LoginResultEvent {
    private String result;

    public LoginResultEvent(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }
}
