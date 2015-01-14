package actuseries.android.com.actuseries.event;

import com.squareup.otto.Bus;

/**
 * Created by charly on 14/01/2015.
 */
public class EventBus {
    private static final Bus BUS = new Bus();

    public static Bus getInstance() {
        return BUS;
    }
}
