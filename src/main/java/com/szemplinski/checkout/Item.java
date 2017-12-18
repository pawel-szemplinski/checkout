package com.szemplinski.checkout;

/**
 * Created by pawel.szemplinski on 2017-12-15.
 */
public class Item {

    public Item(double price) {
        this.regularPrice = price;
        this.priceWithPromotion = price;
    }

    public Item(double price, boolean promoted) {
        this.promoted = promoted;
        this.regularPrice = price;
        this.priceWithPromotion = price;
    }

    boolean promoted = false;
    double regularPrice;
    double priceWithPromotion;

    public boolean isPromoted() {
        return promoted;
    }

    public void setPromoted(boolean promoted) {
        this.promoted = promoted;
    }

    public double getRegularPrice() {
        return regularPrice;
    }

    public void setPriceWithPromotion(double priceAfterPromotion) {
        this.priceWithPromotion = priceAfterPromotion;
    }
}
