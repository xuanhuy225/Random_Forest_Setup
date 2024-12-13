package me.miniapp.base.infrastructure.banner;

import me.miniapp.base.domain.banner.Banner;
import me.miniapp.base.domain.banner.BannerType;
import me.miniapp.base.domain.banner.gateway.BannerGateway;
import me.miniapp.base.infrastructure.banner.converter.BannerMapper;
import me.miniapp.base.infrastructure.banner.converter.BannerTypeMapper;
import me.miniapp.base.infrastructure.banner.repository.BannerRepository;
import me.miniapp.base.infrastructure.banner.repository.BannerTypeRepository;
import me.common.spring.repository.AbstractRepositoryGateway;
import me.common.spring.repository.RepositoryConverter;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class BannerRepositoryGateway extends AbstractRepositoryGateway<Banner, BannerPO, Integer> implements BannerGateway {
    private final BannerRepository repository;
    private static final RepositoryConverter<BannerPO, Banner> converter = BannerMapper.INSTANCE;
    private final BannerTypeRepository bannerTypeRepository;
    private static final RepositoryConverter<BannerTypePO, BannerType> bannerTypeConverter = BannerTypeMapper.INSTANCE;

    @Override
    protected <A extends JpaRepository<BannerPO, Integer>> A getRepository() {
        return (A) repository;
    }

    @Override
    protected <A extends RepositoryConverter<BannerPO, Banner>> A getRepositoryConverter() {
        return (A) converter;
    }

    @Override
    public BannerType findBannerTypeById(Integer bannerTypeId) {
        if (bannerTypeId == null) {
            return null;
        }
        return bannerTypeConverter.mapToEntity(
                bannerTypeRepository.findById(bannerTypeId).orElse(null)
        );
    }

    @Override
    public Banner findById(Integer bannerId) {
        return converter.mapToEntity(
                repository.findById(bannerId).orElse(null)
        );
    }
}
