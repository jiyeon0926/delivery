package delivery.config;

public enum OrderStatus {
    ORDER_COMPLETED("주문 완료"),
    ORDER_ACCEPTED("주문 수락"),
    COOKING("조리 중"),
    COOKING_COMPLETED("조리 완료"),
    DELIVERY_PROGRESS("배달 중"),
    DELIVERY_COMPLETED("배달 완료"),
    ORDER_REJECTED("주문 거절");

    private final String description;

    OrderStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public OrderStatus transitionStatus(OrderStatus status) {
        if (status == OrderStatus.ORDER_COMPLETED) {
            return OrderStatus.ORDER_ACCEPTED;
        } else if (status == OrderStatus.ORDER_ACCEPTED) {
            return OrderStatus.COOKING;
        } else if (status == OrderStatus.COOKING) {
            return OrderStatus.COOKING_COMPLETED;
        } else if (status == OrderStatus.COOKING_COMPLETED) {
            return OrderStatus.DELIVERY_PROGRESS;
        } else {
            return OrderStatus.DELIVERY_COMPLETED;
        }
    }
}
