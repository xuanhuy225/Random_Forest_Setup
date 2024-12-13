package me.miniapp.base.infrastructure.banner;

import me.common.converter.ListLongStringConverter;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "banner")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Data
public class BannerPO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String description;
    @Column(name = "image_url")
    private String imageUrl;
    @Column(name = "redirect_url")
    private String redirectUrl;
    @OneToOne
    @JoinColumn(name = "banner_type")
    private BannerTypePO type;
    @Column(name = "item_idx")
    private Integer itemIdx;
    @Column(name = "allowed_audience_ids")
    @Convert(converter = ListLongStringConverter.class)
    private List<Long> showByAudiences;
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt = new Timestamp(System.currentTimeMillis());

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt = new Timestamp(System.currentTimeMillis());
}
