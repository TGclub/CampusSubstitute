package com.wizzstudio.substitute.service.impl;

import com.wizzstudio.substitute.domain.Address;
import com.wizzstudio.substitute.domain.School;
import com.wizzstudio.substitute.domain.User;
import com.wizzstudio.substitute.service.AddressService;
import com.wizzstudio.substitute.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AddressServiceImpl extends BaseService implements AddressService {

    @Override
    public void addUsualAddress(String userId, String address) {
        User user = userDao.findUserById(userId);
        if (user != null) {
            addressDao.save(new Address(address, userId));
        }
    }

    @Override
    public List<Address> getUsualAddress(String userId) {
        return addressDao.findAddressByUserId(userId);
    }

    /**
     * 采取简单粗暴的模糊匹配
     * @param school 学校名称
     * @return
     */
    @Override
    public List<School> getSchoolInFuzzyMatching(String school) {
        return schoolDao.findBySchoolNameLike("%" + school + "%");
    }
}
