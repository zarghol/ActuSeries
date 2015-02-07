package actuseries.android.com.actuseries.locator;

import actuseries.android.com.actuseries.betaseries.BetaSeriesCaller;
import actuseries.android.com.actuseries.betaseries.NullBetaSeriesCaller;

/**
 *
 */
public class BetaSeriesCallerLocator {

    private static BetaSeriesCaller betaSeriesCaller;

    public static BetaSeriesCaller getService() {
        return betaSeriesCaller;
    }

    public static void provide(BetaSeriesCaller service) {
        if(service == null) {
            betaSeriesCaller = new NullBetaSeriesCaller();
        } else {
            betaSeriesCaller = service;
        }
    }
}
