// Generated code from Butter Knife. Do not modify!
package com.nebulacompanies.ibo.ecom.adapter;

import android.view.View;
import android.widget.ImageView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.nebulacompanies.ibo.ecom.utils.MyButtonEcom;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;
import com.nebulacompanies.ibo.shoppy.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ViewSaleHistoryAdapter$MyViewHolder_ViewBinding implements Unbinder {
  private ViewSaleHistoryAdapter.MyViewHolder target;

  @UiThread
  public ViewSaleHistoryAdapter$MyViewHolder_ViewBinding(ViewSaleHistoryAdapter.MyViewHolder target,
      View source) {
    this.target = target;

    target.name = Utils.findRequiredViewAsType(source, R.id.name, "field 'name'", MyTextViewEcom.class);
    target.tvPrice = Utils.findRequiredViewAsType(source, R.id.tv_offer_price_value, "field 'tvPrice'", MyTextViewEcom.class);
    target.tvCount = Utils.findRequiredViewAsType(source, R.id.tv_order_quantity, "field 'tvCount'", MyTextViewEcom.class);
    target.thumbnail = Utils.findRequiredViewAsType(source, R.id.thumbnail, "field 'thumbnail'", ImageView.class);
    target.btnRemoveItem = Utils.findRequiredViewAsType(source, R.id.btn_remove_item, "field 'btnRemoveItem'", MyButtonEcom.class);
    target.btnAddItem = Utils.findRequiredViewAsType(source, R.id.btn_add_item, "field 'btnAddItem'", MyButtonEcom.class);
    target.btnOrderCancel = Utils.findRequiredViewAsType(source, R.id.btn_cancel, "field 'btnOrderCancel'", MyButtonEcom.class);
    target.tvCancel = Utils.findRequiredViewAsType(source, R.id.tv_cancel, "field 'tvCancel'", MyTextViewEcom.class);
    target.tvConfirmDate = Utils.findOptionalViewAsType(source, R.id.tv_confirm_date, "field 'tvConfirmDate'", MyTextViewEcom.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ViewSaleHistoryAdapter.MyViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.name = null;
    target.tvPrice = null;
    target.tvCount = null;
    target.thumbnail = null;
    target.btnRemoveItem = null;
    target.btnAddItem = null;
    target.btnOrderCancel = null;
    target.tvCancel = null;
    target.tvConfirmDate = null;
  }
}
