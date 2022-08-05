package com.nebulacompanies.ibo.Network;

import com.google.gson.JsonObject;
import com.nebulacompanies.ibo.ecom.model.ActivePromoCodeModel;
import com.nebulacompanies.ibo.ecom.model.AddAddressDetail;
import com.nebulacompanies.ibo.ecom.model.AddToCartModel;
import com.nebulacompanies.ibo.ecom.model.AddressData;
import com.nebulacompanies.ibo.ecom.model.AdvertisementImageListEcom;
import com.nebulacompanies.ibo.ecom.model.Baners;
import com.nebulacompanies.ibo.ecom.model.BanersEcom;
import com.nebulacompanies.ibo.ecom.model.BottomBannerModel;
import com.nebulacompanies.ibo.ecom.model.CancelOrderModel;
import com.nebulacompanies.ibo.ecom.model.CardDetailsModelEcom;
import com.nebulacompanies.ibo.ecom.model.CardDwarkaModelEcom;
import com.nebulacompanies.ibo.ecom.model.CartListModelEcom;
import com.nebulacompanies.ibo.ecom.model.CartListModelRemoveEcom;
import com.nebulacompanies.ibo.ecom.model.CartListModelReviewEcom;
import com.nebulacompanies.ibo.ecom.model.CartListTotalCountModelEcom;
import com.nebulacompanies.ibo.ecom.model.CartRECKListModelEcom;
import com.nebulacompanies.ibo.ecom.model.CategoryDetailsListModelEcom;
import com.nebulacompanies.ibo.ecom.model.CategoryListModelEcom;
import com.nebulacompanies.ibo.ecom.model.CreateTicketModel;
import com.nebulacompanies.ibo.ecom.model.CustomerSupportCategory;
import com.nebulacompanies.ibo.ecom.model.CustomerSupportDetailTicket;
import com.nebulacompanies.ibo.ecom.model.CustomerSupportOrder;
import com.nebulacompanies.ibo.ecom.model.CustomerSupportTicket;
import com.nebulacompanies.ibo.ecom.model.DeleteAddressModel;
import com.nebulacompanies.ibo.ecom.model.DeleteCartModel;
import com.nebulacompanies.ibo.ecom.model.EBCBannerModel;
import com.nebulacompanies.ibo.ecom.model.EBCDescriptionModel;
import com.nebulacompanies.ibo.ecom.model.EcomOfferModel;
import com.nebulacompanies.ibo.ecom.model.FreeProductListModel;
import com.nebulacompanies.ibo.ecom.model.FreeProductsModel;
import com.nebulacompanies.ibo.ecom.model.GenerateCouponModel;
import com.nebulacompanies.ibo.ecom.model.LoginModelEcom;
import com.nebulacompanies.ibo.ecom.model.MarkCartDeleteModel;
import com.nebulacompanies.ibo.ecom.model.MinimumPvModel;
import com.nebulacompanies.ibo.ecom.model.NewCategoryProductDetails;
import com.nebulacompanies.ibo.ecom.model.OrderInventoryModel;
import com.nebulacompanies.ibo.ecom.model.OrderListModelEcom;
import com.nebulacompanies.ibo.ecom.model.OrderModel;
import com.nebulacompanies.ibo.ecom.model.OtherProducts;
import com.nebulacompanies.ibo.ecom.model.PaymentSuccessEcomModel;
import com.nebulacompanies.ibo.ecom.model.PickUpAddressData;
import com.nebulacompanies.ibo.ecom.model.PlaceOrderModel;
import com.nebulacompanies.ibo.ecom.model.ProductListModelEcom;
import com.nebulacompanies.ibo.ecom.model.ProfileModelEcom;
import com.nebulacompanies.ibo.ecom.model.PromoCodeModel;
import com.nebulacompanies.ibo.ecom.model.RegisterModelEcom;
import com.nebulacompanies.ibo.ecom.model.ReviewsModel;
import com.nebulacompanies.ibo.ecom.model.SearchModelEcom;
import com.nebulacompanies.ibo.ecom.model.ShowMRPPriceModelEcom;
import com.nebulacompanies.ibo.ecom.model.TrackListModelEcom;
import com.nebulacompanies.ibo.ecom.model.TrackOrderModel;
import com.nebulacompanies.ibo.ecom.model.TrendingProductModel;
import com.nebulacompanies.ibo.ecom.model.WalletFreezeModel;
import com.nebulacompanies.ibo.ecom.model.WalletHistory;
import com.nebulacompanies.ibo.ecom.model.unicommerce.InventoryModel;
import com.nebulacompanies.ibo.ecom.model.unicommerce.InventoryRequestModel;
import com.nebulacompanies.ibo.ecom.model.unicommerce.OrderStatusModel;
import com.nebulacompanies.ibo.ecom.model.unicommerce.OrderStatusRequestModel;
import com.nebulacompanies.ibo.model.AddReviewDetail;
import com.nebulacompanies.ibo.model.AddToConfirmModel;
import com.nebulacompanies.ibo.model.AddVariantModel;
import com.nebulacompanies.ibo.model.ChooseShoppyModel;
import com.nebulacompanies.ibo.model.ConfirmSellModel;
import com.nebulacompanies.ibo.model.DeleteShoppyCartModel;
import com.nebulacompanies.ibo.model.CityDetails;
import com.nebulacompanies.ibo.model.IDCardModel;
import com.nebulacompanies.ibo.model.IboModel;
import com.nebulacompanies.ibo.model.LoginValidate;
import com.nebulacompanies.ibo.model.MyShoppyModel;
import com.nebulacompanies.ibo.model.OrderHistoryModel;
import com.nebulacompanies.ibo.model.RegistationOTP;
import com.nebulacompanies.ibo.model.ShareRefID;
import com.nebulacompanies.ibo.model.StateDetails;
import com.nebulacompanies.ibo.model.TermsConditionModel;
import com.nebulacompanies.ibo.model.TokenModel;
import com.nebulacompanies.ibo.model.VersionCheck;
import com.nebulacompanies.ibo.model.WalletModel;
import com.nebulacompanies.ibo.util.Constants;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Class : APIInterface
 * Details:
 * Create by : Palak Mehta At Nebula Infra space LLP 09-01-2018
 * Modification by :
 */

public interface APIInterface {

    //Testing

    @FormUrlEncoded
    //@Headers("Content-Type:application/json")
    @POST(Constants.WEB_SERVICES.WS_GET_TOKEN)
    Call<TokenModel> getToken(@Field(Constants.WEB_SERVICES_PARAM.KEY_USERNAME_SHOPPY) String username,
                              @Field(Constants.WEB_SERVICES_PARAM.KEY_PASSWORD_) String password);

    @FormUrlEncoded
    @POST(Constants.WEB_SERVICES.WS_POST_TOKEN_KEY)
    Call<JsonObject> postTokenKey(@Field(Constants.WEB_SERVICES_PARAM.KEY_TOKEN) String token,
                                  @Field(Constants.WEB_SERVICES_PARAM.KEY_DEVICE_ID) String deviceid,
                                  @Field(Constants.WEB_SERVICES_PARAM.KEY_IMEI1) String imei1,
                                  @Field(Constants.WEB_SERVICES_PARAM.KEY_IMEI2) String imei2,
                                  @Field(Constants.WEB_SERVICES_PARAM.KEY_USERID) String UserID);

    @GET(Constants.WEB_SERVICES.WS_LOGIN_VALIDATE_KEY)
    Call<LoginValidate> loginValidate(@Query(Constants.WEB_SERVICES_PARAM.KEY_IBO_ID_SHOPPE) String iboId);

    // Generate OTP For Mobile
    @GET(Constants.WEB_SERVICES.FORGOT_OTP_IBO)
    Call<JsonObject> getIBOForgotPasswordOTP(@Query(Constants.WEB_SERVICES_PARAM.KEY_IBO_ID_FORGOT_PASSWORD) String IBOId);

    @FormUrlEncoded
    @POST(Constants.WEB_SERVICES.WS_FORGOT_PASSWORD)
    Call<JsonObject> getChangedPassword(@Field(Constants.WEB_SERVICES_PARAM.KEY_USERNAME) String Username);

    // Verify OTP for Mobile
    @FormUrlEncoded
    @POST(Constants.WEB_SERVICES.FORGOT_OTP_VERIFY_IBO)
    Call<JsonObject> postIBOForgotPasswordOTPVerify(@Field(Constants.WEB_SERVICES_PARAM.KEY_IBO_PHONE_NUMBER_FORGOT_PASSWORD_VERIFY) String phone,
                                                    @Field(Constants.WEB_SERVICES_PARAM.KEY_IBO_OTP_FORGOT_PASSWORD_VERIFY) String otp,
                                                    @Field(Constants.WEB_SERVICES_PARAM.KEY_IBO_ID_FORGOT_PASSWORD_VERIFY) String id,
                                                    @Field(Constants.WEB_SERVICES_PARAM.KEY_IBO_CODE_FORGOT_PASSWORD_VERIFY) String code,
                                                    @Field(Constants.WEB_SERVICES_PARAM.KEY_IBO_PASSWORD_FORGOT_PASSWORD_VERIFY) String password);

    @GET(Constants.WEB_SERVICES.FORGOT_IBO)
    Call<JsonObject> getIBOForgotPassword(@Query(Constants.WEB_SERVICES_PARAM.KEY_IBO_ID_FORGOT_PASSWORD) String IBOId);

    @FormUrlEncoded
    @POST(Constants.WEB_SERVICES.WS_POST_UPDATE_PROFILE_PIC)
    Call<JsonObject> UpdateProfilePic(@Field(Constants.WEB_SERVICES_PARAM.KEY_UPDATE_PIC_TYPE) String Image);


    @GET(Constants.WEB_SERVICES.WS_CITY_LIST)
    Call<CityDetails> getCities(@Query(Constants.WEB_SERVICES_PARAM.KEY_STATE_ID) int StateId);


    @POST(Constants.WEB_SERVICES.WS_GET_HOLIDAY_ACHIEVER_BONUS_SELECTION)
    Call<JsonObject> sendHolidayAchieverBonus(@Query((Constants.WEB_SERVICES_PARAM.KEY_ID)) Integer id,
                                              @Query((Constants.WEB_SERVICES_PARAM.KEY_FLAG)) String flag);


    @GET(Constants.WEB_SERVICES.WS_GET_HOLIDAY_CHARGES)
    Call<JsonObject> getHolidayCharges();


    @GET(Constants.WEB_SERVICES.REGISTATION_OTP)
    Call<RegistationOTP> getRegistationOtp(@Query(Constants.WEB_SERVICES_PARAM.KEY_MOBILE) String MobileNumber);


    @POST(Constants.WEB_SERVICES.WS_POST_SOP)
    Call<JSONObject> PostSop();

    @POST(Constants.WEB_SERVICES.WS_POST_LAT_LONG)
    Call<JSONObject> PostLatLong(@Query(Constants.WEB_SERVICES_PARAM.KEY_LAT) double lat,
                                 @Query(Constants.WEB_SERVICES_PARAM.KEY_LON) double lon);

    //Ecom
    @GET(Constants.WEB_SERVICES.WS_ADVERTISEMENT_IMAGES_ECOM)
    Call<AdvertisementImageListEcom> getAdvertisementListEcom(@Query(Constants.WEB_SERVICES_PARAM.KEY_ISNEBPRO) boolean isneb,
                                                              @Query(Constants.WEB_SERVICES_PARAM.KEY_ISHome) boolean ishome);

    @GET(Constants.WEB_SERVICES.WS_MINIMUM_PV)
    Call<MinimumPvModel> getMinimumPV();

    @GET(Constants.WEB_SERVICES.WS_ADVERTISEMENT_IMAGES_ECOM)
    Call<Baners> getBanersList( @Query(Constants.WEB_SERVICES_PARAM.KEY_ISNEBPRO) boolean isneb,
                                @Query(Constants.WEB_SERVICES_PARAM.KEY_ISHome) boolean ishome);

    @GET(Constants.WEB_SERVICES.WS_GET_PRODUCT_LIST)
    Call<ProductListModelEcom> getProductListEcom();

    @GET(Constants.WEB_SERVICES.CATEGORY_DETAILS_LIST_ECOM)
    Call<CategoryDetailsListModelEcom> getCategoryDetailsList(@Query(Constants.WEB_SERVICES_PARAM.KEY_CATEGORY_ID) Integer catid);

    @GET(Constants.WEB_SERVICES.WS_GET_CATEGORY_LIST)
    Call<CategoryListModelEcom> getCategoryListEcom();


    @GET(Constants.WEB_SERVICES.WS_GET_ORDER_LIST)
    Call<OrderListModelEcom> getOrderListEcom(@Query(Constants.WEB_SERVICES_PARAM.KEY_REGISTER_USER_ID) String userId);

    @GET(Constants.WEB_SERVICES.MY_INVENTORY)
    Call<OrderInventoryModel> getInventoryList(@Query(Constants.WEB_SERVICES_PARAM.KEY_REGISTER_USER_ID) String userId,
                                               @Query(Constants.WEB_SERVICES_PARAM.KEY_IBO_ID_PV) String iboID);

    @GET(Constants.WEB_SERVICES.MY_SALESLIST)
    Call<OrderHistoryModel> getSalesHistoryList(@Query(Constants.WEB_SERVICES_PARAM.KEY_REGISTER_USER_ID) String userId);

    @GET(Constants.WEB_SERVICES.MY_IBONAME)
    Call<IboModel> getIboname(@Query(Constants.WEB_SERVICES_PARAM.KEY_SHOPPY_USER_ID) String userId);


    @GET(Constants.WEB_SERVICES.WS_GET_SHOPPY_LIST)
    Call<ChooseShoppyModel> getShoppyList();

    @GET(Constants.WEB_SERVICES.WS_GET_TERMS_CONDITION)
    Call<TermsConditionModel> getTermsCondition();


    @GET(Constants.WEB_SERVICES.WS_GET_MY_SHOPPY)
    Call<MyShoppyModel> getMyShoppy(@Query(Constants.WEB_SERVICES_PARAM.KEY_SHOPPY_USER_ID_cap) String userID);

    @GET(Constants.WEB_SERVICES.WS_GET_OFFER_LIST)
    Call<EcomOfferModel> getOfferListEcom();

    @GET(Constants.WEB_SERVICES.WS_DELETE_CART)
    Call<MarkCartDeleteModel> deleteCart(@Query(Constants.WEB_SERVICES_PARAM.KEY_USER_ID) String userID);

    @POST(Constants.WEB_SERVICES.WS_PAYTM_CART)
    Call<OrderModel> paytmCart(@Body JSONObject model);

    @GET(Constants.WEB_SERVICES.WS_DELETE_EWALLET)
    Call<MarkCartDeleteModel> deleteEwallet(@Query(Constants.WEB_SERVICES_PARAM.KEY_ID_TICKET_ORDER_NUMBER) String orderid,
                                            @Query(Constants.WEB_SERVICES_PARAM.KEY_USER_SHOPPYID) String iboID,
                                            @Query(Constants.WEB_SERVICES_PARAM.KEY_AMOUNT) String amountid);

    @GET(Constants.WEB_SERVICES.WS_GET_CSUPPORT_CATEGORY)
    Call<CustomerSupportCategory> getSupportCategoryList();

    @GET(Constants.WEB_SERVICES.WS_GET_WALLET_HISTORY)
    Call<WalletHistory> getWallethistory(@Query(Constants.WEB_SERVICES_PARAM.KEY_IBO_ID_PV) String iboid);

    @GET(Constants.WEB_SERVICES.WS_GET_CSUPPORT_ORDER)
    Call<CustomerSupportOrder> getSupportOrderList(@Query(Constants.WEB_SERVICES_PARAM.KEY_USER_ID) String userID);

    @GET(Constants.WEB_SERVICES.WS_GET_CSUPPORT_TICKET_LIST)
    Call<CustomerSupportTicket> getSupporTicketList(@Query(Constants.WEB_SERVICES_PARAM.KEY_USER_ID) String userID,
                                                    @Query(Constants.WEB_SERVICES_PARAM.KEY_ID_TICKET_STATUS) String status,
                                                    @Query(Constants.WEB_SERVICES_PARAM.KEY_ID_TICKET_CATEGORY_ID) String ticketCategoryId);


    @GET(Constants.WEB_SERVICES.WS_GET_VERSION)
    Call<VersionCheck> getVersion();

    @GET(Constants.WEB_SERVICES.WS_STATE_LIST)
    Call<StateDetails> getStates(@Query(Constants.WEB_SERVICES_PARAM.KEY_COUNTRY_NAME) String CountryName);


    @GET(Constants.WEB_SERVICES.WS_GET_CSUPPORT_TICKET_DETAIL_LIST)
    Call<CustomerSupportDetailTicket> getSupporTicketDetailList(@Query(Constants.WEB_SERVICES_PARAM.KEY_ID_TICKET_MASTER_ID) int ticketMasterId);

    //@FormUrlEncoded
    @POST(Constants.WEB_SERVICES.WS_GET_CSUPPORT_TICKET_MESSAGE_LIST)
    @Multipart
    Call<CreateTicketModel> supportMessage(@Part(Constants.WEB_SERVICES_PARAM.KEY_ID_TICKET_ID) RequestBody ticketMasterId,
                                           @Part MultipartBody.Part imageFile,
                                           @Part(Constants.WEB_SERVICES_PARAM.KEY_ID_TICKET_MESSAGE) RequestBody message);

    @POST(Constants.WEB_SERVICES.WS_SAVE_TICKET)
    @Multipart
    Call<CreateTicketModel> createTicket(@Part(Constants.WEB_SERVICES_PARAM.KEY_ID_TICKET_USER_ID) RequestBody UserId,
                                         @Part MultipartBody.Part imageFile,
                                         @Part(Constants.WEB_SERVICES_PARAM.KEY_ID_TICKET_CREATE_CATEGORY_ID) RequestBody TicketCategoryId,
                                         @Part(Constants.WEB_SERVICES_PARAM.KEY_ID_TICKET_SUBJECT) RequestBody Subject,
                                         @Part(Constants.WEB_SERVICES_PARAM.KEY_ID_TICKET_REMARK) RequestBody Remarks,
                                         @Part(Constants.WEB_SERVICES_PARAM.KEY_ID_TICKET_ORDER_NUMBER) RequestBody OrderNumber);

    @GET(Constants.WEB_SERVICES.ADD_VARIANT)
    Call<AddVariantModel> getAddvariantCall(@Query(Constants.WEB_SERVICES_PARAM.KEY_SHOPPY_QUANTITY) Integer quntity,
                                            @Query(Constants.WEB_SERVICES_PARAM.KEY_VARIANTID) Integer variantid);


    @GET(Constants.WEB_SERVICES.ADD_TO_CART_POST)
    Call<AddToCartModel> getAddToCartModelCall(@Query(Constants.WEB_SERVICES_PARAM.KEY_SHOPPY_DEVICE_ID_ECOM) String deviceID,
                                               @Query(Constants.WEB_SERVICES_PARAM.KEY_SHOPPY_USER_ID) String userID,
                                               @Query(Constants.WEB_SERVICES_PARAM.KEY_SHOPPY_PRODUCT_ID) Integer productID,
                                               @Query(Constants.WEB_SERVICES_PARAM.KEY_SHOPPY_QUANTITY) Integer productQuantity,
                                               @Query(Constants.WEB_SERVICES_PARAM.KEY_SHOPPY_CART_FLAG) String CartFlag,
                                               @Query(Constants.WEB_SERVICES_PARAM.KEY_IBO_ID_ISMANDATORY) Boolean ismandatory);


    @POST(Constants.WEB_SERVICES.ADD_TO_CART_POST_NOLOGIN)
    Call<AddToCartModel> getAddToCartModelCallNOLogin(@Query(Constants.WEB_SERVICES_PARAM.KEY_DEVICE_ID_ECOM) String deviceID,
                                                      @Query(Constants.WEB_SERVICES_PARAM.KEY_PRODUCT_ID) Integer productID,
                                                      @Query(Constants.WEB_SERVICES_PARAM.KEY_QUANTITY) Integer productQuantity,
                                                      @Query(Constants.WEB_SERVICES_PARAM.KEY_CART_FLAG) String CartFlag);


    @POST(Constants.WEB_SERVICES.ADD_TO_CONFIRM)
    Call<AddToConfirmModel> confirmOrder(@Query(Constants.WEB_SERVICES_PARAM.KEY_IBO_KEY_ID_NEW) String userid,
                                         @Query(Constants.WEB_SERVICES_PARAM.KEY_CUSTNAME) String custname,
                                         @Query(Constants.WEB_SERVICES_PARAM.KEY_CUSTNUMBER) String custnumber,
                                         @Query(Constants.WEB_SERVICES_PARAM.KEY_IBO_IDD) String iboidd,
                                         @Query(Constants.WEB_SERVICES_PARAM.KEY_TOTAL) String totalprice,
                                         @Body ConfirmSellModel model
    );


    @GET(Constants.WEB_SERVICES.ADD_TO_CART_POST)
    Call<AddToCartModel> getAddToCartModelCallNotification(@Query(Constants.WEB_SERVICES_PARAM.KEY_DEVICE_ID_ECOM) String deviceID,
                                                           @Query(Constants.WEB_SERVICES_PARAM.KEY_USER_ID) String userID,
                                                           @Query(Constants.WEB_SERVICES_PARAM.KEY_PRODUCT_ID) String productID,
                                                           @Query(Constants.WEB_SERVICES_PARAM.KEY_QUANTITY) Integer productQuantity,
                                                           @Query(Constants.WEB_SERVICES_PARAM.KEY_CART_FLAG) String CartFlag,
                                                           @Query(Constants.WEB_SERVICES_PARAM.KEY_IBO_ID_ISMANDATORY) Boolean ismandatory);


    @POST(Constants.WEB_SERVICES.CANCEL_ORDER)
    Call<CancelOrderModel> cancelOrderCall(@Query(Constants.WEB_SERVICES_PARAM.KEY_ORDER_ID_ECOM) String cancelOrderDetailsId,
                                           @Query(Constants.WEB_SERVICES_PARAM.KEY_ORDER_REASON_ECOM) String cancelReason,
                                           @Query(Constants.WEB_SERVICES_PARAM.KEY_ORDER_REMARK_ECOM) String cancelRemark,
                                           @Query(Constants.WEB_SERVICES_PARAM.KEY_ORDER_QUANTITY_ECOM) String cancelQuantity);


    @POST(Constants.WEB_SERVICES.PLACE_ORDER_POST)
    Call<PlaceOrderModel> placeOrder(@Query(Constants.WEB_SERVICES_PARAM.KEY_DEVICE_ID_ECOM) String deviceID);


    @GET(Constants.WEB_SERVICES.DELETE_FROM_CART_POST)
    Call<DeleteShoppyCartModel> getDeleteFromCartModelCall(@Query(Constants.WEB_SERVICES_PARAM.KEY_DEVICE_ID_ECOM) String deviceID,
                                                           @Query(Constants.WEB_SERVICES_PARAM.KEY_IBO_KEY_ID_NEW) String userID,
                                                           @Query(Constants.WEB_SERVICES_PARAM.KEY_PRODUCT_ID) Integer productID
    );

    @GET(Constants.WEB_SERVICES.DELETE_REC_FROM_CART_POST)
    Call<DeleteShoppyCartModel> getDeleteRecFromCartModelCall(@Query(Constants.WEB_SERVICES_PARAM.KEY_DEVICE_ID_ECOM) String deviceID,
                                                              @Query(Constants.WEB_SERVICES_PARAM.KEY_IBO_KEY_ID_NEW) String userID,
                                                              @Query(Constants.WEB_SERVICES_PARAM.KEY_PRODUCT_ID) Integer productID,
                                                              @Query(Constants.WEB_SERVICES_PARAM.KEY_ID_EBC) Integer ID);


    @POST(Constants.WEB_SERVICES.UPDATE_FROM_CART_POST)
    Call<DeleteShoppyCartModel> updateSku(@Query(Constants.WEB_SERVICES_PARAM.KEY_DEVICE_IDD) String deviceID,
                                          @Query(Constants.WEB_SERVICES_PARAM.KEY_REGISTER_USER_ID) String userID,
                                          @Query(Constants.WEB_SERVICES_PARAM.KEY_REGISTER_OLD_ID) Integer oldid,
                                          @Query(Constants.WEB_SERVICES_PARAM.KEY_REGISTER_NEW_ID) Integer newid,
                                          @Query(Constants.WEB_SERVICES_PARAM.KEY_QUANTITY) Integer qty);


    @POST(Constants.WEB_SERVICES.DELETE_FROM_CART_POST_Nologin)
    Call<DeleteCartModel> getDeleteFromCartModelCallNologin(@Query(Constants.WEB_SERVICES_PARAM.KEY_DEVICE_ID_ECOM) String deviceID,
                                                            @Query(Constants.WEB_SERVICES_PARAM.KEY_PRODUCT_ID) Integer productID
    );

    @POST(Constants.WEB_SERVICES.DELETE_FROM_CART_POST_v2)
    Call<DeleteCartModel> getDeleteFromCartModelCallV2(@Query(Constants.WEB_SERVICES_PARAM.KEY_DEVICE_ID_ECOM) String deviceID,
                                                       @Query(Constants.WEB_SERVICES_PARAM.KEY_IBO_KEY_ID_NEW) String userID,
                                                       @Query(Constants.WEB_SERVICES_PARAM.KEY_PRODUCT_ID) Integer productID,
                                                       @Query(Constants.WEB_SERVICES_PARAM.KEY_ID) Integer id);

    @POST(Constants.WEB_SERVICES.ADD_FREE_PRODUCTS)
    Call<FreeProductsModel> submitProducts(@Query(Constants.WEB_SERVICES_PARAM.KEY_IBO_KEY_ID_NEW) String userID,
                                           @Query(Constants.WEB_SERVICES_PARAM.KEY_DEVICE_ID_ECOM) String deviceID,
                                           @Query(Constants.WEB_SERVICES_PARAM.KEY_PROMO_ID) Integer promoId);

    @POST(Constants.WEB_SERVICES.DELETE_ADDRESS_DETAILS)
    Call<DeleteAddressModel> deleteAddressDetails(@Query(Constants.WEB_SERVICES_PARAM.KEY_ADDRESS_ID_ECOM) Integer ID);


    @GET(Constants.WEB_SERVICES.BANERS_LIST_ECOM)
    Call<BanersEcom> getBanersListEcom(@Query(Constants.WEB_SERVICES_PARAM.KEY_PRODUCT_ID) Integer productid);

    @GET(Constants.WEB_SERVICES.PRODUCT_VARIANTS)
    Call<OtherProducts> getProductVariants(@Query(Constants.WEB_SERVICES_PARAM.KEY_PICK_UP_ID_ECOM) Integer pickupid,
                                           @Query(Constants.WEB_SERVICES_PARAM.KEY_ID_EBC) Integer productid);

    @GET(Constants.WEB_SERVICES.ID_CARD)
    Call<IDCardModel> getIDCard(@Query(Constants.WEB_SERVICES_PARAM.KEY_IBO_ID_PV) String iboid);


/*


    @GET(Constants.WEB_SERVICES.MY_CART_LIST_ECOM)
    Call<CartListModelEcom> getMyCartListEcom(@Query(Constants.WEB_SERVICES_PARAM.KEY_DEVICE_ID_ECOM) String deviceID,
                                              @Query(Constants.WEB_SERVICES_PARAM.KEY_IBO_KEY_ID_NEW) String userID);
*/


    @GET(Constants.WEB_SERVICES.MY_CART_LIST_ECOM)
    Call<CartListModelEcom> getMyCartListEcom(@Query(Constants.WEB_SERVICES_PARAM.KEY_DEVICE_ID_ECOM) String deviceID,
                                              @Query(Constants.WEB_SERVICES_PARAM.KEY_IBO_KEY_ID_NEW) String userID,
                                              @Query(Constants.WEB_SERVICES_PARAM.KEY_IBO_KEY_ID_Pickup) int cityId,
                                              @Query(Constants.WEB_SERVICES_PARAM.KEY_SHOPPY_PACK_ID) int shoppyid);

    @GET(Constants.WEB_SERVICES.MY_CART_LIST_RACK_ECOM)
    Call<CartRECKListModelEcom> getMyRackCartListEcom();

    @GET(Constants.WEB_SERVICES.MY_CART_LIST_ECOM_WO_LOGIN)
    Call<CartListModelEcom> getMyCartListEcomWoLogin(@Query(Constants.WEB_SERVICES_PARAM.KEY_DEVICE_ID_ECOM) String deviceID,
                                                     @Query(Constants.WEB_SERVICES_PARAM.KEY_IBO_KEY_ID_Pickup) int cityId);

    @GET(Constants.WEB_SERVICES.MY_CART_WALLET_AMOUNT)
    Call<WalletModel> getMyWalletAmount(@Query(Constants.WEB_SERVICES_PARAM.KEY_USER_SHOPPYID) String iboid);

    @GET(Constants.WEB_SERVICES.MY_BAL_WALLET)
    Call<WalletFreezeModel> getBalWalletAmount(@Query(Constants.WEB_SERVICES_PARAM.KEY_USER_SHOPPYID) String iboid);

    @GET(Constants.WEB_SERVICES.MY_FREE_PRODUCT_LIST_ECOM)
    Call<FreeProductListModel> getMyFreeProduct(@Query(Constants.WEB_SERVICES_PARAM.KEY_IBO_KEY_ID_NEW) String userID,
                                                @Query(Constants.WEB_SERVICES_PARAM.KEY_SUBTOTAL) double subTotal,
                                                @Query(Constants.WEB_SERVICES_PARAM.KEY_TOTALPV) double totalPV);

    @GET(Constants.WEB_SERVICES.MY_CART_REVIEW_LIST_ECOM)
    Call<CartListModelReviewEcom> getMyCartListReviewEcom(@Query(Constants.WEB_SERVICES_PARAM.KEY_REGISTER_USER_ID) String userid,
                                                          @Query(Constants.WEB_SERVICES_PARAM.KEY_PICK_UP_ID_ECOM) String pickupid);

    @GET(Constants.WEB_SERVICES.MY_CART_REMOVE_LIST_ECOM)
    Call<CartListModelRemoveEcom> getMyCartListRemoveEcom(@Query(Constants.WEB_SERVICES_PARAM.KEY_REGISTER_USER_ID) String userid,
                                                          @Query(Constants.WEB_SERVICES_PARAM.KEY_PICK_UP_ID_ECOM) String pickupid);


    @GET(Constants.WEB_SERVICES.MY_CARD_DETAILS_ECOM)
    Call<CardDetailsModelEcom> getCardDetails(@Query(Constants.WEB_SERVICES_PARAM.KEY_IBO_KEY_ID) String userID,
                                              @Query(Constants.WEB_SERVICES_PARAM.KEY_TOTAL_AMOUNT) String totalAmount,
                                              @Query(Constants.WEB_SERVICES_PARAM.KEY_REGISTER_PAYMENT_ID) String paymentID,
                                              @Query(Constants.WEB_SERVICES_PARAM.KEY_SIGNATURE) String signature,
                                              @Query(Constants.WEB_SERVICES_PARAM.KEY_ADDRESS_TYPE_ECOM_PAY) String addressType,
                                              @Query(Constants.WEB_SERVICES_PARAM.KEY_SHIPPING_ADDRESS_ECOM) String shippingAddress,
                                              @Query(Constants.WEB_SERVICES_PARAM.KEY_BILLING_ADDRESS_ECOM) String billingaddress,
                                              @Query(Constants.WEB_SERVICES_PARAM.KEY_PICKUP_POINT_ECOM) String pickuppoint,
                                              @Query(Constants.WEB_SERVICES_PARAM.KEY_PAYMENT_MODE_ECOM) String paymentmode,
                                              @Query(Constants.WEB_SERVICES_PARAM.KEY_PRODUCT_IS_WAIVE_OFF) String isWaiveOff,
                                              @Query(Constants.WEB_SERVICES_PARAM.KEY_USER_PAYMENT) String userpayment);


    @GET(Constants.WEB_SERVICES.MY_CARD_DETAILS_ECOMV2)
    Call<CardDetailsModelEcom> getCardDetailsV2(@Query(Constants.WEB_SERVICES_PARAM.KEY_IBO_KEY_ID) String iboID,
                                                @Query(Constants.WEB_SERVICES_PARAM.KEY_SHOPPY_USER_ID_cap) String userID,
                                                @Query(Constants.WEB_SERVICES_PARAM.KEY_TOTAL_AMOUNT) String totalAmount,
                                                @Query(Constants.WEB_SERVICES_PARAM.KEY_REGISTER_PAYMENT_ID) String paymentID,
                                                @Query(Constants.WEB_SERVICES_PARAM.KEY_SIGNATURE) String signature,
                                                @Query(Constants.WEB_SERVICES_PARAM.KEY_ADDRESS_TYPE_ECOM_PAY) String addressType,
                                                @Query(Constants.WEB_SERVICES_PARAM.KEY_SHIPPING_ADDRESS_ECOM) String shippingAddress,
                                                @Query(Constants.WEB_SERVICES_PARAM.KEY_BILLING_ADDRESS_ECOM) String billingaddress,
                                                @Query(Constants.WEB_SERVICES_PARAM.KEY_PICKUP_POINT_ECOM) String pickuppoint,
                                                @Query(Constants.WEB_SERVICES_PARAM.KEY_PAYMENT_MODE_ECOM) String paymentmode,
                                                @Query(Constants.WEB_SERVICES_PARAM.KEY_PRODUCT_IS_WAIVE_OFF) String isWaiveOff,
                                                @Query(Constants.WEB_SERVICES_PARAM.KEY_USER_PAYMENT) String userpayment,
                                                @Query(Constants.WEB_SERVICES_PARAM.KEY_EWALLET_PAYMENT) String ewallet,
                                                @Query(Constants.WEB_SERVICES_PARAM.KEY_IBO_ID_SHOPPEID) Integer packid,
                                                @Query(Constants.WEB_SERVICES_PARAM.KEY_shippingcharges) Integer shipping
                                                );

    @GET(Constants.WEB_SERVICES.MY_CART_TOTAL_COUNT_LIST_ECOM)
    Call<CartListTotalCountModelEcom> getMyCartTotalCountListEcom(@Query(Constants.WEB_SERVICES_PARAM.KEY_DEVICE_ID_ECOM) String deviceID,
                                                                  @Query(Constants.WEB_SERVICES_PARAM.KEY_IBO_KEY_ID_NEW) String userID);

    @GET(Constants.WEB_SERVICES.WS_GET_TRENDING_LIST)
    Call<TrendingProductModel> getTrendingProduct();

    @GET(Constants.WEB_SERVICES.WS_GET_NEW_LIST)
    Call<TrendingProductModel> getNewProduct();

    @GET(Constants.WEB_SERVICES.ADDRESS_LIST_ECOM)
    Call<AddressData> getAddressListEcom(@Query(Constants.WEB_SERVICES_PARAM.KEY_ID_TICKET_USER_ID) String userId);

    @GET(Constants.WEB_SERVICES.PICK_UP_LIST_ECOM)
    Call<PickUpAddressData> getPickUpListEcom(@Query(Constants.WEB_SERVICES_PARAM.KEY_CITY_ID_ECOM) String cityID);

    @POST(Constants.WEB_SERVICES.PRODUCT_SHARE)
    Call<ShareRefID> getReferenceId(@Query(Constants.WEB_SERVICES_PARAM.KEY_IBO_ID_PV) String ibokeyid,
                                    @Query(Constants.WEB_SERVICES_PARAM.KEY_REGISTER_PRODUCT_ID) int productId,
                                    @Query(Constants.WEB_SERVICES_PARAM.KEY_REF_ID) String refid,
                                    @Query(Constants.WEB_SERVICES_PARAM.KEY_LINK) String link);

    @POST(Constants.WEB_SERVICES.PRODUCT_REVIEW)
    Call<AddReviewDetail> addReview(@Query(Constants.WEB_SERVICES_PARAM.KEY_ID_PRODUCT) Long productId,
                                    @Query(Constants.WEB_SERVICES_PARAM.KEY_USER_ID) String userid,
                                    @Query(Constants.WEB_SERVICES_PARAM.KEY_ID_TITLE) String title,
                                    @Query(Constants.WEB_SERVICES_PARAM.KEY_ID_DESCRIPTION) String description,
                                    @Query(Constants.WEB_SERVICES_PARAM.KEY_ID_RATING) String rating);

    @GET(Constants.WEB_SERVICES.MY_PROFILE_ECOM)
    Call<ProfileModelEcom> getMyProfileEcom();

    @GET(Constants.WEB_SERVICES.TRACK_ORDER_ECOM)
    Call<TrackListModelEcom> getTrackEcom(@Query(Constants.WEB_SERVICES_PARAM.KEY_ORDER_Id) String orderID);


    @GET(Constants.WEB_SERVICES.WS_GET_PROMO_CODES)
    Call<PromoCodeModel> getPromoCodes(@Query(Constants.WEB_SERVICES_PARAM.KEY_IBO_KEY_ID) String userID);

    @GET(Constants.WEB_SERVICES.WS_GET_ACTIVE_CODES)
    Call<ActivePromoCodeModel> getActivePromoCodes(@Query(Constants.WEB_SERVICES_PARAM.KEY_IBO_ID_PV) String userID);

    @GET(Constants.WEB_SERVICES.WS_GET_GENERATE_COUPON)
    Call<GenerateCouponModel> getCoupon();

    @GET(Constants.WEB_SERVICES.MY_MRP_PRICE_ECOM)
    Call<ShowMRPPriceModelEcom> getMRPPriceEcom();


    @POST(Constants.WEB_SERVICES.POST_CODE)
    Call<AddAddressDetail> generatePromocode(@Query(Constants.WEB_SERVICES_PARAM.KEY_IBO_ID_PV) String ibokeyid,
                                             @Query(Constants.WEB_SERVICES_PARAM.KEY_PROMO_MASTER_ID) Integer promoid);

    @POST(Constants.WEB_SERVICES.ADD_ADDRESS_DETAILS)
    Call<AddAddressDetail> AddAddressDetails(@Query(Constants.WEB_SERVICES_PARAM.KEY_MOBILE_ECOM) String mobile,
                                             @Query(Constants.WEB_SERVICES_PARAM.KEY_FULLNAME_ECOM) String fullName,
                                             @Query(Constants.WEB_SERVICES_PARAM.KEY_ADDRESS_ONE_ECOM) String addressLine1,
                                             @Query(Constants.WEB_SERVICES_PARAM.KEY_ADDRESS_TWO_ECOM) String addressLine2,
                                             @Query(Constants.WEB_SERVICES_PARAM.KEY_LANDMARK_ECOM) String landmark,
                                             @Query(Constants.WEB_SERVICES_PARAM.KEY_CITY_ECOM) String city,
                                             @Query(Constants.WEB_SERVICES_PARAM.KEY_STATE_ECOM) String state,
                                             @Query(Constants.WEB_SERVICES_PARAM.KEY_PINCODE_ECOM) String pinCode,
                                             @Query(Constants.WEB_SERVICES_PARAM.KEY_ADDRESS_TYPE_ECOM) String addressType,
                                             @Query(Constants.WEB_SERVICES_PARAM.KEY_DEVICE_ID_ECOM) String deviceId,
                                             @Query(Constants.WEB_SERVICES_PARAM.KEY_ID_TICKET_USER_ID) String userId);

    @POST(Constants.WEB_SERVICES.SUCCESS_UPI)
    Call<AddAddressDetail> successUPI(@Query(Constants.WEB_SERVICES_PARAM.KEY_ID_OrderId) String orderID,
                                      @Query(Constants.WEB_SERVICES_PARAM.KEY_ID_txnID) String txnID,
                                      @Query(Constants.WEB_SERVICES_PARAM.KEY_ID_Approval) String approval,
                                      @Query(Constants.WEB_SERVICES_PARAM.KEY_ID_Response) String response,
                                      @Query(Constants.WEB_SERVICES_PARAM.KEY_ID_Status) String status,
                                      @Query(Constants.WEB_SERVICES_PARAM.KEY_ID_txnREF) String txnRef,
                                      @Query(Constants.WEB_SERVICES_PARAM.KEY_ID_Remarks) String Remarks);


    @POST(Constants.WEB_SERVICES.UPDATE_ADDRESS_DETAILS)
    Call<AddAddressDetail> editAddressDetails(@Query(Constants.WEB_SERVICES_PARAM.KEY_REGISTER_USER_ID) String userId,
                                              @Query(Constants.WEB_SERVICES_PARAM.KEY_MOBILE_ECOM) String mobile,
                                              @Query(Constants.WEB_SERVICES_PARAM.KEY_FULLNAME_ECOM) String fullName,
                                              @Query(Constants.WEB_SERVICES_PARAM.KEY_ADDRESS_ONE_ECOM) String addressLine1,
                                              @Query(Constants.WEB_SERVICES_PARAM.KEY_ADDRESS_TWO_ECOM) String addressLine2,
                                              @Query(Constants.WEB_SERVICES_PARAM.KEY_LANDMARK_ECOM) String landmark,
                                              @Query(Constants.WEB_SERVICES_PARAM.KEY_CITY_ECOM) String city,
                                              @Query(Constants.WEB_SERVICES_PARAM.KEY_STATE_ECOM) String state,
                                              @Query(Constants.WEB_SERVICES_PARAM.KEY_PINCODE_ECOM) String pinCode,
                                              @Query(Constants.WEB_SERVICES_PARAM.KEY_ADDRESS_TYPE_ECOM) String addressType,
                                              @Query(Constants.WEB_SERVICES_PARAM.KEY_ADDRESS_ID_ECOM) Integer id);

    @POST(Constants.WEB_SERVICES.REGISTER_ACCOUNT)
    Call<RegisterModelEcom> registerAccountEcom(@Query(Constants.WEB_SERVICES_PARAM.KEY_REGISTER_FIRST_NAME) String firstName,
                                                @Query(Constants.WEB_SERVICES_PARAM.KEY_REGISTER_LAST_NAME) String lastName,
                                                @Query(Constants.WEB_SERVICES_PARAM.KEY_REGISTER_MOBILE) String mobile,
                                                @Query(Constants.WEB_SERVICES_PARAM.KEY_REGISTER_EMAIL) String email,
                                                @Query(Constants.WEB_SERVICES_PARAM.KEY_REGISTER_GENDER) String gender);

    @GET(Constants.WEB_SERVICES.LOGIN_ACCOUNT)
    Call<LoginModelEcom> loginAccountEcom(@Query(Constants.WEB_SERVICES_PARAM.KEY_REGISTER_MOBILE_NO) String mobile);


    @GET(Constants.WEB_SERVICES.WS_GET_SEARCH)
    Call<SearchModelEcom> getSearch(@Query(Constants.WEB_SERVICES_PARAM.KEY_SEARCH_TEXT_ECOM) String SearchText);


    @GET(Constants.WEB_SERVICES.WS_CATEGORY)
    Call<NewCategoryProductDetails> getCategory(@Query(Constants.WEB_SERVICES_PARAM.KEY_CATEGORY_ID) Integer catid,
                                                @Query(Constants.WEB_SERVICES_PARAM.KEY_CITY_ID) Integer pickupid,
                                                @Query(Constants.WEB_SERVICES_PARAM.KEY_PAGE_INDEX) int PageIndex,
                                                @Query(Constants.WEB_SERVICES_PARAM.KEY_PAGE_LENGTH) int PageLength);


    @POST(Constants.WEB_SERVICES.PAYMENT_SUCESS_ECOM)
    Call<PaymentSuccessEcomModel> paymentSuccessEcom(@Query(Constants.WEB_SERVICES_PARAM.KEY_REGISTER_USER_NAME) String userName,
                                                     @Query(Constants.WEB_SERVICES_PARAM.KEY_REGISTER_PAYMENT_ID) String paymentid,
                                                     @Query(Constants.WEB_SERVICES_PARAM.KEY_REGISTER_ORDER_ID) String orderid,
                                                     @Query(Constants.WEB_SERVICES_PARAM.KEY_SIGNATURE) String signature,
                                                     @Query(Constants.WEB_SERVICES_PARAM.KEY_ADDRESS_TYPE) String addressType,
                                                     @Query(Constants.WEB_SERVICES_PARAM.KEY_SHIPPING_ADDRESS) String shippingAddress,
                                                     @Query(Constants.WEB_SERVICES_PARAM.KEY_BILLING_ADDRESS) String billingAddress,
                                                     @Query(Constants.WEB_SERVICES_PARAM.KEY_PICKUP_POINT) String pickupPoint,
                                                     @Query(Constants.WEB_SERVICES_PARAM.KEY_PAYMENT_MODE) String paymentMode);

    /*Dwarka Holiday*/
    @GET(Constants.WEB_SERVICES.MY_CARD_DETAILS_DWARKA)
    Call<CardDwarkaModelEcom> getCardDetailsDwarka(@Query(Constants.WEB_SERVICES_PARAM.KEY_IBO_KEY_ID) String userID,
                                                   @Query(Constants.WEB_SERVICES_PARAM.KEY_TOTAL_AMOUNT) String totalAmount,
                                                   @Query(Constants.WEB_SERVICES_PARAM.KEY_PRODUCT_IS_WAIVE_OFF) String waiveOff,
                                                   @Query(Constants.WEB_SERVICES_PARAM.KEY_REGISTER_PRODUCT_ID) String productId,
                                                   @Query(Constants.WEB_SERVICES_PARAM.KEY_VERSION) String version);

    @GET(Constants.WEB_SERVICES.DWARKA_UPI)
    Call<CardDwarkaModelEcom> getUPIDwarka(@Query(Constants.WEB_SERVICES_PARAM.KEY_IBO_ID_PV) String userID,
                                           @Query(Constants.WEB_SERVICES_PARAM.KEY_TOTAL_AMOUNT) String totalAmount,
                                           @Query(Constants.WEB_SERVICES_PARAM.KEY_PRODUCT_IS_WAIVE_OFF) String waiveOff,
                                           @Query(Constants.WEB_SERVICES_PARAM.KEY_REGISTER_PRODUCT_ID) String productId);


    @GET(Constants.WEB_SERVICES.DWARKA_TIMEPLAN_UPI)
    Call<CardDwarkaModelEcom> getTimeplanUPIDwarka(@Query(Constants.WEB_SERVICES_PARAM.KEY_IBO_ID_PV) String userID,
                                                   @Query(Constants.WEB_SERVICES_PARAM.KEY_TOTAL_AMOUNT) String totalAmount,
                                                   @Query(Constants.WEB_SERVICES_PARAM.KEY_PRODUCT_IS_WAIVE_OFF) String waiveOff,
                                                   @Query(Constants.WEB_SERVICES_PARAM.KEY_REGISTER_PRODUCT_ID) String productId,
                                                   @Query(Constants.WEB_SERVICES_PARAM.KEY_Paymentplan_ID) String paymentId);


    @POST(Constants.WEB_SERVICES.PAYMENT_SUCESS_UPI)
    Call<AddAddressDetail> paymentUpiSuccess(@Query(Constants.WEB_SERVICES_PARAM.KEY_IBO_ID_PV) String iboID,
                                             @Query(Constants.WEB_SERVICES_PARAM.KEY_UPI_ORDER_ID) String orderid,
                                             @Query(Constants.WEB_SERVICES_PARAM.KEY_ID_txnID) String txnid,
                                             @Query(Constants.WEB_SERVICES_PARAM.KEY_ID_Approval) String approval,
                                             @Query(Constants.WEB_SERVICES_PARAM.KEY_ID_Response) String response,
                                             @Query(Constants.WEB_SERVICES_PARAM.KEY_ID_Status) String status,
                                             @Query(Constants.WEB_SERVICES_PARAM.KEY_ID_txnREF) String txnRef,
                                             @Query(Constants.WEB_SERVICES_PARAM.KEY_ID_Remarks) String remarks);


    @GET(Constants.WEB_SERVICES.EBC_ECOM)
    Call<EBCBannerModel> getSiteProductList(@Query(Constants.WEB_SERVICES_PARAM.KEY_ID_EBC_DECS) String ecomProductDetailsId);


    /*UNI COMMERCE API*/
    @GET(Constants.WEB_SERVICES.UNI_GET_TOKEN)
    Call<JsonObject> getUniCommToken(@Query(Constants.WEB_SERVICES_PARAM.KEY_USERNAME_) String username,
                                     @Query(Constants.WEB_SERVICES_PARAM.KEY_PASSWORD_) String password,
                                     @Query(Constants.WEB_SERVICES_PARAM.KEY_GRANT_TYPE) String GrantType,
                                     @Query(Constants.WEB_SERVICES_PARAM.KEY_CLIENT_ID) String ClientID);

   /*@POST(Constants.WEB_SERVICES.WS_GET_TOKEN)
     Call<JsonObject> getToken(@Field(Constants.WEB_SERVICES_PARAM.KEY_USERNAME_) String username,
                              @Field(Constants.WEB_SERVICES_PARAM.KEY_PASSWORD_) String password,
                              @Field(Constants.WEB_SERVICES_PARAM.KEY_GRANT_TYPE) String GrantType);*/

    @GET(Constants.WEB_SERVICES.EBC_DETAILS_ECOM)
    Call<EBCDescriptionModel> getEBCDetails(@Query(Constants.WEB_SERVICES_PARAM.KEY_ID_EBC) String idEBC);

    @GET(Constants.WEB_SERVICES.EBC_GET_REVIEWS)
    Call<ReviewsModel> getReviews(@Query(Constants.WEB_SERVICES_PARAM.KEY_IBO_KEY_ID_NEW) String idEBC,
                                  @Query(Constants.WEB_SERVICES_PARAM.KEY_PRODUCT_ID_ECOM) String idproduct);

    @GET(Constants.WEB_SERVICES.EBC_DETAILS_ECOM_UCQuantity)
    Call<EBCDescriptionModel> getEBCDetailsWithUCQuantity(@Query(Constants.WEB_SERVICES_PARAM.KEY_ID_EBC) String idEBC,
                                                          @Query(Constants.WEB_SERVICES_PARAM.KEY_PICK_UP_ID_ECOM) String pickupidEBC);

    @GET(Constants.WEB_SERVICES.EBC_DETAILS_ECOM_UCQuantityV2)
    Call<EBCDescriptionModel> getEBCDetailsWithUCQuantityV2(@Query(Constants.WEB_SERVICES_PARAM.KEY_ID_EBC) String idEBC,
                                                            @Query(Constants.WEB_SERVICES_PARAM.KEY_PICK_UP_ID_ECOM) String pickupidEBC);

    @GET(Constants.WEB_SERVICES.BOTTOM_BANNER_ECOM)
    Call<BottomBannerModel> getBottomBanner(@Query(Constants.WEB_SERVICES_PARAM.KEY_PICK_UP_ID_ECOM) String pickupid,
                                            @Query(Constants.WEB_SERVICES_PARAM.KEY_ISNEBPRO) boolean isneb,
                                            @Query(Constants.WEB_SERVICES_PARAM.KEY_ISHome) boolean ishome
                                            );


    @POST(Constants.WEB_SERVICES.UNI_GET_INVENTORY)
    Call<InventoryModel> postInventory(@Body InventoryRequestModel inventoryModel);

    @POST(Constants.WEB_SERVICES.UNI_GET_ORDER_STATUS)
    Call<OrderStatusModel> getOrderStatus(@Body OrderStatusRequestModel orderStatusRequestModel);

    @GET(Constants.WEB_SERVICES.UNI_GET_PDF)
    Call<ResponseBody> getPDF(@Query("invoiceCodes") String invoice);


    @GET(Constants.WEB_SERVICES.WS_GET_INVOICE)
    Call<TrackOrderModel> getInvoice(@Query(Constants.WEB_SERVICES_PARAM.KEY_ORDER_NO) String orderNo);

}
