package com.szemplinski.checkout.promotions;

import com.szemplinski.checkout.CartItem;
import com.szemplinski.checkout.Item;
import com.szemplinski.checkout.ItemTypeEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by pawel.szemplinski on 2017-12-16.
 */
public class BuyNXGetKYPromo extends Promotion {

    private static final PromoTypeEnum PROMO_TYPE = PromoTypeEnum.NX_KY;

    // N * X
    private Condition promotionCondition;
    // K * Y
    private Bonus promotionBonus;

    public BuyNXGetKYPromo(Condition promotionCondition, Bonus promotionBonus) {
        this.promotionCondition = promotionCondition;
        this.promotionBonus = promotionBonus;
    }

    @Override
    void calculatePromoPrice(Map<ItemTypeEnum, CartItem> itemsForCurrentPromotion) {
        CartItem cartItem = itemsForCurrentPromotion.get(promotionCondition.itemType);
        int howManyBonusItems = cartItem.getItems().size() / promotionCondition.number;

        if (howManyBonusItems > 0) {
            List<Item> bonusItems = new ArrayList<Item>();
            for (int i = 0; i < promotionBonus.number * howManyBonusItems; i++) {
                bonusItems.add(new Item(0.0, true));
            }
            if (cartItems.containsKey(promotionBonus.itemType)) {
                cartItems.get(promotionBonus.itemType).getItems().addAll(bonusItems);
            } else {
                cartItems.put(promotionBonus.itemType, new CartItem(bonusItems, promotionBonus.itemType));
            }
        }
        markItemsAsPromoted(cartItem, howManyBonusItems);
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

    abstract static class ConditionBonus {
        int number;
        ItemTypeEnum itemType;

        public ConditionBonus(int number, ItemTypeEnum itemType) {
            this.number = number;
            this.itemType = itemType;
        }
    }
    public static class Condition extends ConditionBonus {
        public Condition(int number, ItemTypeEnum itemType) {
            super(number, itemType);
        }
    }
    public static class Bonus extends ConditionBonus {
        public Bonus(int number, ItemTypeEnum itemType) {
            super(number, itemType);
        }
    }
}
