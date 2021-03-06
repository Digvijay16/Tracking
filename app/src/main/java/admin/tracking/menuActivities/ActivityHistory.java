package admin.tracking.menuActivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import admin.tracking.R;

public class ActivityHistory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        //Initialisation of custom toolbar
        initToolbar();
    }

    private void initToolbar() {
        ((TextView) findViewById(R.id.txtHeading)).setText(R.string.history);
    }

    public void clickBack(View v) {
        finish();
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

    }
}
