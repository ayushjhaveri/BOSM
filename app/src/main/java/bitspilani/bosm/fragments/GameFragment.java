package bitspilani.bosm.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import bitspilani.bosm.HomeActivity;
import bitspilani.bosm.R;
import bitspilani.bosm.adapters.AdapterPhotos;
import bitspilani.bosm.quilympics.FragmentQuilympics;
import bitspilani.bosm.roulette.RouletteHomeFragment;
import bitspilani.bosm.roulette.RouletteMainFragment;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;


public class GameFragment extends Fragment {

    public GameFragment() {
        // Required empty public constructor
        HomeActivity.currentFragment = "PhotoFragment";
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_game, container, false);

        Typeface oswald_regular = Typeface.createFromAsset(getContext().getAssets(),"fonts/KrinkesDecorPERSONAL.ttf");
        TextView tv_title = (TextView)view.findViewById(R.id.tv_header);
        tv_title.setTypeface(oswald_regular);

        (view.findViewById(R.id.cv_roulette)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new RouletteHomeFragment());
            }
        });

        (view.findViewById(R.id.cv_quilympics)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new FragmentQuilympics());
            }
        });

        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        HomeActivity.currentFragment="PhotoFragment";
    }


    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_view, fragment);
        transaction.addToBackStack("transaction");
        transaction.commit();
    }

}
