package me.miniapp.base.admin.application.banner.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import me.miniapp.base.admin.application.banner.BannerDTO;
import me.miniapp.base.domain.banner.Banner;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-13T17:00:16+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.13 (Amazon.com Inc.)"
)
public class BannerDTOMapperImpl implements BannerDTOMapper {

    @Override
    public Banner mapToEntity(BannerDTO rest) {
        if ( rest == null ) {
            return null;
        }

        Banner banner = new Banner();

        banner.setId( rest.getId() );
        banner.setTitle( rest.getTitle() );
        banner.setDescription( rest.getDescription() );
        banner.setImageUrl( rest.getImageUrl() );
        banner.setRedirectUrl( rest.getRedirectUrl() );
        banner.setType( map( rest.getType() ) );
        if ( rest.getItemIdx() != null ) {
            banner.setItemIdx( Integer.parseInt( rest.getItemIdx() ) );
        }
        List<Long> list = rest.getShowByAudiences();
        if ( list != null ) {
            banner.setShowByAudiences( new ArrayList<Long>( list ) );
        }
        banner.setCreatedAt( rest.getCreatedAt() );
        banner.setUpdatedAt( rest.getUpdatedAt() );

        return banner;
    }

    @Override
    public BannerDTO mapToRest(Banner entity) {
        if ( entity == null ) {
            return null;
        }

        BannerDTO bannerDTO = new BannerDTO();

        bannerDTO.setId( entity.getId() );
        bannerDTO.setTitle( entity.getTitle() );
        bannerDTO.setDescription( entity.getDescription() );
        bannerDTO.setImageUrl( entity.getImageUrl() );
        bannerDTO.setRedirectUrl( entity.getRedirectUrl() );
        bannerDTO.setType( map( entity.getType() ) );
        if ( entity.getItemIdx() != null ) {
            bannerDTO.setItemIdx( String.valueOf( entity.getItemIdx() ) );
        }
        List<Long> list = entity.getShowByAudiences();
        if ( list != null ) {
            bannerDTO.setShowByAudiences( new ArrayList<Long>( list ) );
        }
        bannerDTO.setCreatedAt( entity.getCreatedAt() );
        bannerDTO.setUpdatedAt( entity.getUpdatedAt() );

        return bannerDTO;
    }
}
