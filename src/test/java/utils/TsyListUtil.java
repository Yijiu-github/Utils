package utils;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TsyListUtil<T> {
    /**
     * 按字段排序
     * @param list
     * @param fieldName
     * @param ascending true-升序，false-降序
     * @return
     */
        public List<T> sortList(List<T> list, String fieldName, boolean ascending) {
            Comparator<T> comparator = (o1, o2) -> {
                try {
                    Field field = o1.getClass().getDeclaredField(fieldName);
                    field.setAccessible(true);
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

                } catch (NoSuchFieldException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            };

            Collections.sort(list, comparator);
            return list; // 返回排序后的列表
        }

        private boolean isNumeric(String str) {
            try {
                Integer.parseInt(str);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
    }