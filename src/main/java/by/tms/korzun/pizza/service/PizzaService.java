package by.tms.korzun.pizza.service;

import by.tms.korzun.pizza.dto.Pizza;
import by.tms.korzun.pizza.entity.PizzaEntity;
import by.tms.korzun.pizza.exceptions.NoSuchPizzaException;
import by.tms.korzun.pizza.mapper.PizzaMapper;
import by.tms.korzun.pizza.repository.PizzaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PizzaService {

    private final PizzaRepository pizzaRepository;
    private final PizzaMapper pizzaMapper;

    public List<Pizza> getPizzas() {
        return pizzaRepository.findAll()
                .stream()
                .map(pizzaMapper::destinationToSource)
                .collect(Collectors.toList());
    }

    public Pizza getPizzaById(final Long id) throws NoSuchPizzaException {
        isFoundEntity(id);
        return pizzaRepository.findById(id)
                .map(pizzaMapper::destinationToSource)
                .get();
    }

    public List<Pizza> getPizzasByFilter(final String pizzaType) {
        return pizzaRepository.findAll()
                .stream()
                .map(pizzaMapper::destinationToSource)
                .filter(b -> b.getType().equals(pizzaType))
                .collect(Collectors.toList());
    }

    @Transactional
    public Long addPizza(final Pizza pizza) {
        final PizzaEntity pizzaEntity = pizzaMapper.sourceToDestination(pizza);
        final PizzaEntity pizzaEntityOutput = pizzaRepository.save(pizzaEntity);
        return pizzaEntityOutput.getId();
    }

    public Pizza updatePizzaById(final Long id, final Pizza pizza) throws NoSuchPizzaException {
        isFoundEntity(id);
        final PizzaEntity pizzaEntity = this.pizzaRepository.getOne(id);

        pizzaEntity.setType(pizzaEntity.getType().equals(pizza.getType()) ? pizzaEntity.getType() : pizza.getType());
        pizzaEntity.setName(pizzaEntity.getName().equals(pizza.getName()) ? pizzaEntity.getName() : pizza.getName());
        pizzaEntity.setDescription(
                pizzaEntity.getDescription().equals(pizza.getDescription()) ? pizzaEntity.getDescription()
                        : pizza.getDescription());
        pizzaEntity.setPrice(pizzaEntity.getPrice().equals(pizza.getPrice()) ? pizzaEntity.getPrice() : pizza.getPrice());

        this.pizzaRepository.save(pizzaEntity);

        return pizzaMapper.destinationToSource(pizzaEntity);
    }

    public Long deletePizzaById(final Long id) throws NoSuchPizzaException {
        isFoundEntity(id);
        pizzaRepository.deleteById(id);
        return id;
    }

    private void isFoundEntity(final Long id) throws NoSuchPizzaException {
        final boolean isFound = pizzaRepository.existsById(id);
        if (!isFound) {
            throw new NoSuchPizzaException("No pizza with id = " + id + " was found.");
        }
    }
}
