package me.common.spring.admin.queries;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;


@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class QueryParamAdmin implements Serializable {

    private String orderBy;
    private OrderType orderType;
    private int page;
    private int size;
    private List<FilterPayload> filters;

    private String search;


    public enum OrderType {
        ASC(0),
        DESC(1),
        DEFAULT(2);
        int value;

        OrderType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static OrderType findByValue(int value) {
            for (OrderType orderType : OrderType.values()) {
                if (orderType.getValue() == value) {
                    return orderType;
                }
            }
            return null;
        }

        public static OrderType findByValue(String value) {
            for (OrderType orderType : OrderType.values()) {
                if (orderType.name().equalsIgnoreCase(value)) {
                    return orderType;
                }
            }
            return null;
        }
    }
}
