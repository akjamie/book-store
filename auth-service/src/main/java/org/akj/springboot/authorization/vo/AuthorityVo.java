package org.akj.springboot.authorization.vo;

import lombok.Data;

import jakarta.validation.constraints.NotEmpty;

@Data
public class AuthorityVo {
    @NotEmpty
    private String name;
    private String description;
}
