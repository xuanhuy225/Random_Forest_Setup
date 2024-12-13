package me.miniapp.base.server.application.banner;

import me.miniapp.base.domain.banner.usecase.BannerUseCase;
import me.miniapp.base.server.application.banner.converter.BannerDTOConverter;
import me.miniapp.base.server.application.banner.dto.BannerDTO;
import lombok.extern.slf4j.Slf4j;
import me.common.spring.response.MultiResponse;
import me.common.spring.response.SingleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BannerServiceImpl implements BannerService {
    private final BannerUseCase bannerUseCase;
    private final BannerDTOConverter bannerDTOConverter;

    @Override
    public MultiResponse<BannerDTO> getActiveBanners(Long userId, Integer bannerType) {
        return null;
    }

    @Override
    public SingleResponse<BannerDTO> getActiveBanner(Integer id) {
        return null;
    }
}
