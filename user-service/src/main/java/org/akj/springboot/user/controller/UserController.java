package org.akj.springboot.user.controller;

import org.akj.springboot.common.domain.Result;
import org.akj.springboot.user.domain.entity.User;
import org.akj.springboot.user.mapper.UserMapstructMapper;
import org.akj.springboot.user.service.UserService;
import org.akj.springboot.user.vo.UserRegistrationRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/v1/users")
public class UserController {

	private final UserService userService;

	private final UserMapstructMapper userMapstructMapper;

	public UserController(UserService userService, UserMapstructMapper userMapstructMapper) {
		this.userService = userService;
		this.userMapstructMapper = userMapstructMapper;
	}

	@PostMapping
	@PreAuthorize("hasAuthority('user:add')")
	public Result register(@Valid @RequestBody UserRegistrationRequest userRegistrationRequest) {
		User user = userMapstructMapper.convert(userRegistrationRequest);
		user = userService.add(user);

		return Result.success(userMapstructMapper.convert(user));
	}

	@GetMapping
	@PreAuthorize("hasAuthority('user:list')")
	public Result list() {
		return Result.success(userService.findAll());
	}

	@GetMapping("/userinfo")
	public Result userInfo(Principal principal) {
		return Result.success(userService.findUserByUsername(principal.getName()));
	}
}
