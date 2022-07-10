package by.tms.korzun.pizza.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.ReadOnlyProperty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pizza {
    @ReadOnlyProperty
    private Integer id;
    private String type;
    private Boolean inStock;
    private String name;
    private String description;
    private Double price;
}
