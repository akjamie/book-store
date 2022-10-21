package org.akj.springboot.user.vo;

import lombok.Data;
import org.akj.springboot.user.domain.entity.Authority;
import org.akj.springboot.user.domain.entity.Group;
import org.akj.springboot.user.domain.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Data
public class UserDetails {
	private static final String ROLE_PREFIX = "Role_";
	private User principal;
	List<SimpleGrantedAuthority> authorities = new ArrayList<>();

	public static UserDetails valueOf(User user, List<Group> roles, List<Authority> authorities) {
		UserDetails userDetails = new UserDetails();
		userDetails.setPrincipal(user);

		userDetails.getAuthorities().addAll(roles.stream().map(role -> new SimpleGrantedAuthority(ROLE_PREFIX + role.getName())).toList());
		userDetails.getAuthorities().addAll(authorities.stream().map(authority -> new SimpleGrantedAuthority(authority.getName())).toList());

		return userDetails;
	}

}
