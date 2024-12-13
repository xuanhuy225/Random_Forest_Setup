package me.common.util.regex;

public enum RegexManager {
    VietnameseName("[a-zA-Z_\\u00C0-\\u01B0\\u1EA0-\\u1EFF ']+"),
    Digits("\\d+"),
    DangerousText("[<>!]+"),
    VIBDangerousText("[<>]+"),
    VietnameseCompanyName("[a-zA-Z0-9\\u00C0-\\u01B0\\u1EA0-\\u1EFF %&,()\\.\\-\\\\+\\:;\\\\/\"“”]+");

    private final String value;
    RegexManager(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

}
