package com.wizzstudio.substitute.enums.indent;

import com.wizzstudio.substitute.enums.CodeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 订单排序方式
 * Created By Cx On 2018/11/21 22:25
 */
@Getter
@AllArgsConstructor
public enum IndentSortTypeEnum implements CodeEnum {
    SORT_BY_DEFAULT(10,"根据默认排序"),
    SORT_BY_TIME(10,"根据创建时间排序"),
    SORT_BY_PRICE(20,"根据价格排序"),
    ;

    //状态码
    private Integer code;
    //状态描述
    private String message;
}
