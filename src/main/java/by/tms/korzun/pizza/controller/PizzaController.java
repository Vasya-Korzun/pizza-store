package by.tms.korzun.pizza.controller;

import by.tms.korzun.pizza.dto.Pizza;
import by.tms.korzun.pizza.exceptions.NoSuchPizzaException;
import by.tms.korzun.pizza.service.PizzaService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@RestController
@RequestMapping("/api/pizzas")
public class PizzaController {

    private final PizzaService pizzaService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public final List<Pizza> getPizzaFilter(@RequestParam(name = "type", required = false) final String type) {
        if (type != null) {
            return pizzaService.getPizzasByFilter(type);
        }
        return pizzaService.getPizzas();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public final Long addPizza(@RequestBody final Pizza request) {
        return pizzaService.addPizza(request);
    }

    @PutMapping(value = "/{pizzaId}")
    @ResponseStatus(HttpStatus.OK)
    public final Pizza updatePizzaById(@RequestBody final Pizza pizza, @PathVariable final Long pizzaId)
        throws NoSuchPizzaException {
        return pizzaService.updatePizzaById(pizzaId, pizza);
    }

    @DeleteMapping(value = "/{pizzaId}")
    @ResponseStatus(HttpStatus.OK)
    public final Long deletePizzaById(@PathVariable final Long pizzaId) throws NoSuchPizzaException {
        return pizzaService.deletePizzaById(pizzaId);
    }
}
