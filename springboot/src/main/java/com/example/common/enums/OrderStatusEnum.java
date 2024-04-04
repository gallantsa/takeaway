package com.example.common.enums;

public enum OrderStatusEnum {
    CANCEL("已取消"),
    NO_PAY("待支付"),
    NO_SEND("待发货"),
    NO_RECEIVE("待收货"),
    NO_COMMENT("待评价"),
    MONEY_BACK("已退款"),
    DEON("已完成");

    private String value;

    OrderStatusEnum(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
