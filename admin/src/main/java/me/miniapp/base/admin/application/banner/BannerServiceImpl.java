package me.miniapp.base.admin.application.banner;

import me.miniapp.base.admin.application.banner.mapper.BannerDTOMapper;
import me.miniapp.base.domain.banner.Banner;
import me.miniapp.base.domain.banner.usecase.BannerUseCase;
import me.common.spring.admin.AbstractBaseAdminService;
import me.common.spring.converter.RestConverter;
import me.common.spring.repository.BaseCRUDUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class BannerServiceImpl extends AbstractBaseAdminService<BannerDTO, Banner,Integer> implements BannerService {

    private final BannerUseCase useCase;
    private static final BannerDTOMapper MAPPER = BannerDTOMapper.INSTANCE;


    @Override
    protected BaseCRUDUseCase<Banner, Integer> getUseCase() {
        return useCase;
    }

    @Override
    protected RestConverter<BannerDTO, Banner> getConverter() {
        return MAPPER;
    }
}
