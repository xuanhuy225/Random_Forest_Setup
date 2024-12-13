package me.miniapp.base.server.application.banner.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class BannerDTO implements Serializable {
    private Integer id;
    private Integer bannerType;
    private Integer bannerIndex;
    private String title;
    private String description;
    private String imageUrl;
    private String redirectUrl;
    private Boolean validAudience;
}
