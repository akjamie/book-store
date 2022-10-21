package org.akj.springboot.test.controller;

import org.akj.springboot.common.domain.Result;
import org.akj.springboot.user.client.UserServiceClient;
import org.akj.springboot.user.client.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/hello")
public class HelloWorldController {

	@Autowired
	UserServiceClient userServiceClient;

	@GetMapping
	public Result hello() {
		UserVo userinfo = userServiceClient.userinfo();
		return Result.success(userinfo);
	}
}
