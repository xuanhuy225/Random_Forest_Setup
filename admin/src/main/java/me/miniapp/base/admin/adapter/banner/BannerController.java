package me.miniapp.base.admin.adapter.banner;

import me.miniapp.base.admin.application.banner.BannerDTO;
import me.miniapp.base.admin.application.banner.BannerService;
import me.common.spring.admin.AbstractBaseAdminController;
import me.common.spring.admin.BaseAdminService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/banner")
@AllArgsConstructor
public class BannerController extends AbstractBaseAdminController<BannerDTO,Integer> {

    private final BannerService service;
    @Override
    protected <A extends BaseAdminService<BannerDTO, Integer>> A getService() {
        return (A) service;
    }
}
