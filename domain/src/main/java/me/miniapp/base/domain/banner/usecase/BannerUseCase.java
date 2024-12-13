package me.miniapp.base.domain.banner.usecase;

import me.miniapp.base.domain.banner.Banner;
import me.common.spring.repository.BaseCRUDUseCase;

import java.util.List;


public interface BannerUseCase extends BaseCRUDUseCase<Banner, Integer> {
    List<Banner> findAllActiveByBannerType(Integer bannerTypeId);

    List<Banner> filterAudience(Long uid, List<Banner> banners);

    Banner findById(Integer bannerId);
}
