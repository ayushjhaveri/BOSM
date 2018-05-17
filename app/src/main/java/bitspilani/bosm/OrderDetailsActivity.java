package bitspilani.bosm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import bitspilani.bosm.adapters.AdapterWalletHistory;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class OrderDetailsActivity extends AppCompatActivity {
    Toolbar toolbar;
    private TextView tv_order_unique_id, tv_timestamp,tv_amount;
    double amount=0;
    String order_unique_id="",timestamp="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        init();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Order Details");

        Intent intent = getIntent();
        if(intent!=null){
            amount = intent.getDoubleExtra("amount",0);
            order_unique_id = intent.getStringExtra("order_unique_id");
            timestamp = intent.getStringExtra("timestamp");
        }
        if(amount>=0){
            //added to wallet
            tv_amount.setText(getResources().getString(R.string.Rs)+" "+amount+"");
        }else if(amount<0){
            //paid from wallet
            tv_amount.setText(getResources().getString(R.string.Rs)+" "+(-1*amount)+"");
        }

        tv_order_unique_id.setText(order_unique_id);
        tv_timestamp.setText(timestamp);

    }
    private void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_top);
        tv_amount=(TextView)findViewById(R.id.tv_amount);
        tv_order_unique_id=(TextView)findViewById(R.id.tv_order_unique_id);
        tv_timestamp=(TextView)findViewById(R.id.tv_timestamp);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}
