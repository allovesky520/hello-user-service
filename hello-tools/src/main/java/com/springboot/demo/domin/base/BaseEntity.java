package com.springboot.demo.domin.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author yyzh
 * @version 1.0
 * @title: BaseEntity
 * @description: TODO
 * @date 2021-02-18 16:32:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    public static final int VALID = 1, INVALID = 0;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "create_time", updatable = false)
    @ApiModelProperty(value = "数据创建时间")
    private Timestamp createTime;

    @Column(name = "create_by", updatable = false)
    @ApiModelProperty(value = "数据创建人")
    private String createBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "update_time", insertable = false)
    @ApiModelProperty(value = "数据最近一次修改时间")
    private Timestamp updateTime;

    @Column(name = "update_by", insertable = false)
    @ApiModelProperty(value = "数据修改人")
    private String updateBy;

    @ApiModelProperty(value = "状态：0无效1有效")
    @Column(name = "status")
    private Integer status;

    public BaseEntity modify() {
        this.updateTime = new Timestamp(System.currentTimeMillis());
        //当前账号
//        this.updateBy = HeaderParamHolder.getContext().getAccount();
        return this;
    }

    public BaseEntity create() {
        this.status = 1;
        this.createTime = new Timestamp(System.currentTimeMillis());
//        this.createBy = HeaderParamHolder.getContext().getAccount();
        this.updateTime = new Timestamp(System.currentTimeMillis());
        //当前账号
//        this.updateBy = HeaderParamHolder.getContext().getAccount();
        return this;
    }

    public BaseEntity valid() {
        this.status = 1;
        return this;
    }

    public BaseEntity invalid() {
        this.status = 0;
        return this;
    }

}
