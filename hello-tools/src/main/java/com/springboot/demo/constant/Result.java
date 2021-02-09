package com.springboot.demo.constant;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "返回数据")
public class Result<T> implements Serializable {

    /**
     * 关于用户的状态码，全部在SSO项目中已定义4001-4099，分别有：
     * 4011 登录失败
     * 4017 Token无效
     * 4031 已登录，但权限不足
     */

    /**
     * 成功返回
     */
    public static final int OK = 0;
    /**
     * 请求成功，但未匹配内容
     */
    public static final int NOT_CONTENT = 204;

    /**
     * 提交内容未修改
     */
    public static final int NOT_MODIFIED = 304;
    /**
     * 资源不存在
     */
    public static final int NOT_EXIST = 404;

    /**
     * 参数错误
     */
    public static final int PARAM_ERROR = 4000;
    /**
     * 未知异常
     */
    public static final int INTERNAL_SERVER_ERROR = 5000;
    /**
     * 服务器维护中
     */
    public static final int SERVER_UPGRADE = 5100;

    /**
     * 空对象
     */
    private static final EmptyData EMPTY_DATA = new EmptyData();

    /**
     * 返回响应码
     */
    @ApiModelProperty(value = "返回响应码")
    private int code;
    /**
     * 返回消息
     */
    @ApiModelProperty(value = "返回消息")
    private String message;

    /**
     * 返回数据
     */
    private T data;

    /**
     * 记录总数（分页时会有值返回）
     */
    @ApiModelProperty(value = "记录总数（分页时会有值返回）")
    private Long total;

    /**
     * 成功返回，无数据
     *
     * @return Result
     */
    public static <T> Result<T> ok() {
        ResultBuilder<T> builder = Result.builder();
        return builder.code(OK).build();
    }

    /**
     * 成功返回，有数据
     * 为了统一规范 当data为null时 做了额外处理
     *
     * @param <T> data
     * @return Result
     */
    public static <T> Result<T> ok(T data) {
        ResultBuilder<T> builder = Result.builder();
        return builder.code(OK)
                .data(Optional.ofNullable(data).orElse((T) EMPTY_DATA))
                .build();
    }

    public static <T> Result<List<T>> ok(List<T> data) {
        ResultBuilder<List<T>> builder = Result.builder();
        return builder.code(OK)
                .data(Optional.ofNullable(data).orElse(new ArrayList<>()))
                .build();
    }

    public static <T> Result<Map<String, T>> ok(Map<String, T> data) {
        ResultBuilder<Map<String, T>> builder = Result.builder();
        return builder.code(OK)
                .data(Optional.ofNullable(data).orElse(new HashMap<>()))
                .build();
    }

    /**
     * 成功返回数据及记录总数
     *
     * @param data  数据
     * @param total 记录总数
     * @param <T>
     * @return
     */
    public static <T> Result<List<T>> ok(List<T> data, int total) {
        return ok(data, Long.valueOf(total));
    }

    /**
     * 成功返回，有数据，有分页记录值
     *
     * @param total 总数
     * @param data  分页数据
     * @return Result
     */
    public static <T> Result<List<T>> ok(List<T> data, Long total) {
        ResultBuilder<List<T>> builder = Result.builder();
        return builder.code(OK)
                .data(Optional.ofNullable(data).orElse(new ArrayList<>()))
                .total(total)
                .build();
    }

    /**
     * 查询成功，但无匹配内容
     *
     * @param message 提示消息
     * @return
     */
    public static <T> Result<T> noMatchFound(String message) {
        ResultBuilder<T> builder = Result.builder();
        return builder.code(NOT_CONTENT)
                .total(0L)
                .message(message)
                .build();
    }

    /**
     * 请求失败，参数错误
     *
     * @param message
     * @param <T>
     * @return
     */
    public static <T> Result<T> paramError(String message) {
        ResultBuilder<T> builder = Result.builder();
        return builder.code(PARAM_ERROR)
                .message(message)
                .build();
    }

    /**
     * 失败返回
     *
     * @param <T>
     * @return
     */
    public static <T> Result<T> failed(int code, String message) {
        ResultBuilder<T> builder = Result.builder();
        return builder.code(code).message(message).build();
    }
}
