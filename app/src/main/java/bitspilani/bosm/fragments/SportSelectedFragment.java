package bitspilani.bosm.fragments;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
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
import android.widget.TextView;
import android.widget.Toast;

import com.eftimoff.viewpagertransformers.ZoomOutSlideTransformer;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
public class SportSelectedFragment extends Fragment {

//    FragmentManager fragmentManager = getChildFragmentManager();
    Context context;
    public static ViewPager vpPagerSport;
    public SportSelectedFragment() {
        // Required empty public constructor
        HomeActivity.currentFragment = "SportSelectedFragment";
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_selected_sport, container, false);

        context = getContext();
        RelativeLayout rl_bottom_vp = (RelativeLayout)v.findViewById(R.id.rl_bottom_vp);

        if(!HomeActivity.hasNavBar(getContext())) {
            FrameLayout.LayoutParams relativeParams = (FrameLayout.LayoutParams)rl_bottom_vp.getLayoutParams();
            Resources r = getContext().getResources();
            int px = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    64,
                    r.getDisplayMetrics()
            );
            relativeParams.setMargins(0, 0, 0, px-HomeActivity.getHeight(getContext()));  // left, top, right, bottom
            rl_bottom_vp.setLayoutParams(relativeParams);
        }


        HomeActivity.currentFragment = "SportSelectedFragment";

        final NavigationTabBar ntbSample5 = (NavigationTabBar) v.findViewById(R.id.ntb_sample_5);

        ntbSample5.setIsBadged(true);
        final ArrayList<NavigationTabBar.Model> models5 = new ArrayList<>();
        models5.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.boys), ContextCompat.getColor(context, R.color.back_shade1)
                )
                        .badgeTitle("Boys")
                        .build()
        );
        models5.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.girls), ContextCompat.getColor(context, R.color.back_shade1)
                ).badgeTitle("Girls")
                        .build()
        );
        ntbSample5.setModels(models5);

        vpPagerSport = (ViewPager) v.findViewById(R.id.vp_horizontal_ntb);
        MyPagerAdapter adapterViewPager = new MyPagerAdapter(getChildFragmentManager());
        vpPagerSport.setAdapter(adapterViewPager);
        vpPagerSport.setPageTransformer(true, new ZoomOutSlideTransformer());
        ntbSample5.setViewPager(vpPagerSport, 0);
        ntbSample5.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
                for(int i =0;i<ntbSample5.getModels().size();i++){
                ntbSample5.getModels().get(i).showBadge();
                HomeActivity.currentFragment.equals("CurrentSportFragment");
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


        Typeface oswald_regular = Typeface.createFromAsset(getActivity().getAssets(),"fonts/RobotoCondensed-Bold.ttf");
        TextView title = (TextView)v.findViewById(R.id.tv_header);
        title.setText(Constant.currentSport.getName().toUpperCase());

        title.setTypeface(oswald_regular);

        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        if(!Constant.currentSport.isGender()){
            rl_bottom_vp.setVisibility(View.GONE);

            MyPagerAdapter.NUM_ITEMS = 1;
            adapterViewPager.notifyDataSetChanged();
        }else{
            rl_bottom_vp.setVisibility(View.VISIBLE);
            MyPagerAdapter.NUM_ITEMS = 2;
            adapterViewPager.notifyDataSetChanged();
        }

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

                case 0:
                    return CurrentSportFragment.newInstance(1);
                case 1: // Fragment # 0 - This will show FirstFragment
                    return CurrentSportFragment.newInstance(2);
//                case 2: // Fragment # 0 - This will show FirstFragment different title
//                    return new SportFragment();
                default:
                    return CurrentSportFragment.newInstance(1);
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
        HomeActivity.currentFragment="SportSelectedFragment";
    }
}
