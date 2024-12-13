package me.miniapp.base.domain.banner.usecase;

import me.miniapp.base.domain.banner.Banner;
import me.miniapp.base.domain.banner.gateway.BannerGateway;

import me.common.spring.repository.AbstractCrudUseCase;
import me.common.spring.repository.RepositoryGateway;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@AllArgsConstructor
public class BannerUseCaseImpl extends AbstractCrudUseCase<Banner, Integer> implements BannerUseCase {
    private final BannerGateway gateway;

    @Override
    protected <A extends RepositoryGateway<Banner, Integer>> A getGateway() {
        return (A) gateway;
    }

    @Override
    public List<Banner> findAllActiveByBannerType(Integer bannerTypeId) {
        return null;
    }

    @Override
    public List<Banner> filterAudience(Long uid, List<Banner> banners) {
        return null;
    }

    @Override
    public Banner findById(Integer bannerId) {
        return null;
    }
}
