package me.common.util;

import org.apache.commons.io.FilenameUtils;
import org.hibernate.validator.internal.util.StringHelper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class ValidateUtils {
    public static final List<String> DEFAULT_VALID_IMAGE_EXTENSION = Arrays.asList("JPG", "JPEG", "PNG", "TIF");
    public static final List<String> DEFAULT_VALID_FILE_EXTENSION = Arrays.asList("JPG", "JPEG", "PNG", "TIF", "ZIP", "TXT", "PDF");
    private static final Validator validator;

    static {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    public static boolean isNullOrEmpty(Object obj) {
        return Objects.isNull(obj);
    }

    public static boolean isNonEmpty(Object obj) {
        return !isNullOrEmpty(obj);
    }

    public static boolean notNullOrEmpty(List<?> lst) {
        return lst != null && !lst.isEmpty();
    }

    public static boolean notNullOrEmpty(Object obj) {
        return Objects.nonNull(obj);
    }

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    public static boolean isNonEmpty(String str) {
        return !isNullOrEmpty(str);
    }

    public static boolean isNullOrEmpty(List<?> lst) {
        return lst == null || lst.isEmpty();
    }
    public static boolean isNonEmpty(List<?> lst) {
        return !isNullOrEmpty(lst);
    }

    public static boolean isNullOrEmpty(Map<?, ?> lst) {
        return lst == null || lst.isEmpty();
    }

    public static boolean isNullOrEmpty(JSONObject lst) {
        return lst == null || lst.isEmpty();
    }

    public static boolean isNullOrEmpty(JSONArray lst) {
        return lst == null || lst.isEmpty();
    }

    public static boolean isNullOrEmpty(Set<?> lst) {
        return lst == null || lst.isEmpty();
    }

    public static boolean isNullOrFalse(Boolean cond) {
        return cond == null || !cond;
    }

    public static boolean isJSONValid(String string) {
        try {
            new JSONObject(string);
        } catch (JSONException ex) {
            try {
                new JSONArray(string);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }

    public static boolean isImageFile(String currentName) {
        String extension = FilenameUtils.getExtension(currentName);
        if (!isNullOrEmpty(extension)) {
            String ext = extension.toUpperCase();
            return Arrays.asList("JPG", "JPEG", "PNG", "TIF").contains(ext);
        }
        return false;
    }

    public static boolean isPdfFile(String currentName) {
        String extension = FilenameUtils.getExtension(currentName);
        if (!isNullOrEmpty(extension)) {
            String ext = extension.toUpperCase();
            return Arrays.asList("PDF").contains(ext);
        }
        return false;
    }

    public static boolean isGifFile(String currentName) {
        String extension = FilenameUtils.getExtension(currentName);
        if (!isNullOrEmpty(extension)) {
            String ext = extension.toUpperCase();
            return Arrays.asList("GIF").contains(ext);
        }
        return false;
    }

    public static boolean isVideoFile(String currentName) {
        String extension = FilenameUtils.getExtension(currentName);
        if (!isNullOrEmpty(extension)) {
            String ext = extension.toUpperCase();
            return Arrays.asList("MP4", "MOV", "AVI", "MKV").contains(ext);
        }
        return false;
    }

    public static boolean isValidFile(String currentName, List<String> validExtensionFile) {
        String extension = FilenameUtils.getExtension(currentName);
        if (!isNullOrEmpty(extension)) {
            String ext = extension.toUpperCase();
            return validExtensionFile.contains(ext);
        }
        return false;
    }

    public static boolean isUrl(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (MalformedURLException | URISyntaxException e) {
//            e.printStackTrace();
        }
        return false;
    }

    public static String orElse(String value, String defaultValue) {
        return Optional.ofNullable(value).orElse(defaultValue);
    }

    public static RespValidateBean validateBean(Object o) {
        final RespValidateBean resp = new RespValidateBean();

        final Set<ConstraintViolation<Object>> validate = validator.validate(o);
        if (isNullOrEmpty(validate)) {
            resp.setCode(0).setMsg("success");
        } else {
            resp.setCode(-1);
            final List<String> listError = validate.stream().map(i -> String.format("%s:%s", i.getPropertyPath().toString(), i.getMessage())).collect(Collectors.toList());
            resp.setMsg(StringHelper.join(listError, " | "));
        }
        return resp;
    }

    public static boolean validateNotNull(Object object) {
        try {
            if (isNullOrEmpty(object)) {
                return false;
            }
            for (Field f : object.getClass().getDeclaredFields()) {
                if (Objects.isNull(f.get(object)))
                    return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean validateNotNullExcept(Object object, List<String> excepts) {
        try {
            if (isNullOrEmpty(object)) {
                return false;
            }
            for (Field f : object.getClass().getDeclaredFields()) {
                if (!excepts.contains(f.getName())) {
                    if (Objects.isNull(f.get(object)))
                        return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}

