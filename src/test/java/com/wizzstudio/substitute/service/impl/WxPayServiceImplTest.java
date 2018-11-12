package com.wizzstudio.substitute.service.impl;

import com.wizzstudio.substitute.dto.wx.WxPrePayInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;

/**
 * Created By Cx On 2018/11/6 20:48
 */
@SpringBootTest
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
public class WxPayServiceImplTest {

    @Autowired
    WxPayServiceImpl payService;

    @Test
    public void getSign() {
        WxPrePayInfo wxPrePayInfo = new WxPrePayInfo();
        wxPrePayInfo.setAppid("wxd930ea5d5a258f4f");
        wxPrePayInfo.setBody("test");
        wxPrePayInfo.setDevice_info("1000");
        wxPrePayInfo.setMch_id("10000100");
        wxPrePayInfo.setNonce_str("ibuaiVcKdpRxkhJA");
        System.out.println("hello world!");
    }
}