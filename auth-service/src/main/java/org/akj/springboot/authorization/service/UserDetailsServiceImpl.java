package org.akj.springboot.authorization.service;

import org.akj.springboot.authorization.domain.iam.entity.Authority;
import org.akj.springboot.authorization.domain.iam.entity.User;
import org.akj.springboot.authorization.exception.ErrorCodeMap;
import org.akj.springboot.authorization.repository.AuthorityRepository;
import org.akj.springboot.authorization.repository.GroupRepository;
import org.akj.springboot.authorization.repository.UserRepository;
import org.akj.springboot.common.exception.BusinessException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	private final UserRepository userRepository;

	private final AuthorityRepository authorityRepository;

	private final GroupRepository groupRepository;

	public UserDetailsServiceImpl(UserRepository userRepository, AuthorityRepository authorityRepository,
								  GroupRepository groupRepository) {
		this.userRepository = userRepository;
		this.authorityRepository = authorityRepository;
		this.groupRepository = groupRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String userName) {
		User user = userRepository.findByUserName(userName);
		if (null == user) {
			throw new BusinessException(ErrorCodeMap.USER_NOT_EXIST.code(), ErrorCodeMap.USER_NOT_EXIST.message());
		}

		// authorities
		List<Authority> authorities = authorityRepository.findAllAuthoritiesByUserId(user.getId());
		List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
		if (!CollectionUtils.isEmpty(authorities)) {
			grantedAuthorities =
					authorities.stream().map(authority -> new SimpleGrantedAuthority(authority.getName())).collect(Collectors.toList());
		}

		// roles
		List<SimpleGrantedAuthority> roles = groupRepository.findAllGroupsByUserId(user.getId()).stream()
				.map(group -> {
					String roleName = null;
					String groupName = group.getName();
					if (groupName.contains("-")) {
						roleName = groupName.substring(groupName.lastIndexOf("-") + 1).toUpperCase(Locale.ROOT);
					}

					if (groupName.contains("_")) {
						roleName = groupName.substring(groupName.lastIndexOf("_") + 1).toUpperCase(Locale.ROOT);
					}

					if (groupName.contains(" ")) {
						roleName = groupName.substring(groupName.lastIndexOf(" ") + 1).toUpperCase(Locale.ROOT);
					}

					return new SimpleGrantedAuthority("ROLE_" + roleName);
				})
				.toList();

		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
				Optional.of(Stream.concat(authorities.stream(), roles.stream()).toList()).orElse(Collections.EMPTY_LIST));
	}
}
