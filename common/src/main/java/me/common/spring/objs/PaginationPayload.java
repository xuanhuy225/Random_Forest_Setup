package me.common.spring.objs;

public class PaginationPayload {
    private int pageIndex;
    private int pageSize;
    private String sortBy;
    private SortType sortType;

    public enum SortType {
        ASC(0), DESC(1);
        private int value;
        SortType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
