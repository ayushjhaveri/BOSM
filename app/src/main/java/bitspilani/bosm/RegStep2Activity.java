package bitspilani.bosm;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import bitspilani.bosm.adapters.AdpaterParticipants;

public class RegStep2Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regstep2);

        //Initializing Objects
        ListView participantsListView = (ListView) findViewById(R.id.listview_participants);
        Button button_addParticipant = (Button) findViewById(R.id.button_add_participant);
        Button button_addSport = (Button) findViewById(R.id.button_add_sport);
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_sports);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Participants List View
        final String[] names = {"Ayush Jhaveri","Prashant Khandelwal","Nischal Ganatra","Nikhil Khandelwal","Arpit Anshuman","Another Player","More Players","More even","Short name"};
        ListAdapter participantsAdapter = new AdpaterParticipants(this,names);
        participantsListView.setAdapter(participantsAdapter);

        participantsListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                }
        );

        //Add Participant Button
        button_addParticipant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(RegStep2Activity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_add_participant, null);

                //Dropdown menu for gender
                Spinner spinner_gender = (Spinner) mView.findViewById(R.id.spinner_gender2);
                String[] items_gender = new String[]{"","MALE", "FEMALE"};
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(RegStep2Activity.this,R.layout.spinner_item_gender,items_gender);
                adapter.setDropDownViewResource(R.layout.spinner_item_gender);
                spinner_gender.setAdapter(adapter);


                builder.setView(mView);
                AlertDialog dialog = builder.create();
                // Display the alert dialog on interface
                dialog.show();


            }
        });

        //Add to Sports Button
        button_addSport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawer.openDrawer(Gravity.END);
            }
        });

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

        Intent intent = new Intent(getBaseContext(), SportParticipantsActivity.class);
        intent.putExtra("sportName",item.getTitle().toString());
        startActivity(intent);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_sports);
        drawer.closeDrawer(GravityCompat.END);
        return false;
    }



}
