package com.wizzstudio.substitute.controller;

import com.wizzstudio.substitute.enums.GenderEnum;
import com.wizzstudio.substitute.enums.indent.IndentTypeEnum;
import com.wizzstudio.substitute.enums.ResultEnum;
import com.wizzstudio.substitute.exception.SubstituteException;
import com.wizzstudio.substitute.form.IndentCreateForm;
import com.wizzstudio.substitute.domain.Indent;
import com.wizzstudio.substitute.service.IndentService;
import com.wizzstudio.substitute.util.CommonUtil;
import com.wizzstudio.substitute.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created By Cx On 2018/11/12 22:09
 */
@Slf4j
@RestController
@RequestMapping("/indent")
public class IndentController {

    @Autowired
    IndentService indentService;

    /**
     * 验证订单 非必填参数 是否合法
     */
    private void checkIndentCreateForm(IndentCreateForm indentCreateForm) {
        List<Object> checkObjs = new ArrayList<>();
        if (CommonUtil.getEnum(indentCreateForm.getRequireGender(), GenderEnum.class) == null) {
            //性别类型不匹配
            log.error("[发布订单]性别类型不正确，requireGender={}", indentCreateForm.getRequireGender());
            throw new SubstituteException("性别类型不正确，requireGender=".concat(indentCreateForm.getRequireGender()),
                    ResultEnum.PARAM_ERROR.getCode());
        }
        if (CommonUtil.getEnum(indentCreateForm.getIndentType(), IndentTypeEnum.class) == null) {
            //订单类型不匹配
            log.error("[发布订单]订单类型不正确，indentType={}", indentCreateForm.getIndentType());
            throw new SubstituteException("订单类型不正确，indentType=".concat(indentCreateForm.getIndentType()),
                    ResultEnum.PARAM_ERROR.getCode());
        } else if (indentCreateForm.getIndentType().equals(IndentTypeEnum.HELP_BUY.toString())) {
            //如果是帮我购,以下字段必填
            checkObjs.add(indentCreateForm.getPublisherName());
            checkObjs.add(indentCreateForm.getTakeGoodAddressId());
            checkObjs.add(indentCreateForm.getGoodPrice());
        } else if (indentCreateForm.getIndentType().equals(IndentTypeEnum.HELP_SEND.toString())) {
            //如果是帮我递,以下字段必填
            checkObjs.add(indentCreateForm.getPublisherName());
            checkObjs.add(indentCreateForm.getTakeGoodAddressId());
            checkObjs.add(indentCreateForm.getShippingAddressId());
            checkObjs.add(indentCreateForm.getCompanyName());
            checkObjs.add(indentCreateForm.getPickupCode());
        }
        //如果是随意帮,所有非必填字段都可不填
        //验证必填参数是否已填
        for (Object obj : checkObjs) {
            if (obj == null) {
                log.error("[发布订单]必填参数为空，indentCreateForm={}", indentCreateForm);
                throw new SubstituteException(ResultEnum.PARAM_NULL_ERROR);
            }
        }
    }

    /**
     * 发布帮我购/递/随意帮接口
     */
    @PostMapping
    public ResponseEntity publishNewIndent(@RequestBody @Valid IndentCreateForm indentCreateForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            //表单校验有误
            log.error("[发布订单]参数不正确，indentCreateForm={}", indentCreateForm);
            String msg = bindingResult.getFieldError() == null ? ResultEnum.PARAM_ERROR.getMsg()
                    : bindingResult.getFieldError().getDefaultMessage();
            throw new SubstituteException(msg, ResultEnum.PARAM_ERROR.getCode());
        }
        //验证参数填写是否满足要求
        checkIndentCreateForm(indentCreateForm);
        Indent newIndent = new Indent();
        BeanUtils.copyProperties(indentCreateForm, newIndent);
        newIndent.setIndentType(CommonUtil.getEnum(indentCreateForm.getIndentType(), IndentTypeEnum.class));
        newIndent.setRequireGender(CommonUtil.getEnum(indentCreateForm.getRequireGender(), GenderEnum.class));
        //下单
        indentService.save(newIndent);
        return ResultUtil.success();
    }

    /**
     * 广场订单列表接口，获取待接单的订单liebia
     *
     * @param sort 排序方式，默认值为0，默认：0，时间：10，价格:20
     * @param key  搜索关键字，按送达地址进行模糊匹配
     * @return 订单列表
     */
    @GetMapping("/list")
    public ResponseEntity getIndentList(@RequestParam(defaultValue = "0") Integer sort, @RequestParam(required = false) String key) {
        return ResultUtil.success(indentService.getWaitInFuzzyMatching(sort, key));
    }


    @GetMapping(value = "/detail/{indentId}/{userId}")
    public ResponseEntity getIndentInfo(@PathVariable Integer indentId, @PathVariable String userId) {
        return ResultUtil.success(indentService.getIndentDetail(indentId, userId));
    }

    @GetMapping(value = "/price/{indentId}/{userId}")
    public ResponseEntity addIndentPrice(@PathVariable Integer indentId, @PathVariable String userId) {
        indentService.addIndentPrice(indentId, userId);
        return ResultUtil.success();
    }

    @GetMapping("/performer/{userId}")
    public ResponseEntity getUserPersonalPerformedIndentList(@PathVariable @NotNull String userId) {
        List<Indent> indents = indentService.getUserPerformedIndent(userId);
        return ResultUtil.success(indents);
    }

    @GetMapping("/publisher/{userId}")
    public ResponseEntity getUserPersonalPublishedIndentList(@PathVariable @NotNull String userId) {
        List<Indent> indents = indentService.getUserPublishedIndent(userId);
        return ResultUtil.success(indents);
    }

}
