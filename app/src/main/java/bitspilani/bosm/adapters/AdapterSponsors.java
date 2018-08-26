package bitspilani.bosm.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import bitspilani.bosm.R;
import bitspilani.bosm.items.ItemSponsor;

import java.util.List;

/**
 * Created by Saksham on 22 Aug 2016.
 */
public class AdapterSponsors extends ArrayAdapter<ItemSponsor> {

    Activity activity;

    public AdapterSponsors(Context context, int resource, List<ItemSponsor> objects, Activity activity) {
        super(context, resource, objects);
        this.activity = activity;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_sponsors, null);
            holder = new ViewHolder();
            holder.tvName = (TextView) convertView.findViewById(R.id.textView2);
            holder.ivLogo = (ImageView) convertView.findViewById(R.id.imageView2);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        ItemSponsor item = getItem(position);

        holder.tvName.setText(item.getName());
        holder.ivLogo.setImageResource(item.getImage());

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
        private ImageView ivLogo;
    }

    private void listItemClicked (int position) {
        Toast.makeText(getContext(), getItem(position).getName(), Toast.LENGTH_SHORT).show();
    }
}