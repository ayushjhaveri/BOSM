package bitspilani.bosm.roulette;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.eftimoff.viewpagertransformers.ZoomOutSlideTransformer;

import java.util.ArrayList;

import bitspilani.bosm.HomeActivity;
import bitspilani.bosm.R;
import devlight.io.library.ntb.NavigationTabBar;

///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link HomeFragment.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link HomeFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class RouletteHomeFragment extends Fragment {

//    FragmentManager fragmentManager = getChildFragmentManager();

    private Context context;
    public RouletteHomeFragment() {
        // Required empty public constructor
        HomeActivity.currentFragment="RouletteHomeFragment";
    }

    public static ViewPager vpPagerRoulette;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_roulette_home, container, false);
        context = getContext();


        if(!HomeActivity.hasNavBar(getContext())) {
            RelativeLayout relativeLayout = ((RelativeLayout)v.findViewById(R.id.rl_bottom));
            FrameLayout.LayoutParams relativeParams = (FrameLayout.LayoutParams)relativeLayout.getLayoutParams();
            Resources r = getContext().getResources();
            int px = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    64,
                    r.getDisplayMetrics()
            );
            relativeParams.setMargins(0, 0, 0, px-HomeActivity.getHeight(getContext()));  // left, top, right, bottom
            relativeLayout.setLayoutParams(relativeParams);
        }

        final NavigationTabBar ntbSample5 = (NavigationTabBar) v.findViewById(R.id.ntb_sample_5);

        final ArrayList<NavigationTabBar.Model> models5 = new ArrayList<>();
        models5.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.icon_instructions), ContextCompat.getColor(context,R.color.back_shade1)
                )
                        .badgeTitle("Instructions")
                        .title("title")
                        .build()
        );
        models5.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.home),ContextCompat.getColor(context,R.color.back_shade1)
                ).badgeTitle("Matches")
                        .title("title")
                        .build()
        );
        models5.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.icon_leaderboard), ContextCompat.getColor(context,R.color.back_shade1)
                )
                        .badgeTitle("Rank")
                        .title("title")
                        .build()
        );
        ntbSample5.setModels(models5);



        vpPagerRoulette = (ViewPager) v.findViewById(R.id.vp_horizontal_ntb);
        MyPagerAdapter adapterViewPager = new MyPagerAdapter(getChildFragmentManager());
        vpPagerRoulette.setAdapter(adapterViewPager);
        vpPagerRoulette.setPageTransformer(true, new ZoomOutSlideTransformer());
        ntbSample5.setViewPager(vpPagerRoulette, 1);
        ntbSample5.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
                for(int i =0;i<ntbSample5.getModels().size();i++){
//                    if(i==0)HomeActivity.currentFragment = "RouletteExtraFragment";
//                    else if(i==1)HomeActivity.currentFragment = "RouletteMainFragment";
//                    else if(i==2)HomeActivity.currentFragment = "RouletteLeaderboardFragment";
                    ntbSample5.getModels().get(i).showBadge();
                }
                ntbSample5.getModels().get(position).hideBadge();
            }

            @Override
            public void onPageScrollStateChanged(final int state) {

            }
        });

        ntbSample5.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < ntbSample5.getModels().size(); i++) {
                    final NavigationTabBar.Model model = ntbSample5.getModels().get(i);
                    ntbSample5.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            model.showBadge();
                        }
                    }, i * 100);
                }
            }
        }, 500);

        return v;
    }


    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 3;

        public MyPagerAdapter(FragmentManager fragmentManager) {

            super(fragmentManager);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {

                case 0:
                return new RouletteExtraFragment();
                case 1: // Fragment # 0 - This will show FirstFragment
                    return new RouletteMainFragment();
                case 2: // Fragment # 0 - This will show FirstFragment different title
                    return new RouletteLeaderboardFragment();
                default:
                    return new RouletteMainFragment();
            }
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }

    }
    @Override
    public void onResume() {
        super.onResume();
        HomeActivity.currentFragment="RouletteHomeFragment";
    }
}
