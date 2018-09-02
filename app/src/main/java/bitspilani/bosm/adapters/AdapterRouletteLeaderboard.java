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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

import bitspilani.bosm.R;
import bitspilani.bosm.items.ItemRouletteLeaderboard;

/**
 * Created by Saksham on 12 Sep 2016.
 */
public class AdapterRouletteLeaderboard extends FirestoreAdapter<AdapterRouletteLeaderboard.LeaderboardViewHolder> {

    Context context;

    private void showWalletDialog (int score, int bettingAmount) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_wallet);

        final TextView tvAmount = (TextView) dialog.findViewById(R.id.tvAmount);
        final TextView tvBet = (TextView) dialog.findViewById(R.id.tvBet);

        tvAmount.setText("$"+score);
        tvBet.setText("$"+bettingAmount);

        dialog.show();
    }



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


    public AdapterRouletteLeaderboard(Context context,Query query ){
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

        final ItemRouletteLeaderboard user = new ItemRouletteLeaderboard(
                document.getData().get("email").toString(),
                document.getData().get("name").toString(),
                Integer.parseInt(document.getData().get("score").toString()),
                Integer.parseInt(document.getData().get("betting_amount").toString())
        );

        holder.tvRank.setText(String.valueOf(position + 1));
        holder.tvScore.setText(String.valueOf(user.getScore()));
        holder.tvName.setText(user.getName());
        holder.tvEmail.setText(user.getEmail());

        if (document.getId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
            holder.container.setBackgroundColor(ContextCompat.getColor(context, R.color.back_shade2));
        } else {
            holder.container.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent));
        }

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWalletDialog(user.getScore(), user.getBettingAmount());
            }
        });
    }
}
