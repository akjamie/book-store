package org.akj.springboot.user.client;

import org.akj.springboot.user.client.vo.UserVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(contextId = "user-service-client",
		name = "user-service",
		url = "${service.user.url:}",
		path = "${service.user.context-path:/user-service}")
public interface UserServiceClient {

	@GetMapping("/v1/users/userinfo")
	UserVo userinfo();
}
