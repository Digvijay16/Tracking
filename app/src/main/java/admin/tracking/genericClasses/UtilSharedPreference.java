package admin.tracking.genericClasses;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by kaustubh on 25/10/17.
 */

public class UtilSharedPreference {

    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;
    private static UtilSharedPreference utilSharedPreference;


    public static UtilSharedPreference getInstance(Context context) {

        if (utilSharedPreference == null) {
            utilSharedPreference = new UtilSharedPreference();
            preferences = context.getSharedPreferences("CZar", Context.MODE_PRIVATE);
            editor = preferences.edit();
        }
        return utilSharedPreference;
    }

    public void putData(String key, String value) {
        editor.putString(key, value);
        editor.apply();
    }

    public String getData(String key) {
        return preferences.getString(key, "");
    }


    public void clearSharedPreferences()
    {
        editor.clear();
        editor.commit();
    }
}
