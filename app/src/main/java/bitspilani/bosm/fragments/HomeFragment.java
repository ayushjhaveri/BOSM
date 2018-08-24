package bitspilani.bosm.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

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
public class HomeFragment extends Fragment {

//    FragmentManager fragmentManager = getChildFragmentManager();


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_home, container, false);

        final NavigationTabBar ntbSample5 = (NavigationTabBar) v.findViewById(R.id.ntb_sample_5);
        final ArrayList<NavigationTabBar.Model> models5 = new ArrayList<>();
        models5.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.icon_sportreg_black), ContextCompat.getColor(getContext(),R.color.back_shade1)
                )
                        .badgeTitle("icon")
                        .build()
        );
        models5.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.icon_sportreg_black),ContextCompat.getColor(getContext(),R.color.back_shade1)
                ).badgeTitle("icon")
                        .build()
        );
        models5.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.icon_sportreg_black), ContextCompat.getColor(getContext(),R.color.back_shade1)
                )
                        .badgeTitle("icon")
                        .build()
        );
        ntbSample5.setModels(models5);



        ViewPager vpPager = (ViewPager) v.findViewById(R.id.vp_horizontal_ntb);
        MyPagerAdapter adapterViewPager = new MyPagerAdapter(getChildFragmentManager());
        vpPager.setAdapter(adapterViewPager);

        ntbSample5.setViewPager(vpPager, 0);
        ntbSample5.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
//                ntbSample5.getModels().get(position).hideBadge();
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
                    return new EventFragment();
                case 1: // Fragment # 0 - This will show FirstFragment
                    return new LiveFragment();
                case 2: // Fragment # 0 - This will show FirstFragment different title
                    return new SportFragment();
                default:
                    return new LiveFragment();
            }
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }

    }
}
