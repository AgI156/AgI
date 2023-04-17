package com.hotel.model;


/**
 * 用于封装房间查询
 */
public class RoomSearchBean extends Room {
    private Double priceFrom;
    private Double PriceTo;

    public Double getPriceFrom() {
        return priceFrom;
    }

    public void setPriceFrom(Double priceFrom) {
        this.priceFrom = priceFrom;
    }

    public Double getPriceTo() {
        return PriceTo;
    }

    public void setPriceTo(Double priceTo) {
        PriceTo = priceTo;
    }


}
