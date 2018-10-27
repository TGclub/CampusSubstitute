package com.wizzstudio.substitute.service;


import com.wizzstudio.substitute.pojo.Address;
import com.wizzstudio.substitute.pojo.School;

import java.util.List;

public interface AddressService {
    /**
     * 添加用户常用地址
     * @param userId
     * @param address
     */
    void addUsualAddress(String userId, String address);
    //List<Address> searchAddress();

    /**
     * 获取所有常用地址信息接口
     * @param userId
     * @return
     */
    List<Address> getUsualAddress(String userId);

    /**
     * 通过关键字获取学校
     * @param school 学校名称
     * @return
     */
    List<School> getSchoolInFuzzyMatching(String school);


}
