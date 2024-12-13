package me.miniapp.base.infrastructure.banner.converter;

import javax.annotation.processing.Generated;
import me.miniapp.base.domain.banner.BannerType;
import me.miniapp.base.infrastructure.banner.BannerTypePO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-13T17:05:25+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.13 (Amazon.com Inc.)"
)
public class BannerTypeMapperImpl implements BannerTypeMapper {

    @Override
    public BannerTypePO mapToTableNotNull(BannerType arg0) {
        if ( arg0 == null ) {
            return null;
        }

        BannerTypePO bannerTypePO = new BannerTypePO();

        bannerTypePO.setId( arg0.getId() );
        bannerTypePO.setName( arg0.getName() );

        return bannerTypePO;
    }

    @Override
    public BannerType mapToEntityNotNull(BannerTypePO arg0) {
        if ( arg0 == null ) {
            return null;
        }

        BannerType bannerType = new BannerType();

        bannerType.setId( arg0.getId() );
        bannerType.setName( arg0.getName() );

        return bannerType;
    }
}
