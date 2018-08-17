//package bitspilani.bosm;
//
//import android.graphics.Typeface;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.support.v7.widget.DefaultItemAnimator;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.widget.TextView;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//
//import bitspilani.bosm.adapters.AdapterCurrentSport;
//import bitspilani.bosm.adapters.AdapterSport;
//import bitspilani.bosm.adapters.ItemSport;
//import bitspilani.bosm.items.ItemCurrentSport;
//
//public class CurrentSportActivity extends AppCompatActivity {
//    public static HashMap<String, Integer> map;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_current_sport);
//
//        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_view) ;
//
//        ArrayList<ItemCurrentSport> currentSportArrayList  = new ArrayList<>();
//
//        AdapterCurrentSport adapterCurrentSport = new AdapterCurrentSport(getApplicationContext(),currentSportArrayList);
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
//        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.setAdapter(adapterCurrentSport);
//
//        map = new HashMap<>();
//
//        currentSportArrayList.add(new ItemCurrentSport(
//                1,
//                2,
//                "BITS Pilani",
//                "IIT Delhi",
//                "2",
//                "1",
//                "Gym G",
//                "19:00",
//                "Day 1",
//                "Semi-final"));
//        currentSportArrayList.add(new ItemCurrentSport(
//                1,
//                2,
//                "BITS Pilani",
//                "IIT Delhi",
//                "2",
//                "1",
//                "Gym G",
//                "19:00",
//                "Day 1",
//                "Semi-final"));
//        currentSportArrayList.add(new ItemCurrentSport(
//                1,
//                2,
//                "BITS Pilani",
//                "IIT Delhi",
//                "2",
//                "1",
//                "Gym G",
//                "19:00",
//                "Day 2",
//                "Semi-final"));
//        currentSportArrayList.add(new ItemCurrentSport(
//                1,
//                2,
//                "BITS Pilani",
//                "IIT Delhi",
//                "2",
//                "1",
//                "Gym G",
//                "19:00",
//                "Day 2",
//                "Semi-final"));
//
//        adapterCurrentSport.notifyDataSetChanged();
//
//
//
//        Typeface oswald_regular = Typeface.createFromAsset(getAssets(),"fonts/KrinkesDecorPERSONAL.ttf");
//        TextView title = (TextView)findViewById(R.id.tv_header);
//        title.setTypeface(oswald_regular);
//    }
//}
