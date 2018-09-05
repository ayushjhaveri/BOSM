package bitspilani.bosm.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import java.util.ArrayList;

import bitspilani.bosm.HomeActivity;
import bitspilani.bosm.R;
import bitspilani.bosm.adapters.AdapterPhotos;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;


public class PhotoFragment extends Fragment {

    public PhotoFragment() {
        // Required empty public constructor
        HomeActivity.currentFragment = "PhotoFragment";
    }

    RecyclerView recyclerView;
    AdapterPhotos mAdapter;
    ArrayList<String> phototUrlArrayList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_photo, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        phototUrlArrayList.add("https://i1.wp.com/digital-photography-school.com/wp-content/uploads/2013/07/ar-05.jpg?ssl=1");
        phototUrlArrayList.add("https://static1.squarespace.com/static/54c50337e4b07b213a53ab66/57545205746fb93a8a546428/57545224746fb93a8a5464e9/1465143847101/logatec+smaller+watermark-3-2.jpg");
        phototUrlArrayList.add("http://coxnewstoday.com/wp-content/uploads/2017/10/022208jel_kalerkantho-2017-3-2.jpg");
        phototUrlArrayList.add("https://i1.wp.com/digital-photography-school.com/wp-content/uploads/2013/07/ar-05.jpg?ssl=1");
        phototUrlArrayList.add("https://static1.squarespace.com/static/54c50337e4b07b213a53ab66/57545205746fb93a8a546428/57545224746fb93a8a5464e9/1465143847101/logatec+smaller+watermark-3-2.jpg");
        phototUrlArrayList.add("http://coxnewstoday.com/wp-content/uploads/2017/10/022208jel_kalerkantho-2017-3-2.jpg");

        phototUrlArrayList.add("https://i1.wp.com/digital-photography-school.com/wp-content/uploads/2013/07/ar-05.jpg?ssl=1");
        phototUrlArrayList.add("https://static1.squarespace.com/static/54c50337e4b07b213a53ab66/57545205746fb93a8a546428/57545224746fb93a8a5464e9/1465143847101/logatec+smaller+watermark-3-2.jpg");
        phototUrlArrayList.add("http://coxnewstoday.com/wp-content/uploads/2017/10/022208jel_kalerkantho-2017-3-2.jpg");

        phototUrlArrayList.add("https://i1.wp.com/digital-photography-school.com/wp-content/uploads/2013/07/ar-05.jpg?ssl=1");
        phototUrlArrayList.add("https://static1.squarespace.com/static/54c50337e4b07b213a53ab66/57545205746fb93a8a546428/57545224746fb93a8a5464e9/1465143847101/logatec+smaller+watermark-3-2.jpg");
        phototUrlArrayList.add("http://coxnewstoday.com/wp-content/uploads/2017/10/022208jel_kalerkantho-2017-3-2.jpg");

        phototUrlArrayList.add("https://i1.wp.com/digital-photography-school.com/wp-content/uploads/2013/07/ar-05.jpg?ssl=1");
        phototUrlArrayList.add("https://static1.squarespace.com/static/54c50337e4b07b213a53ab66/57545205746fb93a8a546428/57545224746fb93a8a5464e9/1465143847101/logatec+smaller+watermark-3-2.jpg");
        phototUrlArrayList.add("http://coxnewstoday.com/wp-content/uploads/2017/10/022208jel_kalerkantho-2017-3-2.jpg");

        phototUrlArrayList.add("https://i1.wp.com/digital-photography-school.com/wp-content/uploads/2013/07/ar-05.jpg?ssl=1");
        phototUrlArrayList.add("https://static1.squarespace.com/static/54c50337e4b07b213a53ab66/57545205746fb93a8a546428/57545224746fb93a8a5464e9/1465143847101/logatec+smaller+watermark-3-2.jpg");
        phototUrlArrayList.add("http://coxnewstoday.com/wp-content/uploads/2017/10/022208jel_kalerkantho-2017-3-2.jpg");

        phototUrlArrayList.add("https://i1.wp.com/digital-photography-school.com/wp-content/uploads/2013/07/ar-05.jpg?ssl=1");
        phototUrlArrayList.add("https://static1.squarespace.com/static/54c50337e4b07b213a53ab66/57545205746fb93a8a546428/57545224746fb93a8a5464e9/1465143847101/logatec+smaller+watermark-3-2.jpg");
        phototUrlArrayList.add("http://coxnewstoday.com/wp-content/uploads/2017/10/022208jel_kalerkantho-2017-3-2.jpg");

        phototUrlArrayList.add("https://i1.wp.com/digital-photography-school.com/wp-content/uploads/2013/07/ar-05.jpg?ssl=1");
        phototUrlArrayList.add("https://static1.squarespace.com/static/54c50337e4b07b213a53ab66/57545205746fb93a8a546428/57545224746fb93a8a5464e9/1465143847101/logatec+smaller+watermark-3-2.jpg");
        phototUrlArrayList.add("http://coxnewstoday.com/wp-content/uploads/2017/10/022208jel_kalerkantho-2017-3-2.jpg");




        mAdapter = new AdapterPhotos(getContext(),phototUrlArrayList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(mAdapter);
        alphaAdapter.setDuration(1000);
        alphaAdapter.setFirstOnly(false);
        recyclerView.setAdapter(alphaAdapter);
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        HomeActivity.currentFragment="PhotoFragment";
    }

}
