package bitspilani.bosm.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.ramotion.foldingcell.FoldingCell;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import bitspilani.bosm.R;
import bitspilani.bosm.items.ItemEvent;

import static bitspilani.bosm.HomeActivity.toTitleCase;
import static bitspilani.bosm.HomeActivity.getDayOfMonthSuffix;

//import java.lang.Object;
//import org.apache.commons.text.WordUtils;

public class AdapterEvents extends FirestoreAdapter<AdapterEvents.ViewHolder> {

    private Context context;
    private ProgressBar progressBar;
    private static final String TAG = "AdapterCart";
    private RelativeLayout rl_filled, rl_empty;

    public AdapterEvents(Context context, Query query, ProgressBar progressBar, RelativeLayout rl_filled, RelativeLayout rl_empty) {
        super(query);
        this.progressBar = progressBar;
        this.context = context;
        this.rl_empty=rl_empty;
        this.rl_filled=rl_filled;
    }

    @Override
    public AdapterEvents.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_event, parent, false);

        return new AdapterEvents.ViewHolder(itemView);
    }

    @Override
    protected void onDataChanged() {
        super.onDataChanged();
        progressBar.setVisibility(View.GONE);
        rl_empty.setVisibility(View.GONE);
        rl_filled.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBindViewHolder(final AdapterEvents.ViewHolder holder, final int position) {
       DocumentSnapshot document =  getSnapshot(position);
        Timestamp timestamp  = document.contains("timestamp")?(Timestamp) document.getData().get("timestamp"):Timestamp.now();
        Date date = timestamp.toDate();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        String month_format = "MMM";
        SimpleDateFormat sdf_month = new SimpleDateFormat(month_format);
        String time_format = "h.mm a";
        SimpleDateFormat sdf_time = new SimpleDateFormat(time_format);

        ItemEvent itemEvent = new ItemEvent(
                document.getId(),
                document.contains("title")?toTitleCase(document.getData().get("title").toString()):"",
                cal.get(Calendar.DATE)
                        +getDayOfMonthSuffix(cal.get(Calendar.DATE))+
                        " "+sdf_month.format(date)+"",
                sdf_time.format(cal.getTime()),
                document.contains("venue")?document.getData().get("venue").toString():"",
                document.contains("text")?document.getData().get("text").toString():"",
                        document.contains("club")?toTitleCase(document.getData().get("club").toString()):""
        );

        // use itemEvent here

        holder.tv_title.setText(itemEvent.getEvent_name());
        holder.tv_event_name.setText(itemEvent.getEvent_name());

        holder.tv_info.setText(itemEvent.getEvent_text());
        holder.tv_club.setText(itemEvent.getEvent_club());
        holder.tv_date.setText(itemEvent.getEvent_date());
        holder.tv_time.setText(itemEvent.getEvent_time());
        holder.tv_venue.setText(itemEvent.getEvent_venue());

        holder.tv_club2.setText(itemEvent.getEvent_club());
        holder.tv_date2.setText(itemEvent.getEvent_date());
        holder.tv_time2.setText(itemEvent.getEvent_time());
        holder.tv_venue2.setText(itemEvent.getEvent_venue());

        holder.fc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.fc.toggle(false);
            }
        });
    }

     class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_title, tv_info, tv_club2, tv_venue2, tv_date2, tv_time2, tv_event_name,  tv_club, tv_venue, tv_date, tv_time ;
        FoldingCell fc;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView)itemView.findViewById(R.id.tv_title);
            tv_info = (TextView)itemView.findViewById(R.id.tv_info);
            tv_club2 = (TextView)itemView.findViewById(R.id.tv_club2);
            tv_venue2 = (TextView)itemView.findViewById(R.id.tv_venue2);
            tv_date2 = (TextView)itemView.findViewById(R.id.tv_date2);
            tv_time2 = (TextView)itemView.findViewById(R.id.tv_time2);

            tv_club = (TextView)itemView.findViewById(R.id.tv_club);
            tv_venue = (TextView)itemView.findViewById(R.id.tv_venue);
            tv_date = (TextView)itemView.findViewById(R.id.tv_date);
            tv_time = (TextView)itemView.findViewById(R.id.tv_time);
            tv_event_name = (TextView)itemView.findViewById(R.id.tv_event_name);

            fc = (FoldingCell) itemView.findViewById(R.id.folding_cell);

        }
    }

}
