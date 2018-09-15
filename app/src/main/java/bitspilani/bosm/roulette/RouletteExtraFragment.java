package bitspilani.bosm.roulette;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import bitspilani.bosm.HomeActivity;
import bitspilani.bosm.R;


public class RouletteExtraFragment extends Fragment {


    public RouletteExtraFragment() {
        // Required empty public constructor
        HomeActivity.currentFragment="RouletteHomeFragment";
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_roulette_extra, container, false);

        TextView tv_header = (TextView) view.findViewById(R.id.tv_header);
        Typeface oswald_regular = Typeface.createFromAsset(getActivity().getAssets(), "fonts/KrinkesDecorPERSONAL.ttf");

        tv_header.setTypeface(oswald_regular);

        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        HomeActivity.currentFragment="RouletteHomeFragment";
    }

}
