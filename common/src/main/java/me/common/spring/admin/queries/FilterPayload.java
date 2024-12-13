package me.common.spring.admin.queries;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;


@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class FilterPayload {
    private String key;
    private SearchOperation operation;
    private List<Object> arguments;

    public enum SearchOperation {
        EQUALITY(0),
        NEGATION(1),
        GREATER_THAN(2),
        LESS_THAN(3),
        LIKE(4);
        int value;

        SearchOperation(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static SearchOperation findByValue(int value) {
            for (SearchOperation operation : SearchOperation.values()) {
                if (operation.getValue() == value) {
                    return operation;
                }
            }
            return null;
        }
    }
}
