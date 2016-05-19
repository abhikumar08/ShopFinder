package com.thecodewolves.abhi.mapdemo;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.thecodewolves.abhi.mapdemo.Model.Shop;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Abhi on 19-05-2016.
 */
public class ShopRecyclerViewAdapter extends RecyclerView.Adapter<ShopRecyclerViewAdapter.ShopViewholder> {

    List<Shop> shops;
    Context context;
    public ShopRecyclerViewAdapter(Context context,List<Shop> shops) {
        this.shops = shops;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return shops.size();
    }

    @Override
    public ShopViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.place_card_layout,parent,false);
        ShopViewholder shopViewholder = new ShopViewholder(view);
        return shopViewholder;

    }

    @Override
    public void onBindViewHolder(ShopViewholder holder, int position) {
        holder.shopName.setText(shops.get(position).getName());
        holder.shopAddress.setText(shops.get(position).getVicinity());

        Picasso.with(context).load(shops.get(position).getIcon()).into(holder.shopImage);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public class ShopViewholder extends RecyclerView.ViewHolder{
        @BindView(R.id.cv)
        CardView cardView;

        @BindView(R.id.shop_name)
        TextView shopName;

        @BindView(R.id.shop_address)
        TextView shopAddress;

        @BindView(R.id.imageView)
        ImageView shopImage;

        public ShopViewholder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}