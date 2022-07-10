package by.tms.korzun.pizza.dto;

import by.tms.korzun.pizza.entity.OrderStatus;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class Orders {
    private Integer id;
    private User user;
    private OrderStatus status;
    private Double total;
    private List<CustomerOrder> customerOrders;
}
