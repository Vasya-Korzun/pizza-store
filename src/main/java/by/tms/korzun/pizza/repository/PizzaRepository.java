package by.tms.korzun.pizza.repository;

import by.tms.korzun.pizza.entity.PizzaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PizzaRepository extends JpaRepository<PizzaEntity, Long> {

}
