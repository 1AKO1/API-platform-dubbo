package com.bagamao.apicommon.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
* 接口信息
* @TableName interface_info
*/
@Data
@TableName(value = "interface_info")
public class InterfaceInfo implements Serializable {

    /**
    * 主键
    */
    @TableId(type = IdType.AUTO)
//    @NotNull(message="[主键]不能为空")
//    @ApiModelProperty("主键")
    private Long id;
    /**
    * 名称
    */
//    @NotBlank(message="[名称]不能为空")
//    @Size(max= 256,message="编码长度不能超过256")
//    @ApiModelProperty("名称")
    private String name;
    /**
    * 描述
    */
//    @Size(max= 256,message="编码长度不能超过256")
//    @ApiModelProperty("描述")
    private String description;
    /**
    * 接口地址
    */
//    @NotBlank(message="[接口地址]不能为空")
//    @Size(max= 512,message="编码长度不能超过512")
//    @ApiModelProperty("接口地址")
    private String urlHost;
    //    @NotBlank(message="[接口地址]不能为空")
    //    @Size(max= 512,message="编码长度不能超过512")
    //    @ApiModelProperty("接口地址")
    private String urlPath;
    /**
     * 请求参数
     * [
     *     {"name": "username", "type": "string"},
     * ]
     */
//    @Size(max= -1,message="编码长度不能超过-1")
//    @ApiModelProperty("请求参数")
    private String requestParams;
    /**
    * 请求头
    */
//    @Size(max= -1,message="编码长度不能超过-1")
//    @ApiModelProperty("请求头")
    private String requestHeader;
    /**
    * 响应头
    */
//    @Size(max= -1,message="编码长度不能超过-1")
//    @ApiModelProperty("响应头")
    private String responseHeader;
    /**
     * 接口金额
     */
//    @ApiModelProperty("接口金额")
    private Long price;
    /**
    * 接口状态（0-关闭，1-开启，2-异常）
    */
//    @NotNull(message="[接口状态（0-关闭，1-开启，2-异常）]不能为空")
//    @ApiModelProperty("接口状态（0-关闭，1-开启，2-异常）")
    private Integer status;
    /**
    * 请求类型
    */
//    @NotBlank(message="[请求类型]不能为空")
//    @Size(max= 256,message="编码长度不能超过256")
//    @ApiModelProperty("请求类型")
    private String method;
    /**
    * 创建人
    */
//    @NotNull(message="[创建人]不能为空")
//    @ApiModelProperty("创建人")
    private Long userId;
    /**
    * 创建时间
    */
//    @NotNull(message="[创建时间]不能为空")
//    @ApiModelProperty("创建时间")
    private Date createTime;
    /**
    * 更新时间
    */
//    @NotNull(message="[更新时间]不能为空")
//    @ApiModelProperty("更新时间")
    private Date updateTime;
    /**
    * 是否删除(0-未删, 1-已删)
    */
//    @NotNull(message="[是否删除(0-未删, 1-已删)]不能为空")
//    @ApiModelProperty("是否删除(0-未删, 1-已删)")
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
