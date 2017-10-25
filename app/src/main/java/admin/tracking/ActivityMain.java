package admin.tracking;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import admin.tracking.fragment.FragmentActivities;
import admin.tracking.fragment.FragmentAllVehicles;
import admin.tracking.fragment.FragmentHistory;
import admin.tracking.fragment.FragmentHome;
import admin.tracking.fragment.FragmentNotifications;
import admin.tracking.genericClasses.CircleTransform;
import admin.tracking.menuActivities.ActivityActivities;
import admin.tracking.menuActivities.ActivityAllVehicles;
import admin.tracking.menuActivities.ActivityContactUs;
import admin.tracking.menuActivities.ActivityHistory;
import admin.tracking.menuActivities.ActivityNotifications;

public class ActivityMain extends AppCompatActivity {

    private View navHeader;
    private ImageView imgProfile;
    private TextView txtName;


    // index to identify current nav menu item
    public static int navItemIndex = 0;

    // urls to load navigation header background image
    // and profile image
    private static final String urlNavHeaderBg = "https://api.androidhive.info/images/nav-menu-header-bg.jpg";
    private static final String urlProfileImg = "https://lh3.googleusercontent.com/eCtE_G34M9ygdkmOpYvCag1vBARCmZwnVS6rS5t4JLzJ6QgQSBquM0nuTsCpLhYbKljoyS-txg";
    private DrawerLayout drawer;
    private ImageView imgNavHeaderBg;

    // tags used to attach the fragments
    private static final String TAG_HOME = "home";
    private static final String TAG_ALL_VEHICLES = "photos";
    private static final String TAG_HISTORY = "movies";
    private static final String TAG_ACTIVITIES = "notifications";
    private static final String TAG_NOTIFICATIONS = "settings";
    public static String CURRENT_TAG = TAG_HOME;
    private NavigationView navigationView;

    // toolbar titles respected to selected nav menu item
    private String[] activityTitles;

    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mHandler = new Handler();


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        // Navigation view header
        navHeader = navigationView.getHeaderView(0);
        imgProfile = (ImageView) navHeader.findViewById(R.id.img_profile);
        imgNavHeaderBg = (ImageView) navHeader.findViewById(R.id.img_header_bg);
        txtName = (TextView) navHeader.findViewById(R.id.txtName);

        // load toolbar titles from string resources
        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);

        // load nav menu header data
        loadNavHeader();

        // initializing navigation menu
        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }

    }

    private void setUpNavigationView() {

        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {


                //Check to see which item was being clicked and perform appropriate action
                switch (item.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_home:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_HOME;
                        break;
                    case R.id.nav_all_vehicle:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_ALL_VEHICLES;
                        break;
                    case R.id.nav_history:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_HISTORY;
                        break;
                    case R.id.nav_activities:
                        navItemIndex = 3;
                        CURRENT_TAG = TAG_ACTIVITIES;
                        break;
                    case R.id.nav_notifications:
                        navItemIndex = 4;
                        CURRENT_TAG = TAG_NOTIFICATIONS;
                        break;
                    case R.id.nav_contact_us:
                        // launch new intent instead of loading fragment
                        startActivity(new Intent(ActivityMain.this, ActivityContactUs.class));
                        drawer.closeDrawers();
                        return true;
                    default:
                        navItemIndex = 0;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (item.isChecked()) {
                    item.setChecked(false);
                } else {
                    item.setChecked(true);
                }
                item.setChecked(true);

                loadHomeFragment();

                return true;
            }
        });
    }

    private void loadHomeFragment() {

        // selecting appropriate nav menu item
        selectNavMenu();

        // set toolbar title
        setToolbarTitle();

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();

            return;
        }

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app

        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
                fragmentTransaction.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left);
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }


        //Closing drawer on item click
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }


    @Override
    public void onBackPressed() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//
//    @SuppressWarnings("StatementWithEmptyBody")
//    @Override
//    public boolean onNavigationItemSelected(MenuItem item) {
//        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//
//        switch (id) {
//            case R.id.nav_home:
//                loadHomeFragment();
//                break;
//
//            case R.id.nav_all_vehicle:
//                redirectToAllVehicles();
//                break;
//
//            case R.id.nav_history:
//                redirectToHistory();
//                break;
//
//            case R.id.nav_activities:
//                redirectToActivities();
//                break;
//
//            case R.id.nav_notifications:
//                redirectToNotifications();
//                break;
//
//            case R.id.nav_contact_us:
//                redirectToContactUs();
//                break;
//
//            case R.id.nav_logout:
//                break;
//
//        }
//
//
//        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }


    /***
     * Load navigation menu header information
     * like background image, profile image
     * name, website, notifications action view (dot)
     */
    private void loadNavHeader() {
        // name, website
        txtName.setText("Digvijay Machale");

        // Loading profile image
        Glide.with(this).load(urlProfileImg)
                .crossFade()
                .thumbnail(0.5f)
                .bitmapTransform(new CircleTransform(this))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgProfile);

        // loading header background image
//        Glide.with(this).load(urlNavHeaderBg)
//                .crossFade()
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(imgNavHeaderBg);


//        // showing dot next to notifications label
//        navigationView.getMenu().getItem(3).setActionView(R.layout.menu_dot);
    }

    private void redirectToAllVehicles() {
        startActivity(new Intent(ActivityMain.this, ActivityAllVehicles.class));
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }


    private void redirectToContactUs() {
        startActivity(new Intent(ActivityMain.this, ActivityContactUs.class));
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

    }

    private void redirectToNotifications() {
        startActivity(new Intent(ActivityMain.this, ActivityNotifications.class));
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

    }

    private void redirectToActivities() {
        startActivity(new Intent(ActivityMain.this, ActivityActivities.class));
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

    }

    private void redirectToHistory() {
        startActivity(new Intent(ActivityMain.this, ActivityHistory.class));
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

    }


    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                // home
                FragmentHome homeFragment = new FragmentHome();
                return homeFragment;
            case 1:
                // photos
                FragmentAllVehicles fragmentAllVehicles = new FragmentAllVehicles();
                return fragmentAllVehicles;
            case 2:
                // movies fragment
                FragmentHistory fragmentHistory = new FragmentHistory();
                return fragmentHistory;
            case 3:
                // notifications fragment
                FragmentActivities fragmentActivities = new FragmentActivities();
                return fragmentActivities;

            case 4:
                // settings fragment
                FragmentNotifications fragmentNotifications = new FragmentNotifications();
                return fragmentNotifications;
            default:
                return new FragmentHome();
        }
    }
}
