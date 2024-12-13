package me.common.spring.admin.queries;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaginationPayload {
    private int pageIndex;
    private int pageSize;
    private String sortBy;
    private SortType sortType;

    public enum SortType {
        DEFAULT(0), ASC(1), DESC(2);
        private int value;
        SortType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }


        public static SortType findByValue(int value) {
            for (SortType sortType : SortType.values()) {
                if (sortType.getValue() == value) {
                    return sortType;
                }
            }
            return null;
        }

        public static SortType findByValue(String value) {
            for (SortType sortType : SortType.values()) {
                if (sortType.name().equals(value)) {
                    return sortType;
                }
            }
            return null;
        }




    }
}
