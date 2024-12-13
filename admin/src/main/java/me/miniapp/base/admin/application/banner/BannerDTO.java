package me.miniapp.base.admin.application.banner;

import lombok.Data;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;


@Data
public class BannerDTO implements Serializable {
    private Integer id;
    private String title;
    private String description;
    private String imageUrl;
    private String redirectUrl;
    private BannerTypeEnum type;
    private String itemIdx;
    private List<Long> showByAudiences;
    private Instant createdAt;
    private Instant updatedAt;
}
