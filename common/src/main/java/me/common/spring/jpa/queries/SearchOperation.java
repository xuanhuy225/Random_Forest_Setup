package me.common.spring.jpa.queries;

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
