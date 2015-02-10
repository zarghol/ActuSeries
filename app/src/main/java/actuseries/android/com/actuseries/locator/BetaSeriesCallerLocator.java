package actuseries.android.com.actuseries.locator;

import actuseries.android.com.actuseries.betaseries.caller.BetaSeriesCaller;
import actuseries.android.com.actuseries.betaseries.caller.NullBetaSeriesCaller;

/**
 *
 */
public class BetaSeriesCallerLocator {

    private static BetaSeriesCaller betaSeriesCaller;

    public static BetaSeriesCaller getService() {
        return betaSeriesCaller;
    }

    public static void provide(BetaSeriesCaller service) {
        betaSeriesCaller = (service == null ? new NullBetaSeriesCaller() : service);
    }
}
