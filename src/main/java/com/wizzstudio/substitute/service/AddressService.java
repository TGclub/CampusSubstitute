package com.wizzstudio.substitute.service;


import com.wizzstudio.substitute.pojo.Address;
import com.wizzstudio.substitute.pojo.School;

import java.util.List;

public interface AddressService {
    void addUsualAddress(String userId);
    //List<Address> searchAddress();
    List<Address> getUsualAddress(String userId);
    List<School> getSchoolInFuzzyMatching(String school);


}
