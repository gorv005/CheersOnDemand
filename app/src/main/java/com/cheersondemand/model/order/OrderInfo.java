package com.cheersondemand.model.order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by AB on 6/23/2018.
 */

public class OrderInfo {
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
    private Integer subTotal;
    @SerializedName("total")
    @Expose
    private Integer total;
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
    private List<Object> orderItems = null;
    @SerializedName("coupon")
    @Expose
    private String coupon;
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

    public Integer getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Integer subTotal) {
        this.subTotal = subTotal;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
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

    public List<Object> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<Object> orderItems) {
        this.orderItems = orderItems;
    }

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }
}
