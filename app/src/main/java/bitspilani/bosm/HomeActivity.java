package bitspilani.bosm;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import bitspilani.bosm.fragments.BlankFragment;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //    public static Toolbar toolbar;
    ImageButton ib_cart;
    FragmentManager fm;
    DrawerLayout drawer;
    LinearLayout ll_dots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        setTitle("BOSM 2K18");

        fm = getSupportFragmentManager();
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ll_dots = (LinearLayout) findViewById(R.id.ll_dots);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, null, R.string.app_name, R.string.app_name) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);


                Animation animSlide = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide2);
                ll_dots.startAnimation(animSlide);

//                Toast.makeText(HomeActivity.this, "dsasda", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
                Animation animSlide = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide);
                ll_dots.startAnimation(animSlide);
//                Toast.makeText(HomeActivity.this, "ppppp", Toast.LENGTH_SHORT).show();




            }

        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toggle.setDrawerIndicatorEnabled(false);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ib_cart = (ImageButton) findViewById(R.id.ib_cart);
        ib_cart.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, CartActivity.class));
            }
        });
//        iv_nav = (ImageView)findViewById(R.id.iv_nav);

//        iv_cart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(HomeActivity.this,CartActivity.class));
//            }
//        });
//
//        iv_nav.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (drawer.isDrawerOpen(GravityCompat.START)) {
//                    drawer.closeDrawer(GravityCompat.START);
//                }else if(!drawer.isDrawerOpen(GravityCompat.START)) {
//                    drawer.openDrawer(GravityCompat.START);
//                }
//            }
//        });

//        findViewById(R.id.iv_wallet).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(HomeActivity.this,WalletActivity.class));
//            }
//        });


        FloatingActionButton fab_wallet = (FloatingActionButton) findViewById(R.id.fab);
        fab_wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, WalletActivity.class));
            }
        });

        setTitle("Order Food");
        loadFrag(new BlankFragment(), "Order Food", fm);

//         findViewById(R.id.nav_order_food).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                drawer.closeDrawer(GravityCompat.START);
//                loadFrag(new BlankFragment(),"Order Food",fm);
//            }
//        });
//        findViewById(R.id.nav_home).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                drawer.closeDrawer(GravityCompat.START);
////                loadFrag(new BlankFragment(),"Order Food",fm);
//            }
//        });

        TextView tv_bosm = (TextView) findViewById(R.id.tv_bosm);
        Typeface oswald_regular = Typeface.createFromAsset(getAssets(), "fonts/Oswald-Regular.ttf");
        tv_bosm.setTypeface(oswald_regular);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
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

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_wallet) {
            startActivity(new Intent(HomeActivity.this, WalletActivity.class));
            return true;
        } else if (id == R.id.menu_cart) {
            startActivity(new Intent(HomeActivity.this, CartActivity.class));
            return true;
        }

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
//            loadFrag(new BlankFragment(),"Order Food",fm);
//        }

//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void loadFrag(Fragment f1, String name, FragmentManager fm) {
//        selectedFragment = name;
        FragmentTransaction ft = fm.beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.replace(R.id.fl_view, f1, name);
        ft.commit();
    }

//    @Override
//    public void onClick(View view) {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//
//        switch (view.getId()) {
//            case R.id.nav_home:
//                drawer.closeDrawer(GravityCompat.START);
//                Toast.makeText(HomeActivity.this,"ddsadsad",Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.nav_order_food:
//                drawer.closeDrawer(GravityCompat.START);
//                Toast.makeText(HomeActivity.this,"ddsadsad",Toast.LENGTH_SHORT).show();
//                loadFrag(new BlankFragment(),"Order Food",fm);
//                break;
//        }
//    }
}
