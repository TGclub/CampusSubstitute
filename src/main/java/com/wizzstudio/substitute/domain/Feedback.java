package com.wizzstudio.substitute.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wizzstudio.substitute.util.serializer.Date2LongSerializer;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * 反馈信息
 * Created By Cx On 2018/11/19 23:31
 */
@Entity
@Data
@Builder
public class Feedback {

    //反馈id
    @Id
    private Integer feedbackId;

    //反馈信息
    private String content;

    //用户id
    private String userId;

    //是否已读，0：未读，1：已读，默认为0
    private Boolean isRead;

    //创建时间，默认为当前时间
    @Column(updatable = false,insertable = false)
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;
}
