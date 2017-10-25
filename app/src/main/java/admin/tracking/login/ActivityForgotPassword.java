package admin.tracking.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import admin.tracking.R;

public class ActivityForgotPassword extends AppCompatActivity {

    private EditText edtEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_forgot_password);


        // Initialisation of widgets
        initWidget();
    }

    private void initWidget() {
        edtEmail = (EditText) findViewById(R.id.edtEmail);

    }

    public void clickSubmit(View v) {

        if (isValid()) {

            //Functionality to send email for password.

        }

    }


    private boolean isValid() {

        if (edtEmail.getText().length() < 1) {
            edtEmail.setError("Email cannot be blank");
            return false;
        }

        return true;
    }

}
