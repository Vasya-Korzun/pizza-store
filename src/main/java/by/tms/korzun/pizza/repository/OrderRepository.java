package by.tms.korzun.pizza.repository;

import by.tms.korzun.pizza.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

}
