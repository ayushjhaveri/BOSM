package bitspilani.bosm.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import bitspilani.bosm.R;

public class AdpaterParticipants extends ArrayAdapter<String> {

    public AdpaterParticipants(@NonNull Context context, String[] names){
        super(context, R.layout.layout_participants_row, names);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater ayushsInflater = LayoutInflater.from(getContext());
        View customView = ayushsInflater.inflate(R.layout.layout_participants_row, parent, false);


        String singleParticipantItem = getItem(position);
        TextView name = (TextView) customView.findViewById(R.id.text_name);
        //ImageView ayushsImage = (ImageView) customView.findViewById(R.id.ayushsImage);

        name.setText(singleParticipantItem);
        //ayushsImage.setImageResource(R.drawable.facebook);

        return customView;
    }
}
