package by.tms.korzun.pizza.mapper;


import by.tms.korzun.pizza.dto.CustomerOrder;
import by.tms.korzun.pizza.entity.CustomerOrderEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerOrderMapper {

    CustomerOrderEntity sourceToDestination(CustomerOrder source);

    CustomerOrder destinationToSource(CustomerOrderEntity destination);
}