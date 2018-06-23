package com.cheersondemand.model.order.addtocart;

import com.cheersondemand.model.order.updatecart.Coupon;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by GAURAV on 6/23/2018.
 */

public class Order {

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
    private Float subTotal;
    @SerializedName("total")
    @Expose
    private Float total;
    @SerializedName("applied_discount")
    @Expose
    private Integer appliedDiscount;
    @SerializedName("is_gift")
    @Expose
    private Boolean isGift;
    @SerializedName("is_coupon_valid")
    @Expose
    private Boolean isCouponValid;
    @SerializedName("is_address_available")
    @Expose
    private Boolean isAddressAvailable;
    @SerializedName("is_card_added")
    @Expose
    private Boolean isCardAdded;
    @SerializedName("order_items")
    @Expose
    private List<OrderItem> orderItems = null;
    @SerializedName("coupon")
    @Expose
    private Coupon coupon;
    @SerializedName("delivery_address")
    @Expose
    private String deliveryAddress;

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

    public Float getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Float subTotal) {
        this.subTotal = subTotal;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public Integer getAppliedDiscount() {
        return appliedDiscount;
    }

    public void setAppliedDiscount(Integer appliedDiscount) {
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



    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
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
}
