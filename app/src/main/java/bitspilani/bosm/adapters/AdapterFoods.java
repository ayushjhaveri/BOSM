package bitspilani.bosm.adapters;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import bitspilani.bosm.R;
import bitspilani.bosm.fragments.FragmentFoodItems;
import bitspilani.bosm.items.ItemFood;
import bitspilani.bosm.utils.Constant;

/**
 * Created by Prashant on 4/6/2018.
 */

public class AdapterFoods extends ArrayAdapter<ItemFood> {

    public AdapterFoods(Context context , ArrayList<ItemFood> objects) {
        super(context, 0, objects);
    }

    private static final String TAG = "AdapterFoods";
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ItemFood itemFood = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_food, parent, false);
        }
        // Lookup view for data population
        TextView textView_food_name = (TextView) convertView.findViewById(R.id.tv_food_name);
        TextView textView_food_price = (TextView) convertView.findViewById(R.id.tv_price);
        ImageButton ib_add =(ImageButton) convertView.findViewById(R.id.ib_add);
        // Populate the data into the template view using the data object

        textView_food_name.setText(itemFood.getFood_name());
        textView_food_price.setText(getContext().getResources().getString(R.string.Rs)+itemFood.getPrice());
        ib_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view,"Adding Item to CART",Snackbar.LENGTH_SHORT).show();
                add_to_cart(itemFood.getFood_id(),view);
            }
        });

        // Return the completed view to render on screen
        return convertView;
    }

    public void add_to_cart(final int food_id, final View view){
        class GetData extends AsyncTask<Void, Void, String> {
            @Override
            protected void onPreExecute() {

                FragmentFoodItems.progressBar.setVisibility(View.VISIBLE);
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                FragmentFoodItems.progressBar.setVisibility(View.GONE);
                super.onPostExecute(s);
                parseJSON(s);
            }

            @Override
            protected String doInBackground(Void... params) {

                try {
                    URL url = new URL(Constant.URL_ADD_TO_CART);
                    String urlParams = "user_id=" + Constant.currentItemUser.getId()
                            +"&food_id="+food_id;

                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setDoOutput(true);
                    StringBuilder sb = new StringBuilder();
//
                    OutputStream os = con.getOutputStream();
                    os.write(urlParams.getBytes());
                    os.flush();
                    os.close();

                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }

                    String s = sb.toString().trim();
                    return s;

                } catch (IOException e) {
                    e.printStackTrace();
                    return "error";
                }
            }

            private void parseJSON(String json) {
                try {
//                    Toast.makeText(LoginActivity.this,json,Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Response: " + json);
                    JSONObject root = new JSONObject(json);
                    JSONObject response = root.getJSONObject("response");
                    boolean success = response.getBoolean("success");
                    if(!success) {
                        Snackbar.make(view,"Item already added to the cart!",Snackbar.LENGTH_SHORT).show();
//                        Toast.makeText(getContext(), , Toast.LENGTH_SHORT).show();
                    }else {
                        Snackbar.make(view,"Item added successfully!",Snackbar.LENGTH_SHORT).show();
//                        Toast.makeText(getContext(), "Item added successfully!", Toast.LENGTH_SHORT).show();
                        FragmentFoodItems.progressBar.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    FragmentFoodItems.progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(),getContext().getResources().getText(R.string.connection_error),Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }
        GetData gd = new GetData();
        gd.execute();
    }


}
