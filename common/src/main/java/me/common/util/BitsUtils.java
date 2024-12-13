package me.common.util;

import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class BitsUtils {
    public static Long toMask(Set<Integer> bits) {
        long m = 0L;
        for (Integer b : bits) {
            m |= (1L << (b - 1));
        }
        return m;
    }

    public static Set<Integer> toSet(Long mask) {
        Set<Integer> set = new java.util.HashSet<>();
        long m = 1L;
        int i = 1;
        while (m <= mask) {
            if ((m & mask) == m) {
                set.add(i);
            }
            m = m << 1;
            i += 1;
        }
        return set;
    }
}
