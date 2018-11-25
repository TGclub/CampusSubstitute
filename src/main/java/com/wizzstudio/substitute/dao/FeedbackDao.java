package com.wizzstudio.substitute.dao;

import com.wizzstudio.substitute.domain.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created By Cx On 2018/11/20 0:20
 */
@Repository
public interface FeedbackDao extends JpaRepository<Feedback, Integer> {
    Feedback findByFeedbackId(int id);

    List<Feedback> findByIsRead(Boolean isRead);

}
