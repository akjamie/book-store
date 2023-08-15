package org.akj.springboot.authorization.vo;

import lombok.Data;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

@Data
public class GrantUserAuthorityRequest {
    @Size(min = 1)
    @NotNull
    private List<String> authorities;
}
