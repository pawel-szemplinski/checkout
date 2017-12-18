package com.szemplinski.pawel;

import com.szemplinski.checkout.*;
import com.szemplinski.checkout.promotions.*;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static com.szemplinski.checkout.promotions.BuyNXGetKYPromo.*;

/**
 * Unit test for Checkout App.
 */
public class CheckoutTest
{
    static Logger LOGGER = LoggerFactory.getLogger(Promotion.class);
    @Test
    public void testBuy3PayFor2Promo()
    {
        LOGGER.info("Test of Buy 3 items pay for 2 Promo");
        Cart cart = new Cart();
        List<Item> items = new ArrayList<Item>();
        items.add(new Item(2));
        items.add(new Item(2));
        items.add(new Item(2));
        cart.add(new CartItem(items, ItemTypeEnum.APPLE, new Buy3PayFor2Promo()));
        new CheckoutService().calculateCartTotal(cart);
        cart.printReceipt();
        Assert.assertEquals(4,cart.calculateGrandTotal(), 0.0);
    }

    @Test
    public void testBuy2Promo()
    {
        LOGGER.info("Test of Buy 2 items for a special price");
        Cart cart = new Cart();
        List<Item> items = new ArrayList<Item>();
        items.add(new Item(2));
        items.add(new Item(2));
        items.add(new Item(2));
        items.add(new Item(2));
        items.add(new Item(2));
        items.add(new Item(2));
        items.add(new Item(2));
        cart.add(new CartItem(items, ItemTypeEnum.APPLE, new Buy2Promo(5)));
        new CheckoutService().calculateCartTotal(cart);
        cart.printReceipt();
        Assert.assertEquals(32,cart.calculateGrandTotal(), 0.0);
    }

    @Test
    public void testBuy3CheapestFree()
    {
        LOGGER.info("Test of Buy 3, cheapest one is free");
        Cart cart = new Cart();
        List<Item> items = new ArrayList<Item>();
        Set<ItemTypeEnum> promotionItemTypesSet = new HashSet<ItemTypeEnum>();
        promotionItemTypesSet.add(ItemTypeEnum.APPLE);
        promotionItemTypesSet.add(ItemTypeEnum.BANANA);
        promotionItemTypesSet.add(ItemTypeEnum.ORANGE);

        items.add(new Item(2));
        cart.add(new CartItem(items, ItemTypeEnum.APPLE, new Buy3CheapestFreePromo(promotionItemTypesSet)));

        items = new ArrayList<Item>();
        items.add(new Item(3));
        cart.add(new CartItem(items, ItemTypeEnum.BANANA, new Buy3CheapestFreePromo(promotionItemTypesSet)));

        items = new ArrayList<Item>();
        items.add(new Item(4));
        cart.add(new CartItem(items, ItemTypeEnum.ORANGE, new Buy3CheapestFreePromo(promotionItemTypesSet)));

        items = new ArrayList<Item>();
        items.add(new Item(1));
        cart.add(new CartItem(items, ItemTypeEnum.PLUM, new Buy3CheapestFreePromo(promotionItemTypesSet)));

        new CheckoutService().calculateCartTotal(cart);
        cart.printReceipt();
        Assert.assertEquals(8, cart.calculateGrandTotal(), 0.0);
    }

    @Test
    public void testBuyNXGetKY()
    {
        LOGGER.info("Test of Buy N*X items get K*Y items for free");
        Cart cart = new Cart();
        List<Item> items = new ArrayList<Item>();

        Promotion promotion = new BuyNXGetKYPromo(new Condition(3, ItemTypeEnum.APPLE),
                                                  new Bonus(1, ItemTypeEnum.BANANA));

        items.add(new Item(4));
        items.add(new Item(4));
        items.add(new Item(4));
        items.add(new Item(4));
        items.add(new Item(4));
        items.add(new Item(4));
        cart.add(new CartItem(items, ItemTypeEnum.APPLE, promotion));

        new CheckoutService().calculateCartTotal(cart);
        cart.printReceipt();
        Assert.assertEquals(24, cart.calculateGrandTotal(), 0.0);
    }


    @Test
    public void testMultiplePromotions()
    {
        LOGGER.info("Multiple promotions Test 1");
        Cart cart = new Cart();
        List<Item> items = new ArrayList<Item>();
        items.add(new Item(5));
        items.add(new Item(5));
        items.add(new Item(5));
        items.add(new Item(5));
        items.add(new Item(5));
        cart.add(new CartItem(items, ItemTypeEnum.APPLE, new Buy3PayFor2Promo(), new Buy2Promo(3)));
        new CheckoutService().calculateCartTotal(cart);
        cart.printReceipt();
        Assert.assertEquals(16,cart.calculateGrandTotal(), 0.0);
    }


    @Test
    public void testMultiplePromotions2()
    {
        LOGGER.info("Multiple promotions Test 2");
        Cart cart = new Cart();

        Promotion buyNXGetKYpromotion = new BuyNXGetKYPromo(new Condition(3, ItemTypeEnum.APPLE),
                                                  new Bonus(1, ItemTypeEnum.BANANA));

        Set<ItemTypeEnum> promotionItemTypesSet = new HashSet<ItemTypeEnum>();
        promotionItemTypesSet.add(ItemTypeEnum.APPLE);
        promotionItemTypesSet.add(ItemTypeEnum.BANANA);
        promotionItemTypesSet.add(ItemTypeEnum.ORANGE);

        List<Item> items = new ArrayList<Item>();
        items.add(new Item(5));
        items.add(new Item(5));
        items.add(new Item(5));
        items.add(new Item(5));
        items.add(new Item(5));
        items.add(new Item(5));
        cart.add(new CartItem(items, ItemTypeEnum.APPLE, new Buy3CheapestFreePromo(promotionItemTypesSet), buyNXGetKYpromotion));

        items = new ArrayList<Item>();
        items.add(new Item(10));
        cart.add(new CartItem(items, ItemTypeEnum.BANANA, new Buy3CheapestFreePromo(promotionItemTypesSet)));

        items = new ArrayList<Item>();
        items.add(new Item(15));
        cart.add(new CartItem(items, ItemTypeEnum.ORANGE, new Buy3CheapestFreePromo(promotionItemTypesSet)));

        new CheckoutService().calculateCartTotal(cart);
        cart.printReceipt();
        Assert.assertEquals(50,cart.calculateGrandTotal(), 0.0);
    }

    @Test
    public void testEmptyCart()
    {
        LOGGER.info("Empty cart Test");
        Cart cart = new Cart();
        new CheckoutService().calculateCartTotal(cart);
        cart.printReceipt();
        Assert.assertEquals(0,cart.getGrandTotal(), 0.0);
    }

    @Test
    public void testCartWithoutPromotions()
    {
        LOGGER.info("Cart with no promotions Test");
        Cart cart = new Cart();

        List<Item> items = new ArrayList<Item>();
        items.add(new Item(5));
        items.add(new Item(5));
        items.add(new Item(5));
        items.add(new Item(5));
        items.add(new Item(5));
        items.add(new Item(5));
        cart.add(new CartItem(items, ItemTypeEnum.APPLE));

        items = new ArrayList<Item>();
        items.add(new Item(10));
        cart.add(new CartItem(items, ItemTypeEnum.BANANA));

        items = new ArrayList<Item>();
        items.add(new Item(15));
        cart.add(new CartItem(items, ItemTypeEnum.ORANGE));

        new CheckoutService().calculateCartTotal(cart);
        cart.printReceipt();
        Assert.assertEquals(55,cart.calculateGrandTotal(), 0.0);
    }


    @Test
    public void testNullCkecks()
    {
        LOGGER.info("Null cheks test");
        Cart cart = new Cart();
        List<Item> items = new ArrayList<Item>();
        items.add(new Item(2));
        items.add(new Item(2));
        items.add(new Item(2));
        items.add(new Item(2));
        items.add(new Item(2));
        items.add(new Item(2));
        items.add(new Item(2));
        cart.add(new CartItem(items, ItemTypeEnum.APPLE, null));
        new CheckoutService().calculateCartTotal(cart);
        cart.printReceipt();
        Assert.assertEquals(14,cart.calculateGrandTotal(), 0.0);
    }
}
