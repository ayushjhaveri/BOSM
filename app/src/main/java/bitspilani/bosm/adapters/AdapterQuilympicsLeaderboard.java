package bitspilani.bosm.adapters;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

import bitspilani.bosm.R;
import bitspilani.bosm.items.ItemQuilympicsLeaderboard;
import bitspilani.bosm.items.ItemRouletteLeaderboard;

/**
 * Created by Saksham on 12 Sep 2016.
 */
public class AdapterQuilympicsLeaderboard extends FirestoreAdapter<AdapterQuilympicsLeaderboard.LeaderboardViewHolder> {

    Context context;

    public class LeaderboardViewHolder extends RecyclerView.ViewHolder {
        public TextView tvRank;
        public TextView tvScore;
        public TextView tvName;
        public TextView tvEmail;
        public RelativeLayout container;

        public LeaderboardViewHolder(View view) {
            super(view);
            tvRank = (TextView) view.findViewById(R.id.tvRank);
            tvScore = (TextView) view.findViewById(R.id.tvScore);
            tvName = (TextView) view.findViewById(R.id.tvName);
            tvEmail = (TextView) view.findViewById(R.id.tvEmail);
            container = (RelativeLayout) view.findViewById(R.id.container);
//            tv_venue = (TextView)view.findViewById(R.id.tv_venue);
        }
    }
    public AdapterQuilympicsLeaderboard(Context context, Query query ){
        super(query);
        this.context = context;
    }

    @Override
    public LeaderboardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_roulette_leaderboard, parent, false);



        return new LeaderboardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(LeaderboardViewHolder holder, int position) {

        DocumentSnapshot document  = getSnapshot(position);
        try {
                final ItemQuilympicsLeaderboard user = new ItemQuilympicsLeaderboard(
                        document.contains("email")?document.getData().get("email").toString():"",
                        document.contains("name")?document.getData().get("name").toString():"",
                        document.contains("quilympics_score")?Integer.parseInt(document.getData().get("quilympics_score").toString()):0
                );

            holder.tvRank.setText(String.valueOf(position + 1));
            holder.tvScore.setText(String.valueOf(user.getScore()));
            holder.tvName.setText(user.getName());
            holder.tvEmail.setText(user.getEmail());

            if (document.getId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                holder.container.setBackgroundColor(ContextCompat.getColor(context, R.color.back_shade4));
            } else {
                holder.container.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
