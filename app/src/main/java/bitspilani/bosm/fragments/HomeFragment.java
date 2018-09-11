package bitspilani.bosm.fragments;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.eftimoff.viewpagertransformers.AccordionTransformer;
import com.eftimoff.viewpagertransformers.CubeOutTransformer;
import com.eftimoff.viewpagertransformers.DrawFromBackTransformer;
import com.eftimoff.viewpagertransformers.StackTransformer;
import com.eftimoff.viewpagertransformers.ZoomOutSlideTransformer;

import java.util.ArrayList;

import bitspilani.bosm.HomeActivity;
import bitspilani.bosm.R;
import bitspilani.bosm.utils.Constant;
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
    public static ViewPager vpPager;

    public HomeFragment() {
        HomeActivity.currentFragment = "aaaaaaaa";

        // Required empty public constructor
    }
int pos;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_home, container, false);


        Bundle arguments = getArguments();
        if(getArguments()!=null){
            pos =  arguments.getInt("pos");
            Log.d("ereed",pos+"");
        }else
            pos = 0;


//        Toast.makeText(getActivity(), HomeActivity.currentFragment, Toast.LENGTH_SHORT).show();


        final NavigationTabBar ntbSample5 = (NavigationTabBar) v.findViewById(R.id.ntb_sample_5);

        final ArrayList<NavigationTabBar.Model> models5 = new ArrayList<>();
        models5.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.events), ContextCompat.getColor(getContext(),R.color.back_shade1)
                )
                        .badgeTitle("Events")
                        .title("title")
                        .build()
        );
        models5.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.home),ContextCompat.getColor(getContext(),R.color.back_shade1)
                ).badgeTitle("Home")
                        .title("title")
                        .build()
        );
        models5.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.fixtures), ContextCompat.getColor(getContext(),R.color.back_shade1)
                )
                        .badgeTitle("Scores")
                        .title("title")
                        .build()
        );
        ntbSample5.setModels(models5);



        vpPager = (ViewPager) v.findViewById(R.id.vp_horizontal_ntb);
        MyPagerAdapter adapterViewPager = new MyPagerAdapter(getChildFragmentManager());
        vpPager.setAdapter(adapterViewPager);
        vpPager.setPageTransformer(true, new ZoomOutSlideTransformer());

        ntbSample5.setViewPager(vpPager, pos);


        ntbSample5.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {


                for(int i =0;i<ntbSample5.getModels().size();i++){

//                    if(i==0)HomeActivity.currentFragment = "EventFragment";
//                    else if(i==1)HomeActivity.currentFragment = "LiveFragment";
//                    else if(i==2)HomeActivity.currentFragment = "SportFragment";

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

        vpPager.setCurrentItem(pos);


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
    @Override
    public void onResume() {
        super.onResume();
        HomeActivity.currentFragment="aaaaaaaa";
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onStop() {
        super.onStop();

    }
}
