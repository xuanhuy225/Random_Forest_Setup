package me.common.util;

import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ListStringUtils {

    public static List<Integer> parseStringToListInt(String string) {
        List<Integer> rs = Collections.emptyList();
        if (ObjectUtils.isEmpty(string)) {
            return rs;
        } else {
            try {
                rs = Arrays.stream(string.split(","))
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());
            } catch (Exception ignored) {
            }
            return rs;
        }
    }

    public static String parseListIntToString(List<Integer> list) {
        return list.stream().map(Object::toString).collect(Collectors.joining(","));
    }

    public static List<Long> parseStringToListLong(String string) {
        List<Long> rs = Collections.emptyList();
        if (ObjectUtils.isEmpty(string)) {
            return rs;
        } else {
            try {
                rs = Arrays.stream(string.split(","))
                        .map(Long::parseLong)
                        .collect(Collectors.toList());
            } catch (Exception ignored) {
            }
            return rs;
        }
    }

    public static String parseListLongToString(List<Long> list) {
        return list.stream().map(Object::toString).collect(Collectors.joining(","));
    }

    public static List<Long> convertListStringToListLong(List<String> list) {
        if (list == null) {
            return Collections.emptyList();
        }
        return list.stream().map(Long::parseLong).collect(Collectors.toList());
    }

    public static List<String> convertListLongToListString(List<Long> list) {
        if (list == null) {
            return Collections.emptyList();
        }
        return list.stream().map(Object::toString).collect(Collectors.toList());
    }
}
