package bitspilani.bosm.adapters;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import bitspilani.bosm.R;

/**
 * Created by Prashant on 7 September 2017.
 */

public class AdapterContactus extends RecyclerView.Adapter<AdapterContactus.ViewHolder> {

    private ArrayList<String> post;
    private ArrayList<String> name;
    private ArrayList<String> number;
    private ArrayList<String> mail;
    Activity activity;

    public AdapterContactus(Activity activity, ArrayList<String> post, ArrayList<String> name, ArrayList<String> number, ArrayList<String> mail) {
        this.post = post;
        this.name = name;
        this.number = number;
        this.mail = mail;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_contactus, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {

        viewHolder.tv_Post.setText(post.get(i));
        viewHolder.tv_Name.setText(name.get(i));
        viewHolder.tv_Number.setText(number.get(i));
        viewHolder.tv_Mail.setText(mail.get(i));
        viewHolder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel",number.get(i), null));
                activity.startActivity(intent);
            }
        });

        viewHolder.mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_EMAIL,mail.get(i));
                activity.startActivity(Intent.createChooser(intent, "Send Email"));
            }
        });
    }

    @Override
    public int getItemCount() {
        return post.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_Post;
        private TextView tv_Name;
        private TextView tv_Number;
        private TextView tv_Mail;
        private ImageView call;
        private ImageView mail;
        public ViewHolder(View view) {
            super(view);

            tv_Post = (TextView)view.findViewById(R.id.textView4);
            tv_Name = (TextView)view.findViewById(R.id.textView5);
            tv_Number = (TextView)view.findViewById(R.id.textView6);
            tv_Mail = (TextView)view.findViewById(R.id.textView7);
            call=(ImageView)view.findViewById(R.id.call);
            mail=(ImageView)view.findViewById(R.id.mail);
        }
    }

}