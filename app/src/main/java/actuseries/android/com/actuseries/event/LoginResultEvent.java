package actuseries.android.com.actuseries.event;

/**
 * Created by charly on 14/01/2015.
 */
public class LoginResultEvent {
    private boolean estAuthentifie;

    public LoginResultEvent(boolean estAuthentifie) {
        this.estAuthentifie = estAuthentifie;
    }

    public boolean getResult() {
        return this.estAuthentifie;
    }
}
