package com.wizzstudio.substitute.dao;

import com.wizzstudio.substitute.pojo.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressDao extends JpaRepository<Address, Integer> {
    Address findAddressById(Integer id);
    List<Address> findAddressByUserId(String userId);
}
