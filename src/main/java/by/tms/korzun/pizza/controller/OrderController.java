package by.tms.korzun.pizza.controller;

import by.tms.korzun.pizza.dto.OrderRequest;
import by.tms.korzun.pizza.dto.Orders;
import by.tms.korzun.pizza.entity.OrderStatus;
import by.tms.korzun.pizza.exceptions.NoSuchOrderException;
import by.tms.korzun.pizza.exceptions.NoSuchPizzaException;
import by.tms.korzun.pizza.exceptions.NoSuchUserException;
import by.tms.korzun.pizza.exceptions.OrderIsEmptyException;
import by.tms.korzun.pizza.service.OrderService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@RestController
@RequestMapping(value = "/api/orders")
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize( "hasAnyAuthority('CUSTOMER','ADMIN')")
    public final List<Orders> showOrders() {
        return orderService.showOrders();
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public final Orders addOrder(@RequestBody final OrderRequest orderRequest)
            throws NoSuchUserException, NoSuchPizzaException, OrderIsEmptyException {
        return orderService.addOrder(orderRequest);
    }

    @GetMapping(value = "/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public final Long changeOrderStatus(@PathVariable final Long orderId, @RequestParam(name = "status", required = false) final OrderStatus status)
            throws NoSuchOrderException {
        return orderService.changeOrderStatus(orderId, status);
    }
}
