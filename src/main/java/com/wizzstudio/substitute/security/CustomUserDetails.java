package com.wizzstudio.substitute.security;

import com.wizzstudio.substitute.domain.AdminInfo;
import com.wizzstudio.substitute.enums.Role;
import com.wizzstudio.substitute.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.wizzstudio.substitute.enums.Role.ROLE_ADMIN_1;
import static com.wizzstudio.substitute.enums.Role.ROLE_ADMIN_2;
import static com.wizzstudio.substitute.enums.Role.ROLE_USER;

/**
 * Created by Kikyou on 18-11-9
 */
public class CustomUserDetails implements UserDetails {

    private String userId;
    private Long phone;
    private String openId;
    private Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails() {
    }

    public CustomUserDetails(String userId, Long phone, String openId, Collection<? extends GrantedAuthority> authorities) {
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

    public static CustomUserDetails create(User user) {
        return new CustomUserDetails(
                user.getId(),
                user.getPhone(),
                user.getOpenid(),
                mapTpGrantedAuthority(user)
        );
    }

    public static CustomUserDetails create(AdminInfo admin) {
        return new CustomUserDetails(admin.getAdminId().toString(),
                admin.getAdminPhone(),
                admin.getAdminPass(),
                mapTpGrantedAuthority(admin));
    }

    private static <T> List<GrantedAuthority> mapTpGrantedAuthority(T user) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        if (user instanceof User) {
            grantedAuthorities.add(new SimpleGrantedAuthority(String.valueOf(ROLE_USER)));
            return grantedAuthorities;
        }
        if (user instanceof AdminInfo) {
            switch (((AdminInfo) user).getAdminRole()) {
                case ROLE_ADMIN_1:
                    grantedAuthorities.add(new SimpleGrantedAuthority(String.valueOf(ROLE_ADMIN_1)));
                case ROLE_ADMIN_2:
                    grantedAuthorities.add(new SimpleGrantedAuthority(String.valueOf(ROLE_ADMIN_2)));
                case ROLE_USER:
                    grantedAuthorities.add(new SimpleGrantedAuthority(String.valueOf(ROLE_USER)));
                default:
                    break;
            }
        }
        return grantedAuthorities;
    }
}