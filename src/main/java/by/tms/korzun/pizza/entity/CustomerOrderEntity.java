package by.tms.korzun.pizza.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "customer_order")
public class CustomerOrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_orders_id_sequence")
    @SequenceGenerator(name = "customer_orders_id_sequence", sequenceName = "customer_orders_id_sequence", allocationSize = 1)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity orders;
    @ManyToOne
    @JoinColumn(name = "pizza_id", nullable = false)
    private PizzaEntity pizza;
    @Column(name = "count")
    private Integer count;
}
