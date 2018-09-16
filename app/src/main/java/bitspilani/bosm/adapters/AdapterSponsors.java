package bitspilani.bosm.adapters;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ramotion.foldingcell.FoldingCell;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import bitspilani.bosm.R;
import bitspilani.bosm.items.ItemSponsor;
import bitspilani.bosm.items.ItemSponsor;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static bitspilani.bosm.HomeActivity.getDayOfMonthSuffix;
import static bitspilani.bosm.HomeActivity.toTitleCase;

/**
 * Created by Saksham on 22 Aug 2016.
 */
public class AdapterSponsors extends FirestoreAdapter<AdapterSponsors.ViewHolder> {
    
    private Context context;
    private ProgressBar progressBar;
    private static final String TAG = "AdapterCart";
    private RelativeLayout rl_filled, rl_empty;

    public AdapterSponsors(Context context, Query query, ProgressBar progressBar, RelativeLayout rl_filled, RelativeLayout rl_empty) {
        super(query);
        this.progressBar = progressBar;
        this.context = context;
        this.rl_empty=rl_empty;
        this.rl_filled=rl_filled;
    }

    @Override
    public AdapterSponsors.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_sponsors, parent, false);

        return new AdapterSponsors.ViewHolder(itemView);
    }

    @Override
    protected void onDataChanged() {
        super.onDataChanged();
        progressBar.setVisibility(View.GONE);
        rl_empty.setVisibility(View.GONE);
        rl_filled.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBindViewHolder(final AdapterSponsors.ViewHolder holder, final int position) {
        DocumentSnapshot documentSnapshot = getSnapshot(position);

        holder.tv_name.setText(documentSnapshot.contains("name")?documentSnapshot.getData().get("name").toString():"PAYTM");
        holder.tv_descripition.setText(documentSnapshot.contains("type")?documentSnapshot.getData().get("type").toString():"Payments Partner");

        FirebaseStorage storage = FirebaseStorage.getInstance("gs://bosm-18-1522766608739.appspot.com");
        StorageReference storageRef = storage.getReference();
        Log.d(TAG,"working");
        String name = documentSnapshot.contains("image_url")?documentSnapshot.getData().get("image_url").toString():"paytm.png";
        String path = "sponsors/"+name;
        StorageReference pathReference = storageRef.child(path);
        pathReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.d(TAG,uri.toString());
                Picasso.with(context).load(uri).into(holder.iv_sponsor, new Callback() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onError() {
                    }

                });
                // Got the download URL for 'users/me/profile.png'
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
            }
        });


//        ItemSponsor ItemSponsor = new ItemSponsor(
//                document.getId(),
//                document.contains("title")?toTitleCase(document.getData().get("title").toString()):"",
//                cal.get(Calendar.DATE)
//                        +getDayOfMonthSuffix(cal.get(Calendar.DATE))+
//                        " "+sdf_month.format(date)+"",
//                sdf_time.format(cal.getTime()),
//                document.contains("venue")?document.getData().get("venue").toString():"",
//                document.contains("text")?document.getData().get("text").toString():"",
//                document.contains("club")?toTitleCase(document.getData().get("club").toString()):""
//        );

        // use ItemSponsor here


    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_name, tv_descripition;
        ImageView iv_sponsor;
        ProgressBar progressBar;
        public ViewHolder(View itemView) {
            super(itemView);
           tv_name = (TextView)itemView.findViewById(R.id.tv_name);
           tv_descripition = (TextView)itemView.findViewById(R.id.tv_descripition);
           iv_sponsor = (ImageView) itemView.findViewById(R.id.iv_sponsor);
           progressBar =(ProgressBar)itemView.findViewById(R.id.progressBar);

        }
    }
    
    
    
    
//    @Override
//    public View getView(final int position, View convertView, ViewGroup parent) {
//
//        ViewHolder holder;
//
//        if (convertView == null) {
//            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            convertView = inflater.inflate(R.layout.row_sponsors, null);
//            holder = new ViewHolder();
//            holder.tvName = (TextView) convertView.findViewById(R.id.textView2);
//            holder.ivLogo = (ImageView) convertView.findViewById(R.id.imageView2);
//            holder.tvDescription=(TextView)convertView.findViewById(R.id.tv_description);
//            convertView.setTag(holder);
//        }
//        else {
//            holder = (ViewHolder) convertView.getTag();
//        }
//
//        ItemSponsor item = getItem(position);
//
//        holder.tvName.setText(item.getName());
//        holder.ivLogo.setImageResource(item.getImage());
//        holder.tvDescription.setText(item.getDescription());
//
//        convertView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                listItemClicked(position);
//            }
//        });
//
//        return convertView;
//    }
//
//    static class ViewHolder {
//        private TextView tvName, tvDescription;
//        private ImageView ivLogo;
//    }
//
//    private void listItemClicked (int position) {
//        Toast.makeText(getContext(), getItem(position).getName(), Toast.LENGTH_SHORT).show();
//    }
}