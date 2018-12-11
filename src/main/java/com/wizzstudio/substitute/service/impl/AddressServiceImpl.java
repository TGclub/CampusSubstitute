package com.wizzstudio.substitute.service.impl;

import com.wizzstudio.substitute.dao.AddressDao;
import com.wizzstudio.substitute.domain.Address;
import com.wizzstudio.substitute.domain.School;
import com.wizzstudio.substitute.domain.User;
import com.wizzstudio.substitute.dto.AddressDTO;
import com.wizzstudio.substitute.service.AddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AddressServiceImpl extends BaseService implements AddressService {

    @Autowired
    protected AddressDao addressDao;

    @Override
    public void addUsualAddress(String userId, AddressDTO addressDTO) {
        User user = userDao.findUserById(userId);
        if (!addressDTO.getUserId().equals(userId)) throw new AccessDeniedException("Access Denied");
        if (user != null) {
            Address address = new Address();
            address.setUserId(userId);
            BeanUtils.copyProperties(addressDTO, address);
            addressDao.save(address);
        }
    }

    @Override
    public Address getById(Integer addressId) {
        if (addressId == null) return null;
        return addressDao.findById(addressId).orElse(null);
    }

    @Override
    public List<Address> getUsualAddress(String userId) {
        return addressDao.findAddressByUserIdAndIsDeletedIsFalse(userId);
    }

    @Override
    public List<Address> getAllByAddress(String address) {
        if (address == null) address = "";
        return addressDao.findAllByAddressLikeAndIsDeletedIsFalse("%".concat(address).concat("%"));
    }

    /**
     * 采取简单粗暴的模糊匹配
     *
     * @param school 学校名称
     * @return
     */
    @Override
    public List<School> getSchoolInFuzzyMatching(String school) {
        return schoolDao.findBySchoolNameLike("%" + school + "%");
    }

    @Override
    public void modifyAddress(Integer addressId, String userId, AddressDTO modifyAddressDTO) {
        Address address = addressDao.findAddressById(addressId);
        if (address == null) return;
        if (!address.getUserId().equals(userId)) throw new AccessDeniedException("Access Denied");
        if (modifyAddressDTO.getAddress() != null) address.setAddress(modifyAddressDTO.getAddress());
        if (modifyAddressDTO.getPhone() != null) address.setPhone(modifyAddressDTO.getPhone());
        if (modifyAddressDTO.getUserName() != null) address.setUserName(modifyAddressDTO.getUserName());
        addressDao.save(address);
    }

    @Override
    public void deleteAddress(Integer addressId, String userId) {
        Address address = addressDao.findAddressById(addressId);
        if (address != null) {
            if (!address.getUserId().equals(userId)) throw new AccessDeniedException("Access Denied");
            if (userId.equals(address.getUserId())) {
                address.setIsDeleted(true);
                addressDao.save(address);
            }
        }
    }
}
