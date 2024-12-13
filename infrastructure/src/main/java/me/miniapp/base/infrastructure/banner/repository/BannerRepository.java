package me.miniapp.base.infrastructure.banner.repository;

import me.miniapp.base.infrastructure.banner.BannerPO;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BannerRepository extends JpaRepository<BannerPO, Integer> {

}