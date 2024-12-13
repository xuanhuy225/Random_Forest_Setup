package me.miniapp.base.admin.application.banner.mapper;

import me.miniapp.base.admin.application.banner.BannerDTO;
import me.miniapp.base.admin.application.banner.BannerTypeEnum;
import me.miniapp.base.domain.banner.Banner;
import me.miniapp.base.domain.banner.BannerType;
import me.common.spring.converter.RestConverter;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface BannerDTOMapper extends RestConverter<BannerDTO, Banner> {
    BannerDTOMapper INSTANCE = Mappers.getMapper(BannerDTOMapper.class);

    @Override
    Banner mapToEntity(final BannerDTO rest);

    @Override
    BannerDTO mapToRest(final Banner entity);

    default BannerType map(BannerTypeEnum value){
        BannerType bannerType = new BannerType();
        bannerType.setId(value.getId());
        return bannerType;
    }
    default BannerTypeEnum map(BannerType value){
        if(value == null){
            return null;
        }
        return BannerTypeEnum.findByValue(value.getId());
    }

}
