package admin.tracking.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import admin.tracking.ActivityMain;
import admin.tracking.R;
import admin.tracking.genericClasses.UtilSharedPreference;
import admin.tracking.login.ActivityLogin;

import static admin.tracking.R.id.edtPassword;
import static admin.tracking.R.id.edtUsername;
import static admin.tracking.genericClasses.UtilConstant.Name;
import static admin.tracking.genericClasses.UtilConstant.REQUEST_TIME;
import static admin.tracking.genericClasses.UtilMethods.createProgressDialogPopUp;
import static admin.tracking.genericClasses.UtilMethods.dismissPopUpProgressDialog;
import static admin.tracking.genericClasses.UtilMethods.isNetworkAvailable;
import static admin.tracking.genericClasses.UtilMethods.myToast;
import static admin.tracking.genericClasses.UtilWeb.getCurrentLocations;
import static admin.tracking.genericClasses.UtilWeb.loginCheck;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentHome extends Fragment {


    private  FragmentActivity context;
    private View view;
    private String userid;

    public FragmentHome() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getActivity();
        userid= UtilSharedPreference.getInstance(context).getData(Name);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_home, container, false);
        if (isNetworkAvailable(context)) {
            webserviceLogin();
        } else {

            myToast(context, "" + getResources().getText(R.string.ISAVAILABLE));
        }
        return view;
    }

    private void webserviceLogin() {

        createProgressDialogPopUp(context, getResources().getString(R.string.Please_wait));
        String strUrl=getCurrentLocations +"User_id="+ userid;
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest sr = new StringRequest(Request.Method.GET, strUrl, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                dismissPopUpProgressDialog();
                if (response != null) {
//                    getJsonResponse(response);

                } else {
                    Toast.makeText(context, getResources().getString(R.string.ERROR), Toast.LENGTH_SHORT).show();
                }

            }


        }, new Response.ErrorListener()

        {
            @Override
            public void onErrorResponse(VolleyError error) {
                dismissPopUpProgressDialog();
                myToast(context, getResources().getString(R.string.ERROR));

            }
        })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
//                params.put("DriverId", drievr_id);
//                params.put("Latitude", latitude);
//                params.put("Longitude", longitude);

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
//                params.put(X_API_KEY, API_KEY_VALUE);
//                params.put(TOKEN_KEY, token);

                return params;
            }
        };

        sr.setRetryPolicy(new

                DefaultRetryPolicy(REQUEST_TIME,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(sr);

    }

}
