package com.nebulacompanies.ibo.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nebulacompanies.ibo.Interface.OnProductAdded;
import com.nebulacompanies.ibo.ecom.model.OrderInventoryModel;
import com.nebulacompanies.ibo.ecom.utils.AutoResizeTextView;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;
import com.nebulacompanies.ibo.shoppy.R;
import com.nebulacompanies.ibo.view.NebulaEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SellInventoryAdapter extends RecyclerView.Adapter<SellInventoryAdapter.MyViewHolder> implements Filterable {

    private Context context;
    ArrayList<OrderInventoryModel.Datum> detailsListModels;
    AlertDialog dialogDetails;
    AlertDialog.Builder builder;
    ImageView imgClose;
    EditText editTextQuantity;
    RecyclerView recyclerView;
    OnProductAdded onProductAdded;
    int xPos = 0;
    boolean keyboardshowing = false;
    boolean clickRel = false;
    ArrayList<OrderInventoryModel.Datum> FullList;

    @Override
    public Filter getFilter() {
        return Searched_Filter;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_inventory_product_name)
        MyTextViewEcom tvInventoryProName;

        @BindView(R.id.tv_inventory_item_price)
        MyTextViewEcom tvInventoryItemPrice;

        @BindView(R.id.product_count)
        AutoResizeTextView tvInventoryProductCount;

        @BindView(R.id.tv_total_price)
        MyTextViewEcom tvTotalPrice;

        @BindView(R.id.tv_mycart_tablet)
        MyTextViewEcom tvMyCartTablet;

        @BindView(R.id.thumbnail)
        ImageView thumbnail;


        @BindView(R.id.tv_pv_value)
        MyTextViewEcom tvInventoryPV;

        @BindView(R.id.tv_nv_value)
        MyTextViewEcom tvInventoryNV;

        @BindView(R.id.tv_bv_value)
        MyTextViewEcom tvInventoryBV;

        @BindView(R.id.coursesspinner)
        Spinner spin;

        @BindView(R.id.product_qty)
        MyTextViewEcom prodQty;

        @BindView(R.id.rel_spinner)
        RelativeLayout relPurchase;

        @BindView(R.id.edit_product_qty)
        NebulaEditText editQty;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @SuppressLint("HardwareIds")
    public SellInventoryAdapter(Context context, ArrayList<OrderInventoryModel.Datum> detailsListModels, OnProductAdded onProductAdded) {
        this.context = context;
        this.detailsListModels = detailsListModels;
        this.onProductAdded = onProductAdded;
        FullList = new ArrayList<>(detailsListModels);

        alertBoxTicketDetail();
    }

    public void alertBoxTicketDetail() {

        builder = new AlertDialog.Builder(context);

        // set the custom layout
        final View customLayout = LayoutInflater.from(context).inflate(R.layout.dialog_product_quntity, null);
        builder.setView(customLayout);
        imgClose = customLayout.findViewById(R.id.img_ticket_close);
        editTextQuantity = customLayout.findViewById(R.id.edit_quantity);
        recyclerView = customLayout.findViewById(R.id.recycler_view_q);

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        dialogDetails = builder.create();
        dialogDetails.setCancelable(true);

        imgClose.setOnClickListener(v -> dialogDetails.dismiss());

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_inventory_sell_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    ArrayAdapter<CharSequence> langAdapter;

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int mposition) {
        final OrderInventoryModel.Datum datum = detailsListModels.get(mposition);
        int productquantity = datum.getQuantity();
        int pv = (int) datum.getPv();
        int nv = (int) datum.getNv();
        holder.tvInventoryProName.setText(datum.getProductName());
        holder.tvMyCartTablet.setText(datum.getShortDescription());
        //holder.tvMRPPrice.setVisibility(View.INVISIBLE);
        holder.tvTotalPrice.setText(" = " + (datum.getIboPrice() * productquantity));
        holder.tvInventoryItemPrice.setText(String.valueOf(datum.getIboPrice()));
        //pv calc
        holder.tvInventoryPV.setText(String.valueOf(pv));
        holder.tvInventoryNV.setText(String.valueOf(nv));
        //bv calculation
        holder.tvInventoryBV.setText(datum.getBv() + "%");
        holder.tvInventoryProductCount.setText("" + productquantity);
        String purchase = datum.getPurchseQuantity();
        Log.d("qty==", "purchase : " + purchase);
        holder.prodQty.setText(TextUtils.isEmpty(purchase) ? "0" : purchase);
        Glide.with(context)
                .load(datum.getImage()).fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(getRandomDrawbleColor())
                .into(holder.thumbnail);
        holder.editQty.setVisibility(View.GONE);
        holder.prodQty.setVisibility(View.VISIBLE);
        //holder.prodQty.setText(String.valueOf(myCart.getCartQuantity()));
        holder.relPurchase.setOnClickListener(v -> {
            xPos = mposition;
            // showLog("perform==", "relSpinner click");

            // InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (!keyboardshowing) {
                // showLog("perform==", "Software Keyboard was shown");
                // writeToLog("Software Keyboard was shown");
                // } else {
                // showLog("perform==", "Software Keyboard close");
                holder.spin.setSelection(0, false);
                clickRel = true;
                holder.spin.performClick();
            }
        });

        List<String> list = new ArrayList<>();
        list.add("select");
        int limitqty = (productquantity > 9) ? 10 : productquantity;
        for (int i = 0; i <= limitqty; i++) {
            list.add(i > 9 ? "10+" : String.valueOf(i));
        }


        String[] qtySize = new String[list.size()];
        qtySize = list.toArray(qtySize);
        langAdapter = new ArrayAdapter<>(context, R.layout.spinner_text, qtySize);
        langAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        holder.spin.setAdapter(langAdapter);

        String[] finalQtySize = qtySize;
        holder.spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                xPos = mposition;

                if (position > 0) {
                    /*productidSelected = myCart.getProductId();
                    productidQty = myCart.getCartQuantity();*/
                    String quantity = finalQtySize[position];
                    Log.d("qty==", "quantity : " + quantity);
                    if (quantity.equalsIgnoreCase("10+")) {
                        keyboardshowing = true;
                        holder.editQty.setVisibility(View.VISIBLE);
                        holder.prodQty.setVisibility(View.GONE);
                        holder.editQty.setText("");
                        holder.editQty.requestFocus();
                        InputMethodManager inputMethodManager =
                                (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.toggleSoftInputFromWindow(
                                holder.editQty.getApplicationWindowToken(),
                                InputMethodManager.SHOW_FORCED, 0);
                    } else
                        onProductAdded.onProductSelect(detailsListModels.get(mposition), quantity, mposition);

                } else {
                       /* if (myCart.isMandatory()) {
                            if (position > productidQty)
                                addToCart(position);
                        } else
                            addToCart(position);*/
                    keyboardshowing = false;
                    //   }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // showLog("perform==", "onNothingSelected ");
                if (clickRel)
                    clickRel = false;
                //  keyboardshowing = false;
            }
        });


        holder.editQty.setKeyImeChangeListener((keyCode, event) -> {
           // showLog("perform==", "Back press");

            if (KeyEvent.KEYCODE_BACK == event.getKeyCode()) {
                keyboardshowing = false;
                //  clickRel=false;
                // do something
                holder.editQty.setVisibility(View.GONE);
                holder.prodQty.setVisibility(View.VISIBLE);
                holder.prodQty.setText(String.valueOf(productquantity));

            }
        });
        holder.editQty.setFilters(new InputFilter[]{new InputFilterMinMax("0", String.valueOf(productquantity))});

        holder.editQty.setOnEditorActionListener((v, actionId, event) -> {
           // showLog("perform==", actionId + " :: " + event);
            if ((actionId == EditorInfo.IME_ACTION_DONE)) {
                keyboardshowing = false;
                // clickRel=false;
                //do here your stuff
                String qty = holder.editQty.getText().toString();
                if (TextUtils.isEmpty(qty)) {
                    holder.editQty.setVisibility(View.GONE);
                    holder.prodQty.setVisibility(View.VISIBLE);
                    holder.prodQty.setText(String.valueOf(productquantity));
                } else {
                    int q = Integer.parseInt(qty);
                    if (q > 0) {
                        if (q > productquantity) {
                            holder.editQty.setVisibility(View.GONE);
                            holder.prodQty.setVisibility(View.VISIBLE);
                            holder.prodQty.setText(String.valueOf(productquantity));
                        } else {
                            onProductAdded.onProductSelect(detailsListModels.get(mposition), String.valueOf(q), mposition);

                        }
                    } else {
                        holder.editQty.setVisibility(View.GONE);
                        holder.prodQty.setVisibility(View.VISIBLE);
                        holder.prodQty.setText(String.valueOf(productquantity));
                    }
                }
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                return true;
            }
            return false;
        });

       /* ArrayList<String> datalistOrig = new ArrayList<>();
        for (int i = 0; i <= quantity; i++) {
            datalistOrig.add(String.valueOf(i));
        }
        ArrayList<String> datalist = new ArrayList<>();
        datalist.addAll(datalistOrig);
        langAdapter = new ArrayAdapter<>(context, R.layout.spinner_text, qtySize);
        langAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        holder.spin.setAdapter(langAdapter);*/

       /* holder.relPurchase.setOnClickListener(v -> {
            editTextQuantity.requestFocus();
            ArrayList<String> datalistOrig = new ArrayList<>();
            for (int i = 0; i <= quantity; i++) {
                datalistOrig.add(String.valueOf(i));
            }
            ArrayList<String> datalist = new ArrayList<>();
            datalist.addAll(datalistOrig);
            editTextQuantity.setText("");
            CustomAdapter customAdapter = new CustomAdapter(context, datalist, holder.textPurchaseQuantity, dialogDetails, mposition);
            recyclerView.setAdapter(customAdapter);
            editTextQuantity.setFilters(new InputFilter[]{new InputFilterMinMax("0", String.valueOf(quantity))});

            editTextQuantity.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    datalist.clear();
                    if (TextUtils.isEmpty(s.toString())) {

                        datalist.addAll(datalistOrig);
                    } else {
                        int start = Integer.parseInt(s.toString());
                        for (int i = start; i <= quantity; i++) {
                            datalist.add(String.valueOf(i));
                        }
                    }
                  *//*  CustomAdapter customAdapter = new CustomAdapter(context, datalist, holder.textPurchaseQuantity, dialogDetails, position);
                    recyclerView.setAdapter(customAdapter);*//*
                    customAdapter.notifyDataSetChanged();
                }
            });
            dialogDetails.show();
        });*/
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


    public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

        private LayoutInflater inflater;
        private Context context;
        ArrayList<String> mdatalist;
        MyTextViewEcom textPurchaseQuantity;
        AlertDialog dialogDetails;
        int pos;

        public CustomAdapter(Context context, ArrayList<String> mdatalist, MyTextViewEcom textPurchaseQuantity, AlertDialog dialogDetails, int pos) {
            inflater = LayoutInflater.from(context);
            this.context = context;
            this.mdatalist = mdatalist;
            this.textPurchaseQuantity = textPurchaseQuantity;
            this.dialogDetails = dialogDetails;
            this.pos = pos;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.custom_layout, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            String quantity = mdatalist.get(position);
            holder.serial_number.setText(quantity);
            holder.cardNumber.setOnClickListener(v -> {
                Log.d("Purchase:: ", "Add q: " + quantity + " at " + pos);
                //detailsListModels.get(pos).setPurchseQuantity(quantity);
                onProductAdded.onProductSelect(detailsListModels.get(pos), quantity, pos);
                //textPurchaseQuantity.setText(quantity);
                dialogDetails.dismiss();
            });
        }

        @Override
        public int getItemCount() {
            return mdatalist.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            MyTextViewEcom serial_number;
            CardView cardNumber;

            public MyViewHolder(View itemView) {
                super(itemView);
                serial_number = itemView.findViewById(R.id.tv_subject);
                cardNumber = itemView.findViewById(R.id.card_subject);
            }
        }
    }

    public class InputFilterMinMax implements InputFilter {

        private int min, max;

        public InputFilterMinMax(int min, int max) {
            this.min = min;
            this.max = max;
        }

        public InputFilterMinMax(String min, String max) {
            this.min = Integer.parseInt(min);
            this.max = Integer.parseInt(max);
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            try {
                int input = Integer.parseInt(dest.toString() + source.toString());
                if (isInRange(min, max, input))
                    return null;
                else {
                    Toast.makeText(context, "More than available quantity.", Toast.LENGTH_SHORT).show();
                    return "";
                }
            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
            }
            return "";
        }

        private boolean isInRange(int a, int b, int c) {
            return b > a ? c >= a && c <= b : c >= b && c <= a;
        }
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
}
