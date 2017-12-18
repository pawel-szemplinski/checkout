package com.szemplinski.checkout.promotions;

import com.szemplinski.checkout.CartItem;
import com.szemplinski.checkout.ItemTypeEnum;

import java.util.Map;

public class Buy2Promo extends Promotion {
    private static final PromoTypeEnum PROMO_TYPE = PromoTypeEnum.B2;
    private static final int MIN_NUMBER_OF_ITEMS_FOR_PROMO = 2;
    private double specialPrice = 0;

    public Buy2Promo(double specialPrice) {
        this.specialPrice = specialPrice;
    }

    void calculatePromoPrice(Map<ItemTypeEnum, CartItem> itemsForCurrentPromotion) {
        for (CartItem cartItem : itemsForCurrentPromotion.values()) {
            int itemCount = cartItem.getItemCount();
            if (itemCount >= MIN_NUMBER_OF_ITEMS_FOR_PROMO) {
                int promotionalItemsCount = itemCount / 2;
                updatePromotionalItemsPriceAndSetPromoted(cartItem, promotionalItemsCount);
            }
        }
    }

    private void updatePromotionalItemsPriceAndSetPromoted(CartItem cartItem, int itemCount) {
        for (int i = 0; i < itemCount * MIN_NUMBER_OF_ITEMS_FOR_PROMO; i++) {
            cartItem.getItems().get(i).setPriceWithPromotion(specialPrice);
            cartItem.getItems().get(i).setPromoted(true);
        }
    }

    public PromoTypeEnum getPromoType() {
        return PROMO_TYPE;
    }
}
