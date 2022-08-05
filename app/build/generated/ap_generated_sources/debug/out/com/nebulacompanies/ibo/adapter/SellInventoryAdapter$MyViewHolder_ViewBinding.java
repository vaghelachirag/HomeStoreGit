// Generated code from Butter Knife. Do not modify!
package com.nebulacompanies.ibo.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.nebulacompanies.ibo.ecom.utils.AutoResizeTextView;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;
import com.nebulacompanies.ibo.shoppy.R;
import com.nebulacompanies.ibo.view.NebulaEditText;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SellInventoryAdapter$MyViewHolder_ViewBinding implements Unbinder {
  private SellInventoryAdapter.MyViewHolder target;

  @UiThread
  public SellInventoryAdapter$MyViewHolder_ViewBinding(SellInventoryAdapter.MyViewHolder target,
      View source) {
    this.target = target;

    target.tvInventoryProName = Utils.findRequiredViewAsType(source, R.id.tv_inventory_product_name, "field 'tvInventoryProName'", MyTextViewEcom.class);
    target.tvInventoryItemPrice = Utils.findRequiredViewAsType(source, R.id.tv_inventory_item_price, "field 'tvInventoryItemPrice'", MyTextViewEcom.class);
    target.tvInventoryProductCount = Utils.findRequiredViewAsType(source, R.id.product_count, "field 'tvInventoryProductCount'", AutoResizeTextView.class);
    target.tvTotalPrice = Utils.findRequiredViewAsType(source, R.id.tv_total_price, "field 'tvTotalPrice'", MyTextViewEcom.class);
    target.tvMyCartTablet = Utils.findRequiredViewAsType(source, R.id.tv_mycart_tablet, "field 'tvMyCartTablet'", MyTextViewEcom.class);
    target.thumbnail = Utils.findRequiredViewAsType(source, R.id.thumbnail, "field 'thumbnail'", ImageView.class);
    target.tvInventoryPV = Utils.findRequiredViewAsType(source, R.id.tv_pv_value, "field 'tvInventoryPV'", MyTextViewEcom.class);
    target.tvInventoryNV = Utils.findRequiredViewAsType(source, R.id.tv_nv_value, "field 'tvInventoryNV'", MyTextViewEcom.class);
    target.tvInventoryBV = Utils.findRequiredViewAsType(source, R.id.tv_bv_value, "field 'tvInventoryBV'", MyTextViewEcom.class);
    target.spin = Utils.findRequiredViewAsType(source, R.id.coursesspinner, "field 'spin'", Spinner.class);
    target.prodQty = Utils.findRequiredViewAsType(source, R.id.product_qty, "field 'prodQty'", MyTextViewEcom.class);
    target.relPurchase = Utils.findRequiredViewAsType(source, R.id.rel_spinner, "field 'relPurchase'", RelativeLayout.class);
    target.editQty = Utils.findRequiredViewAsType(source, R.id.edit_product_qty, "field 'editQty'", NebulaEditText.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SellInventoryAdapter.MyViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvInventoryProName = null;
    target.tvInventoryItemPrice = null;
    target.tvInventoryProductCount = null;
    target.tvTotalPrice = null;
    target.tvMyCartTablet = null;
    target.thumbnail = null;
    target.tvInventoryPV = null;
    target.tvInventoryNV = null;
    target.tvInventoryBV = null;
    target.spin = null;
    target.prodQty = null;
    target.relPurchase = null;
    target.editQty = null;
  }
}
