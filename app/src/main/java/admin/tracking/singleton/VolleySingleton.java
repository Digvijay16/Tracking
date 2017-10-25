package admin.tracking.singleton;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by admin on 16-10-2017.
 */

public class VolleySingleton {

    private final Context mContext;
    private static VolleySingleton singletonInstance;
    private RequestQueue requestQueue;


    //Constructor
    private VolleySingleton(Context context) {

        mContext = context;
        requestQueue = getRequestQueue();
    }


    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return requestQueue;
    }

    public static synchronized VolleySingleton getSingletonInstance(Context context) {
        if (singletonInstance == null) {
            singletonInstance = new VolleySingleton(context);
        }
        return singletonInstance;
    }

    public <T> void addToRequestQueue(Request<T> request) {
        requestQueue.add(request);
    }
}
