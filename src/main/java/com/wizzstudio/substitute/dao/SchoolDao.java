package com.wizzstudio.substitute.dao;

import com.wizzstudio.substitute.domain.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchoolDao extends JpaRepository<School, Integer> {
    List<School> findBySchoolNameLike(String schoolName);

    School findSchoolById(Integer id);
}
