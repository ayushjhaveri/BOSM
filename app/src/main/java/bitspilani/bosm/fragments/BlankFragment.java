package bitspilani.bosm.fragments;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.transition.Fade;
import android.support.transition.TransitionInflater;
import android.support.transition.TransitionSet;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import bitspilani.bosm.HomeActivity;
import bitspilani.bosm.LoginActivity;
import bitspilani.bosm.R;
import bitspilani.bosm.adapters.AdapterStalls;
import bitspilani.bosm.items.ItemStall;
import bitspilani.bosm.items.ItemUser;
import bitspilani.bosm.utils.Constant;
//import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends Fragment {

    private static final String TAG = "FragmentStall";
    ListView listview_stalls;
    AdapterStalls adapterStalls;
    ArrayList<ItemStall> stallArrayList;
    private ProgressBar progressBar;

    public BlankFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_blank, container, false);
        init(rootView);

        adapterStalls = new AdapterStalls(getContext(),stallArrayList,getActivity());
        listview_stalls.setAdapter(adapterStalls);

        listview_stalls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                TextView tv = (TextView)view.findViewById(R.id.tv_stall_name);
                Constant.CURRENT_STALL_ID = stallArrayList.get(i).getStall_id();
                Constant.CURRENT_STALL_NAME = stallArrayList.get(i).getName();

                Fragment fragment = new FragmentFoodItems();

//                // 1. Exit for Previous Fragment
//                Fade exitFade = new Fade();
//                exitFade.setDuration(10);
//                previousFragment.setExitTransition(exitFade);

                // 2. Shared Elements Transition
//                TransitionSet enterTransitionSet = new TransitionSet();
//                enterTransitionSet.addTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));
//                enterTransitionSet.setDuration(10);
//                enterTransitionSet.setStartDelay(10);
//                fragment.setSharedElementEnterTransition(enterTransitionSet);
//
//                // 3. Enter Transition for New Fragment
//                Fade enterFade = new Fade();
//                enterFade.setStartDelay(10 + 10);
//                enterFade.setDuration(10);
//                fragment.setEnterTransition(enterFade);
               loadFragment(fragment,tv);
            }
        });

        getStallList();

//        OverScrollDecoratorHelper.setUpOverScroll(listview_stalls);

        return rootView;
    }

    private void loadFragment(Fragment fragment, TextView tv) {
        // load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_view, fragment);
        transaction.addToBackStack("transaction");
        transaction.addSharedElement(tv, ViewCompat.getTransitionName(tv));
        transaction.commit();
    }

    private void init(View rootView){
        listview_stalls = (ListView) rootView.findViewById(R.id.lv_stalls);
        stallArrayList = new ArrayList<>();
        progressBar=(ProgressBar) rootView.findViewById(R.id.progressBar);


        Typeface oswald_regular = Typeface.createFromAsset(getContext().getAssets(),"fonts/KrinkesDecorPERSONAL.ttf");
        TextView tv_title = (TextView)rootView.findViewById(R.id.tv_stall);
        tv_title.setTypeface(oswald_regular);

    }

    public void getStallList(){
        class GetData extends AsyncTask<Void, Void, String> {
            @Override
            protected void onPreExecute() {
                progressBar.setVisibility(View.VISIBLE);
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                progressBar.setVisibility(View.GONE);
                super.onPostExecute(s);
                parseJSON(s);
            }

            @Override
            protected String doInBackground(Void... params) {

                try {
                    URL url = new URL(Constant.URL_GET_STALL_LIST);
//                    String urlParams = "email=" + account.getEmail()+
//                            "&name="+account.getDisplayName();

                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setDoOutput(true);
                    StringBuilder sb = new StringBuilder();
//
//                    OutputStream os = con.getOutputStream();
//                    os.write(urlParams.getBytes());
//                    os.flush();
//                    os.close();

                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }

                    String s = sb.toString().trim();
                    return s;

                } catch (IOException e) {
                    e.printStackTrace();
                    return "error";
                }
            }

            private void parseJSON(String json) {
                try {
//                    Toast.makeText(LoginActivity.this,json,Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Response: " + json);
                    JSONObject root = new JSONObject(json);
                    JSONObject response = root.getJSONObject("response");
                    boolean success = response.getBoolean("success");
                    if(success){
                        JSONArray jsonArray = response.getJSONArray("stall_list");
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            ItemStall itemStall = new ItemStall(
                                    jsonObject.getInt("stall_id"),
                                    jsonObject.getString("name")
                            );
                            stallArrayList.add(itemStall);
                        }
                        adapterStalls.notifyDataSetChanged();

                    }else{
                        progressBar.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(),getResources().getText(R.string.connection_error),Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }
        GetData gd = new GetData();
        gd.execute();
    }
}
