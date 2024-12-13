package me.miniapp.base.domain.banner;

import lombok.Data;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Data
public class Banner implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String title;
    private String description;
    private String imageUrl;
    private String redirectUrl;
    private BannerType type;
    private Integer itemIdx;
    private List<Long> showByAudiences;
    private Instant createdAt;
    private Instant updatedAt;
}
