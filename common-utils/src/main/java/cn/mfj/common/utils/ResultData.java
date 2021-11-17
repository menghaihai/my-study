package cn.mfj.common.utils;

/**
 * 接口返回结果封装
 *
 * @author mfj on 2021/11/17
 */
public class ResultData<T> {

    /**
     * 错误码
     */
    private Integer errorCode;

    /**
     * 错误信息
     */
    private String errorMsg;

    /**
     * 操作成功的状态码
     */
    private static final Integer SUCCESS_CODE = 200;

    /**
     * 操作失败的状态码
     */
    private static final Integer ERROR_CODE = 500;

    /**
     * 数据
     */
    private T data;

    /**
     * 数据结果的封装，自定义异常code和异常信息
     *
     * @param errorCode 错误码
     * @param errorMsg  错误信息
     * @param data      封装的数据
     */
    public ResultData(Integer errorCode, String errorMsg, T data) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.data = data;
    }

    /**
     * 数据结果的封装，该构造为响应成功时使用
     *
     * @param data 数据体
     */
    public ResultData(T data) {
        this.errorCode = SUCCESS_CODE;
        this.data = data;
    }

    /**
     * 成功时结果集封装
     *
     * @param data 封装的数据体
     */
    public void success(T data) {
        success(SUCCESS_CODE, data);
    }

    /**
     * 成功时结果集封装
     *
     * @param errorCode 状态码
     * @param data      封装的数据体
     */
    public void success(Integer errorCode, T data) {
        this.errorCode = errorCode;
        this.data = data;
    }

    /**
     * 失败时结果集的封装，默认使用500错误码
     *
     * @param errorMsg 错误信息
     * @param data     数据体
     */
    public void error(String errorMsg, T data) {
        error(ERROR_CODE, errorMsg, data);
    }

    /**
     * 失败时结果集的封装
     *
     * @param errorCode 错误码
     * @param errorMsg  错误信息
     * @param data      数据体
     */
    public void error(Integer errorCode, String errorMsg, T data) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.data = data;
    }

}
