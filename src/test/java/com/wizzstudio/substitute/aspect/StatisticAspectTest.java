package com.wizzstudio.substitute.aspect;

import com.wizzstudio.substitute.controller.LoginController;
import com.wizzstudio.substitute.dto.wx.WxInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Kikyou on 18-12-15
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class StatisticAspectTest {
    @Autowired
    LoginController loginController;

    @Test
    public void testLoginCount(){
        loginController.login(new WxInfo(), new MockHttpServletResponse());
    }
}
