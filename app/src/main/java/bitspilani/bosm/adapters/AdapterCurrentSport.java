package bitspilani.bosm.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import bitspilani.bosm.CurrentSportActivity;
import bitspilani.bosm.R;
import bitspilani.bosm.items.ItemCurrentSport;

/**
 * Created by Prashant on 4/7/2018.
 */

public class AdapterCurrentSport extends RecyclerView.Adapter<AdapterCurrentSport.ViewHolder> {

    private ArrayList<ItemCurrentSport> arrayList;
    private Context context;

    private static final String TAG = "AdapterCart";

    public AdapterCurrentSport(Context context, ArrayList<ItemCurrentSport> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_current_sport, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final ItemCurrentSport itemCurrentSport = arrayList.get(position);
        if(CurrentSportActivity.map.containsKey(itemCurrentSport.getMatch_date())){

            holder.rl_top.setVisibility(View.GONE);
        }else{
            CurrentSportActivity.map.put(itemCurrentSport.getMatch_date(),position);
            holder.rl_top.setVisibility(View.VISIBLE);
            holder.tv_sort_title.setText(itemCurrentSport.getMatch_date());
        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout rl_top;
        TextView tv_sort_title;

        public ViewHolder(View itemView) {
            super(itemView);
            rl_top = (RelativeLayout)itemView.findViewById(R.id.rl_top);
            tv_sort_title = (TextView)itemView.findViewById(R.id.tv_sort_title);
//            textView_name = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }

}
