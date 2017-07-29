package com.example.shruthi.newsapp;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shruthi.newsapp.Database.Contract;
import com.squareup.picasso.Picasso;

/**
 * Created by Shruthi on 6/26/2017.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.AdapterViewHolder> {
    private NewsAdapterOnClickHandler mClickHandler;
    Context ct;
    private Cursor cursor;
    public static final String TAG = "Adapter";

    public NewsAdapter(Cursor cursor,NewsAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
        this.cursor=cursor;
    }

    public interface NewsAdapterOnClickHandler {
        void onItemClick(Cursor cursor, int clickedItemIndex);
    }

    @Override
    public AdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        ct= viewGroup.getContext();
        int layoutIdForListItem = R.layout.news_item;
        LayoutInflater inflater = LayoutInflater.from(ct);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);

        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterViewHolder adapterViewHolder, final int position) {

        adapterViewHolder.bind(position, adapterViewHolder);

    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }


    public class AdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public final TextView title;
        public final TextView description;
        public final TextView time;
        ImageView img;


        public AdapterViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            description = (TextView) view.findViewById(R.id.description);
            time = (TextView) view.findViewById(R.id.time);
            img = (ImageView)view.findViewById(R.id.img);
            view.setOnClickListener(this);
        }

        public void bind(int pos, AdapterViewHolder holder){
            cursor.moveToPosition(pos);

            //  AdapterViewHolder holder;
// Setting Data to TextViews
            title.setText(cursor.getString(cursor.getColumnIndex(Contract.TABLE_NewsItem.COLUMN_NAME_TITLE)));
            time.setText(cursor.getString(cursor.getColumnIndex(Contract.TABLE_NewsItem.COLUMN_NAME_AUTHOR))
                    +" "+cursor.getString(cursor.getColumnIndex(Contract.TABLE_NewsItem.COLUMN_NAME_PUBLISHED_At)));
            description.setText(cursor.getString(cursor.getColumnIndex(Contract.TABLE_NewsItem.COLUMN_NAME_DESCRIPTION)));

            // Adding Image using Picasso
            String imageURL= cursor.getString(cursor.getColumnIndex(Contract.TABLE_NewsItem.COLUMN_NAME_URL_TO_IMAGE));

            Picasso.with(ct).load(imageURL).into(holder.img);


        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            mClickHandler.onItemClick(cursor, pos);
        }
    }

}



