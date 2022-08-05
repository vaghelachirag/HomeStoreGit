package com.nebulacompanies.ibo.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nebulacompanies.ibo.ecom.ProductDescription;
import com.nebulacompanies.ibo.ecom.model.SubCategoryProductList;
import com.nebulacompanies.ibo.ecom.utils.AspectRatioImageView;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;
import com.nebulacompanies.ibo.shoppy.R;
import com.nebulacompanies.ibo.util.Session;
import java.util.List;
import java.util.Random;

public class SimilarProductAdapter extends RecyclerView.Adapter<SimilarProductAdapter.ViewHolder> {

    private final Context context;
    List<SubCategoryProductList> subCategoryProductListList;
    Session session;
    public SimilarProductAdapter(Context context, List<SubCategoryProductList> subCategoryProductListList) {
        this.context = context;
        this.subCategoryProductListList = subCategoryProductListList;
        session = new Session(context);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final MyTextViewEcom productName;
        private final MyTextViewEcom shortDesc;
        private final MyTextViewEcom weight;
        private final MyTextViewEcom offerPrice;
        private final MyTextViewEcom OriginalPrice;
        private MyTextViewEcom origPricesign;
        private final MyTextViewEcom priceDiscount;
        AspectRatioImageView imageView;
        LinearLayout laycontent;

        public ViewHolder(View view) {
            super(view);
            productName = view.findViewById(R.id.name);
            shortDesc = view.findViewById(R.id.name_short_decs);
            weight = view.findViewById(R.id.product_weight);
            offerPrice = view.findViewById(R.id.tv_offer_price_value);
            OriginalPrice = view.findViewById(R.id.tv_original_price_value);
            imageView = view.findViewById(R.id.thumbnail);
            origPricesign = view.findViewById(R.id.tv_original_price);
            priceDiscount = view.findViewById(R.id.tv_price_discount);
            laycontent = view.findViewById(R.id.laycontent);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.similar_product_list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SubCategoryProductList product = subCategoryProductListList.get(position);

        holder.OriginalPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.priceDiscount.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.offerPrice.setText(String.valueOf(product.getHomeStorePrice()));
        holder.OriginalPrice.setText(String.valueOf(product.getCategorySalePrice()));
        holder.priceDiscount.setText(String.valueOf(product.getCategoryMRP()));

        holder.shortDesc.setText(product.getCategoryShortDescription());
        holder.productName.setText(product.getCategoryName());
        holder.weight.setText(product.getCategoryWeight());
        holder.weight.setVisibility(TextUtils.isEmpty(product.getCategoryWeight())?View.GONE:View.VISIBLE);
        holder.imageView.setImageDrawable(getRandomDrawbleColor());

        // pass the request as a a parameter to the thumbnail request
        Glide.with(context)
                .load(product.getCategoryMainImage())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(getRandomDrawbleColor())
                .into(holder.imageView);

        holder.laycontent.setOnClickListener(view -> {
            Intent intent = new Intent(context, ProductDescription.class);
            intent.putExtra("product_clicked", "0");
            intent.putExtra("catid", product.getCategoryCategoryId());
            intent.putExtra("ebc_id", product.getCategoryId());
            intent.putExtra("quantity_count", product.getCategoryQuantity());
            intent.putExtra("product_id", product.getCategoryProductId());
            intent.putExtra("product_name", product.getCategoryName());
            intent.putExtra("product_offer_price", String.valueOf(product.getCategorySalePrice()));
            intent.putExtra("product_mrp_price", String.valueOf(product.getCategoryMRP()));
            intent.putExtra("product_desc", product.getCategoryDescription());
            intent.putExtra("product_saving", product.getCategorySaving());
            intent.putExtra("product_return", product.getCategoryReturnPolicy());
            intent.putExtra("product_warranty", product.getCategoryWarranty());
            intent.putExtra("product_quantity", product.getCategoryQuantity());
            intent.putExtra("product_MaxSaleQuantity", product.getCategoryMaxSaleQuantity());
            intent.putExtra("product_short_dec", product.getCategoryShortDescription());
            intent.putExtra("product_SKU", product.getCategorySKU());
            context.startActivity(intent);

        });
    }

    public ColorDrawable[] vibrantLightColorList =
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
        return subCategoryProductListList.size();
    }
}
