package bitspilani.bosm.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import bitspilani.bosm.items.ItemDeveloper;
import bitspilani.bosm.R;

import java.util.List;

/**
 * Created by Saksham on 22 Aug 2016.
 */
public class AdapterDevelopers extends ArrayAdapter<ItemDeveloper> {

    Activity activity;

    public AdapterDevelopers(Context context, int resource, List<ItemDeveloper> objects, Activity activity) {
        super(context, resource, objects);
        this.activity = activity;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_developer, null);
            holder = new ViewHolder();
            holder.ivpic = (ImageView) convertView.findViewById(R.id.blapic);
            holder.tvName = (TextView) convertView.findViewById(R.id.blaName);
            holder.tvdesc = (TextView) convertView.findViewById(R.id.bladesc);

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        ItemDeveloper item = getItem(position);

        holder.tvName.setText(item.getName());
        holder.tvdesc.setText(item.getDesc());
        holder.ivpic.setImageResource(item.getImage());
        if(item.getName().equals("Prashant Khandelwal")){
            holder.tvdesc.setTextColor(ContextCompat.getColor(activity,R.color.orange_shade));
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listItemClicked(position);
            }
        });

        return convertView;
    }

    static class ViewHolder {
        private TextView tvName;
        private TextView tvdesc;
        private ImageView ivpic;
    }

    private void listItemClicked (int position) {
//        Toast.makeText(getContext(), getItem(position).getName(), Toast.LENGTH_SHORT).show();
    }
}