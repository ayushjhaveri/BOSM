package bitspilani.bosm.roulette;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import bitspilani.bosm.HomeActivity;
import bitspilani.bosm.R;
import bitspilani.bosm.adapters.AdapterRoulette;
import bitspilani.bosm.items.ItemRoulette;
import bitspilani.bosm.utils.Constant;


public class RouletteMainFragment extends Fragment {
    private ArrayList<ItemRoulette> rouletteArrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private AdapterRoulette mAdapter;
    private ProgressBar progressBar;
    private  RelativeLayout rl_filled, rl_empty;

    public RouletteMainFragment() {
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
        View view= inflater.inflate(R.layout.fragment_roulette_main, container, false);
        progressBar=(ProgressBar)view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        rl_filled = (RelativeLayout)view.findViewById(R.id.rl_filled);
        rl_empty = (RelativeLayout)view.findViewById(R.id.rl_empty);
        rl_filled.setVisibility(View.VISIBLE);
        rl_empty.setVisibility(View.GONE);
        @SuppressLint("StaticFieldLeak") AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressBar.setVisibility(View.VISIBLE);

            }

            @Override
            protected Object doInBackground(Object[] objects) {

                try {
                    Thread.sleep(Constant.sleep);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                if(progressBar.getVisibility()==View.VISIBLE){
                    rl_filled.setVisibility(View.GONE);
                    rl_empty.setVisibility(View.VISIBLE);
                }
                progressBar.setVisibility(View.GONE);
            }
        };
        asyncTask.execute();

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_roulette);

        TextView tv_header = (TextView) view.findViewById(R.id.tv_header);
        Typeface oswald_regular = Typeface.createFromAsset(getActivity().getAssets(), "fonts/RobotoCondensed-Bold.ttf");

        tv_header.setTypeface(oswald_regular);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Query query = db.collection("scores").whereEqualTo("item_type",1).whereEqualTo("match_type",1).whereEqualTo("is_roulette",true).orderBy("timestamp");

        mAdapter = new AdapterRoulette(getContext(),query, progressBar, rl_filled, rl_empty);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        return view;
    }



    @Override
    public void onStart() {
        super.onStart();

        // Start listening for Firestore updates
        if (mAdapter != null) {
            mAdapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAdapter != null) {
            mAdapter.stopListening();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        HomeActivity.currentFragment="RouletteHomeFragment";
    }
}
