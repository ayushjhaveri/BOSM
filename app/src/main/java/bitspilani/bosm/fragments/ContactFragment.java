package bitspilani.bosm.fragments;


import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import bitspilani.bosm.HomeActivity;
import bitspilani.bosm.R;
import bitspilani.bosm.adapters.AdapterContactus;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment {


    public ContactFragment() {
        HomeActivity.currentFragment.equals("ContactFragment");
        // Required empty public constructor
    }
    RecyclerView recyclerView;
    Context context;
    ArrayList<String> post, name, number, mail;
    Activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_contact, container, false);
        context=getContext();
        activity =getActivity();

        TextView tv_header = (TextView) rootView.findViewById(R.id.tv_header);
        Typeface oswald_regular = Typeface.createFromAsset(getActivity().getAssets(), "fonts/RobotoCondensed-Bold.ttf");
        tv_header.setTypeface(oswald_regular);

        //INTIALIZING
        post=new ArrayList<>();
        name=new ArrayList<>();
        number=new ArrayList<>();
        mail=new ArrayList<>();

        //ADDING ENTRIES
        name.add("Surakshit Soni");
        post.add("Sports Secretary");
        number.add("+91-9001436050");
        mail.add("sportssecretary@bits-bosm.org");

        name.add("Samiran");
        post.add("Joint Sports Secretary");
        number.add("+91-7740800430");
        mail.add("sportscouncil@gmail.com");

        name.add("Mrudula Nagesh ");
        post.add("Joint Sports Secretary");
        number.add("+91-8826034708");
        mail.add("sportscouncil@gmail.com");

        post.add("Joint Sports Secretary");
        name.add("Harsh Bhoot");
        number.add("+91-8527741418");
        mail.add("sportscouncil@gmail.com");

        post.add("Publications and Correspondence");
        name.add("Devansh Ghatak");
        number.add("+91-7987023229");
        mail.add("pcr@bits-bosm.org");

        post.add("Sponsorship and Marketing");
        name.add("Harshoman Sinha");
        number.add("+91-8290798880");
        mail.add("sponsorship@bits-bosm.org");
        

        post.add("Reception and Accomodation");
        name.add("Devashish Bonde");
        number.add("+91-8779091634");
        mail.add("recnacc@bits-bosm.org");
        
        post.add("BOSM Controls");
        name.add("Vishnu Raj");
        number.add("+91-9829946080");
        mail.add("controls@bits-bosm.org");


        //SETUP RECYCLER VIEW
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        //SETTING ADAPTER
        RecyclerView.Adapter adapter = new AdapterContactus(activity,post,name,number,mail);
        recyclerView.setAdapter(adapter);

        return rootView;
    }
    @Override
    public void onResume() {
        super.onResume();
        HomeActivity.currentFragment="ContactFragment";
    }
}
