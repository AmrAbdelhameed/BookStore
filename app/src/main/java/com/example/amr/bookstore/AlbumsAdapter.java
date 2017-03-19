package com.example.amr.bookstore;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Amr on 19/03/18.
 */

public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.MyViewHolder> {

    private Context mContext;
    private List<Album> albumList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count;
        public ImageView thumbnail, overflow;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            count = (TextView) view.findViewById(R.id.count);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            overflow = (ImageView) view.findViewById(R.id.overflow);
        }
    }


    public AlbumsAdapter(Context mContext, List<Album> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.album_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Album album = albumList.get(position);
        holder.title.setText(album.getName());
        holder.count.setText(album.getNumOfSongs() + " songs");

        // loading album cover using Glide library
        //Glide.with(mContext).load(album.getThumbnail()).into(holder.thumbnail);

        Picasso.with(mContext).load(album.getThumbnail()).into(holder.thumbnail);

        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, holder.title.getText().toString() + " " + holder.count.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        holder.thumbnail.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {

                Toast.makeText(mContext, holder.title.getText().toString(), Toast.LENGTH_SHORT).show();

                return true;
            }
        });

        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, holder.title.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                showPopupMenu(holder.overflow);
                //    Toast.makeText(mContext, holder.title.getText().toString() + " " + holder.count.getText().toString(), Toast.LENGTH_SHORT).show();
                PopupMenu popup = new PopupMenu(mContext, view);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.menu_album, popup.getMenu());
                popup.setOnMenuItemClickListener(new MyMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.action_add_favourite:
                                Toast.makeText(mContext, holder.title.getText().toString(), Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.action_play_next:

                                // Toast.makeText(mContext, holder.count.getText().toString(), Toast.LENGTH_SHORT).show();

                                Intent emailIntent = new Intent(Intent.ACTION_SENDTO
                                        , Uri.fromParts("mailto", "amrabdelhameedfcis@gmail.com", null));
                                emailIntent.putExtra(Intent.EXTRA_SUBJECT, holder.title.getText().toString());
                                emailIntent.putExtra(Intent.EXTRA_TEXT, holder.count.getText().toString());
                                mContext.startActivity(Intent.createChooser(emailIntent, "Send email..."));

                                return true;
                            default:
                        }
                        return false;
                    }
                });
                popup.show();
            }
        });
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }
}
