package bitspilani.bosm.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ramotion.foldingcell.FoldingCell;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import bitspilani.bosm.R;
import bitspilani.bosm.items.ItemNotification;

import static bitspilani.bosm.HomeActivity.getDayOfMonthSuffix;

public class AdapterNotifications extends RecyclerView.Adapter<AdapterNotifications.ViewHolder> {

    private List<ItemNotification> notificationArrayList;
    private Context context;

//    private static final String TAG = "AdapterCart";

    public AdapterNotifications(Context context, List<ItemNotification> eventsArrayList) {
        this.notificationArrayList = eventsArrayList;
        this.context = context;
    }

    @Override
    public AdapterNotifications.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_notification, parent, false);

        return new AdapterNotifications.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AdapterNotifications.ViewHolder holder, final int position) {
         ItemNotification itemNotifications = notificationArrayList.get(position);

        holder.tv_title.setText(itemNotifications.getNotif_title());
        holder.tv_info.setText(itemNotifications.getNotif_body());
        String month_format = "MMM";
        SimpleDateFormat sdf_month = new SimpleDateFormat(month_format);
        String time_format = "h.mm a";
        SimpleDateFormat sdf_time = new SimpleDateFormat(time_format);

        holder.tv_date.setText(itemNotifications.getCal().get(Calendar.DATE)+
                " "+sdf_month.format(itemNotifications.getCal().getTime())+"");
        holder.tv_time.setText(sdf_time.format(itemNotifications.getCal().getTime()));


    }

    @Override
    public int getItemCount() {
        return notificationArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_title, tv_info, tv_date, tv_time;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_info = itemView.findViewById(R.id.tv_info);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_time = itemView.findViewById(R.id.tv_time);
//
        }
    }
}
