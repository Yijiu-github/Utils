package utils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class TsyListUtil2<T> {
    private final Map<String, Field> fieldCache = new ConcurrentHashMap<>();

    /**
     * 按字段排序
     * @param list 待排序的列表
     * @param fieldName 排序字段名
     * @param ascending true-升序，false-降序
     * @return 排序后的列表
     */
    public List<T> sortList(List<T> list, String fieldName, boolean ascending) {
        Comparator<T> comparator = (o1, o2) -> {
            try {
                Field field = getField(o1.getClass(), fieldName);
                Object fieldValue1 = field.get(o1);
                Object fieldValue2 = field.get(o2);

                if (fieldValue1 instanceof String && isNumeric((String) fieldValue1)) {
                    Integer value1 = Integer.parseInt((String) fieldValue1);
                    Integer value2 = Integer.parseInt((String) fieldValue2);
                    return ascending ? value1.compareTo(value2) : value2.compareTo(value1);
                } else if (fieldValue1 instanceof Comparable) {
                    Comparable value1 = (Comparable) fieldValue1;
                    Comparable value2 = (Comparable) fieldValue2;
                    return ascending ? value1.compareTo(value2) : value2.compareTo(value1);
                } else {
                    throw new IllegalArgumentException("Cannot compare field values");
                }

            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        };

        Collections.sort(list, comparator);
        return list;
    }

    private Field getField(Class<?> clazz, String fieldName) {
        return fieldCache.computeIfAbsent(clazz.getName() + "." + fieldName, k -> {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field;
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private boolean isNumeric(String str) {
        return str != null && str.matches("\\d+");
    }
}
