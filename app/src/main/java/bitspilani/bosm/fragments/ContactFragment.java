package bitspilani.bosm.fragments;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        //INTIALIZING
        post=new ArrayList<>();
        name=new ArrayList<>();
        number=new ArrayList<>();
        mail=new ArrayList<>();

        //ADDING ENTRIES
        name.add("Jayshil");
        post.add("Sports Secretary");
        number.add("+91-9828623535");
        mail.add("sportssecretary@bits-bosm.org");

        name.add("Siddharth");
        post.add("Joint Sports Secretary");
        number.add("+91-7733974342");
        mail.add("siddharth@bits-bosm.org");

        name.add("Aman");
        post.add("Joint Sports Secretary");
        number.add("+91-9714540571");
        mail.add("aman@bits-bosm.org");

        post.add("Joint Sports Secretary");
        name.add("Shreshtha");
        number.add("+91-9873240714");
        mail.add("shreshtha@bits-bosm.org");

        post.add("Publications and Correspondence");
        name.add("Akshay");
        number.add("+91-9929022741");
        mail.add("pcr@bits-bosm.org");

        post.add("Sponsorship and Marketing");
        name.add("Jayesh");
        number.add("+91-8897716880");
        mail.add("sponsorship@bits-bosm.org");
        

        post.add("Reception and Accomodation");
        name.add("Gautham");
        number.add("+91-9444637124");
        mail.add("recnacc@bits-bosm.org");
        
        post.add("BOSM Controls");
        name.add("Pavan");
        number.add("+91-9828629266");
        mail.add("controls@bits-bosm.org");


        //SETUP RECYCLER VIEW
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
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
