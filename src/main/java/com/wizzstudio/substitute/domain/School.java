package com.wizzstudio.substitute.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created By Cx On 2018/10/26 20:50
 */
@Entity
@Data
public class School {
    //学校id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //学校名称
    private String schoolName;
}
