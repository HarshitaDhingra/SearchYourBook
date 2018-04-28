package com.example.android.harshitadhingrabooks;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.List;
public class BAdapter extends ArrayAdapter<Book> {
    public BAdapter(Context context, List<Book> books) {
        super(context, 0, books);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_book, parent, false);
        }
        Book e=getItem(position);
        ImageView i=(ImageView) convertView.findViewById(R.id.ima);
        Picasso.with(getContext()).load(Uri.parse(e.getImageUrl())).error(R.drawable.noimage).into(i);
        TextView tit = (TextView) convertView.findViewById(R.id.title);
        tit.setText(" "+e.getTitle());
        TextView desc = (TextView) convertView.findViewById(R.id.des);
        desc.setText(""+e.getDescription());
        TextView auth = (TextView) convertView.findViewById(R.id.author);
        auth.setText("- "+e.getAuthor());
        return convertView;
    }
}
