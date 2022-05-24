package com.senla.course.security.service;

import com.senla.course.announcementPlatform.model.User;
import com.senla.course.security.dao.UserSecurityDao;
import com.senla.course.security.model.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserDetailService implements UserDetailsService {

    private final UserSecurityDao userSecurityDao;

    public UserDetailService(UserSecurityDao userSecurityDao) {
        this.userSecurityDao = userSecurityDao;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        User user = userSecurityDao.getByUserName(userName);

        return new org.springframework.security.core.userdetails.User(
                user.getLogin(),
                user.getPassword(),
                true,
                true,
                true,
                true,
                getAuthorities(user));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(User user) {
        Set<Role> roles = user.getRoles();
        Set<GrantedAuthority> grantedAuthorityRoles = new HashSet<GrantedAuthority>();
        for (Role role: roles) {
            grantedAuthorityRoles.add(new SimpleGrantedAuthority("ROLE_" + role.getRole()));
        }
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(grantedAuthorityRoles);
        return grantedAuthorities;
    }
}