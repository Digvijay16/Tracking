package admin.tracking.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import admin.tracking.ActivityMain;
import admin.tracking.R;

import static admin.tracking.genericClasses.UtilConstant.REQUEST_TIME;
import static admin.tracking.genericClasses.UtilMethods.isNetworkAvailable;
import static admin.tracking.genericClasses.UtilMethods.myToast;
import static admin.tracking.genericClasses.UtilWeb.loginCheck;

public class ActivityLogin extends AppCompatActivity {

    private TextView edtUsername;
    private EditText edtPassword;
    private TextView txtForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);


        // Initialisation of widgets
        initWidget();
    }

    private void initWidget() {

        edtUsername = (EditText) findViewById(R.id.edtUsername);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        txtForgotPassword = (TextView) findViewById(R.id.txtForgotPassword);
    }


    //call on clicking signin button
    public void clickSignin(View v) {

        if (isValid()) {


            if (isNetworkAvailable(this)) {
//                webserviceLogin();
                redirectToMainActivity();
            } else {

                myToast(this, "" + getResources().getText(R.string.ISAVAILABLE));
            }


        }

    }


    private void webserviceLogin() {
        RequestQueue queue = Volley.newRequestQueue(ActivityLogin.this);
        StringRequest sr = new StringRequest(Request.Method.GET, loginCheck, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                if (response != null && !response.isEmpty()) {
                    try {
                        JSONObject jObjResult = new JSONObject(response);
                        String msgSuccessful = jObjResult.getString("message");
                        if (jObjResult.getString("status").equals("200")) {


                        } else if (jObjResult.getString("status").equals("203")) {

                        } else if (jObjResult.getString("status").equals("204")) {
                        } else if (jObjResult.getString("status").equals("500")) {
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.TRY_AGAIN), Toast.LENGTH_SHORT).show();
                }

            }


        }, new Response.ErrorListener()

        {
            @Override
            public void onErrorResponse(VolleyError error) {

                myToast(ActivityLogin.this, getResources().getString(R.string.ERROR));

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

    //call on clicking forgot password text
    public void clickForgotPassword(View v) {


        redirectToForgotPassword();

    }

    private boolean isValid() {


        if (edtUsername.getText().length() < 1) {
            edtUsername.setError("Username cannot be blank");
            return false;
        } else if (edtPassword.getText().length() < 1) {
            edtPassword.setError("Password cannot be blank");
            return false;
        }


        return true;
    }

    private void redirectToForgotPassword() {
        startActivity(new Intent(ActivityLogin.this, ActivityForgotPassword.class));
    }


    private void redirectToMainActivity() {
        startActivity(new Intent(ActivityLogin.this, ActivityMain.class));
    }


}
