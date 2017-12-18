package com.szemplinski.checkout;

import com.szemplinski.checkout.promotions.Promotion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by pawel.szemplinski on 2017-12-15.
 */
public class CheckoutService {

    static Logger LOGGER = LoggerFactory.getLogger(CheckoutService.class);

    public void calculateCartTotal(Cart cart) {

        try {
            //which promotions are actually in the cart
            Set<Promotion> promotions = new TreeSet<Promotion>();
            for (CartItem cartItem : cart.getItems().values()) {
                promotions.addAll(cartItem.getPromotions());
            }
            for (Promotion promotion : promotions) {
                promotion.calculatePrice(cart.getItems());
            }
        } catch (Exception e) {
            LOGGER.error("Unexpected exception", e);
        }
    }
}
