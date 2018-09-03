package bitspilani.bosm.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jsibbold.zoomage.ZoomageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import bitspilani.bosm.R;

public class AdapterPhotos extends RecyclerView.Adapter<AdapterPhotos.MyViewHolder> {

    private ArrayList<String> photoUrlArrayList;
    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ZoomageView photo;
        ProgressBar progressBar;
        public MyViewHolder(View view) {
            super(view);
            photo = (ZoomageView)view.findViewById(R.id.myZoomageView);
            progressBar = (ProgressBar)view.findViewById(R.id.progressBar);
        }
    }


    public AdapterPhotos(Context context,ArrayList<String> photoUrlArrayList) {
        this.photoUrlArrayList = photoUrlArrayList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_photo, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        String currentPhotoUrl = photoUrlArrayList.get(position);
        Picasso.with(context).load(currentPhotoUrl).into(holder.photo, new Callback() {
            @Override
            public void onSuccess() {
                holder.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return photoUrlArrayList.size();
    }
}
