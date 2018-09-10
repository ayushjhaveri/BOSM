package bitspilani.bosm;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import bitspilani.bosm.adapters.AdapterOrder;
import bitspilani.bosm.adapters.AdapterStalls;
import bitspilani.bosm.adapters.AdapterWalletHistory;
import bitspilani.bosm.fragments.CurrentSportFragment;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class OrderDetailsActivity extends Fragment {

    private ProgressBar progressBar;
    AdapterOrder adapterOrder;
    private int orderUniqueId;

    public static OrderDetailsActivity newInstance(int param1) {
        OrderDetailsActivity fragment = new OrderDetailsActivity();
        Bundle args = new Bundle();
        args.putInt("oui", param1);
        fragment.setArguments(args);
        HomeActivity.currentFragment = "OrderDetailsActivity";
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orderUniqueId = -1;
        if (getArguments() != null) {
            orderUniqueId = getArguments().getInt("oui");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.activity_order_details, container, false);
        init(rootView);
        progressBar.setVisibility(View.VISIBLE);


        FirebaseFirestore db = FirebaseFirestore.getInstance();



        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        if(user==)



        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        Query mQuery = db.collection("orders").document(user.getUid()).collection(String.valueOf(orderUniqueId));

        adapterOrder = new AdapterOrder(getActivity(),mQuery, progressBar);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterOrder);


        return rootView;

    }

    private void init(View rootView){
        progressBar=(ProgressBar) rootView.findViewById(R.id.progressBar);


        Typeface oswald_regular = Typeface.createFromAsset(getActivity().getAssets(),"fonts/KrinkesDecorPERSONAL.ttf");
        TextView tv_title = (TextView)rootView.findViewById(R.id.tv_title);
//        tv_title.setTypeface(oswald_regular);
        tv_title.setText("Order #" + orderUniqueId);

    }

    @Override
    public void onStart() {
        super.onStart();

        // Start listening for Firestore updates
        if (adapterOrder != null) {
            adapterOrder.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (adapterOrder != null) {
            adapterOrder.stopListening();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        HomeActivity.currentFragment="OrderDetailsActivity";
    }


}
