package admin.tracking.genericClasses;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import admin.tracking.R;
import admin.tracking.login.ActivityLogin;

/**
 * Created by admin on 16-10-2017.
 */

public class UtilMethods {


    private static Dialog mDialogMap;

    //Generic method for showing Toast
    //Parameter is context and message
    public static void myToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    //Generic method to check internet is available or not
    //Parameter is context
    public static boolean isNetworkAvailable(Context _context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) _context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    public static void createProgressDialogPopUp(Context context, String message) {
        LayoutInflater mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View lstGroupView = mInflater.inflate(R.layout.dialog_creating_map, null);
        mDialogMap = new Dialog(context);
        mDialogMap.setContentView(lstGroupView);
        mDialogMap.setCancelable(false);
        int height = (int) (context.getResources().getDisplayMetrics().heightPixels * 0.3);
        int width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.5);
        mDialogMap.getWindow().setLayout(width, height);
        mDialogMap.getWindow().setGravity(Gravity.CENTER);
        TextView txtMessage=(TextView) mDialogMap.findViewById(R.id.txtMessage);
        txtMessage.setText(message);
        mDialogMap.show();
    }

    public static void dismissPopUpProgressDialog(){
        if (mDialogMap != null && mDialogMap.isShowing()) {
            mDialogMap.dismiss();
        }
    }

    public static void gotoLogin(Context context) {
        Intent intent = new Intent(context, ActivityLogin.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
