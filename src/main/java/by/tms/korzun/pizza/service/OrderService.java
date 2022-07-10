package by.tms.korzun.pizza.service;

import by.tms.korzun.pizza.dto.OrderRequest;
import by.tms.korzun.pizza.dto.Orders;
import by.tms.korzun.pizza.dto.PositionCount;
import by.tms.korzun.pizza.entity.CustomerOrderEntity;
import by.tms.korzun.pizza.entity.OrderEntity;
import by.tms.korzun.pizza.entity.OrderStatus;
import by.tms.korzun.pizza.entity.UserEntity;
import by.tms.korzun.pizza.exceptions.NoSuchPizzaException;
import by.tms.korzun.pizza.exceptions.NoSuchOrderException;
import by.tms.korzun.pizza.exceptions.NoSuchUserException;
import by.tms.korzun.pizza.exceptions.OrderIsEmptyException;
import by.tms.korzun.pizza.mapper.OrderMapper;
import by.tms.korzun.pizza.mapper.UserMapper;
import by.tms.korzun.pizza.repository.OrderRepository;
import by.tms.korzun.pizza.repository.PizzaRepository;
import by.tms.korzun.pizza.repository.UserRepository;
import lombok.Data;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Log
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final PizzaRepository pizzaRepository;
    private final PizzaService pizzaService;
    private final OrderMapper orderMapper;
    private final UserMapper userMapper;

    public Orders addOrder(final OrderRequest orderRequest)
        throws NoSuchUserException, NoSuchPizzaException, OrderIsEmptyException {

        final UserEntity userEntity = userRepository.findById(orderRequest.getCustomerId())
            .orElseThrow(() -> new NoSuchUserException(
                "No customer with id = " + orderRequest.getCustomerId() + " was found."));

        final Set<PositionCount> goodsIds = orderRequest.getGoods();
        final OrderEntity orderEntity = new OrderEntity();
        final Set<CustomerOrderEntity> customerOrders = findAllCustomerOrders(goodsIds, orderEntity);

        final Double total = calculateTotalCostOrder(customerOrders);
        orderEntity.setUser(userEntity);
        orderEntity.setTotal(total);
        orderEntity.setCustomerOrders(customerOrders);
        final OrderEntity orderSaved = orderRepository.save(orderEntity);
        return orderMapper.destinationToSource(orderSaved);
    }

    @Transactional
    public Long changeOrderStatus(final Long id, final OrderStatus status) throws NoSuchOrderException {
        final OrderEntity orderEntity = orderRepository.findById(id)
            .orElseThrow(() -> new NoSuchOrderException("No order with id = " + id + " was found."));

        orderEntity.setStatus(status);
        orderRepository.save(orderEntity);
        return orderEntity.getId();
    }

    public List<Orders> showOrders() {
        return orderRepository.findAll()
            .stream()
            .map(orderMapper::destinationToSource)
            .collect(Collectors.toList());
    }

    private Double calculateTotalCostOrder(final Set<CustomerOrderEntity> customerOrders) {
        return customerOrders
            .stream()
            .mapToDouble(order -> order.getPizza().getPrice() * order.getCount())
            .sum();
    }

    private Set<CustomerOrderEntity> findAllCustomerOrders(final Set<PositionCount> goodsSet, final OrderEntity orderEntity)
        throws NoSuchPizzaException, OrderIsEmptyException {
        final Set<CustomerOrderEntity> customerOrders = new HashSet<>();

        if (goodsSet.isEmpty()) {
            throw new OrderIsEmptyException("Order is empty!");
        }

        for (final PositionCount goods : goodsSet) {
            final CustomerOrderEntity customerOrderEntity = new CustomerOrderEntity();
            customerOrderEntity.setCount(goods.getCount());
            customerOrderEntity.setPizza(
                pizzaRepository.findById(goods.getId()).orElseThrow(
                    () -> new NoSuchPizzaException("No Pizza with id = " + goods.getId() + " was found.")));
            customerOrderEntity.setOrders(orderEntity);
            customerOrders.add(customerOrderEntity);
        }
        return customerOrders;
    }
}
