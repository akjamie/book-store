package org.akj.springboot.user.service;

import io.micrometer.core.instrument.util.StringUtils;
import org.akj.springboot.user.domain.entity.Authority;
import org.akj.springboot.user.domain.entity.Group;
import org.akj.springboot.user.domain.entity.User;
import org.akj.springboot.user.repository.AuthorityRepository;
import org.akj.springboot.user.repository.GroupRepository;
import org.akj.springboot.user.repository.UserRepository;
import org.akj.springboot.user.vo.UserDetails;
import org.springframework.data.domain.Example;
import org.springframework.lang.NonNull;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class UserService {
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	private final GroupRepository groupRepository;

	private final AuthorityRepository authorityRepository;


	public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, GroupRepository groupRepository, AuthorityRepository authorityRepository) {
		this.userRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.groupRepository = groupRepository;
		this.authorityRepository = authorityRepository;
	}

	public User add(@NonNull User user) {
		if (StringUtils.isNotBlank(user.getPassword())) {
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		}

		return userRepository.save(user);
	}

	public Optional<User> loadUserById(long userId) {
		return userRepository.findById(userId);
	}

	public UserDetails loadUserByEmail(String email) {
		User userSample = new User();
		userSample.setEmail(email);

		User user = userRepository.findOne(Example.of(userSample)).orElse(null);
		if (user == null) {
			return null;
		}

		return wrap(user);
	}

	public UserDetails loadUserByPhoneNumber(String phoneNumber) {
		User userSample = new User();
		userSample.setPhoneNumber(phoneNumber);

		User user = userRepository.findOne(Example.of(userSample)).orElse(null);
		if (user == null) {
			return null;
		}

		return wrap(user);
	}

	public UserDetails loadUserByUsername(String userName) {
		User userSample = new User();
		userSample.setUserName(userName);

		User user = userRepository.findOne(Example.of(userSample)).orElse(null);
		if (user == null) {
			return null;
		}

		return wrap(user);
	}

	public User findUserByUsername(String userName) {
		User userSample = new User();
		userSample.setUserName(userName);

		return userRepository.findOne(Example.of(userSample)).orElse(null);
	}

	private UserDetails wrap(@NonNull User user) {
		List<Group> groups = groupRepository.findAllByUserId(user.getId());
		List<Authority> authorities = authorityRepository.findAllAuthoritiesByUserId(user.getId());

		return UserDetails.valueOf(user, groups, authorities);
	}

	public List<User> findAll() {
		return userRepository.findAll();
	}
}
