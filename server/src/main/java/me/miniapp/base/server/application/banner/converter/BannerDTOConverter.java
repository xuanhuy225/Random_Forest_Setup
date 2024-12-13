package me.miniapp.base.server.application.banner.converter;

import me.miniapp.base.domain.banner.Banner;
import me.miniapp.base.server.application.banner.dto.BannerDTO;
import me.common.spring.converter.RestConverter;
import org.springframework.stereotype.Component;

@Component
public class BannerDTOConverter implements RestConverter<BannerDTO, Banner> {
    @Override
    public BannerDTO mapToRest(Banner entity) {
        return new BannerDTO(
                entity.getId(),
                entity.getType().getId(),
                entity.getItemIdx(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getImageUrl(),
                entity.getRedirectUrl(),
                false
        );
    }
}
