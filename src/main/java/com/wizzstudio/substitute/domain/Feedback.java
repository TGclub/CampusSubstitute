package com.wizzstudio.substitute.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wizzstudio.substitute.util.serializer.Date2LongSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.Date;

/**
 * 反馈信息
 * Created By Cx On 2018/11/19 23:31
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
public class Feedback {

    //反馈id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer feedbackId;

    //反馈信息
    private String content;

    //用户id
    private String userId;

    //是否已读，0：未读，1：已读，默认为0
    private Boolean isRead;

    //创建时间，默认为当前时间
    @Column(updatable = false, insertable = false)
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;
}
