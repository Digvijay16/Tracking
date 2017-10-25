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
import admin.tracking.genericClasses.UtilSharedPreference;

import static admin.tracking.genericClasses.UtilConstant.Code_401;
import static admin.tracking.genericClasses.UtilConstant.Code_404;
import static admin.tracking.genericClasses.UtilConstant.Email;
import static admin.tracking.genericClasses.UtilConstant.Message;
import static admin.tracking.genericClasses.UtilConstant.Name;
import static admin.tracking.genericClasses.UtilConstant.REQUEST_TIME;
import static admin.tracking.genericClasses.UtilConstant.StatusCode;
import static admin.tracking.genericClasses.UtilConstant.Code_200;
import static admin.tracking.genericClasses.UtilConstant.User_Id;
import static admin.tracking.genericClasses.UtilMethods.createProgressDialogPopUp;
import static admin.tracking.genericClasses.UtilMethods.dismissPopUpProgressDialog;
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
                webserviceLogin();
            } else {

                myToast(this, "" + getResources().getText(R.string.ISAVAILABLE));
            }


        }

    }


    private void webserviceLogin() {

        createProgressDialogPopUp(ActivityLogin.this, getResources().getString(R.string.Please_wait));
        String strUrl=loginCheck +"Username="+edtUsername.getText().toString().trim()+"&Password="+ edtPassword.getText().toString().trim();
        RequestQueue queue = Volley.newRequestQueue(ActivityLogin.this);
        StringRequest sr = new StringRequest(Request.Method.GET, strUrl, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                dismissPopUpProgressDialog();
                if (response != null) {
                    getJsonResponse(response);

                } else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.ERROR), Toast.LENGTH_SHORT).show();
                }

            }


        }, new Response.ErrorListener()

        {
            @Override
            public void onErrorResponse(VolleyError error) {
                dismissPopUpProgressDialog();
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

    private void getJsonResponse(String response) {
        try {
            JSONObject jObjResult = new JSONObject(response);
            String message = jObjResult.getString(Message);
            if (jObjResult.getString(StatusCode).equals(Code_200)) {
                String userId = jObjResult.getString(User_Id);
                String name = jObjResult.getString(Name);
                String email = jObjResult.getString(Email);

                UtilSharedPreference.getInstance(ActivityLogin.this).putData(User_Id, userId);
                UtilSharedPreference.getInstance(ActivityLogin.this).putData(Name, name);
                UtilSharedPreference.getInstance(ActivityLogin.this).putData(Email, email);

                myToast(ActivityLogin.this, message);

                redirectToMainActivity();

            } else if (jObjResult.getString(StatusCode).equals(Code_401)) {
                myToast(ActivityLogin.this, message);

            } else if (jObjResult.getString(StatusCode).equals(Code_404)) {
                myToast(ActivityLogin.this, message);

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

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
        startActivity(new Intent(ActivityLogin.this, ActivityMain.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
        finish();
    }


}
