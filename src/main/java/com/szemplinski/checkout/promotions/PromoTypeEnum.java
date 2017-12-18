package com.szemplinski.checkout.promotions;

/**
 * Created by pawel.szemplinski on 2017-12-15.
 */
public enum PromoTypeEnum {
    B3P2(1),
    B2(2),
    B3_CHEAPEST_FREE(3),
    NX_KY(4);

    PromoTypeEnum(int priority) {
        this.priority = priority;
    }

    int priority;
}
