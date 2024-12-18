package me.miniapp.base.domain.banner;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BannerType implements Serializable {
    private Integer id;
    private String name;

    public BannerType(Integer id) {
        this.id = id;
    }
}
