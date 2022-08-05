package com.nebulacompanies.ibo.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.nebulacompanies.ibo.Interface.QuntityClick;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.ecom.model.AddToCartModel;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.shoppy.R;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_SUCCESS;


public class BottommSheetDialog extends BottomSheetDialogFragment {
    ImageView imgCloseCart;
    EditText editTextQuantity, editMoreQty;
    RecyclerView recyclerView;
    MyTextViewEcom txtHeader;
    CardView carddata;
    Button btnAddQty;
    String header;
    ArrayList<String> datalistAdd;
    AddRemoveAdapter customAdapter;
    Activity context;
    int productidQty;
    int productidSelected;
    String shoppyUserID;
    String deviceId;
    APIInterface mAPIInterfaceShoppy;
    QuntityClick quntityClick;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new BottomSheetDialog(getContext(), getTheme());
    }
    public BottommSheetDialog(Activity context, String header, int productidQty, int productidSelected,
                              String shoppyUserID, String deviceId, APIInterface mAPIInterfaceShoppy,
                              QuntityClick quntityClick) {
        this.context = context;
        this.header = header;
        this.productidQty = productidQty;
        this.productidSelected = productidSelected;
        this.deviceId = deviceId;
        this.shoppyUserID = shoppyUserID;
        this.mAPIInterfaceShoppy = mAPIInterfaceShoppy;
        this.quntityClick = quntityClick;
        datalistAdd = new ArrayList<>();
        datalistAdd.add("1");
        datalistAdd.add("2");
        datalistAdd.add("3");
        datalistAdd.add("4");
        datalistAdd.add("5");
        datalistAdd.add("6");
        datalistAdd.add("7");
        datalistAdd.add("8");
        datalistAdd.add("9");

        customAdapter = new AddRemoveAdapter(context, datalistAdd, true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {
        View customLayout = inflater.inflate(R.layout.dialog_product_quntity,
                container, false);

        imgCloseCart = customLayout.findViewById(R.id.img_ticket_close);
        editTextQuantity = customLayout.findViewById(R.id.edit_quantity);
        editMoreQty = customLayout.findViewById(R.id.edit_quantity_more);
        recyclerView = customLayout.findViewById(R.id.recycler_view_q);
        txtHeader = customLayout.findViewById(R.id.txt_headerchoose);
        carddata = customLayout.findViewById(R.id.card_subject);
        btnAddQty = customLayout.findViewById(R.id.btn_update);
        carddata.setVisibility(View.VISIBLE);
        editTextQuantity.setVisibility(View.GONE);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        imgCloseCart.setOnClickListener(v -> {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            dismiss();
        });
        btnAddQty.setOnClickListener(v -> {
            btnAddQty.setEnabled(false);
            String qty = editMoreQty.getText().toString();
            if (TextUtils.isEmpty(qty)) {
                Toast.makeText(context, "Please add proper quantity.", Toast.LENGTH_SHORT).show();
                btnAddQty.setEnabled(true);
            } else {
                int Qty = Integer.parseInt(qty);
                if (Qty > 0) {
                    addToCart(Qty);
                } else {
                    Toast.makeText(context, "Please add proper quantity.", Toast.LENGTH_SHORT).show();
                    btnAddQty.setEnabled(true);
                }
            }
        });

        editMoreQty.setText("");
        editMoreQty.requestFocus();
       // txtHeader.setText(header);
        btnAddQty.setEnabled(true);
        recyclerView.setAdapter(customAdapter);

        return customLayout;
    }

    public class AddRemoveAdapter extends RecyclerView.Adapter<AddRemoveAdapter.MyViewHolder> {

        private LayoutInflater inflater;
        ArrayList<String> mdatalist;
        boolean add;

        public AddRemoveAdapter(Context context, ArrayList<String> mdatalist, boolean add) {
            inflater = LayoutInflater.from(context);
            this.mdatalist = mdatalist;
            this.add = add;
        }

        @NonNull
        @NotNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.custom_layout, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            String quantity = mdatalist.get(position);
            holder.serial_number.setText(quantity);
            holder.cardNumber.setOnClickListener(v -> {
                int qty = Integer.parseInt(datalistAdd.get(position));
                addToCart(qty);
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

    public void addToCart(int selQty) {

        if (productidQty == selQty) {
            btnAddQty.setEnabled(true);
        } else {
            if (Utils.isNetworkAvailable(context)) {
                quntityClick.onFullDialogchange(true);
                btnAddQty.setEnabled(true);
                imgCloseCart.performClick();
                Call<AddToCartModel> wsCallingRegistation;
                boolean add = productidQty < selQty;
                Log.d("perform==", add + " = " + productidQty + " < " + selQty);
                int quantity;
                if (add) {
                    quantity = selQty - productidQty;
                    Log.d("perform==", quantity + " = " + selQty + " - " + productidQty);
                } else {
                    quantity = productidQty - selQty;
                    Log.d("perform==", quantity + " = " + productidQty + " - " + selQty);
                }
                wsCallingRegistation = mAPIInterfaceShoppy.getAddToCartModelCall(deviceId, shoppyUserID, productidSelected, quantity, add ? "plus" : "minus", false);

                Log.d("quantityOUT: ", "quantityOUT: " + quantity);
                wsCallingRegistation.enqueue(new Callback<AddToCartModel>() {
                    @Override
                    public void onResponse(Call<AddToCartModel> call, Response<AddToCartModel> response) {
                        if (response.isSuccessful()) {
                            if (response.body().getResponse().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {
                                Log.d("quantityINBEfore: ", "quantityINBEfore: " + quantity);
                                quntityClick.onUpdateList();
                                Log.d("quantityINAFTER: ", "quantityINAFTER: " + quantity);
                            }
                        }
                        quntityClick.onFullDialogchange(false);
                    }

                    @Override
                    public void onFailure(Call<AddToCartModel> call, Throwable t) {
                        Toast.makeText(context, "Please try again", Toast.LENGTH_SHORT).show();
                        quntityClick.onFullDialogchange(false);
                    }
                });
            } else {
                quntityClick.onFullDialogchange(false);
                new Utils().dialogueNoInternet(context);
            }
        }
    }


    public static class BottomSheetDialog extends com.google.android.material.bottomsheet.BottomSheetDialog {

        public BottomSheetDialog(@NonNull Context context) {
            super(context);
        }

        protected BottomSheetDialog(@NonNull Context context, final boolean cancelable,
                                    DialogInterface.OnCancelListener cancelListener) {
            super(context, cancelable, cancelListener);
        }

        public BottomSheetDialog(@NonNull Context context, @StyleRes final int theme) {
            super(context, theme);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            getWindow().setLayout(400 /*our width*/, ViewGroup.LayoutParams.MATCH_PARENT);
        }
    }
}

