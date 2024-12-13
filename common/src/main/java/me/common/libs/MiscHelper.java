package me.common.libs;

import me.common.util.ValidateUtils;

import java.util.HashMap;
import java.util.Map;

public class MiscHelper {
    private static final Map<Class, String> _classSimpleNameMap = new HashMap<>(); //for cache

    public static String getSimpleClass(Class clazz) {
        if (clazz == null) {
            return "";
        }

        String name = _classSimpleNameMap.get(clazz);
        if (name == null) {
            name = clazz.getSimpleName();
            if (!ValidateUtils.isNullOrEmpty(name)) {
                _classSimpleNameMap.putIfAbsent(clazz, name);
            } else {
                name = "";
            }
        }

        return name;
    }
}
