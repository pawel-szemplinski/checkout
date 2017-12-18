package com.szemplinski.checkout;

import com.szemplinski.checkout.promotions.Promotion;

import java.util.*;

/**
 * Created by pawel.szemplinski on 2017-12-15.
 */
public class CartItem {

    List<Item> items = new ArrayList<Item>();
    ItemTypeEnum itemType;
    List<Promotion> promotions = new ArrayList<Promotion>();

    public CartItem(List<Item> items, ItemTypeEnum itemType, Promotion... promotion) {
        this.items = items;
        this.itemType = itemType;
        if (promotion != null) {
            this.promotions = Arrays.asList(promotion);
        }
    }

    public CartItem(List<Item> items, ItemTypeEnum itemType) {
        this.items = items;
        this.itemType = itemType;
    }

    private CartItem(List<Item> items, ItemTypeEnum itemType, List<Promotion> promotions) {
        this.items = items;
        this.itemType = itemType;
        this.promotions = promotions;
    }

    //Comparator for lowest regular price
    static Comparator<Item> regularPriceComparator =
            new Comparator<Item>() {
                public int compare(Item o1, Item o2) {
                    return o1.regularPrice == o2.regularPrice ? 0
                            : o1.regularPrice > o2.regularPrice ? 1 : -1;
                }
            };

    public static Comparator<Item> getRegularPriceComparator() {
        return regularPriceComparator;
    }

    public List<Item> getItems() {
        return items;
    }

    public CartItem getCopy() {
        ArrayList<Item> newItems = getItemsWithoutAlreadyPromoted();
        Collections.sort(newItems, regularPriceComparator);
        return new CartItem(newItems, itemType, promotions);
    }

    /**
     * Returns a List with Items that haven't fallen under any other promotion earlier
     * meaning regularPrice == priceWithPromotion
     * @return
     */
    public ArrayList<Item> getItemsWithoutAlreadyPromoted() {
        ArrayList<Item> itemsForPromo = new ArrayList<Item>();
        for (Item item : items) {
            if (!item.isPromoted()) {
                itemsForPromo.add(item);
            }
        }
        return itemsForPromo;
    }

    public int getItemCount() {
        return items.size();
    }

    public ItemTypeEnum getItemType() {
        return itemType;
    }

    public List<Promotion> getPromotions() {
        return promotions;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        for (Item item : items) {
            output.append(String.format("|%-20s|", itemType).replace(" ", ".")
                    + String.format("%f", item.priceWithPromotion));
            output.append("\n");
        }
        return output.toString();
    }
}