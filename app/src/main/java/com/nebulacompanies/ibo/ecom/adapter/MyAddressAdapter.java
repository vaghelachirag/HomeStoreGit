package com.nebulacompanies.ibo.ecom.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nebulacompanies.ibo.shoppy.R;
import com.nebulacompanies.ibo.ecom.BillingAddressActivity;
import com.nebulacompanies.ibo.ecom.EditAddressActivity;
import com.nebulacompanies.ibo.ecom.OrderSummaryActivity;
import com.nebulacompanies.ibo.ecom.model.AddressModel;
import com.nebulacompanies.ibo.ecom.utils.MyBoldTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.MyButtonEcom;
import com.nebulacompanies.ibo.ecom.utils.MyItalicTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;

import java.util.ArrayList;

public class MyAddressAdapter extends RecyclerView.Adapter<MyAddressAdapter.MyViewHolder> {
    ArrayList<AddressModel> addressDataList = new ArrayList<>();
    Context context;
    private int lastPosition = -1;
    private int lastSelectedPosition = -1;
    double totalCartAmount;

    public MyAddressAdapter(ArrayList<AddressModel> addressDataList, Context context, double totalCartAmount) {
        this.addressDataList = addressDataList;
        this.context = context;
        this.totalCartAmount = totalCartAmount;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.my_address_list_row, viewGroup, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int i) {
        AddressModel data = addressDataList.get(i);
        viewHolder.name.setText(data.getFullName());
        viewHolder.address_one.setText(data.getAddressLineOne());
        viewHolder.address_two.setText(data.getAddressLineTwo());
        viewHolder.address_three.setText(data.getAddressCity());
        viewHolder.address_four.setText(data.getAddressState() + " " + data.getAddressPincode());
        //setAnimation(viewHolder.parent, i);
        //viewHolder.rdAddress.setChecked(lastSelectedPosition == i);
        viewHolder.rdAddress.setChecked(lastSelectedPosition == i);
        if ((lastSelectedPosition == -1 && i == 0)) {
            viewHolder.rdAddress.setChecked(true);
        }
        viewHolder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addressEdit = new Intent(context, EditAddressActivity.class);
                addressEdit.putExtra("address_id", data.getID());
                addressEdit.putExtra("address_name", data.getFullName());
                addressEdit.putExtra("address_mobile", data.getMobileNo());
                addressEdit.putExtra("address_one", data.getAddressLineOne());
                addressEdit.putExtra("address_two", data.getAddressLineTwo());
                addressEdit.putExtra("address_landmark", data.getAddressLandMark());
                addressEdit.putExtra("address_city", data.getAddressCity());
                addressEdit.putExtra("address_state", data.getAddressState());
                addressEdit.putExtra("address_pincode", data.getAddressPincode());
                addressEdit.putExtra("address_type", data.getAddressType());
                addressEdit.putExtra("address_edit_click", 1);
                addressEdit.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                context.startActivity(addressEdit);
            }
        });

        if (i==0){
            viewHolder.tvAddressDefault.setVisibility( View.VISIBLE );
        }else {
            viewHolder.tvAddressDefault.setVisibility( View.GONE );
        }

       /* viewHolder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(context, OrderSummaryActivity.class);
                login.putExtra("address_position", lastSelectedPosition);
                login.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                context.startActivity(login);

            }
        });*/

        /*viewHolder.rdAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int copyOfLastCheckedPosition = lastSelectedPosition;
                lastSelectedPosition = context.getAdapterPosition();
                notifyItemChanged(copyOfLastCheckedPosition);
                notifyItemChanged(lastSelectedPosition);
            }
        });*/
    }


    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return addressDataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        MyTextViewEcom address_one, address_two, address_three, address_four;
        MyBoldTextViewEcom name;
        MyItalicTextViewEcom tvAddressDefault;
        RelativeLayout parent;
        MyButtonEcom btnSummary, edit;
        RadioButton rdAddress;
        LinearLayout lnAddressDelivery;

        public MyViewHolder(View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            name = itemView.findViewById(R.id.name);
            address_one = itemView.findViewById(R.id.tv_address_one);
            address_two = itemView.findViewById(R.id.tv_address_two);
            address_three = itemView.findViewById(R.id.tv_address_three);
            address_four = itemView.findViewById(R.id.tv_address_four);
            tvAddressDefault = itemView.findViewById(R.id.tv_address_default);
            btnSummary = itemView.findViewById(R.id.add);
            edit = itemView.findViewById(R.id.btn_address_edit);
            rdAddress = itemView.findViewById(R.id.rd_address);
            lnAddressDelivery = itemView.findViewById(R.id.ln_address_delivery);


            rdAddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lastSelectedPosition = getAdapterPosition();
                    notifyDataSetChanged();

                   /* Toast.makeText(context,
                            "selected offer is " + name.getText(),
                            Toast.LENGTH_LONG).show();*/
                }
            });

            btnSummary.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lastSelectedPosition = getAdapterPosition();
                    notifyDataSetChanged();

                    String addressFullName = name.getText().toString();
                    String addressLineOne = address_one.getText().toString();
                    String addressLineTwo = address_two.getText().toString();
                    String addressLineThree = address_three.getText().toString();
                    String addressLineFour = address_four.getText().toString();
                    double totalPrice = totalCartAmount;

                    Log.d("RadioButtonValue:", "RadioButtonValue:" + addressFullName + " " + addressLineOne + " " +
                            addressLineTwo + " " + addressLineThree + " " + addressLineFour+" "+ totalPrice);
                    Intent login = new Intent(context, BillingAddressActivity.class);
                    login.putExtra("addressFullName", addressFullName);
                    login.putExtra("addressLineOne", addressLineOne);
                    login.putExtra("addressLineTwo", addressLineTwo);
                    login.putExtra("addressLineThree", addressLineThree);
                    login.putExtra("addressLineFour", addressLineFour);
                    login.putExtra("totalCartAmount", totalCartAmount);
                    login.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    context.startActivity(login);
                   /* Toast.makeText(context,
                            "selected offer is " + addressName,
                            Toast.LENGTH_LONG).show();*/
                }
            });
        }
    }
}
