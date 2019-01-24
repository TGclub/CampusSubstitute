package com.wizzstudio.substitute.dao;

import com.wizzstudio.substitute.domain.Config;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created By Cx On 2019/1/24 9:53
 */
@Repository
public interface ConfigDao extends JpaRepository<Config,Integer> {
}
