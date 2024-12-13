package me.miniapp.base.infrastructure.banner.converter;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import me.miniapp.base.domain.banner.Banner;
import me.miniapp.base.domain.banner.BannerType;
import me.miniapp.base.infrastructure.banner.BannerPO;
import me.miniapp.base.infrastructure.banner.BannerTypePO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-13T17:05:25+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.13 (Amazon.com Inc.)"
)
public class BannerMapperImpl implements BannerMapper {

    @Override
    public BannerPO mapToTableNotNull(Banner arg0) {
        if ( arg0 == null ) {
            return null;
        }

        BannerPO bannerPO = new BannerPO();

        bannerPO.setId( arg0.getId() );
        bannerPO.setTitle( arg0.getTitle() );
        bannerPO.setDescription( arg0.getDescription() );
        bannerPO.setImageUrl( arg0.getImageUrl() );
        bannerPO.setRedirectUrl( arg0.getRedirectUrl() );
        bannerPO.setType( bannerTypeToBannerTypePO( arg0.getType() ) );
        bannerPO.setItemIdx( arg0.getItemIdx() );
        List<Long> list = arg0.getShowByAudiences();
        if ( list != null ) {
            bannerPO.setShowByAudiences( new ArrayList<Long>( list ) );
        }
        bannerPO.setCreatedAt( map( arg0.getCreatedAt() ) );
        bannerPO.setUpdatedAt( map( arg0.getUpdatedAt() ) );

        return bannerPO;
    }

    @Override
    public Banner mapToEntityNotNull(BannerPO arg0) {
        if ( arg0 == null ) {
            return null;
        }

        Banner banner = new Banner();

        banner.setId( arg0.getId() );
        banner.setTitle( arg0.getTitle() );
        banner.setDescription( arg0.getDescription() );
        banner.setImageUrl( arg0.getImageUrl() );
        banner.setRedirectUrl( arg0.getRedirectUrl() );
        banner.setType( bannerTypePOToBannerType( arg0.getType() ) );
        banner.setItemIdx( arg0.getItemIdx() );
        List<Long> list = arg0.getShowByAudiences();
        if ( list != null ) {
            banner.setShowByAudiences( new ArrayList<Long>( list ) );
        }
        banner.setCreatedAt( map( arg0.getCreatedAt() ) );
        banner.setUpdatedAt( map( arg0.getUpdatedAt() ) );

        return banner;
    }

    protected BannerTypePO bannerTypeToBannerTypePO(BannerType bannerType) {
        if ( bannerType == null ) {
            return null;
        }

        BannerTypePO bannerTypePO = new BannerTypePO();

        bannerTypePO.setId( bannerType.getId() );
        bannerTypePO.setName( bannerType.getName() );

        return bannerTypePO;
    }

    protected BannerType bannerTypePOToBannerType(BannerTypePO bannerTypePO) {
        if ( bannerTypePO == null ) {
            return null;
        }

        BannerType bannerType = new BannerType();

        bannerType.setId( bannerTypePO.getId() );
        bannerType.setName( bannerTypePO.getName() );

        return bannerType;
    }
}
