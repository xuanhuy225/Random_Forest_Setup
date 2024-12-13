package me.common.libs.objs;

public class HttpResponseData {
    Integer httpCode;
    String responseBody;

    public HttpResponseData(Integer httpCode, String responseBody) {
        this.httpCode = httpCode;
        this.responseBody = responseBody;
    }

    public Integer getHttpCode() {
        return httpCode;
    }

    public String getResponseBody() {
        return responseBody;
    }
}
