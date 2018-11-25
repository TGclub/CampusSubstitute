package com.wizzstudio.substitute.dao;

import com.wizzstudio.substitute.domain.WithdrawRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created By Cx On 2018/11/20 0:22
 */
@Repository
public interface WithdrawRequestDao extends JpaRepository<WithdrawRequest, Integer> {
    WithdrawRequest findByWithdrawId(int id);

    List<WithdrawRequest> findAllByIsSolved(boolean isSolved);
}
