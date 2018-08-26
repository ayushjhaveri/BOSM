package bitspilani.bosm;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.paytm.pgsdk.Log;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

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
import java.util.HashMap;
import java.util.Map;

import bitspilani.bosm.adapters.AdapterWalletHistory;
import bitspilani.bosm.items.ItemWalletHistory;
import bitspilani.bosm.utils.Constant;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class WalletActivity extends AppCompatActivity {

    private static final String TAG = "WalletActivity";
    Toolbar toolbar;
    TextView textView_balance;
    StickyListHeadersListView stickyList_history;
    ArrayList<ItemWalletHistory> walletHistoryArrayList;
    private RelativeLayout relativeLayout_add_money;
    private ProgressBar progressBar, progressBar2;
    AdapterWalletHistory adapter;
    private RelativeLayout rl_empty_layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        init();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle(null);

        getWalletAmount();
        getTransactionHistory();

        relativeLayout_add_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WalletActivity.this, AddMoneyBActivity.class));
            }
        });
        stickyList_history.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent =new Intent(WalletActivity.this,OrderDetailsActivity.class);
                intent.putExtra("amount",walletHistoryArrayList.get(i).getAmount());
                intent.putExtra("order_unique_id",walletHistoryArrayList.get(i).getOrder_id());
                SimpleDateFormat timeFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
                String timestamp = timeFormat.format(walletHistoryArrayList.get(i).getDate().getTime());
                intent.putExtra("timestamp",timestamp);
                startActivity(intent);
            }
        });

        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
//                loadFrag(new StallFragment(),"Order Food",fm);
            }
        });

        Typeface oswald_regular = Typeface.createFromAsset(getAssets(),"fonts/KrinkesDecorPERSONAL.ttf");
        TextView tv_title = (TextView)findViewById(R.id.tv_title);
        tv_title.setTypeface(oswald_regular);

    }

    private void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_top);
        textView_balance = (TextView) findViewById(R.id.tv_balance);

        stickyList_history = (StickyListHeadersListView) findViewById(R.id.lv_recent);
        walletHistoryArrayList = new ArrayList<>();
        adapter = new AdapterWalletHistory(this, walletHistoryArrayList);
        stickyList_history.setAdapter(adapter);

        relativeLayout_add_money = (RelativeLayout) findViewById(R.id.rl_2);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar2 = (ProgressBar)findViewById(R.id.progressBar2);
        rl_empty_layout= (RelativeLayout)findViewById(R.id.rl_empty_layout);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
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
                    Log.d(TAG, "Response: " + json);
                    JSONObject root = new JSONObject(json);
                    JSONObject response = root.getJSONObject("response");
                    boolean success = response.getBoolean("success");
                    if(success){
                        double wallet = response.getDouble("balance");
                        Constant.currentItemUser.setUser_wallet(wallet);
                        textView_balance.setText(getResources().getString(R.string.Rs) + " "+ wallet);
                    }else{
                        textView_balance.setText(getResources().getString(R.string.Rs)+" ---");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        GetData gd = new GetData();
        gd.execute();
    }

    public void getTransactionHistory(){
//        Toast.makeText(this,"start",Toast.LENGTH_SHORT).show();
        class GetData extends AsyncTask<Void, Void, String> {
            @Override
            protected void onPreExecute() {
                progressBar2.setVisibility(View.VISIBLE);
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                progressBar2.setVisibility(View.GONE);
                super.onPostExecute(s);
                parseJSON(s);
            }

            @Override
            protected String doInBackground(Void... params) {

                try {
                    URL url = new URL(Constant.URL_GET_WALLET_HISTORY);
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
//                    Toast.makeText(WalletActivity.this,json,Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Response: " + json);
                    JSONObject root = new JSONObject(json);
                    JSONObject response = root.getJSONObject("response");
                    boolean success = response.getBoolean("success");
                    if(success){
                        JSONArray transactionArray = response.getJSONArray("transaction_list");
                        for(int i=0;i<transactionArray.length();i++){
                            JSONObject jsonObject = transactionArray.getJSONObject(i);
                            Calendar calendar = Calendar.getInstance();
                            String timestamp = jsonObject.getString("timestamp");
                            String[] date_time = timestamp.split("T");
                            String[] date= date_time[0].split("-");
                            String[] time= date_time[1].split(":");
                            calendar.set(Calendar.YEAR,Integer.parseInt(date[0]));
                            calendar.set(Calendar.MONTH,Integer.parseInt(date[1]));
                            calendar.set(Calendar.DATE,Integer.parseInt(date[2]));

                            calendar.set(Calendar.HOUR,Integer.parseInt(time[0]));
                            calendar.set(Calendar.MINUTE,Integer.parseInt(time[1]));
//                            calendar.add(Calendar.SECOND,Integer.parseInt(time[2]));

                            ItemWalletHistory itemWalletHistory = new ItemWalletHistory(
                                    jsonObject.getInt( "transaction_id"),
                                    jsonObject.getInt("user_id"),
                                    calendar,
                                    jsonObject.getDouble("amount"),
                                    jsonObject.getString("order_unique_id"),
                                    jsonObject.getString("from"),
                                    jsonObject.getString("to"),
                                    jsonObject.getString("remarks"));
                            walletHistoryArrayList.add(itemWalletHistory);
                        }
                        adapter.notifyDataSetChanged();
                        rl_empty_layout.setVisibility(View.GONE);
                    }else{
                        rl_empty_layout.setVisibility(View.VISIBLE);
//                        textView_balance.setText(getResources().getString(R.string.Rs)+" ---");
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
