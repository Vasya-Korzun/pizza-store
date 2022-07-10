package by.tms.korzun.pizza.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CustomerOrder {
    private Pizza pizza;
    private Integer count;
}
