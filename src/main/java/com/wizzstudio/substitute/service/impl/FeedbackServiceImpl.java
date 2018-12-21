package com.wizzstudio.substitute.service.impl;

import com.wizzstudio.substitute.dao.FeedbackDao;
import com.wizzstudio.substitute.domain.Feedback;
import com.wizzstudio.substitute.service.FeedbackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created By Cx On 2018/12/21 23:46
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    FeedbackDao feedbackDao;
    @Autowired
    CommonCheckService commonCheckService;

    @Override
    public void create(Feedback feedback) {
        commonCheckService.checkUserByUserId(feedback.getUserId());
        feedbackDao.save(feedback);
    }
}
