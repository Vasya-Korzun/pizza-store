package by.tms.korzun.pizza.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
@Builder
public class OrderRequest {
    private Long customerId;
    private Set<PositionCount> goods;
}
