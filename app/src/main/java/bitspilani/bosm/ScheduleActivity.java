package bitspilani.bosm;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.support.v7.widget.Toolbar;

import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

import java.util.ArrayList;
import java.util.List;

import bitspilani.bosm.fragments.ScoreFragmentFive;
import bitspilani.bosm.fragments.ScoreFragmentFour;
import bitspilani.bosm.fragments.ScoreFragmentOne;
import bitspilani.bosm.fragments.ScoreFragmentThree;
import bitspilani.bosm.fragments.ScoreFragmentTwo;

public class ScheduleActivity extends AppCompatActivity{

    private ResideMenu resideMenu;
    private ScheduleActivity mContext;
    private ResideMenuItem itemHome;
    private ResideMenuItem itemProfile;
    private ResideMenuItem itemCalendar;
    private ResideMenuItem itemSettings;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        mContext = this;

        //if( savedInstanceState == null )
        //    changeFragment(new HomeFragment());

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setUpMenu();
    }

    private void setUpMenu() {

        // attach to current activity;
        resideMenu = new ResideMenu(this);
        //resideMenu.setUse3D(true);

        resideMenu.setBackgroundColor(getResources().getColor(R.color.back_shade3));
        resideMenu.setBackground(R.drawable.background_plain);
        resideMenu.attachToActivity(this);
        //resideMenu.setMenuListener(menuListener);
        //valid scale factor is between 0.0f and 1.0f. leftmenu'width is 150dip.
        resideMenu.setScaleValue(0.6f);

        // create menu items;
        itemHome     = new ResideMenuItem(this, R.drawable.icon_college,     "Home");
        ResideMenuItem itemStalls  = new ResideMenuItem(this, R.drawable.icon_cart2,  "Stalls");
        itemCalendar = new ResideMenuItem(this, R.drawable.icon_personadd, "About");
        itemSettings = new ResideMenuItem(this, R.drawable.icon_name, "Developer");

        //itemHome.setOnClickListener(this);
        //itemProfile.setOnClickListener(this);
        //itemCalendar.setOnClickListener(this);
        //itemSettings.setOnClickListener(this);

        resideMenu.addMenuItem(itemHome, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemStalls, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemCalendar, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemSettings, ResideMenu.DIRECTION_LEFT);

        // You can disable a direction by setting ->
        // resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);

        findViewById(R.id.button_menu_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
                resideMenu.addIgnoredView(viewPager);
                resideMenu.addIgnoredView(tabLayout);
            }
        });
        resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);
        resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_LEFT);

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return resideMenu.dispatchTouchEvent(ev);
    }

   /* @Override
    public void onClick(View view) {

        if (view == itemHome){
            changeFragment(new HomeFragment());
        }else if (view == itemProfile){
            changeFragment(new ProfileFragment());
        }else if (view == itemCalendar){
            changeFragment(new CalendarFragment());
        }else if (view == itemSettings){
            changeFragment(new SettingsFragment());
        }

        resideMenu.closeMenu();
    }*/

    /*private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
        @Override
        public void openMenu() {
            Toast.makeText(mContext, "Menu is opened!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void closeMenu() {
            Toast.makeText(mContext, "Menu is closed!", Toast.LENGTH_SHORT).show();
        }
    };
*/
  /*  private void changeFragment(Fragment targetFragment){
        resideMenu.clearIgnoredViewList();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }
*/
    // What good method is to access resideMenu？
    public ResideMenu getResideMenu(){
        return resideMenu;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ScoreFragmentOne(), "WED");
        adapter.addFragment(new ScoreFragmentTwo(), "THU");
        adapter.addFragment(new ScoreFragmentThree(), "FRI");
        adapter.addFragment(new ScoreFragmentFour(), "SAT");
        adapter.addFragment(new ScoreFragmentFive(), "SUN");

        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}