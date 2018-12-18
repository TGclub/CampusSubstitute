package com.wizzstudio.substitute.controller;

import com.wizzstudio.substitute.enums.GenderEnum;
import com.wizzstudio.substitute.enums.indent.IndentTypeEnum;
import com.wizzstudio.substitute.enums.ResultEnum;
import com.wizzstudio.substitute.exception.SubstituteException;
import com.wizzstudio.substitute.form.IndentCreateForm;
import com.wizzstudio.substitute.domain.Indent;
import com.wizzstudio.substitute.form.IndentUserForm;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * 这里验证的是必填参数是否已填，填写格式是否正确，service层验证的是参数对应的数据是否存在，是否属于该用户
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
            checkObjs.add(indentCreateForm.getShippingAddressId());
            checkObjs.add(indentCreateForm.getGoodPrice());
        } else if (indentCreateForm.getIndentType().equals(IndentTypeEnum.HELP_SEND.toString())) {
            //如果是帮我递,以下字段必填
            checkObjs.add(indentCreateForm.getTakeGoodAddress());
            checkObjs.add(indentCreateForm.getShippingAddressId());
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
     * 若用户余额不足，则抛异常
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
        Map<String,Integer> ans = new HashMap<>();
        ans.put("indentId",indentService.create(newIndent));
        return ResultUtil.success(ans);
    }

    /**
     * 增加赏金接口，若用户余额不足，则抛出异常
     */
    @PostMapping("/price")
    public ResponseEntity addIndentPrice(@RequestBody @Valid IndentUserForm indentUserForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            //表单校验有误
            log.error("[增加订单赏金]参数不正确，indentCreateForm={}", indentUserForm);
            String msg = bindingResult.getFieldError() == null ? ResultEnum.PARAM_ERROR.getMsg()
                    : bindingResult.getFieldError().getDefaultMessage();
            throw new SubstituteException(msg, ResultEnum.PARAM_ERROR.getCode());
        }
        indentService.addIndentPrice(indentUserForm.getIndentId(), indentUserForm.getUserId());
        return ResultUtil.success();
    }

    /**
     * 用户接单接口
     */
    @PostMapping("/take")
    public ResponseEntity takeIndent(@RequestBody @Valid IndentUserForm indentUserForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            //表单校验有误
            log.error("[接单]参数不正确，indentCreateForm={}", indentUserForm);
            String msg = bindingResult.getFieldError() == null ? ResultEnum.PARAM_ERROR.getMsg()
                    : bindingResult.getFieldError().getDefaultMessage();
            throw new SubstituteException(msg, ResultEnum.PARAM_ERROR.getCode());
        }
        if (indentUserForm.getFormId() == null){
            log.error("[接单]参数不正确，indentCreateForm={}", indentUserForm);
            throw new SubstituteException("formId为空");
        }
        indentService.takeIndent(indentUserForm.getIndentId(), indentUserForm.getUserId(), indentUserForm.getFormId());
        return ResultUtil.success();
    }

    /**
     * 接单人送达接口
     */
    @PostMapping("/arrived")
    public ResponseEntity arrivedIndent(@RequestBody @Valid IndentUserForm indentUserForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            //表单校验有误
            log.error("[送达订单]参数不正确，indentCreateForm={}", indentUserForm);
            String msg = bindingResult.getFieldError() == null ? ResultEnum.PARAM_ERROR.getMsg()
                    : bindingResult.getFieldError().getDefaultMessage();
            throw new SubstituteException(msg, ResultEnum.PARAM_ERROR.getCode());
        }
        if (indentUserForm.getFormId() == null){
            log.error("[接单]参数不正确，indentCreateForm={}", indentUserForm);
            throw new SubstituteException("formId为空");
        }
        indentService.arrivedIndent(indentUserForm.getIndentId(), indentUserForm.getUserId(), indentUserForm.getFormId());
        return ResultUtil.success();
    }

    /**
     * 下单人完结订单 ： 开始进行分钱
     */
    @PostMapping("/finished")
    public ResponseEntity finishedIndent(@RequestBody @Valid IndentUserForm indentUserForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            //表单校验有误
            log.error("[完结订单]参数不正确，indentCreateForm={}", indentUserForm);
            String msg = bindingResult.getFieldError() == null ? ResultEnum.PARAM_ERROR.getMsg()
                    : bindingResult.getFieldError().getDefaultMessage();
            throw new SubstituteException(msg, ResultEnum.PARAM_ERROR.getCode());
        }
        indentService.finishedIndent(indentUserForm.getIndentId(), indentUserForm.getUserId(), indentUserForm.getFormId());
        return ResultUtil.success();
    }

    /**
     * 下单人取消订单 ： 退钱
     */
    @DeleteMapping("/canceled")
    public ResponseEntity canceledIndent(@RequestBody @Valid IndentUserForm indentUserForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            //表单校验有误
            log.error("[取消订单]参数不正确，indentCreateForm={}", indentUserForm);
            String msg = bindingResult.getFieldError() == null ? ResultEnum.PARAM_ERROR.getMsg()
                    : bindingResult.getFieldError().getDefaultMessage();
            throw new SubstituteException(msg, ResultEnum.PARAM_ERROR.getCode());
        }
        indentService.canceledIndent(indentUserForm.getIndentId(), indentUserForm.getUserId(), indentUserForm.getFormId());
        return ResultUtil.success();
    }

    /**
     * 广场订单列表接口，获取待接单的订单列表，只能看到同性别的订单
     *
     * @param sort 排序方式，默认值为0，默认：0，时间：10，价格:20
     * @param sexType 性别类型，MALE 、 FEMALE ，未知或其他字段会报错
     * @return 订单列表
     */
    @GetMapping("/list")
    public ResponseEntity getIndentList(@RequestParam(defaultValue = "0") Integer sort, GenderEnum sexType) {
        return ResultUtil.success(indentService.getWait(sort,sexType));
    }

    /**
     * 获取某用户已接订单列表
     */
    @GetMapping("/performer/{userId}")
    public ResponseEntity getUserPersonalPerformedIndentList(@PathVariable @NotNull String userId) {
        return ResultUtil.success(indentService.getUserPerformedIndent(userId));
    }

    /**
     * 获取某用户已发布订单列表
     */
    @GetMapping("/publisher/{userId}")
    public ResponseEntity getUserPersonalPublishedIndentList(@PathVariable @NotNull String userId) {
        return ResultUtil.success(indentService.getUserPublishedIndent(userId));
    }


    /**
     * 获取订单详情，若请求用户不是下单人 或 接单人，则隐藏私密信息
     */
    @GetMapping(value = "/detail")
    public ResponseEntity getIndentInfo(Integer indentId, String userId) {
        return ResultUtil.success(indentService.getIndentDetail(indentId,userId));
    }

    @GetMapping("/test")
    public ResponseEntity test() {
        throw new SubstituteException(ResultEnum.PARAM_ERROR);
    }

}
