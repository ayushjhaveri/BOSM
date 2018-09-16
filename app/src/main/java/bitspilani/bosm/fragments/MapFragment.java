package bitspilani.bosm.fragments;



import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

import bitspilani.bosm.HomeActivity;
import bitspilani.bosm.R;

import static android.content.Context.LOCATION_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {

    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 120;
    SupportMapFragment sMapFragment;
    View rootView;
    Context context;

    public MapFragment() {
        // Required empty public constructor
        HomeActivity.currentFragment="MapFragment";
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_map, container, false);
        sMapFragment = SupportMapFragment.newInstance();
        context = getContext();
        sMapFragment.getMapAsync(this);
        android.support.v4.app.FragmentManager sFm = getActivity().getSupportFragmentManager();



        if (sMapFragment.isAdded())
            sFm.beginTransaction().hide(sMapFragment).commit();

        if (!sMapFragment.isAdded()) {
            sFm.beginTransaction().add(R.id.map, sMapFragment).commit();
        } else {
            sFm.beginTransaction().show(sMapFragment).commit();
        }

        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().overridePendingTransition(0, 0);
    }

    @Override
    public void onResume() {
        super.onResume();
        HomeActivity.currentFragment="MapFragment";
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.clear();
        googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                        context, R.raw.mapstyle_night));



        //FOR MAP BOUNDS
        LatLngBounds bounds = new LatLngBounds(
                new LatLng(28.349879, 75.581987), new LatLng(28.368180, 75.593113));
        googleMap.setLatLngBoundsForCameraTarget(bounds);

        //SET MIN ZOOM OUT
        googleMap.setMinZoomPreference(16);
//         googleMap.setMaxZoomPreference(25);

        //HEIGHT AND WIDTH FOR MARKERS
        int width = 150;
        int height = 150;

        //SETTING BITMAP FOR MARKERS
        BitmapDrawable fddraw = (BitmapDrawable) getResources().getDrawable(R.drawable.map_fd);
        Bitmap b_fd = fddraw.getBitmap();
        Bitmap fd = Bitmap.createScaledBitmap(b_fd, width, height, false);

        BitmapDrawable fooddraw = (BitmapDrawable) getResources().getDrawable(R.drawable.map_food);
        Bitmap b_food = fooddraw.getBitmap();
        Bitmap food = Bitmap.createScaledBitmap(b_food, width, height, false);

        BitmapDrawable templedraw = (BitmapDrawable) getResources().getDrawable(R.drawable.map_temple);
        Bitmap b_temple = templedraw.getBitmap();
        Bitmap temple = Bitmap.createScaledBitmap(b_temple, width, height, false);

        BitmapDrawable stadiumdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.map_stad);
        Bitmap b_stadium = stadiumdraw.getBitmap();
        Bitmap stadium = Bitmap.createScaledBitmap(b_stadium, width, height, false);

        BitmapDrawable atmdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.map_atm);
        Bitmap b_atm = atmdraw.getBitmap();
        Bitmap atm = Bitmap.createScaledBitmap(b_atm, width, height, false);

        BitmapDrawable medcdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.map_medc);
        Bitmap b_medc = medcdraw.getBitmap();
        Bitmap medc = Bitmap.createScaledBitmap(b_medc, width, height, false);

//        BitmapDrawable mydraw = (BitmapDrawable) getResources().getDrawable(R.drawable.location);
//        Bitmap b_my = mydraw.getBitmap();
//        Bitmap my = Bitmap.createScaledBitmap(b_my, 70, 70, false);

        //markers
        LatLng clocktower = new LatLng(28.363821, 75.587029);
        googleMap.addMarker(new MarkerOptions().position(clocktower).title("Clock Tower")
                .icon(BitmapDescriptorFactory.fromBitmap(fd)));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(clocktower, 17));

        LatLng FD2 = new LatLng(28.363854, 75.585854);
        googleMap.addMarker(new MarkerOptions().position(FD2).title("FD3")
                .icon(BitmapDescriptorFactory.fromBitmap(fd)));


        LatLng FD3 = new LatLng(28.364074, 75.588062);
        googleMap.addMarker(new MarkerOptions().position(FD3).title("FD2")
                .icon(BitmapDescriptorFactory.fromBitmap(fd)));


        LatLng SAC = new LatLng(28.360647, 75.585401);
        googleMap.addMarker(new MarkerOptions().position(SAC).title("SAC")
                .icon(BitmapDescriptorFactory.fromBitmap(stadium)));


        LatLng Gym_G = new LatLng(28.359127, 75.590132);
        googleMap.addMarker(new MarkerOptions().position(Gym_G).title("Gym G")
                .icon(BitmapDescriptorFactory.fromBitmap(stadium)));

        LatLng Birla_mandir = new LatLng(28.357635, 75.588115);
        googleMap.addMarker(new MarkerOptions().position(Birla_mandir).title("Birla Mandir")
                .icon(BitmapDescriptorFactory.fromBitmap(temple)));

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(clocktower, 17));
        LatLng Swimming_Complex = new LatLng(28.361388, 75.592649);
        googleMap.addMarker(new MarkerOptions().position(Swimming_Complex).title("Swimming Complex")
                .icon(BitmapDescriptorFactory.fromBitmap(stadium)));


        LatLng Akshay = new LatLng(28.357199, 75.590098);
        googleMap.addMarker(new MarkerOptions().position(Akshay).title("Akshay SuperMarket")
                .icon(BitmapDescriptorFactory.fromBitmap(fd)));


        LatLng FK = new LatLng(28.361231, 75.585525);
        googleMap.addMarker(new MarkerOptions().position(FK).title("Food King")
                .icon(BitmapDescriptorFactory.fromBitmap(food)));


        LatLng Looters = new LatLng(28.361471, 75.585190);
        googleMap.addMarker(new MarkerOptions().position(Looters).title("Looters")
                .icon(BitmapDescriptorFactory.fromBitmap(food)));


        LatLng ANC = new LatLng(28.360297, 75.589498);
        googleMap.addMarker(new MarkerOptions().position(ANC).title("All Night Canteen")
                .icon(BitmapDescriptorFactory.fromBitmap(food)));

        LatLng IC = new LatLng(28.364788, 75.586920);
        googleMap.addMarker(new MarkerOptions().position(IC).title("Institue Canteen")
                .icon(BitmapDescriptorFactory.fromBitmap(food)));

        LatLng SR = new LatLng(28.365641, 75.587834);
        googleMap.addMarker(new MarkerOptions().position(SR).title("SR Ground")
                .icon(BitmapDescriptorFactory.fromBitmap(stadium)));

        LatLng MedC = new LatLng(28.357653, 75.590102);
        googleMap.addMarker(new MarkerOptions().position(MedC).title("Medical Center")
                .icon(BitmapDescriptorFactory.fromBitmap(medc)));


        LatLng VFast = new LatLng(28.360422, 75.592033);
        googleMap.addMarker(new MarkerOptions().position(VFast).title("VFAST")
                .icon(BitmapDescriptorFactory.fromBitmap(fd)));


        LatLng SkyLab = new LatLng(28.363521, 75.585181);
        googleMap.addMarker(new MarkerOptions().position(SkyLab).title("Sky Lab")
                .icon(BitmapDescriptorFactory.fromBitmap(food)));


        LatLng Uco = new LatLng(28.363446, 75.590690);
        googleMap.addMarker(new MarkerOptions().position(Uco).title("UCO Bank and ATM")
                .icon(BitmapDescriptorFactory.fromBitmap(atm)));

        LatLng ICICI = new LatLng(28.357075, 75.590364);
        googleMap.addMarker(new MarkerOptions().position(ICICI).title("ICICI ATM")
                .icon(BitmapDescriptorFactory.fromBitmap(atm)));

        LatLng Atm = new LatLng(28.361598, 75.585781);
        googleMap.addMarker(new MarkerOptions().position(Atm).title("Axis Bank ATM")
                .icon(BitmapDescriptorFactory.fromBitmap(atm)));

//        LatLng latLng1 = new LatLng(28.36036036036036,  75.5957153770887);
//        googleMap.addMarker(new MarkerOptions().position(latLng1).title("Axis Bank ATM")
//                .icon(BitmapDescriptorFactory.fromBitmap(my)));


//        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            Snackbar.make(rootView, "Please enable your location!", Snackbar.LENGTH_SHORT).show();
//
//            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION},
//                    MY_PERMISSIONS_REQUEST_LOCATION);
//
//
//            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(clocktower, 17));
//
//
////            final AlertDialog.Builder builder =
////                    new AlertDialog.Builder(context);
////            final String action = Settings.ACTION_LOCATION_SOURCE_SETTINGS;
////            final String message = "Enable either GPS or any other location"
////                    + " service to find current location.  Click OK to go to"
////                    + " location services settings to let you do so.";
////
////            builder.setMessage(message)
////                    .setPositiveButton("OK",
////                            new DialogInterface.OnClickListener() {
////                                public void onClick(DialogInterface d, int id) {
////                                    context.startActivity(new Intent(action));
////                                    d.dismiss();
////                                }
////                            })
////                    .setNegativeButton("Cancel",
////                            new DialogInterface.OnClickListener() {
////                                public void onClick(DialogInterface d, int id) {
////                                    d.cancel();
////                                }
////                            });
////            builder.create().show();
//        }else{
//
//            try {
//                googleMap.setMyLocationEnabled(true);
//                googleMap.getUiSettings().isMyLocationButtonEnabled();
//
//                // Getting LocationManager object from System Service LOCATION_SERVICE
//                LocationManager locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
//
//                // Creating a criteria object to retrieve provider
//                Criteria criteria = new Criteria();
//
//                // Getting the name of the best provider
//                String provider = locationManager.getBestProvider(criteria, true);
//
//                // Getting Current Location
//                Location location = locationManager.getLastKnownLocation(provider);
//
//            //        Moving map camera to current locaton
//                if (location != null) {
//                    // Getting latitude of the current location
//                    double latitude = location.getLatitude();
//
//                    // Getting longitude of the current location
//                    double longitude = location.getLongitude();
//
//                    // Creating a LatLng object for the current location
//                    Log.d("aaaaaaaaa",Double.toString(latitude));
//                    Log.d("aaaaaaaaaaaa",Double.toString(longitude));
//                    LatLng latLng = new LatLng(28.36036036036036,  75.5957153770887);
//                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
//                    googleMap.addMarker(new MarkerOptions().position(latLng).title("Axis Bank ATM")
//                            .icon(BitmapDescriptorFactory.fromBitmap(my)));
//
//                } else {
//                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(clocktower, 17));
//
//                    googleMap.getUiSettings().setCompassEnabled(true);
//
//                }
//            }catch (Exception e){
//
//            }
//
//        }
    }

}

