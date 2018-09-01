//package bitspilani.bosm.adapters;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Typeface;
//import android.support.annotation.NonNull;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentActivity;
//import android.support.v4.app.FragmentTransaction;
//import android.support.v7.widget.CardView;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.firestore.DocumentSnapshot;
//import com.google.firebase.firestore.Query;
//import com.google.firebase.messaging.FirebaseMessaging;
//import com.sackcentury.shinebuttonlib.ShineButton;
//
//import bitspilani.bosm.R;
//import bitspilani.bosm.fragments.SportFragment;
//import bitspilani.bosm.fragments.SportSelectedFragment;
//import bitspilani.bosm.items.ItemRoulette;
//import bitspilani.bosm.utils.Constant;
//
////import bitspilani.bosm.CurrentSportActivity;
//
///**
// * Created by Prashant on 4/7/2018.
// */
//
//public class AdapterMain_R extends FirestoreAdapter<AdapterMain_R.ViewHolder> {
//
//    private Context context;
//
//    private static final String TAG = "AdapterCart";
//
//    public AdapterMain_R(Context context, Query query) {
//        super(query);
//        this.context = context;
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View itemView = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.row_sport, parent, false);
//
//        return new ViewHolder(itemView,context);
//    }
//
//    @Override
//    public void onBindViewHolder(final ViewHolder holder, final int position) {
//        DocumentSnapshot document =  getSnapshot(position);
//
//        ItemRoulette
//
//
//
//
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//
//        public TextView tvName;
//        public TextView tvDescription;
//        public TextView tvDateTime;
//        public TextView tvStatus;
//        public TextView tvTeamA;
//        public TextView tvTeamB;
//        public RelativeLayout teams;
//        public ImageView ivLogo;
//        public ImageView ivStar;
//        Context activity;
//
//        public  ViewHolder(View itemView, Context activity) {
//            super(itemView);
//            this.activity = activity;
//            itemView.setOnClickListener(this);
////            sharedPreferences = activity.getSharedPreferences(LoginActivity.SHARED_USER_DETAILS, Context.MODE_PRIVATE);
//            tvName = (TextView) itemView.findViewById(R.id.tvName);
//            tvDescription = (TextView) itemView.findViewById(R.id.tvDescription);
//            tvDateTime = (TextView) itemView.findViewById(R.id.tvDateTime);
//            tvStatus = (TextView) itemView.findViewById(R.id.tvStatus);
//            tvTeamA = (TextView) itemView.findViewById(R.id.tvTeamA);
//            tvTeamB = (TextView) itemView.findViewById(R.id.tvTeamB);
//            teams = (RelativeLayout) itemView.findViewById(R.id.teams);
//            ivLogo = (ImageView) itemView.findViewById(R.id.ivLogo);
//            ivStar = (ImageView) itemView.findViewById(R.id.star);
//
//        }
//
//        @Override
//        public void onClick(View view) {
////            Intent intent = new Intent(activity, BiddingActivity.class);
////            intent.putExtra("sport", Roulette.events.get(getAdapterPosition()).getSport());
////            intent.putExtra("id", Roulette.events.get(getAdapterPosition()).getId());
////            activity.startActivity(intent);
//        }
//    }
//    @Override
//    public int getItemViewType(int position) {
//        return position;
//    }
//
//}
