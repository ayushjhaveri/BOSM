package bitspilani.bosm;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import bitspilani.bosm.items.ItemUser;
import bitspilani.bosm.utils.Constant;

public class AddMoneyBActivity extends AppCompatActivity {

    private static final String TAG = "AddMoneyBActivity";

    private Toolbar toolbar;
    private TextView textView_balance, textView_add_amount1, textView_add_amount2, textView_add_amount3;
    private EditText editText_amount;
//    Button button_add;
    private ProgressBar progressBar, progressBar2;
    ImageButton ib_add;
    String checkSum="";
    String amount= "0";
    String ORDER_ID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_money_b);
        init();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Add Money To Wallet");
        getWalletAmount();

        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        textView_add_amount1.setText("+ " + getResources().getString(R.string.Rs) + "50");
        textView_add_amount2.setText("+ " + getResources().getString(R.string.Rs) + "100");
        textView_add_amount3.setText("+ " + getResources().getString(R.string.Rs) + "200");
        editText_amount.setHint("Enter Amount(" + getResources().getString(R.string.Rs) + ")");

        textView_add_amount1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText_amount.getText().toString().isEmpty()) {
                    float initial =50;
                    editText_amount.setText(initial + "");
                } else {
                    float initial = Float.parseFloat(editText_amount.getText().toString());
                    initial += 50;
                    editText_amount.setText(initial + "");
                }
            }
        });
        textView_add_amount2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText_amount.getText().toString().isEmpty()) {
                    float initial =100;
                    editText_amount.setText(initial + "");
                } else {
                    float initial = Float.parseFloat(editText_amount.getText().toString());
                    initial += 100;
                    editText_amount.setText(initial + "");
                }
            }
        });
        textView_add_amount3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText_amount.getText().toString().isEmpty()) {
                    float initial =200;
                    editText_amount.setText(initial + "");
                } else {
                    float initial = Float.parseFloat(editText_amount.getText().toString());
                    initial += 200;
                    editText_amount.setText(initial + "");
                }
            }
        });

        ib_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText_amount.getText().toString().isEmpty() || Float.parseFloat(editText_amount.getText().toString())<=0){
                    Snackbar.make(view,"Enter valid amount!",Snackbar.LENGTH_SHORT).show();
                }else{
//                    if (Constant.ACCOUNT_TYPE == Constant.ACCOUNT_TYPE_BITS) {
//                        add_amount();
//                    } else if (Constant.ACCOUNT_TYPE == Constant.ACCOUNT_TYPE_NON_BITS) {
                        amount = editText_amount.getText().toString();
                        ORDER_ID = Constant.random();
                        Log.d(TAG,"ORDER ID: "+ORDER_ID);
                        onStartTransaction();
//                    }
                }
            }
        });

        Intent intent = getIntent();
        if(intent!=null){
            double amount = intent.getDoubleExtra("amount",0);
            editText_amount.setText(amount+"");
        }
        Typeface oswald_regular = Typeface.createFromAsset(getAssets(),"fonts/KrinkesDecorPERSONAL.ttf");
        TextView tv_title = (TextView)findViewById(R.id.tv_title);
        tv_title.setTypeface(oswald_regular);
    }

    private void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_top);
        textView_balance = (TextView) findViewById(R.id.tv_wallet_balance);
        textView_add_amount1 = (TextView) findViewById(R.id.textView3);
        textView_add_amount2 = (TextView) findViewById(R.id.textView2);
        textView_add_amount3 = (TextView) findViewById(R.id.textView4);
        editText_amount = (EditText) findViewById(R.id.et_amount);
//        button_add = (Button) findViewById(R.id.bt_add);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar2 = (ProgressBar) findViewById(R.id.progressBar2);
        ib_add =(ImageButton) findViewById(R.id.ib_add);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void add_amount(final String amount){
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
                    URL url = new URL(Constant.URL_ADD_AMOUNT_TO_WALLET);
                    String urlParams = "user_id=" + Constant.currentItemUser.getId()+
                            "&amount=" + amount;
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
//                    Toast.makeText(AddMoneyBActivity.this,json,Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Response: " + json);
                    JSONObject root = new JSONObject(json);
                    JSONObject response = root.getJSONObject("response");
                    boolean success = response.getBoolean("success");
                    final AlertDialog.Builder builder = new AlertDialog.Builder(AddMoneyBActivity.this);
                    if(success){
                        builder.setMessage(getResources().getString(R.string.Rs)+" "+editText_amount.getText().toString()+" added successfully!");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                               finish();
                            }
                        });
                        textView_balance.setText(response.getDouble("balance")+"");
                        Constant.currentItemUser.setUser_wallet(response.getDouble("balance"));
                    }else{
                        builder.setMessage("Something went wrong, Please try again!");
                        builder.setPositiveButton("OK", null);
                    }
                    builder.create();
                    builder.show();
                } catch (JSONException e) {
                    Toast.makeText(AddMoneyBActivity.this,getResources().getText(R.string.connection_error),Toast.LENGTH_SHORT).show();
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
                progressBar2.setVisibility(View.VISIBLE);
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                progressBar2.setVisibility(View.GONE);
                textView_balance.setVisibility(View.VISIBLE);
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
                        textView_balance.setText(getResources().getString(R.string.Rs) + " "+ wallet);
                        Constant.currentItemUser.setUser_wallet(wallet);
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

    public void onStartTransaction() {

        Log.i("button","button Click");
        progressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.URL_GENERATE_CHECKSUM,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.i("REsponse",response);


                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            // JSONArray result = jsonObject.getJSONArray("result");
                            //JSONObject Data = result.getJSONObject(0);
                            checkSum = jsonObject.getString("CHECKSUMHASH");
                            progressBar.setVisibility(View.GONE);
                            Log.i("checkSum",checkSum);

                            startTransaction();

                        } catch (JSONException e) {
                            progressBar.setVisibility(View.GONE);
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
//                        linlaHeaderProgress.setVisibility(View.GONE);
                        Toast.makeText(AddMoneyBActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();

                params.put("ORDER_ID", ORDER_ID);
                params.put("MID","Mobile92719464287478");
                params.put("CUST_ID", "123");
                params.put("CHANNEL_ID", "WAP");
                params.put("INDUSTRY_TYPE_ID", "Retail");
                params.put("WEBSITE", "APPSTAGING");
                params.put("TXN_AMOUNT", amount);
                params.put("CALLBACK_URL","https://pguat.paytm.com/paytmchecksum/paytmCallback.jsp");
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(AddMoneyBActivity.this);
        requestQueue.add(stringRequest);

    }

    public void startTransaction() {
//        linlaHeaderProgress.setVisibility(View.VISIBLE);
        PaytmPGService Service = PaytmPGService.getStagingService();
        Map<String, String> paramMap = new HashMap<String, String>();

        // these are mandatory parameters

        paramMap.put("ORDER_ID", ORDER_ID);
        paramMap.put("MID","Mobile92719464287478");
        paramMap.put("CUST_ID", "123");
        paramMap.put("CHANNEL_ID", "WAP");
        paramMap.put("INDUSTRY_TYPE_ID", "Retail");
        paramMap.put("WEBSITE", "APPSTAGING");
        paramMap.put("TXN_AMOUNT", amount);
        paramMap.put("CALLBACK_URL","https://pguat.paytm.com/paytmchecksum/paytmCallback.jsp");
//        paramMap.put("CHECKSUMHASH","W1Be8zRVC3GmmzbjD3grh+0X6PyJrL9jg7A2FNCxqe2C3sWOQV+mnIAo6g7RQCHwrOiU/wWK+sTFTsQwR8XxQM+vBO7Lr8/6jXZJd+F0Zww=");
        paramMap.put("CHECKSUMHASH",checkSum);

        PaytmOrder Order = new PaytmOrder(paramMap);

        Service.initialize(Order, null);

        Service.startPaymentTransaction(this, true, true,
                new PaytmPaymentTransactionCallback() {
                    @Override
                    public void onTransactionResponse(Bundle bundle) {

                        Log.d("LOG", "Payment Transaction : " + bundle);
                        Toast.makeText(getApplicationContext(), "Payment Transaction response "+bundle.toString(), Toast.LENGTH_LONG).show();
                        add_amount(amount);
//                        setResponse();
//                        Intent i = new Intent(WalletActivity.this, CrMainActivity.class);
//                        i.putExtra("payment","done");
//                        startActivity(i);

                    }

                    @Override
                    public void networkNotAvailable() {
                        Toast.makeText(getApplicationContext(), "network not available! ", Toast.LENGTH_LONG).show();
//                        linlaHeaderProgress.setVisibility(View.GONE);
                    }

                    @Override
                    public void clientAuthenticationFailed(String s) {

//                        linlaHeaderProgress.setVisibility(View.GONE);
                    }

                    @Override
                    public void someUIErrorOccurred(String s) {

//                        linlaHeaderProgress.setVisibility(View.GONE);
                    }

                    @Override
                    public void onErrorLoadingWebPage(int i, String s, String s1) {

//                        linlaHeaderProgress.setVisibility(View.GONE);
                    }

                    @Override
                    public void onBackPressedCancelTransaction() {

//                        linlaHeaderProgress.setVisibility(View.GONE);
                    }

                    @Override
                    public void onTransactionCancel(String s, Bundle bundle) {

                        Log.d("LOG", "Payment Transaction Failed " + s);
//                        linlaHeaderProgress.setVisibility(View.GONE);
                        Toast.makeText(getBaseContext(), "Payment Transaction Failed ", Toast.LENGTH_LONG).show();

                    }
                });


    }
}
