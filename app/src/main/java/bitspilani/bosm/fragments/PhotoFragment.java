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

import bitspilani.bosm.R;
import bitspilani.bosm.adapters.AdapterPhotos;


public class PhotoFragment extends Fragment {

    public PhotoFragment() {
        // Required empty public constructor
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

        phototUrlArrayList.add("https://upload.wikimedia.org/wikipedia/commons/thumb/9/92/Youth-soccer-indiana.jpg/1200px-Youth-soccer-indiana.jpg");
        phototUrlArrayList.add("https://static.toiimg.com/thumb/imgsize-458724,msid-65658548,width-650,resizemode-4/65658548.jpg");
        phototUrlArrayList.add("https://guardian.ng/wp-content/uploads/2016/06/Sports.jpg");
        phototUrlArrayList.add("https://upload.wikimedia.org/wikipedia/commons/thumb/9/92/Youth-soccer-indiana.jpg/1200px-Youth-soccer-indiana.jpg");
        phototUrlArrayList.add("https://static.toiimg.com/thumb/imgsize-458724,msid-65658548,width-650,resizemode-4/65658548.jpg");
        phototUrlArrayList.add("https://guardian.ng/wp-content/uploads/2016/06/Sports.jpg");
        phototUrlArrayList.add("https://upload.wikimedia.org/wikipedia/commons/thumb/9/92/Youth-soccer-indiana.jpg/1200px-Youth-soccer-indiana.jpg");
        phototUrlArrayList.add("https://static.toiimg.com/thumb/imgsize-458724,msid-65658548,width-650,resizemode-4/65658548.jpg");
        phototUrlArrayList.add("https://guardian.ng/wp-content/uploads/2016/06/Sports.jpg");
        phototUrlArrayList.add("https://upload.wikimedia.org/wikipedia/commons/thumb/9/92/Youth-soccer-indiana.jpg/1200px-Youth-soccer-indiana.jpg");
        phototUrlArrayList.add("https://static.toiimg.com/thumb/imgsize-458724,msid-65658548,width-650,resizemode-4/65658548.jpg");
        phototUrlArrayList.add("https://guardian.ng/wp-content/uploads/2016/06/Sports.jpg");
        phototUrlArrayList.add("https://upload.wikimedia.org/wikipedia/commons/thumb/9/92/Youth-soccer-indiana.jpg/1200px-Youth-soccer-indiana.jpg");
        phototUrlArrayList.add("https://static.toiimg.com/thumb/imgsize-458724,msid-65658548,width-650,resizemode-4/65658548.jpg");
        phototUrlArrayList.add("https://guardian.ng/wp-content/uploads/2016/06/Sports.jpg");
        phototUrlArrayList.add("https://upload.wikimedia.org/wikipedia/commons/thumb/9/92/Youth-soccer-indiana.jpg/1200px-Youth-soccer-indiana.jpg");
        phototUrlArrayList.add("https://static.toiimg.com/thumb/imgsize-458724,msid-65658548,width-650,resizemode-4/65658548.jpg");
        phototUrlArrayList.add("https://guardian.ng/wp-content/uploads/2016/06/Sports.jpg");

        mAdapter = new AdapterPhotos(getContext(),phototUrlArrayList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        return view;
    }

}
