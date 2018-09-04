package bitspilani.bosm.roulette;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        return inflater.inflate(R.layout.fragment_roulette_extra, container, false);
    }
    @Override
    public void onResume() {
        super.onResume();
        HomeActivity.currentFragment="RouletteHomeFragment";
    }

}
