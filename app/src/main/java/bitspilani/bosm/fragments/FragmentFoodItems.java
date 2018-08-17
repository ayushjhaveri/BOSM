package bitspilani.bosm.fragments;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.transition.TransitionInflater;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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

import bitspilani.bosm.CartActivity;
import bitspilani.bosm.R;
import bitspilani.bosm.WalletActivity;
import bitspilani.bosm.adapters.AdapterFoods;
import bitspilani.bosm.adapters.AdapterStalls;
import bitspilani.bosm.items.ItemFood;
import bitspilani.bosm.items.ItemStall;
import bitspilani.bosm.utils.Constant;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentFoodItems extends Fragment {


    private static final String TAG = "FragmentFoodItems";

    public FragmentFoodItems() {
        // Required empty public constructor
    }

    ImageButton ib_cart;
    ListView listview_food_items;
    TextView tv_stall_name;
    AdapterFoods adapterFoods;
    ArrayList<ItemFood> foodArrayList;
    @SuppressLint("StaticFieldLeak")
    public static ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_food_items, container, false);
        init(rootView);


        adapterFoods = new AdapterFoods(getContext(),foodArrayList);
        listview_food_items.setAdapter(adapterFoods);

        getFoodItemList();

        tv_stall_name.setText(Constant.CURRENT_STALL_NAME);

        listview_food_items.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });





        return rootView;
    }

    private void init(View rootView){
        listview_food_items = (ListView) rootView.findViewById(R.id.lv_food_items);
        foodArrayList = new ArrayList<>();
        tv_stall_name = (TextView)rootView.findViewById(R.id.tv_stall_name);
        progressBar=(ProgressBar) rootView.findViewById(R.id.progressBar);
        Typeface oswald_regular = Typeface.createFromAsset(getContext().getAssets(),"fonts/KrinkesDecorPERSONAL.ttf");
        tv_stall_name.setTypeface(oswald_regular);


        //imagebutton for cart
        ib_cart = (ImageButton) rootView.findViewById(R.id.ib_cart);
        ib_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), CartActivity.class));
            }
        });

        //fab for wallet
        FloatingActionButton fab_wallet = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab_wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), WalletActivity.class));
            }
        });

    }

    public void getFoodItemList(){
        class GetData extends AsyncTask<Void, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressBar.setVisibility(View.VISIBLE);
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
                    URL url = new URL(Constant.URL_GET_FOOD_ITEM_LIST);
                    String urlParams = "stall_id=" + Constant.CURRENT_STALL_ID;

                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setDoOutput(true);
                    StringBuilder sb = new StringBuilder();
//
                    OutputStream os = con.getOutputStream();
                    os.write(urlParams.getBytes());
                    os.flush();
                    os.close();

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
                        JSONArray jsonArray = response.getJSONArray("food_item_list");
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            ItemFood itemFood = new ItemFood(
                                    jsonObject.getInt("food_id"),
                                    jsonObject.getString("name"),
                                    jsonObject.getDouble("price")
                            );
                            foodArrayList.add(itemFood);
                        }
                        adapterFoods.notifyDataSetChanged();

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
