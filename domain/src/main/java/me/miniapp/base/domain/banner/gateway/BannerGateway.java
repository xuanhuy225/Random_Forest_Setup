package me.miniapp.base.domain.banner.gateway;

import me.miniapp.base.domain.banner.Banner;
import me.miniapp.base.domain.banner.BannerType;
import me.common.spring.repository.RepositoryGateway;


public interface BannerGateway extends RepositoryGateway<Banner, Integer> {

    BannerType findBannerTypeById(Integer bannerTypeId);

    Banner findById(Integer bannerId);
}
