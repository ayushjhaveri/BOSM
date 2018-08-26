package bitspilani.bosm.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;

import bitspilani.bosm.R;
import bitspilani.bosm.items.ItemNotification;

public class AdapterNotifications extends RecyclerView.Adapter<AdapterNotifications.ViewHolder> {

    private ArrayList<ItemNotification> notificationArrayList;
    private Context context;

//    private static final String TAG = "AdapterCart";

    public AdapterNotifications(Context context, ArrayList<ItemNotification> eventsArrayList) {
        this.notificationArrayList = eventsArrayList;
        this.context = context;
    }

    @Override
    public AdapterNotifications.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_notification3, parent, false);

        return new AdapterNotifications.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AdapterNotifications.ViewHolder holder, final int position) {
         ItemNotification itemNotifications = notificationArrayList.get(position);

        holder.tv_name.setText(itemNotifications.getNotif_name());
        holder.tv_info.setText(itemNotifications.getnotif_info());
//        holder.tv_date.setText(itemNotifications.getNotif_date());
        holder.tv_time.setText(itemNotifications.getNotif_time());


    }

    @Override
    public int getItemCount() {
        return notificationArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_name, tv_info, tv_date, tv_time;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_info = itemView.findViewById(R.id.tv_info);
//            tv_date = itemView.findViewById(R.id.tv_date);
            tv_time = itemView.findViewById(R.id.tv_time);

        }
    }
}
