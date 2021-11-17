package cn.mfj.common.utils;

import cn.mfj.common.exception.AssertException;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

/**
 * 断言工具类
 *
 * @author mfj on 2021/11/17
 */
public class AssertUtils {

    /**
     * 默认错误码
     */
    private static final Integer ERROR_CODE = 500;

    /**
     * 默认错误信息
     */
    private static final String ERROR_MESSAGE = "参数错误";

    /**
     * 断言指定对象为空
     *
     * @param obj      断言对象
     * @param errorMsg 错误信息
     */
    public static void isEmpty(Object obj, String errorMsg) {
        if (!isEmpty(obj)) {
            throw new AssertException(ERROR_CODE, errorMsg);
        }
    }

    /**
     * 断言指定对象为空
     *
     * @param obj       断言对象
     * @param errorCode 错误码
     * @param errorMsg  错误信息
     */
    public static void isEmpty(Object obj, Integer errorCode, String errorMsg) {
        if (!isEmpty(obj)) {
            throw new AssertException(errorCode, errorMsg);
        }
    }

    /**
     * 断言指定对象非空
     *
     * @param obj      断言对象
     * @param errorMsg 错误信息
     */
    public static void isNotEmpty(Object obj, String errorMsg) {
        if (isEmpty(obj)) {
            throw new AssertException(ERROR_CODE, errorMsg);
        }
    }

    /**
     * 断言指定对象非空
     *
     * @param obj       断言对象
     * @param errorCode 错误码
     * @param errorMsg  错误信息
     */
    public static void isNotEmpty(Object obj, Integer errorCode, String errorMsg) {
        if (isEmpty(obj)) {
            throw new AssertException(errorCode, errorMsg);
        }
    }

    /**
     * 断言当前result为true，否则抛出异常
     *
     * @param result 断言的条件，为true正常执行，否则抛出异常
     */
    public static void isTrue(boolean result) {
        isTrue(result, ERROR_CODE, ERROR_MESSAGE);
    }

    /**
     * 断言当前result为true，否则抛出异常
     *
     * @param result   断言的条件，为true正常执行，否则抛出异常
     * @param errorMsg 错误信息
     */
    public static void isTrue(boolean result, String errorMsg) {
        isTrue(result, ERROR_CODE, errorMsg);
    }

    /**
     * 断言当前result为true，否则抛出异常
     *
     * @param result    断言的条件，为true正常执行，否则抛出异常
     * @param errorCode 错误码
     * @param errorMsg  错误信息
     */
    public static void isTrue(boolean result, Integer errorCode, String errorMsg) {
        isFalse(!result, errorCode, errorMsg);
    }

    /**
     * 断言当前result为false，否则抛出异常
     *
     * @param result 断言的条件，为false正常执行，否则抛出异常
     */
    public static void isFalse(boolean result) {
        isFalse(result, ERROR_CODE, ERROR_MESSAGE);
    }

    /**
     * 断言当前result为false，否则抛出异常
     *
     * @param result   断言的条件，为false正常执行，否则抛出异常
     * @param errorMsg 错误信息
     */
    public static void isFalse(boolean result, String errorMsg) {
        isFalse(result, ERROR_CODE, errorMsg);
    }

    /**
     * 断言当前result为false，否则抛出异常
     *
     * @param result    断言的条件，为false正常执行，否则抛出异常
     * @param errorCode 错误码
     * @param errorMsg  错误信息
     */
    public static void isFalse(boolean result, Integer errorCode, String errorMsg) {
        if (!result) {
            throw new AssertException(errorCode, errorMsg);
        }
    }

    /**
     * 获取到指定对象的断言结果
     *
     * @param obj 断言对象
     * @return 断言结果
     */
    @SuppressWarnings("rawtypes")
    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        } else if (obj instanceof CharSequence) {
            return ((CharSequence) obj).length() == 0;
        } else if (obj.getClass().isArray()) {
            return Array.getLength(obj) == 0;
        } else if (obj instanceof Collection) {
            return ((Collection) obj).isEmpty();
        } else {
            return obj instanceof Map && ((Map) obj).isEmpty();
        }
    }
}
