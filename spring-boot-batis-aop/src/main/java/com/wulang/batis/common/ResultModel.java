package com.wulang.batis.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用来快速返回结果的model对象
 */

@Data
@ApiModel(value = "ResultModel", description = "用来快速返回结果的model对象")
public class ResultModel<T> {

    @ApiModelProperty(value = "每页显示条数")
    private Long limit;

    @ApiModelProperty(value = "当前页")
    private Long page;

    @ApiModelProperty(value = "总页数")
    private Long pages;

    @ApiModelProperty(value = "总条数")
    private Long total;

    @ApiModelProperty(value = "是否成功返回")
    private Boolean success;

    @ApiModelProperty(value = "返回的状态码")
    private Integer code;

    @ApiModelProperty(value = "返回消息")
    private String msg;

    @ApiModelProperty(value = "返回名称")
    private String name;

    @ApiModelProperty(value = "返回标题")
    private String title;

    @ApiModelProperty(value = "返回数据实体")
    private T data;
    @ApiModelProperty(value = "返回数据实体")
    private T time;


    public ResultModel() {
    }

    public ResultModel(Long limit, Long page, Long pages, Long total, Boolean success, String msg, T data) {
        this.limit = limit;
        this.page = page;
        this.pages = pages;
        this.total = total;
        this.success = success;
        this.msg = msg;
        this.data = data;
    }

    public ResultModel(Boolean success, Long limit, Long page, Long pages, Long total, String msg, T data) {
        this.success = success;
        this.limit = limit;
        this.page = page;
        this.pages = pages;
        this.total = total;
        this.msg = msg;
        this.data = data;
    }


    public Boolean isSuccess() {
        return getSuccess();
    }

    public ResultModel(Boolean success, Integer code, String msg, String title, T data) {
        this.success = success;
        this.code = code;
        this.msg = msg;
        this.title = title;
        this.data = data;
    }

    public ResultModel(Boolean success, Integer code, String name, String title, T data, T time) {
        this.success = success;
        this.code = code;
        this.name = name;
        this.title = title;
        this.time = time;
        this.data = data;
    }

    /**
     * 成功返回结果集
     *
     * @param data
     * @return
     */
    public ResultModel success(Object data) {
        return new ResultModel<>(RestConstant.HTTP_SUCCESS, RestConstant.DEFAULT_SUCCESS_CODE, RestConstant.DEFAULT_MSG, RestConstant.DEFAULT_TITLE, data);
    }

    public ResultModel success(String msg) {
        return new ResultModel<>(RestConstant.HTTP_SUCCESS, RestConstant.DEFAULT_SUCCESS_CODE, msg, RestConstant.DEFAULT_TITLE, RestConstant.DEFAULT_DATA);
    }
    public static ResultModel success1(String msg) {
        return new ResultModel<>(RestConstant.HTTP_SUCCESS, RestConstant.DEFAULT_SUCCESS_CODE, msg, RestConstant.DEFAULT_TITLE, RestConstant.DEFAULT_DATA);
    }
    public ResultModel success1(String msg, Object data) {
        return new ResultModel<>(RestConstant.HTTP_SUCCESS, RestConstant.DEFAULT_SUCCESS_CODE, msg, RestConstant.DEFAULT_TITLE, data);
    }

    public static ResultModel success(String title, String msg, Object data) {
        return new ResultModel<>(RestConstant.HTTP_SUCCESS, RestConstant.DEFAULT_SUCCESS_CODE, msg, title, data);
    }

    public static ResultModel success(String msg, Object data) {
        return new ResultModel<>(RestConstant.HTTP_SUCCESS, RestConstant.DEFAULT_SUCCESS_CODE, msg, RestConstant.DEFAULT_TITLE, data);
    }

    public static ResultModel success(String msg, Object time, Object data) {
        return new ResultModel<>(RestConstant.HTTP_SUCCESS, RestConstant.DEFAULT_SUCCESS_CODE, msg, RestConstant.DEFAULT_TITLE, time, data);
    }

    /**
     * 失败返回结果集
     *
     * @param msg
     * @return
     */
    public ResultModel failure(String msg) {
        return new ResultModel<>(RestConstant.HTTP_FAILURE, RestConstant.DEFAULT_FAILURE_CODE, msg, RestConstant.DEFAULT_TITLE, RestConstant.DEFAULT_DATA);
    }
    public static ResultModel failure1(String msg) {
        return new ResultModel<>(RestConstant.HTTP_FAILURE, RestConstant.DEFAULT_FAILURE_CODE, msg, RestConstant.DEFAULT_TITLE, RestConstant.DEFAULT_DATA);
    }

    public static ResultModel failure(Object data) {
        return new ResultModel<>(RestConstant.HTTP_FAILURE, RestConstant.DEFAULT_FAILURE_CODE, RestConstant.DEFAULT_MSG, RestConstant.DEFAULT_TITLE, data);
    }

    public static ResultModel failure(String msg, Object data) {
        return new ResultModel<>(RestConstant.HTTP_FAILURE, RestConstant.DEFAULT_FAILURE_CODE, msg, RestConstant.DEFAULT_TITLE, data);
    }

    public static ResultModel failure(Integer code, String msg) {
        return new ResultModel<>(RestConstant.HTTP_FAILURE, code, msg, RestConstant.DEFAULT_TITLE, RestConstant.DEFAULT_DATA);
    }

    public static ResultModel failure() {
        return new ResultModel<>(RestConstant.HTTP_FAILURE, RestConstant.DEFAULT_FAILURE_CODE, RestConstant.MSG_FAILURE, RestConstant.DEFAULT_TITLE, RestConstant.DEFAULT_DATA);
    }


    /**
     * 查询数据成功返回结果集
     *
     * @param data
     * @return
     */
    public static ResultModel selectSuccess(Object data) {
        return new ResultModel<>(RestConstant.HTTP_SUCCESS, RestConstant.DEFAULT_SUCCESS_CODE, RestConstant.SELECT_MSG_SUCCESS, RestConstant.DEFAULT_TITLE, data);
    }

    /**
     * 查询数据失败
     *
     * @return
     */
    public static ResultModel selectFailure() {
        return new ResultModel<>(RestConstant.HTTP_FAILURE, RestConstant.DEFAULT_FAILURE_CODE, RestConstant.SELECT_MSG_FAILURE, RestConstant.DEFAULT_TITLE, RestConstant.DEFAULT_DATA);
    }

    public static ResultModel selectPaginationSuccess(PageResponse page, Object data) {
        return new ResultModel<>(RestConstant.HTTP_SUCCESS, page.getLimit(), page.getPage(), page.getTotalPage(), page.getTotal(), RestConstant.SELECT_MSG_SUCCESS, data);
    }
    public static ResultModel selectPaginationSuccess(PageResponse page) {
        return new ResultModel<>(RestConstant.HTTP_SUCCESS, page.getLimit(), page.getPage(), page.getTotalPage(), page.getTotal(), RestConstant.SELECT_MSG_SUCCESS, page.getResultData());
    }
    /**
     * 新增数据成功
     *
     * @return
     */
    public static ResultModel createSuccess() {
        return new ResultModel<>(RestConstant.HTTP_SUCCESS, RestConstant.DEFAULT_SUCCESS_CODE, RestConstant.CREATE_MSG_SUCCESS, RestConstant.DEFAULT_TITLE, RestConstant.DEFAULT_DATA);
    }

    public static ResultModel createSuccess(Object data) {
        return new ResultModel<>(RestConstant.HTTP_SUCCESS, RestConstant.DEFAULT_SUCCESS_CODE, RestConstant.CREATE_MSG_SUCCESS, RestConstant.DEFAULT_TITLE, data);
    }


    /**
     * 新增数据失败
     *
     * @return
     */
    public static ResultModel createFailure() {
        return new ResultModel<>(RestConstant.HTTP_FAILURE, RestConstant.DEFAULT_FAILURE_CODE, RestConstant.CREATE_MSG_FAILURE, RestConstant.DEFAULT_TITLE, RestConstant.DEFAULT_DATA);
    }

    /**
     * 更新数据成功返回结果集
     *
     * @return
     */
    public static ResultModel updateSuccess() {
        return new ResultModel<>(RestConstant.HTTP_SUCCESS, RestConstant.DEFAULT_SUCCESS_CODE, RestConstant.UPDATE_MSG_SUCCESS, RestConstant.DEFAULT_TITLE, RestConstant.DEFAULT_DATA);
    }

    public static ResultModel updateSuccess(Object data) {
        return new ResultModel<>(RestConstant.HTTP_SUCCESS, RestConstant.DEFAULT_SUCCESS_CODE, RestConstant.UPDATE_MSG_SUCCESS, RestConstant.DEFAULT_TITLE, data);
    }

    public static ResultModel updateSuccess(Object data, String title) {
        return new ResultModel<>(RestConstant.HTTP_SUCCESS, RestConstant.DEFAULT_SUCCESS_CODE, RestConstant.UPDATE_MSG_SUCCESS, title, data);
    }


    /**
     * 更新数据失败
     *
     * @return
     */
    public static ResultModel updateFailure() {
        return new ResultModel<>(RestConstant.HTTP_FAILURE, RestConstant.DEFAULT_FAILURE_CODE, RestConstant.UPDATE_MSG_FAILURE, RestConstant.DEFAULT_TITLE, RestConstant.DEFAULT_DATA);
    }

    public static ResultModel updateFailure(String msg) {
        return new ResultModel<>(RestConstant.HTTP_FAILURE, RestConstant.DEFAULT_FAILURE_CODE, msg, RestConstant.DEFAULT_TITLE, RestConstant.DEFAULT_DATA);
    }

    /**
     * 删除数据成功
     *
     * @return
     */
    public static ResultModel deleteSuccess() {
        return new ResultModel<>(RestConstant.HTTP_SUCCESS, RestConstant.DEFAULT_SUCCESS_CODE, RestConstant.DELETE_MSG_SUCCESS, RestConstant.DELETE_TITLE_SUCCESS, RestConstant.DEFAULT_DATA);
    }

    public static ResultModel deleteSuccess(Object data) {
        return new ResultModel<>(RestConstant.HTTP_SUCCESS, RestConstant.DEFAULT_SUCCESS_CODE, RestConstant.DELETE_MSG_SUCCESS, RestConstant.DELETE_TITLE_SUCCESS, data);
    }

    /**
     * 删除数据失败
     *
     * @return
     */
    public static ResultModel deleteFailure() {
        return new ResultModel<>(RestConstant.HTTP_FAILURE, RestConstant.DEFAULT_FAILURE_CODE, RestConstant.DELETE_MSG_FAILURE, RestConstant.DELETE_TITLE_FAILURE, RestConstant.DEFAULT_DATA);
    }

    public static ResultModel deleteFailure(String msg) {
        return new ResultModel<>(RestConstant.HTTP_FAILURE, RestConstant.DEFAULT_FAILURE_CODE, msg, RestConstant.DELETE_TITLE_FAILURE, RestConstant.DEFAULT_DATA);
    }

    public static ResultModel deleteFailure(String title, String msg) {
        return new ResultModel<>(RestConstant.HTTP_FAILURE, RestConstant.DEFAULT_FAILURE_CODE, msg, title, RestConstant.DEFAULT_DATA);
    }


    public Boolean hashListData() {
        return this.isSuccess() && (this.getTotal() != null && !"0".equals(this.getTitle()));
    }


}
