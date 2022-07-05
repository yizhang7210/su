package dev.su.domain.datasource;

import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class Utils {

    public static <K, V> Map<K, V> mergeMaps(Map<K, V> map1, Map<K, V> map2) {
        Map<K, V> newMap = new HashMap<>();
        newMap.putAll(map1);
        newMap.putAll(map2);
        return newMap;
    }

}
