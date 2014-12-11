package actuseries.android.com.actuseries.betaseries;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Clement on 10/12/2014.
 */
public interface CompletionHandlerData<T> {
    public T completionMethod(JSONObject json) throws Exception;
    public void handleError(Exception e);

}
