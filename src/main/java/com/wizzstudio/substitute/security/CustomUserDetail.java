package com.wizzstudio.substitute.security;

import com.wizzstudio.substitute.enums.Role;
import com.wizzstudio.substitute.pojo.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetail implements UserDetails {

    private String userId;

    private Long phone;

    private String openId;

    private Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetail() {
    }

    public CustomUserDetail(String userId, Long phone, String openId, Collection<? extends GrantedAuthority> authorities) {
        this.userId = userId;
        this.phone = phone;
        this.openId = openId;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return openId;
    }

    @Override
    public String getUsername() {
        return userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public static CustomUserDetail create(User user) {
        return new CustomUserDetail(
                user.getId(),
                user.getPhone(),
                user.getOpenid(),
                mapTpGrantedAuthority(user)
        );
    }

    private static List<GrantedAuthority> mapTpGrantedAuthority(User user) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        switch (user.getRole()) {
            case ROLE_ADMIN_2:
                grantedAuthorities.add(new SimpleGrantedAuthority(String.valueOf(Role.ROLE_ADMIN_2)));
            case ROLE_ADMIN_1:
                grantedAuthorities.add(new SimpleGrantedAuthority(String.valueOf(Role.ROLE_ADMIN_1)));
            case ROLE_USER:
                grantedAuthorities.add(new SimpleGrantedAuthority(String.valueOf(Role.ROLE_USER)));
            default:
                break;
        }
        return grantedAuthorities;
    }
}
