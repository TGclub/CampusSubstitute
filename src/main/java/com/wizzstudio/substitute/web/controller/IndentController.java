package com.wizzstudio.substitute.web.controller;

import com.wizzstudio.substitute.constants.Constants;
import com.wizzstudio.substitute.dto.IndentDTO;
import com.wizzstudio.substitute.dto.ResultDTO;
import com.wizzstudio.substitute.exception.InvalidMessageException;
import com.wizzstudio.substitute.pojo.Indent;
import com.wizzstudio.substitute.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/indent")
public class IndentController extends BaseController {

    /**
     * 发布帮我购、递接口
     *
     * @param publisherId
     * @param indentDTO
     * @return
     */
    @RequestMapping(value = "/{publisherId}", method = RequestMethod.POST)
    public ResponseEntity publishNewIndent(@PathVariable String publisherId, @RequestBody @Valid IndentDTO indentDTO,
                                           BindingResult result) {
        if (result.hasErrors()) {
            throw new InvalidMessageException();
        }
        Indent newIndent = new Indent();
        BeanUtils.copyProperties(indentDTO, newIndent);
        newIndent.setPublisherId(publisherId);
        indentService.publishedNewIndent(newIndent);
        return new ResponseEntity<ResultDTO>(new ResultDTO<>(Constants.REQUEST_SUCCEED, Constants.QUERY_SUCCESSFULLY,
                null), HttpStatus.OK);
    }

    @RequestMapping(value = "publisher/{userId}", method = RequestMethod.GET)
    public ResponseEntity getUserPublishedIndents(@PathVariable String userId) {
        return ResultUtil.success(indentService.getUserPublishedIndent(userId));
    }

    @RequestMapping(value = "performer/{userId}", method = RequestMethod.GET)
    public ResponseEntity getUserPerformerIndents(@PathVariable String userId){
        return ResultUtil.success(indentService.getUserPerformedIndent(userId));
    }

    /**
     * to be continued
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity getIndentsList() {
        return null;
    }

    @RequestMapping(value = "/detail/{indentId}/{userId}")
    public ResponseEntity getIndentInfo(@PathVariable Integer indentId, String userId){
        return ResultUtil.success(indentService.getSpecificIndentInfo(indentId));
    }

    @RequestMapping(value = "/price/{indentId}/{userId}")
    public ResponseEntity addIndentPrice(@PathVariable Integer indentId, @PathVariable String userId) {
        indentService.addIndentPrice(indentId);
        return ResultUtil.success();
    }



}
