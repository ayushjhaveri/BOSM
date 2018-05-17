package bitspilani.bosm.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import bitspilani.bosm.R;
import bitspilani.bosm.items.ItemWalletHistory;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by Prashant on 4/5/2018.
 */

public class AdapterWalletHistory extends BaseAdapter implements StickyListHeadersAdapter {

    private ArrayList<ItemWalletHistory> arrayList;
    private LayoutInflater inflater;
    private Context context;

    public AdapterWalletHistory(Context context, ArrayList<ItemWalletHistory> arrayList) {
        inflater = LayoutInflater.from(context);
        this.arrayList = arrayList;
        this.context=context;
    }


    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        //Inflating row
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.layout_wallet_history_row, parent, false);

            holder.textView_amount = (TextView) convertView.findViewById(R.id.tv_amount);
            holder.textView_header = (TextView) convertView.findViewById(R.id.tv_header);
            holder.textView_comment = (TextView) convertView.findViewById(R.id.tv_comment);
            holder.textView_time = (TextView) convertView.findViewById(R.id.tv_time);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if(arrayList.get(position).getAmount()>=0){
            holder.textView_header.setText("Added to Wallet");
            holder.textView_amount.setTextColor(ContextCompat.getColor(context,R.color.colorPosAmount));
            holder.textView_amount.setText( "+ "+context.getResources().getString(R.string.Rs)+" "+arrayList.get(position).getAmount()+"");
        }else{
            holder.textView_header.setText("Paid for Order");
            holder.textView_amount.setTextColor(ContextCompat.getColor(context,R.color.colorNegAmount));
            holder.textView_amount.setText( "- "+context.getResources().getString(R.string.Rs)+" "+ (-1*arrayList.get(position).getAmount())+"");
        }
        holder.textView_comment.setText("#"+arrayList.get(position).getOrder_id());
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");

        holder.textView_time.setText(timeFormat.format(arrayList.get(position).getDate().getTime()));

        return convertView;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;
        //Inflating row header
        if (convertView == null) {
            holder = new HeaderViewHolder();
            convertView = inflater.inflate(R.layout.layout_wallet_history_row_header, parent, false);
            holder.textView_transaction_date = (TextView) convertView.findViewById(R.id.tv_transaction_date);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String text = dateFormat.format(arrayList.get(position).getDate().getTime());
        holder.textView_transaction_date.setText(text);
        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        //return the first character of the country as ID because this is what headers are based upon
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
//        String text = dateFormat.format(arrayList.get(position).getDate().getTime());
        return arrayList.get(position).getDate().get(Calendar.DATE);
    }

    class HeaderViewHolder {
        TextView textView_transaction_date;
    }

    class ViewHolder {
        TextView textView_amount, textView_header, textView_comment, textView_time;
    }

}