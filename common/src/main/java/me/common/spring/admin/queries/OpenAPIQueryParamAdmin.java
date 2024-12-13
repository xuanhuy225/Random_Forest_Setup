package me.common.spring.admin.queries;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;


@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class OpenAPIQueryParamAdmin implements Serializable {

    private String order;
    private int page;
    @JsonProperty("itemsPerPage")
    private int itemsPerPage;
}
