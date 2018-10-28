package com.wizzstudio.substitute.web.controller;

import com.wizzstudio.substitute.constants.Constants;
import com.wizzstudio.substitute.dto.IndentDTO;
import com.wizzstudio.substitute.dto.ResultDTO;
import com.wizzstudio.substitute.exception.InvalidMessageException;
import com.wizzstudio.substitute.pojo.Indent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/indent")
public class IndentController extends BaseController {

    /**
     * 发布帮我购、递接口
     * @param publisherId
     * @param indentDTO
     * @return
     */
    @RequestMapping(value = "/{publisherId}", method = RequestMethod.POST)
    public ResponseEntity publishNewIndent(@PathVariable String publisherId, @RequestBody @Valid IndentDTO indentDTO, BindingResult result) {
        if (result.hasErrors()) {
            throw new InvalidMessageException();
        }
        Indent newIndent = new Indent();
        BeanUtils.copyProperties(indentDTO, newIndent);
        indentService.publishedNewIndent(newIndent);
        return new ResponseEntity<ResultDTO>(new ResultDTO<>(Constants.REQUEST_SUCCEED, Constants.QUERY_SUCCESSFULLY, null), HttpStatus.OK);
    }

}
