package bitspilani.bosm.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bitspilani.bosm.R;
import bitspilani.bosm.items.ItemScore;

public class AdapterScores extends ArrayAdapter<ItemScore>{

    private Context mContext;
    private List<ItemScore> scoresList = new ArrayList<>();

    public AdapterScores(@NonNull Context context, @LayoutRes ArrayList<ItemScore> list) {
        super(context, 0 , list);
        mContext = context;
        scoresList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_sport_row, parent, false);
        }
        ItemScore currentScore = scoresList.get(position);

        TextView sport = (TextView)convertView.findViewById(R.id.text_sport);
        TextView round = (TextView)convertView.findViewById(R.id.text_round);
        TextView date = (TextView)convertView.findViewById(R.id.text_date);
        TextView time = (TextView)convertView.findViewById(R.id.text_time);
        TextView venue = (TextView)convertView.findViewById(R.id.text_venue);
        TextView collegeOne = (TextView)convertView.findViewById(R.id.text_college_one);
        TextView collegeTwo = (TextView)convertView.findViewById(R.id.text_college_two);
        TextView scoreOne = (TextView)convertView.findViewById(R.id.text_score_one);
        TextView scoreTwo = (TextView)convertView.findViewById(R.id.text_score_two);

        sport.setText(currentScore.getSport());
        round.setText(currentScore.getRound());
        date.setText(currentScore.getMatchDate());
        time.setText(currentScore.getMatchTime());
        venue.setText(currentScore.getVenue());
        collegeOne.setText(currentScore.getCollegeOne());
        collegeTwo.setText(currentScore.getCollegeTwo());
        scoreOne.setText(currentScore.getScoreOne());
        scoreTwo.setText(currentScore.getScoreTwo());

        int score1 = Integer.valueOf(scoreOne.getText().toString());
        int score2 = Integer.valueOf(scoreTwo.getText().toString());

        if(score1>score2){
            collegeOne.setTextColor(Color.parseColor("#E65100"));
            scoreOne.setTextColor(Color.parseColor("#E65100"));
        }
        else if(score2>score1){
            collegeTwo.setTextColor(Color.parseColor("#E65100"));
            scoreTwo.setTextColor(Color.parseColor("#E65100"));
        }
        else{
            scoreOne.setTextColor(Color.parseColor("#E65100"));
            scoreTwo.setTextColor(Color.parseColor("#E65100"));
        }

        return convertView;
    }
}
