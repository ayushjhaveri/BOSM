package bitspilani.bosm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

import bitspilani.bosm.adapters.AdapterScores;
import bitspilani.bosm.items.ItemScore;


public class ScheduleActivity extends AppCompatActivity{

    private ListView listView;
    private AdapterScores mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        listView = (ListView)findViewById(R.id.scores_list);
        ArrayList<ItemScore> scoresList = new ArrayList<>();

        scoresList.add(new ItemScore("Football", "Group", "19/9", "11:00", "Gym G", "BITS Goa", "Manipal", "3", "1"));
        scoresList.add(new ItemScore("Football", "Group", "19/9", "11:00", "Gym G", "BITS Pilani", "MNIT", "5", "5"));
        scoresList.add(new ItemScore("Football", "Quarterfinal", "20/9", "11:00", "Gym G", "BITS Pilani", "IIT Kharagpur", "4", "2"));
        scoresList.add(new ItemScore("Football", "Quarterfinal", "20/9", "11:00", "Gym G", "Bits Goa","IIT Kanpur", "2", "5"));
        scoresList.add(new ItemScore("Football", "Semifinal", "21/9", "11:00", "Gym G", "Bits Goa", "IIT Delhi", "3", "4"));
        scoresList.add(new ItemScore("Football", "Semfinal", "21/9", "11:00", "Gym G", "BITS Pilani", "BITS Hyderabad", "7", "7"));
        scoresList.add(new ItemScore("Football", "Final", "22/9", "11:00", "Gym G", "BITS Pilani", "BITS Goa", "8", "0"));


        mAdapter = new AdapterScores(this, scoresList);
        listView.setAdapter(mAdapter);
    }
}
