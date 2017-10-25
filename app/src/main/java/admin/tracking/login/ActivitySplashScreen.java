package admin.tracking.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import admin.tracking.ActivityMain;
import admin.tracking.R;
import admin.tracking.genericClasses.UtilSharedPreference;

import static admin.tracking.genericClasses.UtilConstant.User_Id;

/**
 * Created by digvijaymachale on 25/10/17.
 */

public class ActivitySplashScreen extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 1500;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        goToMainActivity();
    }


    private void goToMainActivity(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (UtilSharedPreference.getInstance(ActivitySplashScreen.this).getData(User_Id).equals("")) {
                    Intent intent = new Intent(ActivitySplashScreen.this, ActivityLogin.class);
                    // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
//                    overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                    finish();
                } else {
                    Intent intent = new Intent(ActivitySplashScreen.this, ActivityMain.class);
                    //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
//                    overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                    finish();
                }

            }
        }, SPLASH_TIME_OUT);
    }
}
