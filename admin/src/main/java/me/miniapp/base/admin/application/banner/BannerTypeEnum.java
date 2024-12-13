package me.miniapp.base.admin.application.banner;

import lombok.Getter;


@Getter
public enum BannerTypeEnum {
    WELCOME(1, "welcome"),
    MASTHEAD(2, "masthead"),
    FEATURED(3, "featured");
    private Integer id;
    private String name;

    BannerTypeEnum(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public static BannerTypeEnum findByValue(Integer id) {
        for (BannerTypeEnum e : BannerTypeEnum.values()) {
            if (e.getId().equals(id)) {
                return e;
            }
        }
        return null;
    }
}
