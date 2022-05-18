package com.senla.course.security.service;

import com.senla.course.security.model.UserSecurity;
import com.senla.course.security.repository.MyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
public class UserDetail implements UserDetailsService {

    @Autowired
    public MyRepository myRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        UserSecurity user = myRepository.getByUserName(userName);

        return new org.springframework.security.core.userdetails.User(
                user.getLogin(),
                user.getPassword(),
                true,
                true,
                true,
                true,
                getAuthorities(user));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(UserSecurity user) {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRoles().get(0)));
    }
}