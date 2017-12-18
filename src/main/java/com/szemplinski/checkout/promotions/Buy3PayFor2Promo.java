package com.szemplinski.checkout.promotions;

import com.szemplinski.checkout.CartItem;
import com.szemplinski.checkout.ItemTypeEnum;

import java.util.Map;

/**
 * Created by pawel.szemplinski on 2017-12-16.
 */
public class Buy3PayFor2Promo extends Promotion {

    private static final PromoTypeEnum PROMO_TYPE = PromoTypeEnum.B3P2;
    private static final int MIN_NUMBER_OF_ITEMS_FOR_PROMO = 3;

    void calculatePromoPrice(Map<ItemTypeEnum, CartItem> itemsForCurrentPromotion) {
        for (CartItem cartItem : itemsForCurrentPromotion.values()) {
            int itemCount = cartItem.getItemCount();
            if (itemCount >= MIN_NUMBER_OF_ITEMS_FOR_PROMO) {
                int promotionalItemsCount = itemCount / MIN_NUMBER_OF_ITEMS_FOR_PROMO;
                zeroPromotionalItemsPrice(cartItem, promotionalItemsCount);
                markItemsAsPromoted(cartItem, promotionalItemsCount * MIN_NUMBER_OF_ITEMS_FOR_PROMO);
            }
        }
    }

    private void zeroPromotionalItemsPrice(CartItem cartItem, int itemCount) {
        for (int i = 0; i < itemCount; i++) {
            cartItem.getItems().get(i).setPriceWithPromotion(0);
        }
    }

    private void markItemsAsPromoted(CartItem cartItem, int itemCount) {
        for (int i = 0; i < itemCount; i++) {
            cartItem.getItems().get(i).setPromoted(true);
        }
    }

    @Override
    public PromoTypeEnum getPromoType() {
        return PROMO_TYPE;
    }
}
