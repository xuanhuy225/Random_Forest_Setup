package me.common.spring.jpa.queries;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class Filter {
    private String key;
    private SearchOperation operation;
    private List<Object> arguments;
}
