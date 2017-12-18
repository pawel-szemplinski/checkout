package com.szemplinski.checkout.promotions;

import com.szemplinski.checkout.CartItem;
import com.szemplinski.checkout.ItemTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by pawel.szemplinski on 2017-12-15.
 */
public abstract class Promotion implements Comparable<Promotion> {

    static Logger LOGGER = LoggerFactory.getLogger(Promotion.class);

    Map<ItemTypeEnum, CartItem> cartItems;
    Map<ItemTypeEnum, CartItem> itemsForCurrentPromotion = new HashMap<ItemTypeEnum, CartItem>();

    abstract void calculatePromoPrice(Map<ItemTypeEnum, CartItem> itemsForCurrentPromotion);
    public abstract PromoTypeEnum getPromoType();

    public void calculatePrice(Map<ItemTypeEnum, CartItem> cartItems) {
        LOGGER.debug("Calculating prices for promo: " + getPromoType());
        this.cartItems = cartItems;
        for (CartItem cartItem : cartItems.values()) {
            LOGGER.debug("Cart item: " + cartItem);
            if (cartItem.getPromotions().contains(this)) {
                itemsForCurrentPromotion.put(cartItem.getItemType(), cartItem.getCopy());
            }
        }
        if (itemsForCurrentPromotion.size() > 0) {
            calculatePromoPrice(itemsForCurrentPromotion);
        }
    }

    //Promotion are equal if they have equal promotion type
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Promotion promo = (Promotion) obj;
        return promo.getPromoType().equals(this.getPromoType());
    }

    @Override
    public int hashCode() {
        int result = getPromoType().hashCode();
        result = 31 * result;
        return result;
    }

    public int compareTo(Promotion o) {
        return this.getPromoType().priority - o.getPromoType().priority;
    }
}
