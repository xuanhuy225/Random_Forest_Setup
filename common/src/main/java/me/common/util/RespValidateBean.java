package me.common.util;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RespValidateBean {
    private Integer code = -1;
    private String msg = "";

    RespValidateBean(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public RespValidateBean() {
    }


    public boolean isValid() {
        return code != null && code == 0;
    }

    public void setSuccess() {
        this.code = 0;
        this.msg = "Success";
    }

    public void setUnknownError() {
        this.code = -1;
        this.msg = "Unknown error";
    }
}
