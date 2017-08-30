package com.therichclass.marquant.devit;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.therichclass.marquant.devit.Controller.DetailActivity;
import com.therichclass.marquant.devit.Model.Item;

import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by marquant on 8/28/2017.
 */

public class DevAdapter extends RecyclerView.Adapter<DevAdapter.ViewHolder> {
    private List<Item> items;
    private Context context;

    public DevAdapter(Context applicationContext, List<Item> itemArrayList) {
        this.context = applicationContext;
        this.items = itemArrayList;
    }
    @Override
    public DevAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dev_home_page, viewGroup, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(DevAdapter.ViewHolder viewHolder, int i) {
        viewHolder.title.setText(items.get(i).getLogin());

        Glide.with(context)
                .load(items.get(i).getAvatarUrl())
                .placeholder(R.drawable.laptop)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .bitmapTransform(new CropCircleTransformation(context))
                .into(viewHolder.imageView);
}
    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private ImageView imageView;
        public ViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.user_name);
            imageView = (ImageView) view.findViewById(R.id.image_home_page);

            //on item click
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        Item clickedDataItem = items.get(pos);
                        Intent intent = new Intent(context, DetailActivity.class);
                        intent.putExtra("login", items.get(pos).getLogin());
                        intent.putExtra("html_url", items.get(pos).getHtmlUrl());
                        intent.putExtra("avatar_url", items.get(pos).getAvatarUrl());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        //Toast.makeText(v.getContext(), "You clicked " + pos, Toast.LENGTH_SHORT).show();
                    }
                }

            });
        }
    }}
