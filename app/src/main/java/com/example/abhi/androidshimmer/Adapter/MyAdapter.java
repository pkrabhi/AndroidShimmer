package com.example.abhi.androidshimmer.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.abhi.androidshimmer.Model.Model;
import com.example.abhi.androidshimmer.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    List<Model> modelList;

    public MyAdapter(Context context, List<Model> modelList) {
        this.context = context;
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_layout,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Picasso.get().load(modelList.get(position).getThumbnailUrl())
                .into(holder.thumbnail);

        holder.title.setText(modelList.get(position).getTitle());
        holder.url.setText(modelList.get(position).getUrl());

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView thumbnail;
        TextView url,title;

        public MyViewHolder(View itemView) {
            super(itemView);

            thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
            url = (TextView) itemView.findViewById(R.id.url);
            title = (TextView) itemView.findViewById(R.id.title);
        }
    }
}
