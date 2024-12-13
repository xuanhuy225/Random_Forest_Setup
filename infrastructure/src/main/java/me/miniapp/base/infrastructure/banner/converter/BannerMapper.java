package me.miniapp.base.infrastructure.banner.converter;

import me.miniapp.base.domain.banner.Banner;
import me.miniapp.base.infrastructure.banner.BannerPO;
import me.common.spring.repository.RepositoryConverter;
import me.common.spring.repository.mapper.ExtendTimestampMapInstant;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface BannerMapper extends RepositoryConverter<BannerPO, Banner>, ExtendTimestampMapInstant {
    BannerMapper INSTANCE = Mappers.getMapper(BannerMapper.class);
}
