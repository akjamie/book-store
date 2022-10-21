package org.akj.springboot.user.client.vo;

import lombok.Data;

@Data
public class UserVo {
	private Long id;

	private String userName;

	private String aliasName;

	private String phoneNumber;

	private String email;

	private String status;
}
