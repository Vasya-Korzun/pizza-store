package by.tms.korzun.pizza.mapper;

import by.tms.korzun.pizza.dto.Pizza;
import by.tms.korzun.pizza.entity.PizzaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PizzaMapper {
    PizzaEntity sourceToDestination(Pizza source);

    Pizza destinationToSource(PizzaEntity destination);
}