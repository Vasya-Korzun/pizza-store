package by.tms.korzun.pizza.mapper;


import by.tms.korzun.pizza.dto.Orders;
import by.tms.korzun.pizza.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mappings({
        @Mapping(target = "id", source = "source.id"),
        @Mapping(target = "customerOrders", source = "source.customerOrders"),
        @Mapping(target = "user", source = "source.user"),
        @Mapping(target = "status", source = "source.status"),
        @Mapping(target = "total", source = "source.total")
    })
    OrderEntity sourceToDestination(Orders source);

    @Mappings({
        @Mapping(target = "id", source = "destination.id"),
        @Mapping(target = "customerOrders", source = "destination.customerOrders"),
        @Mapping(target = "user", source = "destination.user"),
        @Mapping(target = "status", source = "destination.status"),
        @Mapping(target = "total", source = "destination.total"),
        @Mapping(target = "user.password", ignore = true)
    })
    Orders destinationToSource(OrderEntity destination);
}