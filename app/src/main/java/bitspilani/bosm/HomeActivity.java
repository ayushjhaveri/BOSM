package bitspilani.bosm;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;

import bitspilani.bosm.adapters.ItemSport;
import bitspilani.bosm.fragments.CurrentSportFragment;
import bitspilani.bosm.fragments.EventFragment;
import bitspilani.bosm.fragments.GameFragment;
import bitspilani.bosm.fragments.MapFragment;
import bitspilani.bosm.fragments.PhotoFragment;
import bitspilani.bosm.fragments.SportFragment;
import bitspilani.bosm.fragments.SportSelectedFragment;
import bitspilani.bosm.fragments.ContactFragment;
import bitspilani.bosm.fragments.DevelopersFragment;
import bitspilani.bosm.fragments.EpcFragment;
import bitspilani.bosm.fragments.HomeFragment;
import bitspilani.bosm.fragments.HpcFragment;
import bitspilani.bosm.fragments.SponsorsFragment;
import bitspilani.bosm.hover.MultipleSectionsHoverMenuService;
import bitspilani.bosm.roulette.RouletteHomeFragment;
import bitspilani.bosm.utils.Constant;
import io.mattcarroll.hover.overlay.OverlayPermission;

import static bitspilani.bosm.fragments.HomeFragment.vpPager;
import static bitspilani.bosm.fragments.SportSelectedFragment.vpPagerSport;
import static bitspilani.bosm.roulette.RouletteHomeFragment.vpPagerRoulette;
import static com.google.common.base.Preconditions.checkArgument;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //    public static Toolbar toolbar;
//    ImageButton ib_cart;
    static FragmentManager fm;
    DrawerLayout drawer;
    LinearLayout ll_dots;
    private FirebaseFirestore db;

    public static String currentFragment = "";

    private static final int REQUEST_CODE_HOVER_PERMISSION = 1000;

    private boolean mPermissionsRequested = false;

    public static FragmentManager getSFM() {
        return fm;
    }
    public static View view;
    public static TextView tv_number;

    long delay;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        fm = getSupportFragmentManager();
        //checking service
        FirebaseApp.initializeApp(this);
        FirebaseMessaging.getInstance().subscribeToTopic("all");

        FirebaseMessaging.getInstance().subscribeToTopic("prashant");
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .setPersistenceEnabled(true)
                .build();

        db = FirebaseFirestore.getInstance();
        db.setFirestoreSettings(settings);
       db.collection("constant").document("required_version_code").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {

                    try {
                        PackageInfo pInfo = HomeActivity.this.getPackageManager().getPackageInfo(getPackageName(), 0);
                        int verCode = pInfo.versionCode;
                        int serverCode = Integer.parseInt(task.getResult().getData().get("value").toString());
                        if(verCode < serverCode){
                            AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                            builder.setTitle("New Version Available")
                                    .setCancelable(false)
                                    .setMessage("Please update the app to continue using")
                                    .setPositiveButton("update", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            // continue with delete

                                            final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                                            try {
                                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                                            } catch (android.content.ActivityNotFoundException anfe) {
                                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                                            }
                                        }
                                    }).show();


                        }
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });



        delay = 200;

        LayoutInflater inflater  = LayoutInflater.from(getApplicationContext());
        view  = inflater.inflate(R.layout.layout_hover_icon, null, false);
        tv_number =  (TextView)view.findViewById(R.id.tv_number);

        final Intent startHoverIntent = new Intent(HomeActivity.this, MultipleSectionsHoverMenuService.class);
        startService(startHoverIntent);

        tv_number.setText("12");
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        setTitle("BOSM '18");

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ll_dots = (LinearLayout) findViewById(R.id.ll_dots);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, null, R.string.app_name, R.string.app_name) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);


                Animation animSlide = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide2);
                ll_dots.startAnimation(animSlide);

//                Toast.makeText(HomeActivity.this, "dsasda", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
                Animation animSlide = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide);
                ll_dots.startAnimation(animSlide);
//                Toast.makeText(HomeActivity.this, "ppppp", Toast.LENGTH_SHORT).show();

            }

        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toggle.setDrawerIndicatorEnabled(false);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ll_dots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(GravityCompat.START)) {

                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }

        });


        if (getIntent().getExtras() != null && getIntent().getStringExtra("NOTIFICATION") != null) {
            if (getIntent().getStringExtra("NOTIFICATION").equals("1")) {
                Log.d("notif", "1");
                loadFragHome(new HomeFragment(), "Home", fm, 0);
            } else if (getIntent().getStringExtra("NOTIFICATION").equals("2")) {
                Log.d("notif", "2");
                if (getIntent().getIntExtra("sport_id",-1)>0){
                        db.collection("sports").document(String.valueOf(getIntent().getIntExtra("sport_id",0))).get().addOnCompleteListener(
                                new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        final ItemSport itemSport  = new ItemSport(
                                                Integer.parseInt(task.getResult().getId()),
                                                task.getResult().getData().get("sport_name").toString(),
                                                Boolean.parseBoolean(task.getResult().getData().get("is_gender").toString())

                                        );
                                        Constant.currentSport= itemSport;
                                        loadFrag(new SportSelectedFragment(),"Notification", fm );
                                    }
                                }
                        );
//                    Constant.currentSport.setSport_id(getIntent().getIntExtra("sport_id",-1));
                }
                else {
                    loadFragHome(new HomeFragment(), "Home", fm, 2);
                }
            }
        } else {
            Log.d("notif", "564564");
            loadFragHome(new HomeFragment(), "Home", fm, 1);
        }

        TextView tv_bosm = (TextView) findViewById(R.id.tv_bosm);
        Typeface oswald_regular = Typeface.createFromAsset(getAssets(), "fonts/Oswald-Regular.ttf");
        tv_bosm.setTypeface(oswald_regular);


//        findViewById(R.id.tv_logout).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FirebaseAuth.getInstance().signOut();
//                startActivity(new Intent(HomeActivity.this,LoginActivity.class));
//                stopService(startHoverIntent);
//                finish();
//            }
//        });

        findViewById(R.id.rl_home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (HomeActivity.currentFragment.equals("aaaaaaaa") || HomeActivity.currentFragment.equals("LiveFragment")) {
                        } else {
                            Log.d("notif", "56546434");
                            loadFragHome(new HomeFragment(), "Home", fm, 1);
                        }
                    }
                }, delay);
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        findViewById(R.id.tv_game).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (HomeActivity.currentFragment.equals("GAMEFRAGMENT")) {
                        } else {
                            loadFrag(new GameFragment(), "Game", fm);
                        }
                    }
                }, delay);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        findViewById(R.id.tv_photos).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if (HomeActivity.currentFragment.equals("PhotoFragment")) {

                        } else {
                            loadFrag(new PhotoFragment(), "Photos", fm);
                        }
                    }
                }, delay);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        findViewById(R.id.tv_map).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if (HomeActivity.currentFragment.equals("MapFragment")) {

                        } else {
                            loadFrag(new MapFragment(), "Map", fm);
                        }
                    }
                }, delay);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        findViewById(R.id.tv_developers).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (HomeActivity.currentFragment.equals("DevelopersFragment")) {

                        } else {
                            loadFrag(new DevelopersFragment(), "Develoeprs", fm);
                        }
                    }
                }, delay);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        findViewById(R.id.tv_sponsors).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (HomeActivity.currentFragment.equals("SponsorsFragment")) {

                        } else {
                            loadFrag(new SponsorsFragment(), "Sponsors", fm);
                        }
                    }
                }, delay);
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        findViewById(R.id.tv_epc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (HomeActivity.currentFragment.equals("EpcFragment")) {

                        } else {
                            loadFrag(new EpcFragment(), "EPC", fm);
                        }
                    }
                }, delay);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        findViewById(R.id.tv_hpc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (HomeActivity.currentFragment.equals("HpcFragment")) {

                        } else {
                            loadFrag(new HpcFragment(), "HPC", fm);
                        }
                    }
                }, delay);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        findViewById(R.id.tv_contacts).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if (HomeActivity.currentFragment.equals("StallFragment")) {

                        } else {
                            loadFrag(new ContactFragment(), "Contact Us", fm);
                        }
                    }
                }, delay);
                drawer.closeDrawer(GravityCompat.START);
            }
        });


    }


    @Override
    protected void onResume() {
        super.onResume();

        db.collection("constant").document("required_version_code").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {

                    try {
                        PackageInfo pInfo = HomeActivity.this.getPackageManager().getPackageInfo(getPackageName(), 0);
                        int verCode = pInfo.versionCode;
                        int serverCode = Integer.parseInt(task.getResult().getData().get("value").toString());
                        if(verCode < serverCode){
                            AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                            builder.setTitle("New Version Available")
                                    .setCancelable(false)
                                    .setMessage("Please update the app to continue using")
                                    .setPositiveButton("update", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            // continue with delete

                                            final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                                            try {
                                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                                            } catch (android.content.ActivityNotFoundException anfe) {
                                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                                            }
                                        }
                                    }).show();


                        }
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        // On Android M and above we need to ask the user for permission to display the Hover
        // menu within the "alert window" layer.  Use OverlayPermission to check for the permission
        // and to request it.
        if (!mPermissionsRequested && !OverlayPermission.hasRuntimePermissionToDrawOverlay(this)) {
            @SuppressWarnings("NewApi")
            Intent myIntent = OverlayPermission.createIntentToRequestOverlayPermission(this);
            startActivityForResult(myIntent, REQUEST_CODE_HOVER_PERMISSION);
        }
        registerReceiver(mMessageReceiver, new IntentFilter("unique_name"));
    }

    //Must unregister onPause()
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mMessageReceiver);
    }


    //This is the handler that will manager to process the broadcast intent
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            // Extract data included in the Intent
            String message = intent.getStringExtra("message");
            int number = Integer.parseInt(HomeActivity.tv_number.getText().toString());
            number ++;
            HomeActivity.tv_number.setText(number+"");
            HomeActivity.tv_number.setVisibility(View.VISIBLE);

            //do other stuff here
        }
    };

    @Override
    protected void onDestroy() {
        Log.i("MAINACT", "onDestroy!");
        super.onDestroy();

    }

    public static boolean hasNavBar (Context context)
    {
        Resources resources = context.getResources();
        int id = resources.getIdentifier("config_showNavigationBar", "bool", "android");
        return id > 0 && resources.getBoolean(id);
    }

    public static int getHeight(Context context){
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        Toast.makeText(this, currentFragment, Toast.LENGTH_SHORT).show();
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (currentFragment.equals("aaaaaaaa") && vpPager.getCurrentItem() != 1) {
//                if(vpPager.getCurrentItem()!=1){
            vpPager.setCurrentItem(1);

        } else if (currentFragment.equals("RouletteHomeFragment") && vpPagerRoulette.getCurrentItem() != 1) {
//                if(vpPager.getCurrentItem()!=1){
            vpPagerRoulette.setCurrentItem(1);

        }
//           else if((currentFragment.equals("LiveFragment")||currentFragment.equals("HomeFragment")||currentFragment.equals("EventFragment")||currentFragment.equals("SportFragment"))&& vpPager.getCurrentItem()==1){super.onBackPressed();}
//           else if((currentFragment.equals("RouletteHomeFragment")||currentFragment.equals("RouletteMainFragment")||currentFragment.equals("RouletteExtraFragment")||currentFragment.equals("RouletteLeaderboardFragment"))&& vpPagerRoulette.getCurrentItem()==1){super.onBackPressed();}
//       else if(currentFragment.equals("EventFragment")){
//               currentFragment="LiveFragment";
//          vpPager.setCurrentItem(1);
//
//      }else if(currentFragment.equals("SportFragment")){
//               currentFragment="LiveFragment";
//          vpPager.setCurrentItem(1);
//
//        }
//       else if(currentFragment.equals("RouletteExtraFragment") || currentFragment.equals("RouletteLeaderboardFragment")){
//               currentFragment="RouletteMainFragment";
//           vpPagerRoulette.setCurrentItem(1);
//
//       }

//      else if(vpPagerSport.getCurrentItem()==0||vpPagerSport.getCurrentItem()==1){
//           loadFrag(new HomeFragment(), "Sport", fm);
//           vpPager.setCurrentItem(2);
//       }

        else {
            super.onBackPressed();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        Drawable drawable = menu.getItem(0).getIcon();
        if (drawable != null) {
            // If we don't mutate the drawable, then all drawable's with this id will have a color
            // filter applied to it.
            drawable.mutate();
            drawable.setColorFilter(getResources().getColor(R.color.color_grey), PorterDuff.Mode.SRC_ATOP);
//            drawable.setAlpha(alpha);
        }
        Drawable drawable2 = menu.getItem(1).getIcon();
        if (drawable2 != null) {
            // If we don't mutate the drawable, then all drawable's with this id will have a color
            // filter applied to it.
            drawable2.mutate();
            drawable2.setColorFilter(getResources().getColor(R.color.color_grey), PorterDuff.Mode.SRC_ATOP);
//            drawable.setAlpha(alpha);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //noinspection SimplifiableIfStatement
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
//
//        if (id == R.id.nav_food_stalls) {
//            // Handle the camera action
//            setTitle("Order Food");
//            loadFrag(new StallFragment(),"Order Food",fm);
//        }

//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public static void loadFrag(Fragment f1, String name, FragmentManager fm) {
//        selectedFragment = name;
        FragmentTransaction ft = fm.beginTransaction()
                .addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//        ft.setCustomAnimations(R.anim.fade_out, R.anim.fade_in);
        ft.replace(R.id.fl_view, f1, name);
        ft.commit();
    }

    public static void loadFragHome(Fragment f1, String name, FragmentManager fm, int pos) {
//        selectedFragment = name;
        Bundle arguments = new Bundle();
        arguments.putInt("pos", pos);
        f1.setArguments(arguments);
        FragmentTransaction ft = fm.beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//        ft.setCustomAnimations(R.anim.fade_out, R.anim.fade_in);
        ft.replace(R.id.fl_view, f1, name);
        ft.commit();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (REQUEST_CODE_HOVER_PERMISSION == requestCode) {
            mPermissionsRequested = true;
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    public static String toTitleCase(String str) {

        if (str == null) {
            return null;
        }

        boolean space = true;
        StringBuilder builder = new StringBuilder(str);
        final int len = builder.length();

        for (int i = 0; i < len; ++i) {
            char c = builder.charAt(i);
            if (space) {
                if (!Character.isWhitespace(c)) {
                    // Convert to title case and switch out of whitespace mode.
                    builder.setCharAt(i, Character.toTitleCase(c));
                    space = false;
                }
            } else if (Character.isWhitespace(c)) {
                space = true;
            } else {
                builder.setCharAt(i, Character.toLowerCase(c));
            }
        }

        return builder.toString();
    }

    public static String getDayOfMonthSuffix(final int n) {
        checkArgument(n >= 1 && n <= 31, "illegal day of month: " + n);
        if (n >= 11 && n <= 13) {
            return "th";
        }
        switch (n % 10) {
            case 1:
                return "st";
            case 2:
                return "nd";
            case 3:
                return "rd";
            default:
                return "th";
        }
    }




}
