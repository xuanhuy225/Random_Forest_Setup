package me.miniapp.base.infrastructure.banner.repository;

import me.miniapp.base.infrastructure.banner.BannerTypePO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BannerTypeRepository extends JpaRepository<BannerTypePO, Integer> {
}
