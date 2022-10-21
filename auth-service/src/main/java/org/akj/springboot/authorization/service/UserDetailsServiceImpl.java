package org.akj.springboot.authorization.service;

import org.akj.springboot.authorization.domain.iam.entity.Authority;
import org.akj.springboot.authorization.domain.iam.entity.User;
import org.akj.springboot.authorization.exception.ErrorCodeMap;
import org.akj.springboot.authorization.repository.AuthorityRepository;
import org.akj.springboot.authorization.repository.UserRepository;
import org.akj.springboot.common.exception.BusinessException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    private final AuthorityRepository authorityRepository;

    public UserDetailsServiceImpl(UserRepository userRepository, AuthorityRepository authorityRepository) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) {
        User user = userRepository.findByUserName(userName);
        if (null == user) {
            throw new BusinessException(ErrorCodeMap.USER_NOT_EXIST.code(), ErrorCodeMap.USER_NOT_EXIST.message());
        }

        List<Authority> authorities = authorityRepository.findAllAuthoritiesByUserId(user.getId());

        List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
        if (!CollectionUtils.isEmpty(authorities)) {
            grantedAuthorities =
                    authorities.stream().map(authority -> new SimpleGrantedAuthority(authority.getName())).collect(Collectors.toList());
        }

        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
                Optional.ofNullable(grantedAuthorities).orElse(Collections.EMPTY_LIST));
    }
}
