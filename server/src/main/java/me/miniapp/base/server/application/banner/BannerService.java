package me.miniapp.base.server.application.banner;

import me.miniapp.base.server.application.banner.dto.BannerDTO;
import me.common.spring.response.MultiResponse;
import me.common.spring.response.SingleResponse;

public interface BannerService {

    MultiResponse<BannerDTO> getActiveBanners(Long userId, Integer bannerType);

    SingleResponse<BannerDTO> getActiveBanner(Integer id);
}
