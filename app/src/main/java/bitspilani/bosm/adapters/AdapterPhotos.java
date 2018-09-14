package bitspilani.bosm.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import bitspilani.bosm.R;

public class AdapterPhotos extends FirestoreAdapter<AdapterPhotos.MyViewHolder> {

    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView photo;
        TextView tv_error;
//        ZoomageView photo2;
        ProgressBar progressBar;
        public MyViewHolder(View view) {
            super(view);
            photo = (ImageView) view.findViewById(R.id.photo_view);
//            photo2 = (ZoomageView)view.findViewById(R.id.myZoomageView);
            progressBar = (ProgressBar)view.findViewById(R.id.progressBar);
            tv_error = (TextView)view.findViewById(R.id.tv_error);
        }
    }


    public AdapterPhotos(Context context, Query query) {
        super(query);
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
        DocumentSnapshot documentSnapshot = getSnapshot(position);
        holder.tv_error.setVisibility(View.GONE);
        holder.progressBar.setVisibility(View.VISIBLE);
        FirebaseStorage storage = FirebaseStorage.getInstance("gs://bosm-18-1522766608739.appspot.com");
        StorageReference storageRef = storage.getReference();

        String name = documentSnapshot.contains("name")?documentSnapshot.getData().get("name").toString():"one.jpg";
        String path = "images/"+name;
        StorageReference pathReference = storageRef.child(path);
        pathReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(context).load(uri).into(holder.photo, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.progressBar.setVisibility(View.GONE);
                        holder.tv_error.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        holder.progressBar.setVisibility(View.GONE);
                        holder.tv_error.setVisibility(View.VISIBLE);
                    }

                });
                // Got the download URL for 'users/me/profile.png'
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                holder.progressBar.setVisibility(View.GONE);
                holder.tv_error.setVisibility(View.VISIBLE);
            }
        });


    }

}
