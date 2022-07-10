package by.tms.korzun.pizza.mapper;


import by.tms.korzun.pizza.dto.User;
import by.tms.korzun.pizza.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserEntity sourceToDestination(User source);

    @Mapping(target = "password", ignore = true)
    User destinationToSource(UserEntity destination);
}