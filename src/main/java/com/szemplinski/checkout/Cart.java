package com.szemplinski.checkout;

import java.util.*;

/**
 * Created by pawel.szemplinski on 2017-12-15.
 */
public class Cart {
    private Map<ItemTypeEnum, CartItem> items = new HashMap<ItemTypeEnum, CartItem>();

    private double grandTotal = 0.0;
    public void add(CartItem cartItem) {
        if (cartItem != null) {
            items.put(cartItem.getItemType(), cartItem);
        }
    }

    public void addItems(ArrayList<CartItem> items) {
        items.addAll(items);
    }

    public Map<ItemTypeEnum, CartItem> getItems() {
        //return new HashMap<ItemTypeEnum, CartItem>(items);
        return items;
    }

    public double calculateGrandTotal() {
        grandTotal = 0.0;
        for (CartItem cartItem : items.values()) {
            for (Item item : cartItem.getItems()) {
                grandTotal += item.priceWithPromotion;
            }
        }
        return grandTotal;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        output.append("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        output.append("\nRECEIPT\n\n");
        for (CartItem cartItem : items.values()) {
            output.append(cartItem.toString());
        }
        output.append("\n");
        output.append("TOTAL: " + grandTotal);
        output.append("\n");
        output.append(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n");
        return output.toString();
    }

    public void printReceipt() {
        calculateGrandTotal();
        System.out.print(this);
    }

    public double getGrandTotal() {
        return grandTotal;
    }
}
