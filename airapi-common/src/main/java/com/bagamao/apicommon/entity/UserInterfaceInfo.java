package com.bagamao.apicommon.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/*
* 用户调用接口关系表
* */
@TableName("user_interface_info")
@Data
public class UserInterfaceInfo implements Serializable {
    /*
    * 主键
    * */
    @TableId(type = IdType.AUTO)
    private Long id;

    /*
     * 调用用户id
     * */
    private Long userId;

    /*
     * 接口id
     * */
    private Long interfaceInfoId;

    /*
     * 总调用次数
     * */
    private Integer totalNum;

    /*
     * 剩余次数
     * */
    private Integer leftNum;

    /*
     * 针对当前用户的接口状态：0 正常， 1 禁用
     * */
    private Integer status;

    /*
     * 创建时间
     * */
    private Date createTime;

    /*
     * 更新时间
     * */
    private Date updateTime;

    /*
     * 是否删除 0 未删除， 1 已删除
     * */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
