package me.miniapp.base.infrastructure.banner;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serializable;

@Entity
@Table(name = "banner_type_enum")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Data
public class BannerTypePO implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    private String name;
}
