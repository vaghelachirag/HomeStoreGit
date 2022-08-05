package com.nebulacompanies.ibo.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nebulacompanies.ibo.ecom.model.OrderInventoryModel;
import com.nebulacompanies.ibo.ecom.utils.AutoResizeTextView;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;
import com.nebulacompanies.ibo.shoppy.R;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyInventoryAdapter extends RecyclerView.Adapter<MyInventoryAdapter.MyViewHolder> implements Filterable {

    private Context context;
    ArrayList<OrderInventoryModel.Datum> detailsListModels;
    ArrayList<OrderInventoryModel.Datum> FullList;
    @Override
    public Filter getFilter() {
        return Searched_Filter;
    }

    private final Filter Searched_Filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<OrderInventoryModel.Datum> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(FullList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (OrderInventoryModel.Datum item : FullList) {
                    if (item.getProductName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            detailsListModels.clear();
            detailsListModels.addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }
    };
    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_inventory_product_name)
        MyTextViewEcom tvInventoryProName;

        @BindView(R.id.tv_inventory_item_price)
        MyTextViewEcom tvInventoryItemPrice;

        @BindView(R.id.product_count)
        AutoResizeTextView tvInventoryProductCount;

        @BindView(R.id.tv_mycart_tablet)
        MyTextViewEcom tvMyCartTablet;

        @BindView(R.id.thumbnail)
        ImageView thumbnail;

        @BindView(R.id.img_my_cart_delete)
        ImageView imgDelete;

        @BindView(R.id.inventory_view)
        CardView cardView;

        @BindView(R.id.tv_pv_value)
        MyTextViewEcom tvInventoryPV;

        @BindView(R.id.tv_nv_value)
        MyTextViewEcom tvInventoryNV;

        @BindView(R.id.tv_bv_value)
        MyTextViewEcom tvInventoryBV;

        @BindView(R.id.tv_total_price)
        MyTextViewEcom tvMRPPrice;

        @BindView(R.id.txtreward)
        MyTextViewEcom txtReward;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @SuppressLint("HardwareIds")
    public MyInventoryAdapter(Context context, ArrayList<OrderInventoryModel.Datum> detailsListModels) {
        this.context = context;
        this.detailsListModels = detailsListModels;
        FullList = new ArrayList<>(detailsListModels);
    }

    @Override
    public MyInventoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_inventory_list_item, parent, false);

        return new MyInventoryAdapter.MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyInventoryAdapter.MyViewHolder holder, int position) {
        OrderInventoryModel.Datum datum = detailsListModels.get(position);
        int quantity = datum.getQuantity();
        int pv = (int) datum.getPv();
        int nv = (int) datum.getNv();
        holder.tvInventoryProName.setText(datum.getProductName());
        holder.tvMyCartTablet.setText(datum.getShortDescription());
        holder.tvMRPPrice.setText(" = " + datum.getHomeStorePrice()* quantity);
        holder.tvInventoryItemPrice.setText(String.valueOf(datum.getHomeStorePrice()));
        //pv calc
        holder.tvInventoryPV.setText(String.valueOf(quantity * pv));
        holder.tvInventoryNV.setText(String.valueOf(quantity * nv));
        //bv calculation
        holder.tvInventoryBV.setText(datum.getBv() +"%");
        holder.tvInventoryProductCount.setText(String.valueOf(datum.getQuantity()));

        Glide.with(context)
                .load(datum.getImage()).fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(getRandomDrawbleColor())
                .into(holder.thumbnail);
    }

    private ColorDrawable[] vibrantLightColorList =
            {
                    new ColorDrawable(Color.parseColor("#9ACCCD")), new ColorDrawable(Color.parseColor("#8FD8A0")),
                    new ColorDrawable(Color.parseColor("#CBD890")), new ColorDrawable(Color.parseColor("#DACC8F")),
                    new ColorDrawable(Color.parseColor("#D9A790")), new ColorDrawable(Color.parseColor("#D18FD9")),
                    new ColorDrawable(Color.parseColor("#FF6772")), new ColorDrawable(Color.parseColor("#DDFB5C"))
            };


    public ColorDrawable getRandomDrawbleColor() {
        int idx = new Random().nextInt(vibrantLightColorList.length);
        return vibrantLightColorList[idx];
    }


    @Override
    public int getItemCount() {
        return detailsListModels.size();
    }


}
