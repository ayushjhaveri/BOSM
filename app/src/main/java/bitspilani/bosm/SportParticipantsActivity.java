package bitspilani.bosm;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import bitspilani.bosm.adapters.AdpaterParticipants;

public class SportParticipantsActivity extends RegStep2Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sports_participants);

        ListView sportsParticipantsListView = (ListView) findViewById(R.id.listview_participants);

        TextView sportTitle = (TextView) findViewById(R.id.title_sport);
        sportTitle.setText(getIntent().getStringExtra("sportName"));

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_sports);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Menu menu = navigationView.getMenu();
        menu.add("Badminton (Boys)");
        menu.add("Badminton (Girls");
        menu.add("Football (Boys)");
        menu.add("Football (Girls)");
        menu.add("Table Tennis (Boys)");
        menu.add("Table Tennis (Girls)");
        menu.add("Hockey (Boys)");
        menu.add("Hockey (Girls)");
        menu.add("Badminton (Boys)");
        menu.add("Badminton (Girls");
        menu.add("Football (Boys)");
        menu.add("Football (Girls)");
        menu.add("Table Tennis (Boys)");
        menu.add("Table Tennis (Girls)");
        menu.add("Hockey (Boys)");
        menu.add("Hockey (Girls)");

        //Participants List View
        final String[] names = {"Ayush Jhaveri","Prashant Khandelwal","Nischal Ganatra","Nikhil Khandelwal","Arpit Anshuman","Another Player","More Players","More even","Short name"};
        ListAdapter participantsAdapter = new AdpaterParticipants(this,names);
        sportsParticipantsListView.setAdapter(participantsAdapter);

        sportsParticipantsListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                }
        );


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        TextView sportTitle = (TextView) findViewById(R.id.title_sport);
        sportTitle.setText(item.getTitle().toString());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_sports);
        drawer.closeDrawer(GravityCompat.END);
        return false;
    }



}
