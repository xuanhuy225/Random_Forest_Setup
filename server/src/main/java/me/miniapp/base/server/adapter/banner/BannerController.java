package me.miniapp.base.server.adapter.banner;

import me.miniapp.base.server.application.banner.BannerService;
import me.miniapp.base.server.application.banner.dto.BannerDTO;
import me.common.spring.response.MultiResponse;
import me.common.spring.response.SingleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/banner")
@RequiredArgsConstructor
@Tag(name = "Banner", description = "Entrypoint for Banner")
public class BannerController {
    private final BannerService bannerService;

    @GetMapping(value = "")
    @Operation(
            summary = "Get banners by app type and type",
            description = "Get banners by app type and type"
    )
    public MultiResponse<BannerDTO> getAll(@RequestParam(name = "bannerType", required = false) Integer bannerType) {
        return null;
    }

    @GetMapping(value = "/{id}")
    @Operation(
            summary = "Get banner by id",
            description = "Get banner by id"
    )
    public SingleResponse<BannerDTO> getById(@PathVariable(name = "id") Integer id) {
        return bannerService.getActiveBanner(id);
    }
}
