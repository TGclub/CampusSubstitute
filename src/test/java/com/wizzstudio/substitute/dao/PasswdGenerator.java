package com.wizzstudio.substitute.dao;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Created by Kikyou on 18-12-16
 */
public class PasswdGenerator {

    public static void main(String args[]) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("testtest"));
    }
}
