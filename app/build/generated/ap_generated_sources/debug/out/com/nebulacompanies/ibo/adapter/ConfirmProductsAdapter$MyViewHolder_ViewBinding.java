// Generated code from Butter Knife. Do not modify!
package com.nebulacompanies.ibo.adapter;

import android.view.View;
import android.widget.ImageView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.cardview.widget.CardView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.nebulacompanies.ibo.ecom.utils.AutoResizeTextView;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;
import com.nebulacompanies.ibo.shoppy.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ConfirmProductsAdapter$MyViewHolder_ViewBinding implements Unbinder {
  private ConfirmProductsAdapter.MyViewHolder target;

  @UiThread
  public ConfirmProductsAdapter$MyViewHolder_ViewBinding(ConfirmProductsAdapter.MyViewHolder target,
      View source) {
    this.target = target;

    target.tvInventoryProName = Utils.findRequiredViewAsType(source, R.id.tv_inventory_product_name, "field 'tvInventoryProName'", MyTextViewEcom.class);
    target.tvInventoryItemPrice = Utils.findRequiredViewAsType(source, R.id.tv_inventory_item_price, "field 'tvInventoryItemPrice'", MyTextViewEcom.class);
    target.tvInventoryProductCount = Utils.findRequiredViewAsType(source, R.id.product_count, "field 'tvInventoryProductCount'", AutoResizeTextView.class);
    target.tvMyCartTablet = Utils.findRequiredViewAsType(source, R.id.tv_mycart_tablet, "field 'tvMyCartTablet'", MyTextViewEcom.class);
    target.thumbnail = Utils.findRequiredViewAsType(source, R.id.thumbnail, "field 'thumbnail'", ImageView.class);
    target.imgDelete = Utils.findRequiredViewAsType(source, R.id.img_my_cart_delete, "field 'imgDelete'", ImageView.class);
    target.cardView = Utils.findRequiredViewAsType(source, R.id.inventory_view, "field 'cardView'", CardView.class);
    target.tvInventoryPV = Utils.findRequiredViewAsType(source, R.id.tv_pv_value, "field 'tvInventoryPV'", MyTextViewEcom.class);
    target.tvInventoryNV = Utils.findRequiredViewAsType(source, R.id.tv_nv_value, "field 'tvInventoryNV'", MyTextViewEcom.class);
    target.tvInventoryBV = Utils.findRequiredViewAsType(source, R.id.tv_bv_value, "field 'tvInventoryBV'", MyTextViewEcom.class);
    target.tvMRPPrice = Utils.findRequiredViewAsType(source, R.id.tv_total_price, "field 'tvMRPPrice'", MyTextViewEcom.class);
    target.txtReward = Utils.findRequiredViewAsType(source, R.id.txtreward, "field 'txtReward'", MyTextViewEcom.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ConfirmProductsAdapter.MyViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvInventoryProName = null;
    target.tvInventoryItemPrice = null;
    target.tvInventoryProductCount = null;
    target.tvMyCartTablet = null;
    target.thumbnail = null;
    target.imgDelete = null;
    target.cardView = null;
    target.tvInventoryPV = null;
    target.tvInventoryNV = null;
    target.tvInventoryBV = null;
    target.tvMRPPrice = null;
    target.txtReward = null;
  }
}
