package me.miniapp.base.infrastructure.banner.converter;

import me.miniapp.base.domain.banner.BannerType;
import me.miniapp.base.infrastructure.banner.BannerTypePO;
import me.common.spring.repository.RepositoryConverter;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BannerTypeMapper extends RepositoryConverter<BannerTypePO, BannerType> {
    BannerTypeMapper INSTANCE = Mappers.getMapper(BannerTypeMapper.class);
}
