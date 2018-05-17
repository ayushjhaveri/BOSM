package bitspilani.bosm;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import bitspilani.bosm.adapters.AdapterCart;
import bitspilani.bosm.items.ItemCart;
import bitspilani.bosm.items.ItemFood;
import bitspilani.bosm.utils.Constant;
//import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

public class CartActivity extends AppCompatActivity {

    private static final String TAG = "CartActivity";
//    Toolbar toolbar;
    public static double sum=0;
    RecyclerView recyclerView;
    AdapterCart adapterCart;
    public static TextView tv_total_price,tv_items;
    private TextView tv_pay;
    private ArrayList<ItemCart> cartArrayList;
    public static RelativeLayout rl_empty_layout;
    public static ProgressBar progressBar;

//    ImageView iv_back;

    ImageButton ib_pay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        init();
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle(null);
        tv_total_price.setText(getResources().getString(R.string.Rs)+" ---");

        adapterCart = new AdapterCart(this, cartArrayList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterCart);
        getCartList();

//        tv_pay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(cartArrayList.size()>0){
//                    getWalletAmount();
//                }else{
//                    Toast.makeText(CartActivity.this,"Please enter items in the cart first!",Toast.LENGTH_SHORT).show();
//                }
//            }
//        });


        Typeface oswald_regular = Typeface.createFromAsset(getAssets(),"fonts/KrinkesDecorPERSONAL.ttf");
        TextView tv_title = (TextView)findViewById(R.id.tv_cart);
        tv_title.setTypeface(oswald_regular);

//        OverScrollDecoratorHelper.setUpOverScroll(recyclerView, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);

        ib_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cartArrayList.size()>0){
                    getWalletAmount();
                }else{
                    Toast.makeText(CartActivity.this,"Please enter items in the cart first!",Toast.LENGTH_SHORT).show();
                }
            }
        });

//        findViewById(R.id.iv_wallet).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(CartActivity.this,WalletActivity.class));
//            }
//        });

//        iv_back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onBackPressed();
//            }
//        });
//        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onBackPressed();
////                loadFrag(new BlankFragment(),"Order Food",fm);
//            }
//        });
    }

    private void init() {
//        toolbar = (Toolbar) findViewById(R.id.toolbar_top);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        rl_empty_layout = (RelativeLayout) findViewById(R.id.rl_empty_layout);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        tv_total_price=(TextView) findViewById(R.id.tv_total_price);
        tv_items=(TextView) findViewById(R.id.tv_items);
//        tv_pay=(TextView) findViewById(R.id.tv_pay);

//        iv_back = (ImageView)findViewById(R.id.iv_back);
        ib_pay = (ImageButton) findViewById(R.id.ib_pay);

        cartArrayList = new ArrayList<>();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void getCartList(){
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
                    URL url = new URL(Constant.URL_GET_USER_CART);
                    String urlParams = "user_id=" + Constant.currentItemUser.getId();

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
                        rl_empty_layout.setVisibility(View.GONE);
                        sum=0;
                        JSONArray jsonArray = response.getJSONArray("cart_list");
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            ItemCart itemFood = new ItemCart(
                                    jsonObject.getInt("cart_id"),
                                    jsonObject.getInt("food_id"),
                                    jsonObject.getInt("quantity"),
                                    jsonObject.getString("food_name"),
                                    jsonObject.getDouble("food_price")
                            );
                            sum += (itemFood.getTotalPrice());
                            cartArrayList.add(itemFood);
                        }
                        tv_total_price.setText(getResources().getString(R.string.Rs)+" "+sum+"");
                        tv_items.setText("Items("+cartArrayList.size()+")");
                        adapterCart.notifyDataSetChanged();
                    }else{
                    rl_empty_layout.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(CartActivity.this,getResources().getText(R.string.connection_error),Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }
        GetData gd = new GetData();
        gd.execute();
    }
    public void place_order(){
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
                    URL url = new URL(Constant.URL_PLACE_ORDER);
                    ArrayList<String> cart_ids = new ArrayList<>();
                    for(int i=0;i<cartArrayList.size();i++){
                        cart_ids.add(cartArrayList.get(i).getCart_id()+"");
                    }
                    String parameter = android.text.TextUtils.join(",", cart_ids);
                    Log.d(TAG,"Parameter: "+parameter);

                    String urlParams = "cart_ids=" + parameter+
                            "&amount="+sum;

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
                        //go to new activity
                        Intent intent =new Intent(CartActivity.this,OrderDetailsActivity.class);
                        intent.putExtra("amount",response.getDouble("amount"));
                        intent.putExtra("order_unique_id",response.getString("order_unique_id"));
                        Calendar calendar = Calendar.getInstance();
                        String timestamp = response.getString("timestamp");
                        String[] date_time = timestamp.split("T");
                        String[] date= date_time[0].split("-");
                        String[] time= date_time[1].split(":");
                        calendar.set(Calendar.YEAR,Integer.parseInt(date[0]));
                        calendar.set(Calendar.MONTH,Integer.parseInt(date[1]));
                        calendar.set(Calendar.DATE,Integer.parseInt(date[2]));

                        calendar.set(Calendar.HOUR,Integer.parseInt(time[0]));
                        calendar.set(Calendar.MINUTE,Integer.parseInt(time[1]));
//                            calendar.add(Calendar.SECOND,Integer.parseInt(time[2]));
                        SimpleDateFormat timeFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
                        String timestamp1 = timeFormat.format(calendar.getTime());
                        intent.putExtra("timestamp",timestamp1);
                        startActivity(intent);

                        double balance = Constant.currentItemUser.getUser_wallet();
                        balance=balance-sum;
                        CartActivity.sum = 0;
                        tv_total_price.setText(getResources().getString(R.string.Rs)+" "+sum+"");
                        tv_items.setText("");
                        cartArrayList.clear();
                        rl_empty_layout.setVisibility(View.VISIBLE);
                        adapterCart.notifyDataSetChanged();
                        Constant.currentItemUser.setUser_wallet(balance);
                        Toast.makeText(CartActivity.this,"Order placed successfully!",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(CartActivity.this,"low balance!",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(CartActivity.this,getResources().getText(R.string.connection_error),Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }
        GetData gd = new GetData();
        gd.execute();
    }

    public void getWalletAmount(){
//        Toast.makeText(this,"start",Toast.LENGTH_SHORT).show();
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
                    URL url = new URL(Constant.URL_GET_WALLET);
                    String urlParams = "email=" + Constant.currentItemUser.getUser_email();

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
//                   Toast.makeText(WalletActivity.this,json,Toast.LENGTH_SHORT).show();
                    com.paytm.pgsdk.Log.d(TAG, "Response: " + json);
                    JSONObject root = new JSONObject(json);
                    JSONObject response = root.getJSONObject("response");
                    boolean success = response.getBoolean("success");
                    if(success){
                        double wallet = response.getDouble("balance");
//                        Toast.makeText(CartActivity.this,"sum :"+sum,Toast.LENGTH_SHORT).show();
                        if(sum<=wallet){
                            Toast.makeText(CartActivity.this,"Placing your order!",Toast.LENGTH_SHORT).show();
                            place_order();
                        }else{
                            //redirect to adding wallet
                            double amount=sum-wallet;
                            Intent intent = new Intent(CartActivity.this,AddMoneyBActivity.class);
                            intent.putExtra("amount",amount);
                            startActivity(intent);

                        }
                        Constant.currentItemUser.setUser_wallet(wallet);
                    }else{
                        Toast.makeText(CartActivity.this,getResources().getString(R.string.connection_error),Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        GetData gd = new GetData();
        gd.execute();
    }
}
