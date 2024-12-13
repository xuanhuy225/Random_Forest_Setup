package me.common.objs;

public class ContentType {
    public static final String JPEG = "image/jpeg";
    public static final String PNG = "image/png";
    public static final String XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    public static final String SVG = "image/svg+xml";

    public static Boolean validateImage(String contentType) {
        if (contentType != null) {
            switch (contentType) {
                case JPEG:
                case PNG:
                    return true;
                default:
                    break;
            }
        }
        return false;
    }

    public static Boolean validateExcel(String contentType) {
        if (contentType != null) {
            switch (contentType) {
                case XLSX:
                    return true;
                default:
                    break;
            }
        }
        return false;
    }
}
