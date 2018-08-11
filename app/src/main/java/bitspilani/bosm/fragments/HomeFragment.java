package bitspilani.bosm.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import bitspilani.bosm.R;
import bitspilani.bosm.adapters.AdapterLive;
import bitspilani.bosm.items.ItemLive;
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
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_home, container, false);


//        rl_empty_layout = (RelativeLayout) findViewById(R.id.rl_empty_layout);
//        progressBar = (ProgressBar) findViewById(R.id.progressBar);
//        tv_total_price=(TextView) findViewById(R.id.tv_total_price);
//        tv_items=(TextView) findViewById(R.id.tv_items);
////        tv_pay=(TextView) findViewById(R.id.tv_pay);
//
////        iv_back = (ImageView)findViewById(R.id.iv_back);
//        ib_pay = (ImageButton) findViewById(R.id.ib_pay);



        final NavigationTabBar ntbSample5 = (NavigationTabBar) v.findViewById(R.id.ntb_sample_5);
        final ArrayList<NavigationTabBar.Model> models5 = new ArrayList<>();
        models5.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.icon_sportreg_black), Color.WHITE
                ).build()
        );
        models5.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.icon_sportreg_black), Color.WHITE
                ).build()
        );
        ntbSample5.setModels(models5);



        ViewPager vpPager = (ViewPager) v.findViewById(R.id.vp_horizontal_ntb);
        MyPagerAdapter adapterViewPager = new MyPagerAdapter(getFragmentManager());
        vpPager.setAdapter(adapterViewPager);

        ntbSample5.setViewPager(vpPager, 0);
        ntbSample5.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
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
        private static int NUM_ITEMS = 2;

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
                case 0: // Fragment # 0 - This will show FirstFragment
                    return LiveFragment.newInstance(0, "Page # 1");
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    return ScoreFragment.newInstance(1, "Page # 2");
                default:
                    return LiveFragment.newInstance(2, "Page # 3");
            }
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }

    }
}
