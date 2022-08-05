package com.nebulacompanies.ibo.ecom.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nebulacompanies.ibo.ecom.MyViewOrderActivity;
import com.nebulacompanies.ibo.ecom.TrackOrderActivity;
import com.nebulacompanies.ibo.ecom.model.OrderListModel;
import com.nebulacompanies.ibo.ecom.utils.MyBoldTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.MyButtonEcom;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;
import com.nebulacompanies.ibo.shoppy.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ProductViewHolder> {

    private final Context mCtx;

    private final ArrayList<OrderListModel> orderDetailsModels;

    public OrderListAdapter(Context mCtx, ArrayList<OrderListModel> orderDetailsModels) {
        this.mCtx = mCtx;
        this.orderDetailsModels = orderDetailsModels;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.orders_items, null);
        return new ProductViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        //getting the product of the specified position
        OrderListModel orderListModel = orderDetailsModels.get(position);
        //binding the data with the viewholder views
        holder.tvOrderTitle.setText(orderListModel.getOrderNumber());

        long unixSeconds = orderListModel.getOrderDate(); // convert seconds to milliseconds
        Date date = new Date(unixSeconds * 1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMMM-yyyy (hh:mm a)");
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT-0"));
        String formattedDate = sdf.format(date);
        System.out.println(formattedDate);
        holder.tvOrderDate.setText(formattedDate);

        holder.tvOrderStatusSuccess.setVisibility(View.GONE);

        holder.btnTrackOrder.setOnClickListener(view -> {
            Intent intent = new Intent(mCtx, TrackOrderActivity.class);
             intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            mCtx.startActivity(intent);
        });

        holder.btnViewOrder.setOnClickListener(view -> {
            holder.btnViewOrder.setEnabled(false);

            Intent intent = new Intent(mCtx, MyViewOrderActivity.class);
            String listdata = new Gson().toJson(orderListModel, new TypeToken<OrderListModel>() {
            }.getType());
            intent.putExtra("listdata", listdata);
            intent.putExtra("view_order_data", orderDetailsModels.get(holder.getAdapterPosition()).getOrderDetails());
            intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
            mCtx.startActivity(intent);
            holder.btnViewOrder.setEnabled(true);
        });
    }

    @Override
    public int getItemCount() {
        return orderDetailsModels.size();
    }


    class ProductViewHolder extends RecyclerView.ViewHolder {

        MyTextViewEcom tvOrderDate, tvOrderStatusSuccess, tvProductName;
        MyBoldTextViewEcom tvOrderTitle;
        MyButtonEcom btnTrackOrder, btnViewOrder;
        CardView cvOrderItem;
        //ImageView imgTrack;

        public ProductViewHolder(View itemView) {
            super(itemView);

            cvOrderItem = itemView.findViewById(R.id.cv_order_item);
            tvOrderTitle = itemView.findViewById(R.id.tv_order_title);
            tvOrderStatusSuccess = itemView.findViewById(R.id.tv_order_status_success);
            tvOrderDate = itemView.findViewById(R.id.tv_order_date);
            tvProductName = itemView.findViewById(R.id.tv_product_details);
           // imgTrack = itemView.findViewById(R.id.img_track);
            btnTrackOrder = itemView.findViewById(R.id.btn_track_order);
            btnViewOrder = itemView.findViewById(R.id.btn_view_order);
        }
    }
}