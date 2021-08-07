package com.tony.mulitithread.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("t_file")
public class FileInfo implements Serializable {

    private static final long serialVersionUID = 5519424234648389493L;

    /**
     * 文件ID
     */
    @TableId(type = IdType.UUID)
    private String fileId;
    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 文件相对地址
     */
    private String fileUrl;
    /**
     * 文件后缀
     */
    private String fileSuffix;
    /**
     * 文件大小，单位KB
     */
    private int fileSize;
    /**
     * 文件类型，1：合同附件，2：合同依据材料，3：履约保障信息，4：发票，5：合同模板文件，6：合同审查意见汇总表，7：合同盖章电子版，8：通用审批导出
     */
    private int relationType;
    /**
     * 关联ID，例如合同ID
     */
    private String relationId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 数据状态，0：正常，-1：删除
     */
    private int status;
    /**
     * 数据来源，1：融通
     */
    private int sourceStatus;

}
