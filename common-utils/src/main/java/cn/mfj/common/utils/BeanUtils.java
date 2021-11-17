package cn.mfj.common.utils;

import java.util.*;

/**
 * 对BeanUtils的封装，主要针对的是List、Map特殊对象的处理
 *
 * @author mfj on 2021/11/17
 */
public class BeanUtils {

    /**
     * Bean copy，同spring-beans的copy
     *
     * @param source 信息源对象
     * @param target 目标对象
     */
    public static void copyProperties(Object source, Object target) {
        org.springframework.beans.BeanUtils.copyProperties(source, target);
    }

    /**
     * Bean copy，根据class类型生成目标对象
     *
     * @param source 信息源对象
     * @param tClass 目标对象的类型
     * @param <T>    方法泛型
     * @return 目标对象
     */
    public static <T> T copyProperties(Object source, Class<T> tClass) {
        if (source == null) {
            return null;
        }
        T target = null;
        try {
            target = tClass.getConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        copyProperties(source, target);
        return target;
    }

    /**
     * List bean copy，生成指定类型泛型的List对象，并copy源数据
     *
     * @param sourceList 源数据列表
     * @param tClass     目标数据类型
     * @param <T>        目标数据泛型
     * @return copy完成后的目标数据列表
     */
    @SuppressWarnings("rawtypes")
    public static <T> List<T> copyListProperties(List sourceList, Class<T> tClass) {
        List<T> targetList = new ArrayList<>();
        if (sourceList != null && sourceList.size() != 0) {
            for (Object source : sourceList) {
                T target = copyProperties(source, tClass);
                targetList.add(target);
            }
        }
        return targetList;
    }


    /**
     * Set bean copy，生成指定类型泛型的List对象，并copy源数据
     *
     * @param sourceSet 源数据列表
     * @param tClass    目标数据类型
     * @param <T>       目标数据泛型
     * @return copy完成后的目标数据列表
     */
    @SuppressWarnings("rawtypes")
    public static <T> Set<T> copySetProperties(Set sourceSet, Class<T> tClass) {
        Set<T> targetSet = new HashSet<>();
        if (sourceSet != null && sourceSet.size() != 0) {
            for (Object source : sourceSet) {
                T target = copyProperties(source, tClass);
                targetSet.add(target);
            }
        }
        return targetSet;
    }

    /**
     * Map bean copy，仅仅支持基本类型包装类、String和带有setter、getter的bean
     * 对于Collection、Map、Properties等类型的封装不支持
     *
     * @param sourceMap  源信息Map对象
     * @param keyClass   Map对象的Key类型
     * @param valueClass Map对象的Value类型
     * @param <T>        Map对象的Key泛型
     * @param <K>        Map对象的Value泛型
     * @return copy完成后的目标Map对象
     */
    public static <T, K> Map<T, K> copyMapProperties(Map<T, K> sourceMap, Class<T> keyClass, Class<K> valueClass) {
        Map<T, K> targetMap = new HashMap<>();
        if (sourceMap != null && sourceMap.keySet().size() != 0) {
            sourceMap.forEach((key, value) -> {
                T keyTarget = copyProperties(key, keyClass);
                K valueTarget = copyProperties(value, valueClass);
                targetMap.put(keyTarget, valueTarget);
            });
        }
        return targetMap;
    }
}
