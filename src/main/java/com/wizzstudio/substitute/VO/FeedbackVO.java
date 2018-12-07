package com.wizzstudio.substitute.VO;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wizzstudio.substitute.util.serializer.Date2LongSerializer;
import lombok.Data;

import java.util.Date;

/**
 * Created by Kikyou on 18-12-7
 */
@Data
public class FeedbackVO {

    private Integer feedbackId;

    //反馈信息
    private String content;

    //用户id
    private String userId;

    //是否已读，0：未读，1：已读，默认为0
    private Boolean isRead;

    //创建时间，默认为当前时间
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    private Long phone;

}
