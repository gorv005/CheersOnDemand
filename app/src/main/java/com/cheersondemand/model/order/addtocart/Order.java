package com.cheersondemand.model.order.addtocart;

import com.cheersondemand.model.DeliveryAddress;
import com.cheersondemand.model.order.OrderHistory;
import com.cheersondemand.model.order.updatecart.Coupon;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by AB on 6/23/2018.
 */

public class Order implements Serializable{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("order_number")
    @Expose
    private String orderNumber;
    @SerializedName("order_date")
    @Expose
    private String orderDate;
    @SerializedName("sub_total")
    @Expose
    private Double subTotal;
    @SerializedName("tax")
    @Expose
    private String tax;
    @SerializedName("total")
    @Expose
    private Double total;
    @SerializedName("applied_discount")
    @Expose
    private Double appliedDiscount;
    @SerializedName("is_gift")
    @Expose
    private Boolean isGift;
    @SerializedName("is_coupon_valid")
    @Expose
    private Boolean isCouponValid;
    @SerializedName("is_address_available")
    @Expose
    private Boolean isAddressAvailable;
    @SerializedName("delivered_on")
    @Expose
    private String deliveredOn;
    @SerializedName("is_store_open")
    @Expose
    private Boolean isStoreOpen;
    @SerializedName("is_card_added")
    @Expose
    private Boolean isCardAdded;
    @SerializedName("order_history")
    @Expose
    private List<OrderHistory> orderHistory = null;
    @SerializedName("order_items")
    @Expose
    private List<OrderItem> orderItems = null;
    @SerializedName("coupon")
    @Expose
    private Coupon coupon;
    @SerializedName("delivery_address")
    @Expose
    private DeliveryAddress deliveryAddress;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getAppliedDiscount() {
        return appliedDiscount;
    }

    public void setAppliedDiscount(Double appliedDiscount) {
        this.appliedDiscount = appliedDiscount;
    }

    public Boolean getIsGift() {
        return isGift;
    }

    public void setIsGift(Boolean isGift) {
        this.isGift = isGift;
    }

    public Boolean getIsCouponValid() {
        return isCouponValid;
    }

    public void setIsCouponValid(Boolean isCouponValid) {
        this.isCouponValid = isCouponValid;
    }

    public Boolean getIsAddressAvailable() {
        return isAddressAvailable;
    }

    public void setIsAddressAvailable(Boolean isAddressAvailable) {
        this.isAddressAvailable = isAddressAvailable;
    }

    public Boolean getIsCardAdded() {
        return isCardAdded;
    }

    public void setIsCardAdded(Boolean isCardAdded) {
        this.isCardAdded = isCardAdded;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public String getDeliveredOn() {
        return deliveredOn;
    }

    public void setDeliveredOn(String deliveredOn) {
        this.deliveredOn = deliveredOn;
    }

    public DeliveryAddress getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(DeliveryAddress deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public Boolean getGift() {
        return isGift;
    }

    public void setGift(Boolean gift) {
        isGift = gift;
    }

    public Boolean getCouponValid() {
        return isCouponValid;
    }

    public void setCouponValid(Boolean couponValid) {
        isCouponValid = couponValid;
    }

    public Boolean getAddressAvailable() {
        return isAddressAvailable;
    }

    public void setAddressAvailable(Boolean addressAvailable) {
        isAddressAvailable = addressAvailable;
    }

    public Boolean getCardAdded() {
        return isCardAdded;
    }

    public void setCardAdded(Boolean cardAdded) {
        isCardAdded = cardAdded;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    public Boolean getStoreOpen() {
        return isStoreOpen;
    }

    public void setStoreOpen(Boolean storeOpen) {
        isStoreOpen = storeOpen;
    }

    public List<OrderHistory> getOrderHistory() {
        return orderHistory;
    }

    public void setOrderHistory(List<OrderHistory> orderHistory) {
        this.orderHistory = orderHistory;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }
}
