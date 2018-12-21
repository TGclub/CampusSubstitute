package com.wizzstudio.substitute.controller;

import com.wizzstudio.substitute.domain.Feedback;
import com.wizzstudio.substitute.enums.ResultEnum;
import com.wizzstudio.substitute.exception.SubstituteException;
import com.wizzstudio.substitute.service.FeedbackService;
import com.wizzstudio.substitute.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 反馈有关接口
 * Created By Cx On 2018/12/21 23:45
 */
@RestController
@RequestMapping("/feedback")
@Slf4j
public class FeedbackController {

    @Autowired
    FeedbackService feedbackService;

    @PostMapping
    public ResponseEntity create(@RequestBody Feedback feedback, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            String msg = bindingResult.getFieldError() == null ? ResultEnum.PARAM_ERROR.getMsg()
                    : bindingResult.getFieldError().getDefaultMessage();
            log.error("[创建反馈]创建失败，feedback={}",feedback);
            throw new SubstituteException(msg, ResultEnum.PARAM_ERROR.getCode());
        }
        feedback.setIsRead(false);
        feedbackService.create(feedback);
        return ResultUtil.success();
    }
}
