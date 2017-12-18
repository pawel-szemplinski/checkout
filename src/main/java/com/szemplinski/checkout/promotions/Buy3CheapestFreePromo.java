package com.szemplinski.checkout.promotions;

import com.szemplinski.checkout.CartItem;
import com.szemplinski.checkout.Item;
import com.szemplinski.checkout.ItemTypeEnum;

import java.util.*;

/**
 * Created by pawel.szemplinski on 2017-12-16.
 */
public class Buy3CheapestFreePromo extends Promotion{
    private static final PromoTypeEnum PROMO_TYPE = PromoTypeEnum.B3_CHEAPEST_FREE;
    private static final int NUMBER_OF_ITEMS_FOR_PROMO = 3;

    Set<ItemTypeEnum> promotedItemTypesSet = new HashSet<ItemTypeEnum>();
    TreeSet<Item> promotedItems = new TreeSet<Item>(CartItem.getRegularPriceComparator());

    public Buy3CheapestFreePromo(Set<ItemTypeEnum> promotedItemTypesSet) {
        this.promotedItemTypesSet = promotedItemTypesSet;
    }

    @Override
    void calculatePromoPrice(Map<ItemTypeEnum, CartItem> itemsForCurrentPromotion) {
        int numberOfPromotedSets = 0;
        while (true) {
            for (CartItem cartItem : itemsForCurrentPromotion.values()) {
                if (promotedItemTypesSet.contains(cartItem.getItemType())) {
                    if (cartItem.getItems().size() > numberOfPromotedSets) {
                        promotedItems.add(cartItem.getItems().get(numberOfPromotedSets));
                    }
                }
            }
            //there should always be 3 items eligable for promotion
            if (promotedItems.size() == NUMBER_OF_ITEMS_FOR_PROMO) {
                zeroFirsItemPriceAndMarkItemsPromoted();
                promotedItems.clear();
                numberOfPromotedSets++;
            } else {
                break;
            }
        }
    }

    private void zeroFirsItemPriceAndMarkItemsPromoted() {
        promotedItems.first().setPriceWithPromotion(0);
        //mark all 3 promoted
        for (Item item : promotedItems) {
            item.setPromoted(true);
        }

    }

    @Override
    public PromoTypeEnum getPromoType() {
        return PROMO_TYPE;
    }
}
