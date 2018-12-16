package com.wizzstudio.substitute.service;


import com.wizzstudio.substitute.domain.Address;
import com.wizzstudio.substitute.domain.School;
import com.wizzstudio.substitute.dto.AddressDTO;

import java.util.List;

public interface AddressService {
    /**
     * 添加用户常用地址
     * @param userId
     * @param addressDTO
     */
    void addUsualAddress(String userId, AddressDTO addressDTO);
    //List<Address> searchAddress();

    /**
     * 通过id获取address信息
     */
    Address getById(Integer addressId);

    /**
     * 获取所有常用地址信息接口
     * @param userId
     * @return
     */
    List<Address> getUsualAddress(String userId);

    List<Address> getAllByAddress(String address);

    /**
     * 修改地址
     * @param addressId
     * @param userId
     * @param modifyAddressDTO
     */
    void modifyAddress(Integer addressId, String userId, AddressDTO modifyAddressDTO);

    /**
     * 删除地址
     * @param addressId
     */
    void deleteAddress(Integer addressId, String userId);

}
